package bgu.spl.a2;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;


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
	private Map<String, ArrayList<Action<?>>> actorsQueues;
	private Map<String, AtomicBoolean> actorsStatus; //true - in use, false - not in use
	private VersionMonitor version;
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
		actorsQueues = new HashMap<String, ArrayList<Action<?>>>();
		actorsStatus = new HashMap<String, AtomicBoolean>();
		version = new VersionMonitor();
		numOfThreads = nthreads;
		threadsArray = new Thread[numOfThreads];
		for (int i=0; i<numOfThreads; i++) 
		{ 
			threadsArray[i] = new Thread( () ->
			{
				while (!Thread.interrupted()) 
				{
					for(String actorId : actorsStatus.keySet()) 
					{
						if (actorsStatus.get(actorId).compareAndSet(false, true))  //try lock
						{
							if(actorsQueues.get(actorId).isEmpty()) {
								ArrayList<Action<?>> queueToRun = actorsQueues.get(actorId);
								Action<?> action = queueToRun.get(queueToRun.size()-1);
								queueToRun.remove(queueToRun.size()-1);
								version.inc();
								action.handle(this, actorId, getPrivateState(actorId));//TODO: really not sure about this
								actorsStatus.get(actorId).set(false); //unlock
								try 
								{ //TODO: WTF?!
									version.await(version.getVersion()+1);
								}
								catch (InterruptedException e) {};
							}
						}
					}
				}
			});
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
	public PrivateState getPrivateState(String actorId){ 

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
		if (actorsPrivateState.get(actorId)==null) { //If the actor not exists, create a new actor 
			ArrayList<Action<?>> queue = new ArrayList<Action<?>>();
			queue.add(action);
			actorsQueues.put(actorId,queue);
			actorsPrivateState.put(actorId, actorState);
			actorsStatus.put(actorId, new AtomicBoolean());
		}
		else {
			actorsQueues.get(actorId).add(action); //Add action to actors queue
		}
		version.inc();

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
