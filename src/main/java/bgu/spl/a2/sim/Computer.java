package bgu.spl.a2.sim;

import java.util.List;
import java.util.Map;

import org.omg.PortableInterceptor.SUCCESSFUL;

import com.google.gson.annotations.SerializedName;

public class Computer {

	@SerializedName(value = "Type", alternate = "computerType")
	String computerType;
	
	@SerializedName(value = "Sig Fail", alternate = "failSig")
	long failSig;
	
	@SerializedName(value = "Sig Success", alternate = "successSig")
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
	
	/**
	 * get the type of the computer
	 * @return computerType string
	 */
	public String getComputerType() {
		return computerType;
	}
}
