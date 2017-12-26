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
 * The student supply a list of
 *	courses he is interested in them, and wish to register for ONLY one course of them. The courses are ordered
 *	by preference. If he succeeds to register for the course with the highest preference the Acion will not try to
 *	register for the rest. Otherwise it will try to register for the second one, and so on. At the end, he will register
 *	for at most one course.
 */

class RegisterWithPreferences extends Action<Boolean> {
	
	private int currCourse;
	private String Student;
	private String[] Preferences;
	private String[] Grade;
	
	/**
	 * Constructor
	 * @param student
	 * @param preferences
	 * @param grade
	 */
	public RegisterWithPreferences(String student, String[] preferences, String[] grade) {
		setPromise(new Promise<Boolean>());
		setActionName("Register With Preferences");
		currCourse = 0;
		Student = student;
		Preferences = preferences;
		Grade = grade;
	}

	
	/**
	 * Try to register student to the first course in the preference list.
	 * If failed, do the same for the next one.
	 * If tried all the courses and failed - set Result to FALSE
	 */
	@Override
	protected void start() {	
		throwExceptionForInvalidActorStateType(StudentPrivateState.class);
		
		//Create ParticipatingInCourseAction for the student and the current course
		String course = Preferences[currCourse];
		String grade = Grade[currCourse];
		
		Action<Boolean> attemptToRegisterStudent = new ParticipatingInCourseAction(course, Student ,new String[] {grade});
		Promise<Boolean> registerResult = (Promise<Boolean>) sendMessage(attemptToRegisterStudent, course, new CoursePrivateState());
		
		List<Action<Boolean>> dependency = new ArrayList<Action<Boolean>>();
		dependency.add(attemptToRegisterStudent);
		then(dependency, () ->{
			boolean success = registerResult.get();
			
			if(success) { //if managed to register then all is done - set result to TRUE
				complete(success);
			}else {	//if registration failed
				if(iterateToNextCourse()) { //go to next course in the preference list and start again
					start();
				}else { //if there is no next course
					complete(success);
				}
			}
		});

	}
	
	/**
	 * Change this action to handle the next preference of the student
	 * @return TRUE if a next course exists, FALSE  otherwise
	 */
	private boolean iterateToNextCourse() {
		currCourse +=1;
		if(currCourse>=Preferences.length) {
			return false;
		}
		return true;
	}


	@Override
	public String toString() {
		return "RegisterWithPreferences [currCourse=" + currCourse + ", Student=" + Student + ", Preferences="
				+ Arrays.toString(Preferences) + ", Grade=" + Arrays.toString(Grade) + "]";
	}
	
	
}
