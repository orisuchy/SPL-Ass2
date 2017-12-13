package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.Computer;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

class CheckStudentAdminObligationsAction extends Action<Boolean> {

	private Computer Computer;
	private String[] Conditions;
	private StudentPrivateState studentState;
	
	/**
	 * Constructor
	 * @param computer - to calculate conditions
	 * @param conditions - list of courses student must pass
	 */
	public CheckStudentAdminObligationsAction(Computer computer, String[] conditions) {
		this.Computer = computer;
		Conditions = conditions;
	}



	@Override
	protected void start() {
		checkActorStateType(StudentPrivateState.class);
		studentState = (StudentPrivateState) getCurrentPrivateState();
		
		
		
	}

}
