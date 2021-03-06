package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.*;


class AddSpacesAction extends Action<Boolean> {
	private CoursePrivateState _courseState;
	private int spaceToAdd;
	
	/**
	 * Constructor
	 * @param How much space to add
	 */
	public AddSpacesAction(int spaceToAdd) {
		super();
		setActionName("Add Spaces");
		this.spaceToAdd = spaceToAdd;
	}
	/**
	 * Opening New places In a Course
	 */
	@Override
	protected void start() {
		throwExceptionForInvalidActorStateType(CoursePrivateState.class);
		_courseState = (CoursePrivateState)getCurrentPrivateState();
		
		Boolean spaceAdded = _courseState.addSpots(spaceToAdd);
		complete(spaceAdded);

	}
	
	@Override
	public String toString() {
		return "AddSpacesAction [spaceToAdd=" + spaceToAdd + "]";
	}

}
