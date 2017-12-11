package bgu.spl.a2.sim;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

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
	//private AtomicBoolean _free = new AtomicBoolean(false);
	private AtomicInteger _numberOfRequests = new AtomicInteger();
	
	
	/**
	 * Constructor
	 * @param computer
	 */
	public SuspendingMutex(Computer computer){
		_computer = computer;
		_promiseQueue = new LinkedList<Promise<Computer>>();
		//_free.set(true);
		_numberOfRequests.set(0);
	}
	/**
	 * Computer acquisition procedure
	 * Note that this procedure is non-blocking and should return immediatly
	 * 
	 * @return a promise for the requested computer
	 */
	public Promise<Computer> down(){
		Promise<Computer> returnedPromise = new Promise<Computer>();
		//if(_free.compareAndSet(true, false)) {
		if(_numberOfRequests.compareAndSet(0, 1)) { //Was first to request computer
			returnedPromise.resolve(_computer);
		} else { //must wait in line
			_numberOfRequests.getAndIncrement();
			_promiseQueue.add(returnedPromise);
		}
		
		return returnedPromise;
	}
	/**
	 * Computer return procedure
	 * releases a computer which becomes available in the warehouse upon completion
	 */
	public void up(){	
		if(_numberOfRequests.get()==0) {
			return;
		} else {
			_numberOfRequests.getAndDecrement();
			Promise<Computer> nextPromiseToHandle = _promiseQueue.poll();
			if(nextPromiseToHandle == null) {
				throw new RuntimeException("Popped NULL promise from mutex queue");
			}
			
			
		}
		
		
	}
	
	/**
	 * returns the number of promises currently waiting in the Queue
	 */
	public int getWaitingQueueSize() {
		return _promiseQueue.size();
	}
	
}
