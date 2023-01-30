package FlowChartCreator;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Scanner;


public class Runner {
	
	public static void main(String[] args) throws MalformedURLException, IOException {
		
//		LinkedHashMap<CourseKey,Course> allCoursesMap = new LinkedHashMap<CourseKey,Course>();
//		new CourseLoader(allCoursesMap);
//
//		Course[][] arrayChart = new Course[3][4];
//
//		System.out.println(allCoursesMap);
//
//		Scanner scan;
//		scan = new Scanner(new File("C:\\Users\\kosikoaj\\Documents\\Rose\\FreshmanFall\\CSSE230\\FlowChartCreator\\CSMajor1YearTest.txt"));
//
//		LinkedHashSet<CourseKey> coursesRequired = new LinkedHashSet<CourseKey>();
//
//		String temp;
//		while(scan.hasNext()) {
//			temp = scan.nextLine();
//			coursesRequired.add(new CourseKey(temp.substring(0, temp.indexOf(" ")),
//					Short.parseShort(temp.substring(temp.indexOf(" ") + 1))));
//		}
//
//		System.out.println(coursesRequired);
//
//		Iterator<CourseKey> itr = coursesRequired.iterator();
//		while(itr.hasNext()) {
//			Course temp2 = allCoursesMap.get(itr.next());
//			System.out.println(temp2);
//		}
		
		Scanner scan = new Scanner(System.in);

		System.out.println("Generate new files? Input other than y is considered no");

		if(scan.nextLine().equals("y")) {
			URLGrabber urls = new URLGrabber();//Grab new major information
			urls.createMajorClassesFile();
			urls.getAllClassData();
		}
//		
//		new FlowChartMaker();

		
	}

}
