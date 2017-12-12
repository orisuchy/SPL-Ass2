package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

class AddStudentGradeAction extends Action<Boolean> {

	@Override
	protected void start() {
		throwExceptionForInvalidActorStateType(StudentPrivateState.class);
		
		
		
	}

}
