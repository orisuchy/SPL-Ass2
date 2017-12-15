package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

/*
Add Student:
	- Behavior: This action adds a new student to a specied department.
	- Actor: Must be initially submitted to the Department's actor.
*/
class AddStudentAction extends Action<Boolean> {

	private DepartmentPrivateState _departmentState;
	private String Student;
		
	/**
	 * Constructor
	 * @param Department string
	 * @param Student string
	 */
	public AddStudentAction(String Student) {
		setActionName("Add Student Course-Grade");
		this.Student = Student;
	}
	
	
	/**
	 * Try adding the student to the department
	 */
	@Override
	protected void start() {
		throwExceptionForInvalidActorStateType(DepartmentPrivateState.class);
		_departmentState = (DepartmentPrivateState)getCurrentPrivateState();

		Boolean studentAdded = _departmentState.addStudent(Student);
		_departmentState.addRecord(getActionName());
		complete(studentAdded);
	}
}
