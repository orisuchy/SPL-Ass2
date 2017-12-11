package bgu.spl.a2.sim;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

import bgu.spl.a2.Promise;

/**
 * 
 * this class is related to {@link Computer}
 * it indicates if a computer is free or not
 * 
 * Note: this class can be implemented without any synchronization. 
 * However, using synchronization will be accepted as long as the implementation is blocking free.
 *
 */
public class SuspendingMutex {
	
	private Computer _computer;
	private Queue<Promise<Computer>> _promiseQueue;
	private AtomicBoolean _free;
	
	/**
	 * Constructor
	 * @param computer
	 */
	public SuspendingMutex(Computer computer){
		_computer = computer;
		_promiseQueue = new LinkedList<Promise<Computer>>();
		_free.set(true);
	}
	/**
	 * Computer acquisition procedure
	 * Note that this procedure is non-blocking and should return immediatly
	 * 
	 * @return a promise for the requested computer
	 */
	public Promise<Computer> down(){
		//TODO: replace method body with real implementation
		throw new UnsupportedOperationException("Not Implemented Yet.");
	}
	/**
	 * Computer return procedure
	 * releases a computer which becomes available in the warehouse upon completion
	 */
	public void up(){
		//TODO: replace method body with real implementation
		throw new UnsupportedOperationException("Not Implemented Yet.");
	}
	
	/**
	 * returns the number of promises currently waiting in the Queue
	 */
	public int getWaitingQueueSize() {
		return _promiseQueue.size();
	}
	
}
