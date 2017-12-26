package bgu.spl.a2.sim.actions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import bgu.spl.a2.Action;
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
		super();
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
		courseState = (CoursePrivateState)getCurrentPrivateState();
		
		addFlag(courseState);
		List<Action<Boolean>> depencencies = new ArrayList<Action<Boolean>>();
		
		//create CheckStudentHasPrequisitesAction
		List<String> prerequisite = courseState.getPrequisites();
		Action<Boolean> checkStudentHasPrerequisites = new CheckStudentHasPrerequisitesAction(prerequisite);
		Promise<Boolean> studentHasPrerequisitesPromise = 
				(Promise<Boolean>) sendMessage(checkStudentHasPrerequisites, Student, new StudentPrivateState());
		
		//create callback
		depencencies.add(checkStudentHasPrerequisites);	
		then(depencencies, () -> {
			Boolean hasPrequisites = studentHasPrerequisitesPromise.get();
			if(hasPrequisites & courseHasAvailableSpace()) {
				courseState.registerStudent(Student);	
				//Add the course and grade info to the student
				Action<Boolean> addCourseAndGrade;
				try {
					int studentGrade = Integer.parseInt(Grade[0]);
					addCourseAndGrade = new AddStudentGradeAction(Course, studentGrade);
				}
				catch(NumberFormatException e){
					addCourseAndGrade = new AddStudentGradeAction(Course, -1);
				}
				
				depencencies.clear();
				depencencies.add(addCourseAndGrade);
				Promise<Boolean> courseGradeAdded = (Promise<Boolean>) sendMessage(addCourseAndGrade, Student, new StudentPrivateState());
				then(depencencies, ()->{
					removeFlag(courseState);
					complete(courseGradeAdded.get());
				});
				
			}else {
				removeFlag(courseState);
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

	/**
	 * Adds a flag to the log of the Course actor that indicates that there 
	 * is a registration in progress
	 * @param actor
	 */
	private void addFlag(CoursePrivateState actor) {
		actor.getLogger().add(progressFlag());
	}
	
	/**
	 * Removes the flag from the log of the Course actor
	 * @param actor
	 */
	private void removeFlag(CoursePrivateState actor) {
		actor.getLogger().remove(progressFlag());
	}
	
	/**
	 * Returns a unique flag for this action
	 * @return
	 */
	private String progressFlag() {
		 return Student+"@"+this.getClass().getName() + "@" + Integer.toHexString(this.hashCode());
	}
	
	@Override
	public String toString() {
		return "ParticipatingInCourseAction [Course=" + Course + ", Student=" + Student + ", Grade="
				+ Arrays.toString(Grade) + "]";
	}
	
	
}
