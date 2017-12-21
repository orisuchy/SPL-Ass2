package bgu.spl.a2.sim.actions;

import java.util.ArrayList;
import java.util.Arrays;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.callback;
import bgu.spl.a2.sim.Computer;
import bgu.spl.a2.sim.Simulator;
import bgu.spl.a2.sim.SuspendingMutex;
import bgu.spl.a2.sim.Warehouse;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

/**
 * Check Administrative Obligations:
	- Behavior: The department's secretary have to allocate one of the computers available in the ware-
			house, and check for each student if he meets some administrative obligations. 
			The computer generates a signature and save it in the private state of the students.
	- Actor: Must be initially submitted to the department's actor.
 *
 */

class CheckAdministrativeObligations extends Action<Boolean> {
	
	private String[] Students;
	private String[] Conditions;
	private String Computer;
	private String Department;
	
	/**
	 * Constructor
	 * @param students to check
	 * @param conditions - courses to check for grade >= 56
	 * @param computer - type of computer to request from warehouse
	 * @param department
	 */
	public CheckAdministrativeObligations(String[] students, String[] conditions, String computer, String department) {
		setPromise(new Promise<Boolean>());
		setActionName("Administrative Check");
		Students = students;
		Conditions = conditions;
		Computer = computer;
		Department = department;
	}


	/**
	 * Attempt to retrieve a computer from the warehouse.
	 * Once the computer is obtained, send actions to all students to verify their admin obligation.
	 * Once their admin obligations are checked - release the computer
	 */
	@Override
	protected void start() {
		
		//Get computer promise from warehouse
		Warehouse warehouse = Simulator.getWarehouse();
		SuspendingMutex mutex = warehouse.getComputer(Computer);
		Promise<Computer> promisedComputer = mutex.down();
		
		
		if(promisedComputer.isResolved()) { //mutex was clear and immediately received computer
			//Do phase1 and insert phase2 as "then" callback
			ArrayList<Action<Boolean>> dependencies1 = phase1(promisedComputer);
			then(dependencies1, () ->{
				phase2(dependencies1, mutex);
			});
		}else { //computer is unavailable
			//subscribe to the promise to get back into the pool
			promisedComputer.subscribe(()->{
				getActorThreadPool().submit(this, Department, getCurrentPrivateState());
			});
			
			//create empty dependency list for "then" and make "then" callback be phase1
			ArrayList<Action<Computer>> dependencies0 = new ArrayList<Action<Computer>>(); //list is empty to allow immediate follow-up when calling action.handle again
			then(dependencies0, ()->{
				//Do phase1 and insert phase2 as "then" callback
				ArrayList<Action<Boolean>> dependencies1 = phase1(promisedComputer);
				then(dependencies1, () ->{
					phase2(dependencies1, mutex);
				});	
			});
		}
	}

	/**
	 * Once a computer is obtained - create a "CheckStudentAdminObligationsAction"
	 * for each student and send to the pool;
	 * Call "then" to add these actions as the dependency list and add {@link phase2(ArrayList<Action<Boolean>>, SuspendingMutex)} as the "then" callback
	 * @param promisedComputer
	 * @return action dependency list for the next phase
	 */
	protected ArrayList<Action<Boolean>> phase1(Promise<Computer> promisedComputer) {
		Computer receivedComputer = promisedComputer.get();
		
		//create an action for each student that will verify their admin obligations
		ArrayList<Action<Boolean>> dependencies1 = new  ArrayList<Action<Boolean>>();
		for(String student : Students) {
			Action<Boolean> checkStudent = 
					new CheckStudentAdminObligationsAction(receivedComputer, Conditions);
			dependencies1.add(checkStudent);
			sendMessage(checkStudent, student, new StudentPrivateState());
		}
		return dependencies1;
	}
	
	/**
	 * Verify that the students were checked for admin obligations.
	 * Release the computer mutex once done.
	 * @param dependencies1 - list of previous actions to check
	 * @param mutex - once done, release this mutex
	 */
	protected void phase2(ArrayList<Action<Boolean>> dependencies1, SuspendingMutex mutex) {
		boolean success = true;
		for (Action<Boolean> action : dependencies1) {
			if(!action.getResult().get()) {
				success = false;
				break;
			}
		}
		mutex.up(); //release mutex
		complete(success);
	}

	@Override
	public String toString() {
		return "CheckAdministrativeObligations [Students=" + Arrays.toString(Students) + ", Conditions="
				+ Arrays.toString(Conditions) + ", Computer=" + Computer + "]";
	}
}
