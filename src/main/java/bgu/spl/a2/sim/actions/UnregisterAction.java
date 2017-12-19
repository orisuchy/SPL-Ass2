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
	
	public UnregisterAction(String Student, String Course) {
		setPromise(new Promise<Boolean>());
		setActionName("Unregister");
		this._student = Student;
		this._course = Course;
	}

	@Override
	protected void start() {
		throwExceptionForInvalidActorStateType(CoursePrivateState.class);
		_CourseState = (CoursePrivateState)getCurrentPrivateState();
		List<Action<Boolean>> depencencies = new ArrayList<Action<Boolean>>();
		Action<Boolean> RemoveCourseFromGradesSheet = new RemoveCourseFromGradesSheet(_student);
		
		StudentPrivateState studentState = (StudentPrivateState)getActorThreadPool().getPrivateState(_student);
		Promise<Boolean> removeCourseGradePromise = (Promise<Boolean>) sendMessage(RemoveCourseFromGradesSheet,_course, studentState);
		depencencies.add(RemoveCourseFromGradesSheet);
		then(depencencies,()->{
			complete(removeCourseGradePromise.get());
		});
		
	}

}
