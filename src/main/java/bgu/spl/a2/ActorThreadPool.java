package bgu.spl.a2;
import java.util.*;


/**
 * represents an actor thread pool - to understand what this class does please
 * refer to your assignment.
 *
 * Note for implementors: you may add methods and synchronize any of the
 * existing methods in this class *BUT* you must be able to explain why the
 * synchronization is needed. In addition, the methods you add can only be
 * private, protected or package protected - in other words, no new public
 * methods
 */
public class ActorThreadPool {
	private int numOfThreads;
	private Thread[] threadsArray;
	private Map<String, PrivateState> actorsPrivateState;
	private Map<String, ArrayList<Action>> actorsQueues;
	/**
	 * creates a {@link ActorThreadPool} which has n threads. Note, threads
	 * should not get started until calling to the {@link #start()} method.
	 *
	 * Implementors note: you may not add other constructors to this class nor
	 * you allowed to add any other parameter to this constructor - changing
	 * this may cause automatic tests to fail..
	 *
	 * @param nthreads
	 *            the number of threads that should be started by this thread
	 *            pool
	 */
	public ActorThreadPool(int nthreads) {
		actorsPrivateState = new HashMap<String, PrivateState>();
		actorsQueues = new HashMap<String, ArrayList<Action>>();
		numOfThreads = nthreads;
		threadsArray = new Thread[numOfThreads];
		for (int i=0; i<numOfThreads; i++) { //TODO: 
			threadsArray[i] = new Thread(); 
			//TODO: need to enter lambda as runnable 
			//foreach over the queues map and run over them 
		}
		
	}

	/**
	 * getter for actors
	 * @return actors
	 */
	public Map<String, PrivateState> getActors(){
		
	return actorsPrivateState;
	}
	
	/**
	 * getter for actor's private state
	 * @param actorId actor's id
	 * @return actor's private state
	 */
	public PrivateState getPrivaetState(String actorId){ 
		
		//TODO: What if null? What if map is empty?
		
	return	actorsPrivateState.get(actorId);
		
	}


	/**
	 * submits an action into an actor to be executed by a thread belongs to
	 * this thread pool
	 *
	 * @param action
	 *            the action to execute
	 * @param actorId
	 *            corresponding actor's id
	 * @param actorState
	 *            actor's private state (actor's information)
	 */
	public void submit(Action<?> action, String actorId, PrivateState actorState) {
		if (actorsPrivateState.get(actorId)==null) { //If the actor  exists, create a new actor 
			ArrayList<Action> queue = new ArrayList<Action>();
			queue.add(action);
			actorsQueues.put(actorId,queue);
			actorsPrivateState.put(actorId, actorState);
			}
		else {
			actorsQueues.get(actorId).add(action); //Add action to actors queue
		}
		
	}

	/**
	 * closes the thread pool - this method interrupts all the threads and waits
	 * for them to stop - it is returns *only* when there are no live threads in
	 * the queue.
	 *
	 * after calling this method - one should not use the queue anymore.
	 *
	 * @throws InterruptedException
	 *             if the thread that shut down the threads is interrupted
	 */
	public void shutdown() throws InterruptedException {
		// TODO: replace method body with real implementation
		throw new UnsupportedOperationException("Not Implemented Yet.");
	}

	/**
	 * start the threads belongs to this thread pool
	 */
	public void start() {
		for (int i=0; i<numOfThreads; i++) {
			threadsArray[i].start();
			}
	}

}
