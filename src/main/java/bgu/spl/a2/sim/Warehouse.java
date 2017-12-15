package bgu.spl.a2.sim;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * represents a warehouse that holds a finite amount of computers
 *  and their suspended mutexes.
 * 
 */
public class Warehouse {
	
	ArrayList<SuspendingMutex> _computerMutexes;
	
	
	//TODO - delete if not needed
	/**
	 * Empty Constructor
	 */
//	Warehouse(){
//		_computers = new ArrayList<SuspendingMutex>();
//		_rotatingIndex.set(0);
//	}
	
	/**
	 * Constructor
	 * @param computers array
	 */
	public Warehouse(Computer[] computersArray){
		if(computersArray.length==0) {
			throw new RuntimeException("Computers array is empty. Cannot construct empty warehouse");
		}
		_computerMutexes = new ArrayList<SuspendingMutex>();
		for (int i=0; i<computersArray.length; i++) {
			_computerMutexes.add(new SuspendingMutex(computersArray[i]));
		}
	}
	
	/**
	 * returns SuspendingMutex with a computer matching the received type
	 * @param String type
	 * @return SuspendingMutex containing a promise with a computer
	 * 		NULL if the warehouse does not contain a computer of the required type
	 */
	public SuspendingMutex getComputer(String type){
		SuspendingMutex returnedMutex = null;
		for(SuspendingMutex mutex : _computerMutexes) {
			if(mutex.getComputerType() == type) {
				returnedMutex = mutex;
				break;
			}
		}
		return returnedMutex;
	}
	
	//TODO - erase if not needed
	//public void addComputer(Computer computer) {
	//	_computers.add(new SuspendingMutex(computer));
	//}
	

}
