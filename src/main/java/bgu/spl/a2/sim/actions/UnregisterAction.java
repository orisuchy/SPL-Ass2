package bgu.spl.a2.sim.actions;

import java.util.ArrayList;
import java.util.List;

import bgu.spl.a2.Action;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.*;

class UnregisterAction<R> extends Action<Boolean> {
	private CoursePrivateState _CourseState;
	private String _student;
	
	public UnregisterAction(String Student) {
		setPromise(new Promise<Boolean>());
		setActionName("unregister the student " + Student);
		this._student = Student;
	}

	@Override
	protected void start() {
		throwExceptionForInvalidActorStateType(CoursePrivateState.class);
		_CourseState = (CoursePrivateState)getCurrentPrivateState();
		List<Action<Boolean>> depencencies = new ArrayList<Action<Boolean>>();
		Action<Boolean> RemoveCourseFromGradesSheet = new RemoveCourseFromGradesSheet(_student);
		
		Promise<Boolean> removeCourseGradePromise = (Promise<Boolean>) sendMessage(RemoveCourseFromGradesSheet, _student, new StudentPrivateState());
		depencencies.add(RemoveCourseFromGradesSheet);
		then(depencencies,()->{
			complete(removeCourseGradePromise.get());
		});
		
	}

}
