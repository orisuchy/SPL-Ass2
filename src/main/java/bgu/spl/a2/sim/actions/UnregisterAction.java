package bgu.spl.a2.sim.actions;

import java.util.ArrayList;
import java.util.List;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.*;

class UnregisterAction extends Action<Boolean> {
	private CoursePrivateState _CourseState;
	private String _student;
	private String _course;
	private String _flag;
	
	/**
	 * Constructor
	 * @param Student string
	 * @param Course string
	 */
	public UnregisterAction(String Student, String Course) {
		super();
		setActionName("Unregister");
		this._student = Student;
		this._course = Course;
		this._flag=null;
	}
	
	/**
	 * Unregister student from course
	 * Update the list of students of course
	 * Remove the course from the grades sheet of the student and increases the number of available spaces
	 */
	@Override
	protected void start() {
		throwExceptionForInvalidActorStateType(CoursePrivateState.class);
		_CourseState = (CoursePrivateState)getCurrentPrivateState();	
		
		//verify if the actor contains a registration-in-progress flag and re-enter the queue
		if(getFlag(_CourseState)!=null & _flag==null) {
			_flag = getFlag(_CourseState);			
			then(null, () -> { start(); });
			getActorThreadPool().submit(this, _course, _CourseState);		
			return;	
		} else if(getFlag(_CourseState)!=null && getFlag(_CourseState).equals(_flag)) {		
			then(null, () -> { start(); });
			getActorThreadPool().submit(this, _course, _CourseState);		
			return;
		}
		
		
		List<Action<Boolean>> depencencies = new ArrayList<Action<Boolean>>();
		Action<Boolean> RemoveCourseFromGradesSheet = new RemoveCourseFromGradesSheet(_course);
		//update the list of students of course
		Boolean studentUnregistered = _CourseState.unregisterStudent(_student);
		
		//remove the course from the grades sheet of the student and increases the number of available spaces
		Promise<Boolean> removeCourseGradePromise = 
				(Promise<Boolean>) sendMessage(RemoveCourseFromGradesSheet,_student, new StudentPrivateState());
		depencencies.add(RemoveCourseFromGradesSheet);
		then(depencencies,()->{
			complete(removeCourseGradePromise.get() & studentUnregistered);
		});
		
	}

	
	/**
	 * Attempts to find a flag in the actor indicating that the registration of the student is in progress
	 * @param actor
	 * @return String of the flag
	 * 		NULL if there is no flag
	 */
	public String getFlag(CoursePrivateState actor) {
		List<String> log = actor.getLogger();
		
		String substring = _student + "@"+ ParticipatingInCourseAction.class.getName();
		String flag = null;
		for(int i=log.size()-1; i>=0; i--) {
			if(log.get(i).contains(substring)) {
				flag = log.get(i);
				break;
			}
		}
		return flag;
	}
	
	
	@Override
	public String toString() {
		return "UnregisterAction [_student=" + _student + ", _course=" + _course + "]";
	}

	
}
