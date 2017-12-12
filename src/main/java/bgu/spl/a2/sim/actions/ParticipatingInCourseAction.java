package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;
import bgu.spl.a2.sim.privateStates.CoursePrivateState;

/**
 * 
	Participating In Course:
		- Behavior: This action should try to register the student in the course, if it succeeds, should add the
			course to the grades sheet of the student, and give him a grade if supplied. See the input example.
		- Actor: Must be initially submitted to the course's actor.
 */

class ParticipatingInCourseAction extends Action<Boolean> {
	/*
	 * "Action": "Participate In Course",
		"Student": "123456789",
		"Course": "SPL",
		"Grade": ["98"]
	 */
	
	private CoursePrivateState courseState;
	private String Course;
	private String Student;
	private String[] Grade;
	
	
	public ParticipatingInCourseAction(String course, String student, String[] grade) {
		setActionName("Participate In Course");
		this.Course = course;
		this.Student = student;
		this.Grade = grade;
	}

	@Override
	protected void start() {
		throwExceptionForInvalidActorStateType(CoursePrivateState.class);
		courseState = (CoursePrivateState)_actorState;
		
		
		
		
	}

}
