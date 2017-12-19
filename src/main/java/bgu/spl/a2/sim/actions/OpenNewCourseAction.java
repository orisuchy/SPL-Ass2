package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

class OpenNewCourseAction extends Action<Boolean> {
	private DepartmentPrivateState _departmentState;
	private String _course;
	private String _department;
	private int _space;
	private String[] _prerequisites ;
	
	
	public OpenNewCourseAction(String _course, String _department, int _space,
			String[] _prerequisites) {
		setPromise(new Promise<Boolean>());
		setActionName("Open New Course");
		this._course = _course;
		this._department = _department;
		this._space = _space;
		this._prerequisites = _prerequisites;
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
