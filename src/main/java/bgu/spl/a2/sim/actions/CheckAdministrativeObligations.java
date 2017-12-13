package bgu.spl.a2.sim.actions;

import java.util.ArrayList;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.Computer;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;
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
	
	/**
	 * Constructor
	 * @param students to check
	 * @param conditions - courses to check for grade >= 56
	 * @param computer - type of computer to request from warehouse
	 * @param department
	 */
	public CheckAdministrativeObligations(String[] students, String[] conditions, String computer, String department) {
		Students = students;
		Conditions = conditions;
		Computer = computer;
		Department = department;
	}


	/**
	 * Send action to retireve computer. Once computer is available, send the computer to 
	 * 
	 */
	
	//TODO - release computer!!!!
	@Override
	protected void start() {
		//get computer
		ArrayList<Action<Computer>> dependencies1 = new ArrayList<Action<Computer>>();
		Action<Computer> getComputer = new GetComputerAction(Computer, Department);
		dependencies1.add(getComputer);
		sendMessage(getComputer, Department, new DepartmentPrivateState());
		
		then(dependencies1, ()->{
			Computer receivedComputer = getComputer.getResult().get();
			
			//create an action for each student that will verify their admin obligations
			ArrayList<Action<Boolean>> dependencies2 = new  ArrayList<Action<Boolean>>();
			for(String student : Students) {
				Action<Boolean> checkStudent = 
						new CheckStudentAdminObligationsAction(receivedComputer, Conditions);
				dependencies2.add(checkStudent);
				sendMessage(checkStudent, student, new StudentPrivateState());
			}
			
			//make new dependency list on these "check student" actions
			then(dependencies2, () ->{
				boolean success = true;
				for (Action<Boolean> action : dependencies2) {
					if(!action.getResult().get()) {
						success = false;
						break;
					}
				}
				complete(success);
			});	
		});
	}
}
