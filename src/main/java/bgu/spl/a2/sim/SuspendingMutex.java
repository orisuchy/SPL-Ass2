package bgu.spl.a2.sim;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
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
	private AtomicInteger _numberOfRequests = new AtomicInteger();
	
	
	/**
	 * Constructor
	 * @param computer
	 */
	public SuspendingMutex(Computer computer){
		_computer = computer;
		_promiseQueue = new ConcurrentLinkedQueue<Promise<Computer>>();
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
		if(_numberOfRequests.compareAndSet(0, 1)) { //Was first to request computer
			Simulator.simOut("MUTEX DOWN: compared and set(0,1) - first!");
			returnedPromise.resolve(_computer);
		} else { //must wait in line
			_numberOfRequests.incrementAndGet();
			Simulator.simOut("MUTEX DOWN: waiting in line " + _numberOfRequests.get() + " " + _promiseQueue.size());
			_promiseQueue.add(returnedPromise);
		}
		
		return returnedPromise;
	}
	/**
	 * Computer return procedure
	 * releases a computer which becomes available in the warehouse upon completion
	 */
	public void up(){	
		if(_numberOfRequests.compareAndSet(1, 0)) {
			Simulator.simOut("MUTEX UP: compared and set(1,0) - last one");
			return;
		} else {
			_numberOfRequests.decrementAndGet();
			Simulator.simOut("MUTEX UP: " + _numberOfRequests.get() + " " + _promiseQueue.size());
			Promise<Computer> nextPromiseToHandle = _promiseQueue.poll();
			if(nextPromiseToHandle != null) {
				//throw new RuntimeException("Popped NULL promise from mutex queue");
				nextPromiseToHandle.resolve(_computer);
			}
			
		}
	}
	
	
	private void resolveTop() {
		if(!_promiseQueue.isEmpty()) {
			Promise<Computer> toResolve = _promiseQueue.peek();
			toResolve.resolve(_computer);
		}
	}
	
	/**
	 * get the type of the computer
	 * @return computerType string
	 */
	public String getComputerType() {
		return _computer.getComputerType();
	}
}
