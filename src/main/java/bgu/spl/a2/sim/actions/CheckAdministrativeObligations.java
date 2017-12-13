package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.Simulator;
import bgu.spl.a2.sim.Computer;
import bgu.spl.a2.sim.SuspendingMutex;
import bgu.spl.a2.sim.Warehouse;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;

/**
 * Check Administrative Obligations:
	- Behavior: The department's secretary have to allocate one of the computers available in the ware-
			house, and check for each student if he meets some administrative obligations. 
			The computer generates a signature and save it in the private state of the students.
	- Actor: Must be initially submitted to the department's actor.
 *
 */

class CheckAdministrativeObligations extends Action<Boolean> {

	/* 
	 * "Action" : "Administrative Check",
		"Department": "CS",
		"Students": ["123456789","5959595959"],
		"Computer": "A",
		"Conditions" : ["SPL", "Data Bases"]
	 */
	
	private String[] Students;
	private String[] Conditions;
	private String Computer;
	private String Department;
	
	
	public CheckAdministrativeObligations(String[] students, String[] conditions, String computer, String department) {
		Students = students;
		Conditions = conditions;
		Computer = computer;
		Department = department;
	}


	@Override
	protected void start() {
		 //TODO - must make action to get computer
		 
	}
}
