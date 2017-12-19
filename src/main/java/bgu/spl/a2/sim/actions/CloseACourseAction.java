package bgu.spl.a2.sim.actions;

import java.util.ArrayList;
import java.util.List;
import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;


/**
 * 
 Close A Course:
	- Behavior: This action should close a course. Should unregister all the registered students in the
	course and remove the course from the department courses' list and from the grade sheets of the
	students. The number of available spaces of the closed course should be updated to -1. DO NOT
	remove its actor. After closing the course, all the request for registration should be denied.
	- Actor: Must be initially submitted to the department's actor.
 */

class CloseACourseAction extends Action<Boolean> {
	private DepartmentPrivateState _departmentState;
	private String _course;
	
	/**
	 * Constructor
	 * @param name of course
	 */
	public CloseACourseAction(String course) {
		setPromise(new Promise<Boolean>());
		setActionName("Close Course");
		_course = course;
	}
	
	
	/**
	 * Remove the course from the list of the department.
	 * Send an UnregisterAllAction to the actor of the course and wait for the result to complete.
	 */
	@Override 
	protected void start() {
		throwExceptionForInvalidActorStateType(DepartmentPrivateState.class);
		_departmentState = (DepartmentPrivateState)getCurrentPrivateState();
		
		//create UnregisterAllAction
		List<Action<Boolean>> depencencies = new ArrayList<Action<Boolean>>();
		Action<Boolean> action = new UnregisterAllAction(_course);
		depencencies.add(action);
		sendMessage(action, _course, new CoursePrivateState());
		
		//Remove the course from the department courses' list
		_departmentState.removeCourse(_course);

		//complete results
		then(depencencies, ()->{
			_departmentState.addRecord(getActionName());
			complete(action.getResult().get());
		});
	}
}
