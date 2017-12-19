package bgu.spl.a2.sim.actions;

import java.util.ArrayList;
import java.util.List;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

class CloseACourseAction<R> extends Action<Boolean> {
	private DepartmentPrivateState _departmentState;
	private String _course;
	
	public CloseACourseAction(String course) {
		setPromise(new Promise<Boolean>());
		setActionName("Close Course");
		_course = course;
	}
	
	@Override 
	protected void start() {
		throwExceptionForInvalidActorStateType(DepartmentPrivateState.class);
		_departmentState = (DepartmentPrivateState)getCurrentPrivateState();
		List<Action<Boolean>> depencencies = new ArrayList<Action<Boolean>>();
		
		//Unregister all the registered students in the course
		
		//Remove the course from the department courses' list
		Boolean courseRemoved = _departmentState.removeCourse(_course);
		
		//Remove from the grade sheets of the students
		
		//The number of available spaces of the closed course will be updated to -1
		
		//After closing the course, all the request for registration should be denied
	}

}
