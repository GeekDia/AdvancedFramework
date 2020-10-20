package fr.ag2.extentReport;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import fr.ag2.utilities.Constants;
import fr.ag2.utilities.TestUtils;

public class ExtentManager {

	static ExtentReports extent;

	static Map<Integer, ExtentTest> extentTestMap = new HashMap<Integer, ExtentTest>();

	final static String reportFileName = Constants.reportPath + File.separator + TestUtils.getDate() + "_extent.html";

	// we use synchronized because we need to support parallel execution
	public synchronized static ExtentReports getReporter() {
		if (extent == null) {

			ExtentSparkReporter html = new ExtentSparkReporter(reportFileName);
			extent = new ExtentReports();
			// add configuration

			try {
				html.loadXMLConfig(Constants.reportConfigurationPath);
			} catch (IOException e) {
				e.printStackTrace();
			}

			extent.attachReporter(html);

			extent.setSystemInfo("Organization : ", Constants.systemInfOrganization);
			extent.setSystemInfo("Executed by : ", System.getProperty("user.name"));

			extent.setSystemInfo("Exceuted on OS : ", System.getProperty("os.name"));
			try {
				extent.setSystemInfo("Environement : ", TestUtils.readProperties("env"));
			} catch (Exception e) {
				e.printStackTrace();
			}

			extent.setSystemInfo("Build-N° : ", Constants.systemInfoBuild);

		}

		return extent;
	}

	public static synchronized ExtentTest getTest() {
		return (ExtentTest) extentTestMap.get((int) (long) (Thread.currentThread().getId()));

	}

	public static synchronized ExtentTest startTest(String testName) {
		ExtentTest test = getReporter().createTest(testName);
		extentTestMap.put((int) (long) (Thread.currentThread().getId()), test);

		return test;
	}

}
