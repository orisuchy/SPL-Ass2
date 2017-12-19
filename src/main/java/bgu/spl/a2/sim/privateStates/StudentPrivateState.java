package bgu.spl.a2.sim.privateStates;

import java.util.HashMap;

import bgu.spl.a2.PrivateState;

/**
 * this class describe student private state
 */
public class StudentPrivateState extends PrivateState{

	private HashMap<String, Integer> grades;
	private long signature;
	
	/**
 	 * Implementors note: you may not add other constructors to this class nor
	 * you allowed to add any other parameter to this constructor - changing
	 * this may cause automatic tests to fail..
	 */
	public StudentPrivateState() {
		//TODO: replace method body with real implementation
		throw new UnsupportedOperationException("Not Implemented Yet.");
	}

	public HashMap<String, Integer> getGrades() {
		return grades;
	}

	public long getSignature() {
		return signature;
	}

	public void setSignature(long sig) {
		signature = sig;
	}
	
	/**
	 * Add grade to course. If course not listed, then the course is added as well
	 * @param course
	 * @param grade
	 * @return TRUE if grade successfully added, FALSE otherwise
	 */
	public boolean addGrade(String course, int grade) {
		grades.put(course, grade);
		
		//extra verification:
		return (grades.get(course)==grade);
	}
	
	/**
	 * Remove grade of course.
	 * @param course
	 * @return TRUE if grade successfully removed, FALSE otherwise
	 */
	public boolean removeGrade(String course) {
		grades.remove(course);
		
		//extra verification:
		return (!grades.containsKey(course));
	}
	
	/**
	 * Check if the student is registered to the course
	 * @param course
	 * @return TRUE if student's grades list contains the course
	 */
	public boolean isRegisteredToCourse(String course) {
		return grades.containsKey(course);
	}
}
