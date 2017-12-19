package bgu.spl.a2.sim.actions;

import bgu.spl.a2.ActorThreadPool;
import bgu.spl.a2.Promise;
import bgu.spl.a2.sim.privateStates.*;
import bgu.spl.a2.Action;

/**
 * A class that is designed to be create by JSON deserialization.
 * Has a public method {@link getAction()} that returns a {@link SubmittableActionBox} class.
 * Has public method {@link getAndSubmit(ActorThreadPool)} that automatically gets 
 * the {@link SubmittableActionBox} class and submits it to the pool.
 */
public class SubmittableActionBoxFactory {
	
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
	
	private SubmittableActionBox creation = null;
	
	/**
	 * Create the appropriate {@link SubmittableActionBox} class by using the fields that were
	 * initialized by the JSON deserialization
	 * @return {@link SubmittableActionBox} class corresponding with the initialized fields
	 */
	public SubmittableActionBox createAction(){
		
		if(creation!=null) {
			return creation;
		}
		
		if(Action.equals("Open Course")) {
			int space = Integer.parseInt(Space);
			Action<Boolean>  action = new OpenNewCourseAction(Course,space,Prerequisites);
			creation = (new SubmittableActionBox(action, Department, new DepartmentPrivateState()));	
			
		}else if(Action.equals("Add Student")) {
			Action<Boolean> action = new AddStudentAction(Student);
			creation = (new SubmittableActionBox(action, Department, new DepartmentPrivateState()));
			
		}else if(Action.equals("Participate In Course")) {
			Action<Boolean> action = new ParticipatingInCourseAction(Course, Student, Grade);
			creation = (new SubmittableActionBox(action, Course, new CoursePrivateState()));
				
		}else if(Action.equals("Add Spaces")) {
			//TODO - wait for Ori implementation of action
			
			
		}else if(Action.equals("Register With Preferences")) {
			Action<Boolean> action = new RegisterWithPreferences(Student, Preferences, Grade);
			creation = (new SubmittableActionBox(action, Student, new StudentPrivateState()));
			
		}else if(Action.equals("Unregister")) {
			//TODO - wait for Ori implementation of action
			
			
			
		}else if(Action.equals("Close Course")) {
			//TODO - wait for Ori implementation of action
			
			
			
		}else if(Action.equals("Administrative Check")) {
			Action<Boolean> action = new CheckAdministrativeObligations(Students, Conditions, Computer, Department);	
			creation = (new SubmittableActionBox(action, Department, new DepartmentPrivateState()));

		}else {
			throw new RuntimeException("SubmittableActionBoxFactory recieved illegal Action");
		}
			
		return creation;
	}
	
	
	public Promise<?> getPromise() {
		return createAction().getAction().getResult();
	}
	
	
	/**
	 * Automatically get and submit into the provided pool
	 * @param pool
	 */
	public Promise<?> getPromiseAndSubmit(ActorThreadPool pool) {
		SubmittableActionBox toSubmit = createAction();
		return toSubmit.submitAction(pool);
	}
	
}
