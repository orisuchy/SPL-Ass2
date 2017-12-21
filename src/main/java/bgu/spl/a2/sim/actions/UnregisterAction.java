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
	
	/**
	 * Constructor
	 * @param Student string
	 * @param Course string
	 */
	public UnregisterAction(String Student, String Course) {
		setPromise(new Promise<Boolean>());
		setActionName("Unregister");
		this._student = Student;
		this._course = Course;
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
		List<Action<Boolean>> depencencies = new ArrayList<Action<Boolean>>();
		Action<Boolean> RemoveCourseFromGradesSheet = new RemoveCourseFromGradesSheet(_course);
		//update the list of students of course
		Boolean studentUnregistered = _CourseState.unregisterStudent(_student);
		
		//remove the course from the grades sheet of the student and increases the number of available spaces
		@SuppressWarnings("unchecked")
		Promise<Boolean> removeCourseGradePromise = (Promise<Boolean>) sendMessage(RemoveCourseFromGradesSheet,_student, new StudentPrivateState());
		depencencies.add(RemoveCourseFromGradesSheet);
		then(depencencies,()->{
			complete(removeCourseGradePromise.get() & studentUnregistered);
		});
		
	}

	@Override
	public String toString() {
		return "UnregisterAction [_student=" + _student + ", _course=" + _course + "]";
	}

	
}
