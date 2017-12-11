package bgu.spl.a2.sim;

import java.util.List;
import java.util.Map;

import org.omg.PortableInterceptor.SUCCESSFUL;

public class Computer {

	String computerType;
	long failSig;
	long successSig;
	
	public Computer(String computerType) {
		this.computerType = computerType;
	}
	
	/**
	 * this method checks if the courses' grades fulfill the conditions
	 * @param courses
	 * 							courses that should be pass
	 * @param coursesGrades
	 * 							courses' grade
	 * @return a signature if couersesGrades grades meet the conditions
	 */
	public long checkAndSign(List<String> courses, Map<String, Integer> coursesGrades){
		boolean passedRequiredCourses = true;
		
		for(int i=0; i<courses.size() & passedRequiredCourses; i++) {
			
			String currentCourse = courses.get(i);
			
			if(!coursesGrades.containsKey(currentCourse)) {
				passedRequiredCourses = false;
				break;
			}else {
				if(coursesGrades.get(currentCourse) < 56) {
					passedRequiredCourses = false;
					break;
				}
			}	
		}
		long ret = (passedRequiredCourses) ? successSig : failSig;
		return ret;
	}
}
