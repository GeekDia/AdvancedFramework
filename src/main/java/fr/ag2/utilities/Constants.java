package fr.ag2.utilities;

import java.io.File;

public class Constants {

	// EXTENT REPORT DETAILS
	public final static String reportPath = System.getProperty("user.dir") + File.separator + "reports";
			

	public final static String reportConfigurationPath = System.getProperty("user.dir")
			+ "/src/test/resources/configs/html-report-config.xml";
	
	public final static String systemInfOrganization = "AG2";
	public final static String systemInfoBuild = "1.87";

	// EMAIL DETAILS
	public static String server = "smtp.gmail.com";
	public static String from = "bdia.sne@gmail.com";
	public static String password = "Bamba666.";
	public static String[] to = { "cheikhbamba.dia@gmail.com", "bdia.sne@gmail.com" };
	public static String subject = "Extent Project Report";

	// SQL DATABASE DETAILS
	public static String driver = " ";
	public static String dbConnectionUrl = " ";
	public static String dbUserName = " ";
	public static String dbPassword = " ";

	// MYSQL DATABASE DETAILS
	public static String mysqldriver = " ";
	public static String mysqluserName = " ";
	public static String mysqlpassword = " ";
	public static String mysqlurl = " ";

}
