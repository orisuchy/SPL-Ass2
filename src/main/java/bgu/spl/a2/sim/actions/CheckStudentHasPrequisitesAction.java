package bgu.spl.a2.sim.actions;

import java.util.ArrayList;
import java.util.List;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

class CheckStudentHasPrequisitesAction extends Action<Boolean> {

	private StudentPrivateState studentState;
	private List<String> prequisites;
	
	
	public CheckStudentHasPrequisitesAction(List<String> prequisites) {
		this.prequisites = new ArrayList<>();
		for(String course : prequisites) {
			this.prequisites.add(course);
		}
	}


	@Override
	protected void start() {
		throwExceptionForInvalidActorStateType(StudentPrivateState.class);
		studentState = (StudentPrivateState)_actorState;
		
		Boolean studentHasPrequisites = true;
		
		for (String course : prequisites) {
			if(!studentState.isRegisteredToCourse(course)) {
				studentHasPrequisites = false;
				break;
			}
		}	
		complete(studentHasPrequisites);
	}
}