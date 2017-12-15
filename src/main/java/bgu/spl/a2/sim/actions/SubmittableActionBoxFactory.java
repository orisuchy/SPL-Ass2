package bgu.spl.a2.sim.actions;

import bgu.spl.a2.ActorThreadPool;
import bgu.spl.a2.sim.privateStates.*;
import bgu.spl.a2.Action;

/**
 * A class that is designed to be create by JSON deserialization.
 * Has a public method {@link getAction()} that returns a {@link SubmittableActionBox} class.
 * Has public method {@link getAndSubmit(ActorThreadPool)} that automatically gets 
 * the {@link SubmittableActionBox} class and submits it to the pool.
 */
class SubmittableActionBoxFactory {
	
	//TODO - erase when done
	/* 
	"Action":"Open Course",
	"Department": "CS",
	"Course": "SPL",
	"Space": "400",
	"Prerequisites" : ["Data Structures", "Intro to CS"]
	 
	"Action": "Add Student",
	"Department": "CS",
	"Student": "123456789"
	 
	"Action": "Participate In Course",
	"Student": "123456789",
	"Course": "SPL",
	"Grade": ["98"]
	 
	"Action": "Add Spaces",
	"Course": "SPL",
	"Number": "100"
	
	"Action": "Register With Preferences",
	"Student": "5959595959",
	"Preferences": ["Data Bases","SPL"],
	"Grade": ["98","56"]
	 
	"Action": "Unregister",
	"Student": "123456789",
	"Course": "Data Bases"
	 
	"Action": "Close Course",
	"Department": "CS",
	"Course": "Data Bases"
	 
	"Action" : "Administrative Check",
	"Department": "CS",
	"Students": ["123456789","5959595959"],
	"Computer": "A",
	"Conditions" : ["SPL", "Data Bases"]
	 
	 */
	
	private String Action;
	private String Department;
	private String Course;
	private String Student;
	private String[] Prerequisites;
	private String[] Preferences;
	private String[] Grade;
	private String Computer;
	private String[] Students;
	private String[] Conditions;
	private String Space;
	private String Number;
	
	
	/**
	 * Create the appropriate {@link SubmittableActionBox} class by using the fields that were
	 * initialized by the JSON deserialization
	 * @return {@link SubmittableActionBox} class corresponding with the initialized fields
	 */
	public SubmittableActionBox getAction(){
		
		if(Action=="Open Course") {
			//TODO - wait for Ori implementation of action
			
			
			
		}else if(Action=="Add Student") {
			Action<Boolean> action = new AddStudentAction(Student);
			return (new SubmittableActionBox(action, Department, new DepartmentPrivateState()));
			
		}else if(Action=="Participate In Course") {
			Action<Boolean> action = new ParticipatingInCourseAction(Course, Student, Grade);
			return (new SubmittableActionBox(action, Course, new CoursePrivateState()));
				
		}else if(Action=="Add Spaces") {
			//TODO - wait for Ori implementation of action
			
			
		}else if(Action=="Register With Preferences") {
			Action<Boolean> action = new RegisterWithPreferences(Student, Preferences, Grade);
			return (new SubmittableActionBox(action, Student, new StudentPrivateState()));
			
		}else if(Action=="Unregister") {
			//TODO - wait for Ori implementation of action
			
			
			
		}else if(Action=="Close Course") {
			//TODO - wait for Ori implementation of action
			
			
			
		}else if(Action=="Administrative Check") {
			Action<Boolean> action = new CheckAdministrativeObligations(Students, Conditions, Computer, Department);	
					return (new SubmittableActionBox(action, Department, new DepartmentPrivateState()));

		}else {
			throw new RuntimeException("SubmittableActionBoxFactory recieved illegal Action");
		}
		return null;
	}
	
	/**
	 * Automatically get and submit into the provided pool
	 * @param pool
	 */
	public SubmittableActionBox getAndSubmit(ActorThreadPool pool) {
		SubmittableActionBox toSubmit = getAction();
		toSubmit.submitAction(pool);
		return toSubmit;
	}
	
}
