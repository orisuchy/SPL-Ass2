package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;


/**
 * Action that updates a student's grade in a course
 * If the course does not exist in the student's list it will add it
 */

class AddStudentGradeAction extends Action<Boolean> {

	private StudentPrivateState studentState;
	private String course;
	private int grade;
	
	/**
	 * Constructor
	 * @param course
	 * @param grade
	 */
	public AddStudentGradeAction(String course, int grade){
		super();
		setActionName("Participate In Course");
		this.course = course;
		this.grade = grade;
	}
	
	/**
	 * add the course grade to the student
	 */
	@Override
	protected void start() {
		throwExceptionForInvalidActorStateType(StudentPrivateState.class);
		studentState = (StudentPrivateState)getCurrentPrivateState();
		
		Boolean addedGrade = studentState.addGrade(course, grade);
		studentState.addRecord(getActionName());
		complete(addedGrade);
	}

	@Override
	public String toString() {
		return "AddStudentGradeAction [course=" + course + ", grade=" + grade + "]";
	}

}
