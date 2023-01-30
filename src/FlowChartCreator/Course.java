package FlowChartCreator;

import java.util.ArrayList;

public class Course {
	
	public short courseNumber;
	public String courseDepartment;
	public String courseName;
	public String courseInfo;
	public ArrayList<CourseKey> coursePredecessors;

	public ArrayList<CourseKey> courseSuccessors;
	public ArrayList<CourseKey> courseCorequisites;
	public short creditHours;
	public String quartersOffered;
	
	public Course(short courseNumber, String courseInfo,
			ArrayList<CourseKey> predecessors,ArrayList<CourseKey> successors,
			String courseDepartment, String courseName,
			short credits, String quartersOffered,
			ArrayList<CourseKey> corequisites) {
		this.courseNumber = courseNumber;
		this.courseInfo = courseInfo;
		this.courseDepartment = courseDepartment;
		this.quartersOffered = quartersOffered;
		this.courseName = courseName;
		creditHours = credits;
		coursePredecessors = predecessors;
		courseSuccessors = successors;
		courseCorequisites = corequisites;
	}
	

	
	public String toString() {
		return courseDepartment + " " + courseNumber;
	}

}
