package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

class OpenNewCourseAction<R> extends Action<Boolean> {
	private DepartmentPrivateState _departmentState;
	private String _course;
	
	public OpenNewCourseAction(String course) { //TODO: Do I need to get prequisites and availableSpots?
		setPromise(new Promise<Boolean>());
		setActionName("Open New Course");
		this._course = course;
	}

	@Override
	protected void start() {
		throwExceptionForInvalidActorStateType(DepartmentPrivateState.class);
		_departmentState = (DepartmentPrivateState)getCurrentPrivateState();

		Boolean courseAdded = _departmentState.addCourse(_course);
		_departmentState.addRecord(getActionName());
		complete(courseAdded);
		//TODO:  The course has an initially available spaces and a list of prerequisites.
	}

}
