package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

class OpenNewCourseAction extends Action<Boolean> {
	private DepartmentPrivateState _departmentState;
	private String _course;
	private int _space;
	private String[] _prerequisites ;
	
	
	public OpenNewCourseAction(String _course, int _space, String[] _prerequisites) {
		setPromise(new Promise<Boolean>());
		setActionName("Open New Course");
		this._course = _course;
		this._space = _space;
		this._prerequisites = _prerequisites;
	}


	@Override
	protected void start() {
		throwExceptionForInvalidActorStateType(DepartmentPrivateState.class);
		_departmentState = (DepartmentPrivateState)getCurrentPrivateState();

		Boolean courseAdded = _departmentState.addCourse(_course);
		_departmentState.addRecord(getActionName());
		
		CoursePrivateState newCourse = new CoursePrivateState();
		newCourse.addSpots(_space);
		newCourse.addPrerequisites(_prerequisites);
		getActorThreadPool().submit(null, _course, newCourse);
		
		complete(courseAdded);
	}
}
