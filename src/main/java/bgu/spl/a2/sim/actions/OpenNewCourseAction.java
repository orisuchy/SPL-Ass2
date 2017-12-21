package bgu.spl.a2.sim.actions;

import java.util.Arrays;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

class OpenNewCourseAction extends Action<Boolean> {
	private DepartmentPrivateState _departmentState;
	private String _course;
	private int _space;
	private String[] _prerequisites ;
	
	/**
	 * Constructor
	 * @param Course name
	 * @param Available Spots in course
	 * @param Prerequisites list of the course
	 */
	public OpenNewCourseAction(String _course, int _space, String[] _prerequisites) {
		setPromise(new Promise<Boolean>());
		setActionName("Open Course");
		this._course = _course;
		this._space = _space;
		this._prerequisites = _prerequisites;
	}

	/**
	 * Opens a new course in a specified department
	 */
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

	@Override
	public String toString() {
		return "OpenNewCourseAction [_course=" + _course + ", _space=" + _space + ", _prerequisites="
				+ Arrays.toString(_prerequisites) + "]";
	}

}
