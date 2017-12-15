package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.ActorThreadPool;
import bgu.spl.a2.PrivateState;

/**
 * This class acts as a wrapper for <Action, ActorId, PrivateState>
 * This class is meant to streamline the submission process 
 */
public class SubmittableActionBox {
	
	private Action<?> action;
	private String actorId;
	private PrivateState privateState;
	
	/**
	 * Constructor
	 * @param action
	 * @param actorId
	 * @param privateState
	 */
	public SubmittableActionBox(Action<?> action, String actorId, PrivateState privateState) {
		this.action = action;
		this.actorId = actorId;
		this.privateState = privateState;
	}
	
	/**
	 * Submit the action to the provided {@link ActorThreadPool}
	 * @param pool
	 */
	public void submitAction(ActorThreadPool pool) {
		pool.submit(action, actorId, privateState);
	}
	
	public Action<?> getAction() {
		return action;
	}

	public String getActorId() {
		return actorId;
	}

	public PrivateState getPrivateState() {
		return privateState;
	}

}
