package Common;

import java.io.File;

public class Constant {
	public static final String project_dir = System.getProperty("user.dir");
	public static final File newfilePath = new File(project_dir,"geckodriver-v0.11.1-win64\\geckodriver.exe");
	public static final String gecko_path = newfilePath.getAbsolutePath();	
	public static final String URL = "http://192.168.74.25/patest";
	public static final String gecko_property = "webdriver.gecko.driver";
	
}
