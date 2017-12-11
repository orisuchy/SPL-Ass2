package bgu.spl.a2.sim;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * represents a warehouse that holds a finite amount of computers
 *  and their suspended mutexes.
 * 
 */
public class Warehouse {
	
	ArrayList<SuspendingMutex> _computers;
	AtomicInteger _currentIndex;
	
	
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
	Warehouse(Computer[] computersArray){
		if(computersArray.length==0) {
			throw new RuntimeException("Computers array is empty. Cannot construct empt warehouse");
		}
		_computers = new ArrayList<SuspendingMutex>();
		_currentIndex.set(0);
		for (int i=0; i<computersArray.length; i++) {
			_computers.add(new SuspendingMutex(computersArray[i]));
		}
	}
	
	/**
	 * sets the current index to the next one
	 * @return SuspendingMutex containing a promise with a computer
	 */
	public SuspendingMutex getComputer(){
		SuspendingMutex ret = _computers.get(_currentIndex.get());
		nextComputerIndex();
		return ret;
	}
	
	
	/**
	 * sets the current index to the next one
	 * @return next index
	 */
	private int nextComputerIndex() {
		int indexOfLastComputer = _computers.size()-1;
		if(_currentIndex.compareAndSet(indexOfLastComputer, 0)) {
			_currentIndex.getAndIncrement();
		}	
		return _currentIndex.get();
	}
	
	//TODO - erase if not needed
	//public void addComputer(Computer computer) {
	//	_computers.add(new SuspendingMutex(computer));
	//}
	

}
