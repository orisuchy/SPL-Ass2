package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

public class RemoveCourseFromGradesSheet extends Action<Boolean> {

	private StudentPrivateState studentState;
	private String course;
	
	/**
	 * Constructor
	 * @param course - String of course's name
	 */
	public RemoveCourseFromGradesSheet(String course) {
		setPromise(new Promise<Boolean>());
		setActionName("Remove Course " + course + " From Grades Sheet");
		this.course = course;
		}
	

	/**
	 * Remove the course from the grade sheet of the student
	 */
	@Override
	protected void start() {
		throwExceptionForInvalidActorStateType(StudentPrivateState.class);
		studentState = (StudentPrivateState)getCurrentPrivateState();		
		Boolean courseGradeRemoved = studentState.removeGrade(course);			
		complete(courseGradeRemoved);
	}


	@Override
	public String toString() {
		return "RemoveCourseFromGradesSheet [course=" + course + "]";
	}
	
}