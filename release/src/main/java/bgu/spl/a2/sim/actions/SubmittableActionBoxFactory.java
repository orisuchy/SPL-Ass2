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
			int number = Integer.parseInt(Number);
			Action<Boolean> action = new AddSpacesAction(number);
			creation = (new SubmittableActionBox(action, Course, new CoursePrivateState()));	
			
		}else if(Action.equals("Register With Preferences")) {
			Action<Boolean> action = new RegisterWithPreferences(Student, Preferences, Grade);
			creation = (new SubmittableActionBox(action, Student, new StudentPrivateState()));
			
		}else if(Action.equals("Unregister")) {
			Action<Boolean> action = new UnregisterAction(Student, Course);
			creation = (new SubmittableActionBox(action, Course, new CoursePrivateState()));

		}else if(Action.equals("Close Course")) {
			Action<Boolean> action = new CloseACourseAction(Course);	
			creation = (new SubmittableActionBox(action, Department, new DepartmentPrivateState()));
			
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
