package bgu.spl.a2.sim;

import bgu.spl.a2.ActorThreadPool;


/**
 * A class that is designed to be create by JSON string.
 * Has a public method - {@link getAction()} that returns a {@link SubmittableActionBox} class
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
	private String[] Grade;
	private String Computer;
	private String[] Conditions;
	private String Space;
	private String Number;
	
	public SubmittableActionBox getAction(){
		
		if(Action=="Open Course") {
			//TODO - wait for Ori implementation of action
			
			
			
		}else if(Action=="Add Student") {
			
			
			
			
		}else if(Action=="Participate In Course") {
			
			
			
			
		}else if(Action=="Add Spaces") {
			
			
			
			
		}else if(Action=="Register With Preferences") {
			
			
			
			
		}else if(Action=="Unregister") {
			
			
			
			
		}else if(Action=="Close Course") {
			
			
			
			
		}else if(Action=="Administrative Check") {
			
			
			
			
		}else {
			//TODO - throw exception
			
			
			
		}
		
		return null;
	}
	
	public void getAndSubmit(ActorThreadPool pool) {
		
	}
	
}
