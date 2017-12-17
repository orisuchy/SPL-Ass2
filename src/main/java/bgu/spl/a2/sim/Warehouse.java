package bgu.spl.a2.sim;

import java.util.HashMap;
import java.util.Map;

/**
 * represents a warehouse that holds a finite amount of computers
 *  and their suspended mutexes.
 * 
 */
public class Warehouse {
	
	Map<String, SuspendingMutex> _computerMutexes;
	
	
	/**
	 * Empty Constructor
	 */
	Warehouse(){
		_computerMutexes = new HashMap<String, SuspendingMutex>();
	}
	
	/**
	 * Constructor
	 * @param computers array
	 */
	public Warehouse(Computer[] computersArray){
		if(computersArray.length==0) {
			throw new RuntimeException("Computers array is empty. Cannot construct empty warehouse");
		}
		_computerMutexes = new HashMap<String, SuspendingMutex>();
		for (int i=0; i<computersArray.length; i++) {
			_computerMutexes.put(computersArray[i].getComputerType(), new SuspendingMutex(computersArray[i]));
		}
	}
	
	/**
	 * returns SuspendingMutex with a computer matching the received type
	 * @param String type
	 * @return SuspendingMutex containing a promise with a computer
	 * 		NULL if the warehouse does not contain a computer of the required type
	 */
	public SuspendingMutex getComputer(String type){
		return _computerMutexes.get(type);
	}
	
	
	public void addComputer(Computer computer) {
		_computerMutexes.put(computer.getComputerType(), new SuspendingMutex(computer));
	}
	

}
