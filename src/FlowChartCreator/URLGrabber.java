package FlowChartCreator;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Scanner;

public class URLGrabber {
	
	private URL url;
	private String urlString;
	private LinkedHashSet<String> courseList;
	private LinkedHashSet<String> majorList;

	
	public URLGrabber() throws IOException {
		Scanner coursePageScanner;
		String string;
		int count = 0;//debugging
		
		urlString = "https://www.rose-hulman.edu/academics/course-catalog/current";
		url = new URL(urlString);
		courseList = new LinkedHashSet<String>();
		majorList = new LinkedHashSet<String>();
		coursePageScanner = new Scanner(url.openStream());
		
		while(coursePageScanner.hasNext()) {
			string = coursePageScanner.nextLine();
			if(string.contains("<h3 class=\"desc-header\">Course Descriptions</h3>")) {//Found list of course urls
				coursePageScanner.nextLine();
				break;
			}
		}
		string = coursePageScanner.nextLine();

		while(!string.trim().equals("</ul>")) {
			if(string.trim().isEmpty()) {
				continue;
			}
			string = string.substring(string.indexOf("programs/")+9,string.indexOf("/course-descriptions"));//Isolates url from html
			courseList.add(string);
			string = coursePageScanner.nextLine();
		}
		
		for(String course: courseList) {
			System.out.println((count++) + ": " + course);
		}
		coursePageScanner.close();
		coursePageScanner = new Scanner(url.openStream());
		
		while(coursePageScanner.hasNext()) {
			string = coursePageScanner.nextLine();
			if(string.contains("<h3 class=\"programs-head\">Undergraduate Programs of Study</h3>")) {//Found list of major urls
				coursePageScanner.nextLine();
				System.out.println("Reachable?");
				break;
			}
		}
		count = 0;
		while(!string.trim().equals("</ul>")) {
			string = coursePageScanner.nextLine();
			if(string.contains("Pre-Pro") || string.contains(" <!-- ") || string.contains("SMO") || string.contains("special") || string.contains("</ul>") ) {
				continue;
			}
			string = string.substring(string.indexOf("programs/")+9,string.indexOf("/index.html"));//Isolates url from html
			majorList.add(string);
			System.out.println((count++) + ": " +string);
			
		}
		count = 0;
		for(String course: majorList) {
			System.out.println((count++) + ": " + course);
		}
		coursePageScanner.close();
		
	}


	
	
	public void createMajorClassesFile() throws IOException {
		Iterator<String> iterator = majorList.iterator();
		File parentDirectory = new File("MajorRequirements");
		parentDirectory.mkdir();
		Scanner scanner;
		String majorName;
		
		while(iterator.hasNext()) {
			majorName = iterator.next();
			PrintWriter courseFile = new PrintWriter(new File(parentDirectory, 
					majorName.replace(" ", "_") + "_Curriculum.txt"));//Creates file in parentDirectory
		
		
		scanner = new Scanner(new URL(urlString + "/programs/" + majorName.replace(" ", "%20") ).openStream());

		
		String string;
		
		while(scanner.hasNext()) {
			string = scanner.nextLine();
			if (string.contains("courseSequence")) {//Beginning of Course list
				for(int i = 0; i < 5; i++) {
					scanner.nextLine();
				}
				
				for(int i = 0; i < 6; i++) {
					string = scanner.nextLine();
					string = string.substring(string.indexOf("<td>")+4);//Removes HTML wrapper
					try {
						string = string.substring(0,string.indexOf("</td>"));
						if(string.contains("&amp;")) {
							string = string.replace("&amp;", "&"); //Fixes HTML & formatting
							
						}
					} catch(StringIndexOutOfBoundsException e) {
						
					}
					
					courseFile.println(string);
					for(int j = 0; j < 2; j++) {
						scanner.nextLine();
					}
					if (scanner.nextLine().trim().isEmpty()) {
						break;
					}
				}
			}
			
		}
		scanner.close();
		courseFile.close();
		}
	}
	
	public void getAllClassData() throws IOException, MalformedURLException{
		File parentDirectory = new File("SubjectCourses");
		parentDirectory.mkdir();
		for(String subject: courseList) {
			String urlSyntax = subject.replace(" ", "%20");
			getOneClassData(parentDirectory, new URL(urlString + "/programs/" + urlSyntax + "/course-descriptions.html"));
		}
	}
	
	public void getOneClassData(File parentDirectory, URL courseURL) throws IOException {
		Scanner scanner = new Scanner(courseURL.openStream());
		String courseName = courseURL.toString();
		courseName = courseName.substring(courseName.indexOf("programs/")+9,courseName.indexOf("/course-descriptions"));
		courseName = courseName.replace("%20", "_");
		PrintWriter courseFile = new PrintWriter(new File(parentDirectory, courseName + "_Courses.txt"));//Creates file in parentDirectory
		while(scanner.hasNext()) {
			String string = scanner.nextLine();
			if (string.contains("course-link")) {
				string = string.substring(string.indexOf("<strong>")+8,string.indexOf("</strong>"));
				courseFile.println(string);
				scanner.nextLine();//Heading to prerequisites
				string = scanner.nextLine();
				courseFile.print("Prerequisities: ");
				while(string.contains("href")) {//Grab all Prerequisites loop
					courseFile.print(string.substring(string.indexOf("html\">")+6,string.indexOf("</a>")));
					
					if (string.contains("and") || string.contains("either") || string.contains("or")) {
						courseFile.print(" " + string.substring(string.indexOf("</a>")+4) + " ");
					}
					string = scanner.nextLine();
				}
				
				if(!string.contains("Corequisites")) {//Bump to corequisites if no prerequisites
					string = scanner.nextLine();
				}
				courseFile.print("\nCorequisites: " + string.substring(string.indexOf(".html\">")+6) + "\n\n");//Currently Broken
				
			}
		}
		courseFile.close();
	}
}
		

