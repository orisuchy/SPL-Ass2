package bgu.spl.a2.sim.actions;

import java.util.ArrayList;
import java.util.List;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.StudentPrivateState;

class CheckStudentHasPrerequisitesAction extends Action<Boolean> {

	private StudentPrivateState studentState;
	private List<String> prequisites;
	
	/**
	 * Constructor
	 * @param prequisites - list of courses to check
	 */
	public CheckStudentHasPrerequisitesAction(List<String> prequisites) {
		super();
		setActionName("Check student has prerequisites");
		this.prequisites = new ArrayList<>();
		for(String course : prequisites) {
			this.prequisites.add(course);
		}
	}

	/**
	 * check if the student has is registered in all courses in a given course list
	 * store the result (TRUE/FALSE) in the Action's promise
	 */
	@Override
	protected void start() {
		throwExceptionForInvalidActorStateType(StudentPrivateState.class);
		studentState = (StudentPrivateState)getCurrentPrivateState();
		
		Boolean studentHasPrequisites = true;
		
		for (String course : prequisites) {
			if(!studentState.isRegisteredToCourse(course)) {
				studentHasPrequisites = false;
				break;
			}
		}	
		complete(studentHasPrequisites);
	}

	@Override
	public String toString() {
		return "CheckStudentHasPrerequisitesAction [prequisites=" + prequisites + "]";
	}
	
}