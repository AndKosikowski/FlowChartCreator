package FlowChartCreator;

public class CourseKey {

	private short courseNumber;
	private String courseDepartment;
	
	public CourseKey(String courseDepartment, short courseNumber) {//Used to identify Courses
		this.courseNumber = courseNumber;
		this.courseDepartment = courseDepartment;
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof CourseKey) {
			CourseKey ck2 = (CourseKey) o;
			return courseNumber == ck2.courseNumber && courseDepartment.equals(ck2.courseDepartment);
		}
		return false;
	}
	
	public String toString() {
		return courseDepartment + " " + courseNumber;
	}
}
