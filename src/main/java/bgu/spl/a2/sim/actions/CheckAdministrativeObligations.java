package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;

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
	
	
	
	@Override
	protected void start() {
		// TODO Auto-generated method stub

	}

}
