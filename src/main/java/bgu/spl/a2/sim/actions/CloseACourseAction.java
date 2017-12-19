package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

class CloseACourseAction<R> extends Action<Boolean> {
	private DepartmentPrivateState _departmentState;
	private String _course;
	
	public CloseACourseAction(String course) {
		_course = course;
	}
	@Override 
	protected void start() {
		// TODO Auto-generated method stub

	}

}
