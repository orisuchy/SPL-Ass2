package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.*;


class AddSpacesAction extends Action<Boolean> {
	private CoursePrivateState _courseState;
	private int spaceToAdd;
	
	public AddSpacesAction(int spaceToAdd) {
		setPromise(new Promise<Boolean>());
		setActionName("Add Spaces");
		this.spaceToAdd = spaceToAdd;
	}
	
	@Override
	protected void start() {
		throwExceptionForInvalidActorStateType(CoursePrivateState.class);
		_courseState = (CoursePrivateState)getCurrentPrivateState();
		
		Boolean spaceAdded = _courseState.addSpots(spaceToAdd);
		_courseState.addRecord(getActionName());
		complete(spaceAdded);

	}

}
