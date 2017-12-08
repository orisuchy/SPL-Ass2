package bgu.spl.a2;

import java.util.Collection;

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

	//TODO - Jonathan - should some of those be final?
	private String _actionName;
	private Promise<R> _result;
	private boolean _started = false;
	private callback _callback;
	private Collection<? extends Action<?>> _dependenies;
	
	protected ActorThreadPool _pool;
	protected String _actorId;
	protected PrivateState _actorState;
	
	
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
		   start();
	   }
	   else if(dependenciesResolved()){
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
       	_dependenies = actions;
       	_callback = callback;
    }
    
    /**
     * checks to see if all dependency actions are resolved
     * 
     * @return true if all dependency actions
     */
    private final boolean dependenciesResolved() {
    	for (Action<?> dependency : _dependenies) {
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
       	_result.resolve(result);
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
		_pool.submit(action, actorId, actorState); //add dependecy action to pool
   
		Promise<?> promise = action.getResult();
		promise.subscribe(() ->{		
			_pool.submit(this, _actorId, _actorState); //add callback to be put back into the pool when promise is resolved
		});

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
}