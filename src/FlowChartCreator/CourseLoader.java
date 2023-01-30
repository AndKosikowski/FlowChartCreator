package FlowChartCreator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Scanner;

public class CourseLoader {
	
	
	Scanner scan;
	File file;
	
	public CourseLoader(LinkedHashMap<CourseKey,Course> courseList) {

		file = new File("C:\\Users\\kosikoaj\\Documents\\Rose\\FreshmanFall\\CSSE230\\FlowChartCreator\\SubjectCourses");
		
		short courseNumber;
		String courseDepartment;
		String courseName;
		String courseInfo;
		ArrayList<CourseKey> coursePredecessors;
		ArrayList<CourseKey> courseSuccessors;
		ArrayList<CourseKey> courseCorequisites;
		short creditHours;
		String quartersOffered;
		String temp;
		
		for(String text: file.list()) {
			try {
				scan = new Scanner(new File("C:\\Users\\kosikoaj\\Documents\\Rose\\FreshmanFall\\CSSE230\\FlowChartCreator\\SubjectCourses" + "\\" + text));
			}
			catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		while(scan.hasNext()) { 
			courseDepartment = scan.nextLine();
			courseNumber = Short.parseShort(scan.nextLine());
			courseName = scan.nextLine();
			creditHours = Short.parseShort(scan.nextLine());
			quartersOffered = scan.nextLine();
			courseInfo = scan.nextLine();
			
			courseCorequisites = new ArrayList<CourseKey>();
			coursePredecessors = new ArrayList<CourseKey>();
			courseSuccessors = new ArrayList<CourseKey>();
			temp = scan.nextLine();
			
			while(!temp.equals("none")) {//Adds corequisities until reads none, repeats for pre and suc
				courseCorequisites.add(new CourseKey(temp.substring(0, temp.indexOf(" ")),
						Short.parseShort(temp.substring(temp.indexOf(" ") + 1))));
				temp = scan.nextLine();
			}
			temp = scan.nextLine();
			while(!temp.equals("none")) {
				coursePredecessors.add(new CourseKey(temp.substring(0, temp.indexOf(" ")),
						Short.parseShort(temp.substring(temp.indexOf(" ") + 1))));
				temp = scan.nextLine();
			}
			temp = scan.nextLine();
			while(!temp.equals("none")) {
				courseSuccessors.add(new CourseKey(temp.substring(0, temp.indexOf(" ")),
						Short.parseShort(temp.substring(temp.indexOf(" ") + 1))));
				temp = scan.nextLine();
			}
			courseList.put(new CourseKey(courseDepartment, courseNumber),
					new Course(courseNumber, courseInfo, coursePredecessors, courseSuccessors,
							courseDepartment, courseName, creditHours, quartersOffered, courseCorequisites));
			if (scan.hasNext()) {
				scan.nextLine();
			}

		}
	}
		
		scan.close();
	}
}

