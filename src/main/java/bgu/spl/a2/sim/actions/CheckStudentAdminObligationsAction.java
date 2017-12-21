package bgu.spl.a2.sim.actions;

import java.util.ArrayList;
import java.util.List;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.Computer;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

class CheckStudentAdminObligationsAction extends Action<Boolean> {

	private Computer Computer;
	private List<String> Conditions;
	private StudentPrivateState studentState;
	
	/**
	 * Constructor
	 * @param computer - to calculate conditions
	 * @param conditions - list of courses student must pass
	 */
	public CheckStudentAdminObligationsAction(Computer computer, String[] conditions) {
		setPromise(new Promise<Boolean>());
		setActionName("Administrative Check");
		Computer = computer;
		Conditions = new ArrayList<String>();
		for(String course : conditions) {
			Conditions.add(course);
		}
	}


	/**
	 * use provided computer to check the student's admin obligations
	 */
	@Override
	protected void start() {
		throwExceptionForInvalidActorStateType(StudentPrivateState.class);
		studentState = (StudentPrivateState) getCurrentPrivateState();
		
		long sig = Computer.checkAndSign(Conditions, studentState.getGrades());
		studentState.setSignature(sig);
		studentState.addRecord(getActionName());
		
		boolean success = (studentState.getSignature() == sig);
		complete(success);
	}


	@Override
	public String toString() {
		return "CheckStudentAdminObligationsAction [Computer=" + Computer + ", Conditions=" + Conditions + "]";
	}
	
}
