package bgu.spl.a2.sim.actions;

import java.util.ArrayList;
import java.util.List;
import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;

/**
 * 
 * Creates {@link UnregisterAction} for all students in a course
 * must be submitted to the course's actor
 */
class UnregisterAllAction extends Action<Boolean> {

	private CoursePrivateState _courseState;
	private String _course;
	
	/**
	 * Constructor
	 * @param _course
	 */
	public UnregisterAllAction(String _course) {
		this._course = _course;
	}

	
	/**
	 * Create UnregisterAction for each student in the course state and send to the
	 * ActorThreadPool
	 */
	@Override
	protected void start() {
		throwExceptionForInvalidActorStateType(CoursePrivateState.class);
		_courseState = (CoursePrivateState)getCurrentPrivateState();
		ArrayList<Action<Boolean>> dependencies = new ArrayList<Action<Boolean>>();

		List<String> students = _courseState.getRegStudents();
		for(String student : students) {
			Action<Boolean> action = new UnregisterAction(student, _course);
			dependencies.add(action);
			getActorThreadPool().submit(action, _course, _courseState);
		}
		
		then(dependencies, ()->{
			boolean success = _courseState.getRegStudents().isEmpty();
			if(success) {
				complete(success);
			}
			else {
				start();
			}
		});
	}
}
