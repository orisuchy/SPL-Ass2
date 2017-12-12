package bgu.spl.a2.sim.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import bgu.spl.a2.Action;
import bgu.spl.a2.PrivateState;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

/**
 * 
	Participating In Course:
		- Behavior: This action should try to register the student in the course, if it succeeds, should add the
			course to the grades sheet of the student, and give him a grade if supplied. See the input example.
		- Actor: Must be initially submitted to the course's actor.
 */

class ParticipatingInCourseAction extends Action<Boolean> {
	/*
	 * "Action": "Participate In Course",
		"Student": "123456789",
		"Course": "SPL",
		"Grade": ["98"]
	 */
	
	private CoursePrivateState courseState;
	private String Course;
	private String Student;
	private String[] Grade;
	
	
	public ParticipatingInCourseAction(String course, String student, String[] grade) {
		setActionName("Participate In Course");
		this.Course = course;
		this.Student = student;
		this.Grade = grade;
	}

	@Override
	protected void start() {
		throwExceptionForInvalidActorStateType(CoursePrivateState.class);
		courseState = (CoursePrivateState)_actorState;
		
		List<Action<Boolean>> depencencies = new ArrayList();
		
		//create CheckStudentHasPrequisitesAction
		List<String> prequisites = courseState.getPrequisites();
		Action<Boolean> checkStudentHasPrequisites = new CheckStudentHasPrequisitesAction(prequisites);
		Promise<Boolean> studentHasPrequisitesPromise = (Promise<Boolean>) sendMessage(checkStudentHasPrequisites, Student, new StudentPrivateState());
		
		//create callback
		depencencies.add(checkStudentHasPrequisites);	
		then(depencencies, () -> {
			Boolean hasPrequisites = studentHasPrequisitesPromise.get();
			if(hasPrequisites & courseHasAvailableSpace()) {
				
				//TODO - add student to courseState AND record it
				
				
				
				//Add the course and grade info to the student
				Action<Boolean> addCourseAndGrade;
				try {
					int studentGrade = Integer.parseInt(Grade[0]);
					addCourseAndGrade = new AddStudentGradeAction(Course, studentGrade);
				}
				catch(NumberFormatException e){
					addCourseAndGrade = new AddStudentGradeAction(Course, -1);
				}
				_pool.submit(addCourseAndGrade, Student, new StudentPrivateState());
				
			}else {
				complete(false);
			}	
		});
	}
	
	
	/**
	 * checks the course private state for available spaces
	 * @return TRUE if course has available spaces, FALSE otherwise
	 */
	private boolean courseHasAvailableSpace() {
		return (courseState.getAvailableSpots()>0);
	}
}
