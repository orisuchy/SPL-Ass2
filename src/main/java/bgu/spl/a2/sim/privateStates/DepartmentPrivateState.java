package bgu.spl.a2.sim.privateStates;

import java.util.ArrayList;
import java.util.List;

import bgu.spl.a2.PrivateState;

/**
 * this class describe department's private state
 */
public class DepartmentPrivateState extends PrivateState{
	private List<String> courseList;
	private List<String> studentList;
	
	/**
 	 * Implementors note: you may not add other constructors to this class nor
	 * you allowed to add any other parameter to this constructor - changing
	 * this may cause automatic tests to fail..
	 */
	public DepartmentPrivateState() {
		courseList = new ArrayList<String>();
		studentList = new ArrayList<String>();
	}

	public List<String> getCourseList() {
		return courseList;
	}

	public List<String> getStudentList() {
		return studentList;
	}
	
	
	/**
	 * tries to add a student to the department. Does NOT add the student
	 * if studentList already contains studentName
	 * @param studentName string
	 * @return TRUE if was able to add student, FALSE otherwise
	 */
	public boolean addStudent(String studentName) {
		if(studentList.contains(studentName)) {
			return false;
		}
		else {
			studentList.add(studentName);
			return true;
		}
	}

	public Boolean addCourse(String courseName) {
		if(courseList.contains(courseName)) {
			return false;
		}
		else {
			courseList.add(courseName);
			return true;
		}
	}

	public Boolean removeCourse(String course) {
		if (courseList.contains(course))
			courseList.remove(course);
		return !courseList.contains(course);
		
	}
}
