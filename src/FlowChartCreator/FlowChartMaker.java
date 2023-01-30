package FlowChartCreator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class FlowChartMaker {
	//Greedy Algorithm
	//Swing
	
	public FlowChartMaker() {
		Scanner scan;
		HashSet<String> courseNames = new HashSet<String>();
		String temp;
		String temp2;
		HashMap<String,Integer> electiveCount = new HashMap<String,Integer>();
		
		
		try {
			scan = new Scanner(new File("C:\\Users\\kosikoaj\\Documents\\Rose\\FreshmanFall\\CSSE230\\FlowChartCreator\\MajorRequirements\\Computer_Science_Curriculum.txt"));
		} catch (FileNotFoundException e) {
			System.out.println("Major file doesn't exist");
			return;
		}
		while(scan.hasNext()) {
			temp = scan.nextLine();//Next line of Course file/ Next Course
//			System.out.println(temp);
			if(temp.contains("Elective")) {
				if (temp.contains("\t")) {
					temp2 = temp.substring(0, temp.indexOf("\t"));//Fixed tab error
				}
				else {
					temp2 = temp.substring(0, temp.indexOf(" "));//Free Elective courses on last line use tab and not space, breaking this
				}
				if (electiveCount.containsKey(temp2)) {
					electiveCount.replace(temp2, electiveCount.get(temp2) + 1);
				}
				else {
					electiveCount.put(temp2, 1);
				}
			}
			else {
				courseNames.add(temp);
			}
		}
		Graph<String> graph = new AdjacencyListGraph<String>(courseNames);
		for(String course: courseNames) {
			String courseAbbreviation;
			if(course.indexOf(" ") > course.indexOf("/")) {
				courseAbbreviation = course.substring(0, course.indexOf("/"));
			}
			else {
				courseAbbreviation = course.substring(0, course.indexOf(" "));
			}
			
			String currentCourseSelected;
			switch(courseAbbreviation) {
			case "MA": currentCourseSelected = "Mathematics";
			break;
			case "CSSE": currentCourseSelected = "Mathematics";
			break;
			case "PH": currentCourseSelected = "Mathematics";
			break;
			case "CHEM": currentCourseSelected = "Mathematics";
			break;
			default: currentCourseSelected = "Mathematics";
			}
			
			
			try {
				scan = new Scanner(new File("C:\\Users\\kosikoaj\\Documents\\Rose\\FreshmanFall\\CSSE230\\FlowChartCreator\\SubjectCourses\\" + currentCourseSelected + "_Courses.txt"));
			} catch (FileNotFoundException e) {
				System.out.println("Course file doesn't exist");
				return;
			}
		}

		
		
		
		
	}
	
}
