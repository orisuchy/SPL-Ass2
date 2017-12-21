package bgu.spl.a2.sim.privateStates;

import java.util.ArrayList;
import java.util.List;

import bgu.spl.a2.PrivateState;

/**
 * this class describe course's private state
 */
public class CoursePrivateState extends PrivateState{

	private Integer availableSpots;
	private Integer registered;
	private List<String> regStudents;
	private List<String> prequisites;
	
	/**
 	 * Implementors note: you may not add other constructors to this class nor
	 * you allowed to add any other parameter to this constructor - changing
	 * this may cause automatic tests to fail..
	 */
	public CoursePrivateState() {
		super();
		availableSpots = 0;
		registered = 0;
		regStudents = new ArrayList<String>();
		prequisites = new ArrayList<String>();
	}

	public Integer getAvailableSpots() {
		return availableSpots;
	}
	
	public Boolean addSpots(int numOfSpots) {
		Integer currSpots = getAvailableSpots();
		availableSpots = availableSpots + numOfSpots;
		return (getAvailableSpots() == currSpots + numOfSpots);
	}

	public Integer getRegistered() {
		return registered;
	}

	public List<String> getRegStudents() {
		return regStudents;
	}

	public List<String> getPrequisites() {
		return prequisites;
	}
	
	/**
	 * Register student to the course.
	 * @param student string - name of student to add
	 * @return TRUE if the student was successfully added to the list
	 * 		 FALSE otherwise or if the student is already registered
	 */
	public boolean registerStudent(String student) {
		if(regStudents.contains(student)) {return false;} //student already exists!
		regStudents.add(student);
		registered = registered + 1;
		availableSpots = availableSpots -1;
		
		return regStudents.contains(student);
	}
	public Boolean unregisterStudent(String student) {
		if (regStudents.contains(student)) {
			regStudents.remove(student);
			availableSpots++;
			registered--;
		}
			
		return !regStudents.contains(student);
	}
	
	public void addPrerequisites(String[] prequisitesArray) {
		for (String course : prequisitesArray) {
			prequisites.add(course);
		}	
	}
	
	public void removeCourseSpots() {
		availableSpots = -1;
	}

	@Override
	public String toString() {
		return "CoursePrivateState [actions=" + getLogger() + ", availableSpots=" + availableSpots + ", registered=" + registered + ", regStudents="
				+ regStudents + ", prequisites=" + prequisites + "]";
	}
	
	
}
