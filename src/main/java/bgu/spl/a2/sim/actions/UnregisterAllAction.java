package bgu.spl.a2.sim.actions;

import java.util.ArrayList;
import java.util.List;
import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
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
		setPromise(new Promise<Boolean>());
		setActionName("Close Course");
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
		
		//remove the spots from the course
		_courseState.removeCourseSpots();
		
		//creates actions for all students
		ArrayList<Action<Boolean>> dependencies = new ArrayList<Action<Boolean>>();
		List<String> students = _courseState.getRegStudents();
		for(String student : students) {
			Action<Boolean> action = new UnregisterAction(student, _course);
			dependencies.add(action);
			getActorThreadPool().submit(action, _course, _courseState);
		}
		
		then(dependencies, ()->{
			
			boolean success = true;
			for (Action<Boolean> action : dependencies) {
				if(!action.getResult().get()) {
					success = false;
					break;
				}
				complete(success);
			}
		});
	}


	@Override
	public String toString() {
		return "UnregisterAllAction [_course=" + _course + "]";
	}
	
}
