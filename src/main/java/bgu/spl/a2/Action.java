package bgu.spl.a2;

import java.util.Collection;

//TODO - delete
import bgu.spl.a2.sim.Simulator;

/**
 * an abstract class that represents an action that may be executed using the
 * {@link ActorThreadPool}
 *
 * Note for implementors: you may add methods and synchronize any of the
 * existing methods in this class *BUT* you must be able to explain why the
 * synchronization is needed. In addition, the methods you add to this class can
 * only be private!!!
 *
 * @param <R> the action result type
 */
public abstract class Action<R> {

	private String _actionName;
	private Promise<R> _result;
	private boolean _started = false;
	private callback _callback;
	private Collection<? extends Action<?>> _dependencies;
	
	private ActorThreadPool _pool;
	private String _actorId;
	private PrivateState _actorState;
	
	
	/**
     * start handling the action - note that this method is protected, a thread
     * cannot call it directly.
     */
    protected abstract void start();
    

    /**
    *
    * start/continue handling the action
    *
    * this method should be called in order to start this action
    * or continue its execution in the case where it has been already started.
    *
    * IMPORTANT: this method is package protected, i.e., only classes inside
    * the same package can access it - you should *not* change it to
    * public/private/protected
    *
    */
   /*package*/ final void handle(ActorThreadPool pool, String actorId, PrivateState actorState) {
	   if(!_started) {
		   _started = true;
		   _actorId = actorId;
		   _actorState = actorState;
		   _pool = pool;
		   start();
	   }
	   else if(dependenciesResolved() & !_result.isResolved()){
		   _callback.call();
	   }
   }
    
    
    /**
     * add a callback to be executed once *all* the given actions results are
     * resolved
     * 
     * Implementors note: make sure that the callback is running only once when
     * all the given actions completed.
     *
     * @param actions
     * @param callback the callback to execute once all the results are resolved
     */
    protected final void then(Collection<? extends Action<?>> actions, callback callback) {
       	_dependencies = actions;
       	_callback = callback;
    }
    
    /**
     * checks to see if all dependency actions are resolved
     * 
     * @return true if all dependency actions
     */
    private final boolean dependenciesResolved() {
    	if(_dependencies == null) {
    		return true;
    	}
    	
    	for (Action<?> dependency : _dependencies) {
    		Promise<?> promise = dependency.getResult();
    		if(!promise.isResolved()) {
    			return false;
    		}
    	}
    	return true;
    }

    /**
     * resolve the internal result - should be called by the action derivative
     * once it is done.
     *
     * @param result - the action calculated result
     */
    protected final void complete(R result) {
    	_actorState.addRecord(getActionName());
       	_result.resolve(result);
       	Simulator.simOut("Action: " + this.toString() + "    Result: " +  result.toString());
    }
    
    /**
     * @return action's promise (result)
     */
    public final Promise<R> getResult() {
    	return _result;
    }
    
    /**
     * send an action to an other actor
     * 
     * @param action
     * 				the action
     * @param actorId
     * 				actor's id
     * @param actorState
	 * 				actor's private state (actor's information)
	 *    
     * @return promise that will hold the result of the sent action
     */
	public Promise<?> sendMessage(Action<?> action, String actorId, PrivateState actorState){
		Promise<?> promise = action.getResult();
		promise.subscribe(() ->{
			
			_pool.submit(this, _actorId, _actorState); //add callback to be put back into the pool when promise is resolved
		});

		_pool.submit(action, actorId, actorState); //add dependecy action to pool
		
        return promise;
	}
	
	/**
	 * set action's name
	 * @param actionName
	 */
	public void setActionName(String actionName){
        _actionName = actionName;
	}
	
	/**
	 * @return action's name
	 */
	public String getActionName(){
        return _actionName;
	}
	
	/**
	 * checks the type of the _actorState field against a given Class
	 * @param 
	 * 		Class expectedStateType - the expected type of the _actorState field
	 * @return 
	 * 		TRUE if _actorState in an instance of expectedStateType, FALSE otherwise
	 */
	private boolean checkActorStateType(Class expectedStateType) {
		return (expectedStateType.isInstance(_actorState));
	}
	
	/**
	 * throw RuntimeException if current ActorState is not of valid type
	 * @param 
	 * 		Class expectedStateType - the expected type of the _actorState field
	 * @throws RuntimeException if ActorState is not of the expected type
	 */
	protected void throwExceptionForInvalidActorStateType(Class expectedStateType) {
		if(!checkActorStateType(expectedStateType)) {
			throw new RuntimeException(getActionName() + " did not recieve correct ActorStateType. Expected: " + expectedStateType);
		}
	}
	
	protected PrivateState getCurrentPrivateState() {
		return _actorState;
	}
	
	protected ActorThreadPool getActorThreadPool() {
		return _pool;
	}
	
	
	protected String getActorId() {
		return _actorId;
	}
	
	protected void setPromise(Promise<R> promise) {
		_result = promise;
	}
}