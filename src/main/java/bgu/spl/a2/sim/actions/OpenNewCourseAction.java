package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

class OpenNewCourseAction<R> extends Action<Boolean> {
	private DepartmentPrivateState _departmentState;
	private String Course;
	
	public OpenNewCourseAction(String course) {
		setPromise(new Promise<Boolean>());
		setActionName("Open New Course");
		this.Course = course;
	}

	@Override
	protected void start() {
		throwExceptionForInvalidActorStateType(DepartmentPrivateState.class);
		_departmentState = (DepartmentPrivateState)getCurrentPrivateState();

		Boolean courseAdded = _departmentState.addCourse(Course);
		_departmentState.addRecord(getActionName());
		complete(courseAdded);
		
	}

}
