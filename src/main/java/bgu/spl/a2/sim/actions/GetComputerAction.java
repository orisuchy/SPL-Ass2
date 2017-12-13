package bgu.spl.a2.sim.actions;

import java.util.ArrayList;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.Computer;
import bgu.spl.a2.sim.Simulator;
import bgu.spl.a2.sim.SuspendingMutex;
import bgu.spl.a2.sim.Warehouse;
import bgu.spl.a2.sim.privateStates.DepartmentPrivateState;


/**
 * An action that gets a computer from the warehouse
 */

class GetComputerAction extends Action<Computer> {

	private String Department;
	private String ComputerType;
	
	/**
	 * Consturctor
	 * @param computerType
	 * @param department
	 */
	public GetComputerAction(String computerType, String department) {
		ComputerType = computerType;
		Department = department;
	}

	
	/**
	 * ask the warehouse for a computer. If first to ask - store the computer as
	 * the result of this action.
	 * If must wait in line - subscribe to the promise of the mutex. 
	 */
	@Override
	protected void start() {
		Warehouse warehouse = Simulator.getWarehouse();
		SuspendingMutex mutex = warehouse.getComputer(ComputerType);
		Promise<Computer> computerPromise = mutex.down();
		
		if(computerPromise.isResolved()) {
			complete(computerPromise.get());
		}
		else {
			computerPromise.subscribe(() ->{
				 _pool.submit(this, Department, new DepartmentPrivateState());
			});	
			
			then(new ArrayList<Action<Object>>(), () -> {
				complete(computerPromise.get());	
			});
		}
	}
}
