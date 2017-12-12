package bgu.spl.a2.sim.actions;

import bgu.spl.a2.Action;

/**
 * 
	Participating In Course:
		- Behavior: This action should try to register the student in the course, if it succeeds, should add the
			course to the grades sheet of the student, and give him a grade if supplied. See the input example.
		- Actor: Must be initially submitted to the course's actor.
 */

class ParticipatingInCourseAction<R> extends Action<R> {

	/*
	 * "Action": "Participate In Course",
		"Student": "123456789",
		"Course": "SPL",
		"Grade": ["98"]
	 */
	
	
	ParticipatingInCourseAction(){
		
	}
	
	@Override
	protected void start() {
		// TODO Auto-generated method stub

	}

}
