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
	
	private CoursePrivateState courseState;
	private String Course;
	private String Student;
	private String[] Grade;
	
	/**
	 * Constructor
	 * @param course to add student to 
	 * @param student to add 
	 * @param grade of the student in the course (grade.size()==1)
	 */
	public ParticipatingInCourseAction(String course, String student, String[] grade) {
		setActionName("Participate In Course");
		this.Course = course;
		this.Student = student;
		this.Grade = grade;
	}

	/**
	 * register a student to a course only if there is available space
	 * and if the student has passed the prerequisite courses (grade 56 or above)
	 * the student will be added to the course private state
	 * and the student private state will add the course and grade
	 */
	@Override
	protected void start() {
		throwExceptionForInvalidActorStateType(CoursePrivateState.class);
		courseState = (CoursePrivateState)_actorState;
		
		List<Action<Boolean>> depencencies = new ArrayList();
		
		//create CheckStudentHasPrequisitesAction
		List<String> prerequisite = courseState.getPrequisites();
		Action<Boolean> checkStudentHasPrerequisites = new CheckStudentHasPrerequisitesAction(prerequisite);
		@SuppressWarnings("unchecked")
		Promise<Boolean> studentHasPrerequisitesPromise = (Promise<Boolean>) sendMessage(checkStudentHasPrerequisites, Student, new StudentPrivateState());
		
		//create callback
		depencencies.add(checkStudentHasPrerequisites);	
		then(depencencies, () -> {
			Boolean hasPrequisites = studentHasPrerequisitesPromise.get();
			if(hasPrequisites & courseHasAvailableSpace()) {
				courseState.registerStudent(Student);
				courseState.addRecord(getActionName());
				
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
