package fr.ag2.listeners;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import fr.ag2.extentReport.ExtentManager;
import fr.ag2.utilities.Constants;
import fr.ag2.utilities.DriverManager;

import org.apache.commons.io.FileUtils;

public class ExtentListeners implements ITestListener {
	
	
	private static  Map<String, String> params = new HashMap<String, String>();

	public void onTestStart(ITestResult result) {
		params = getTestNGParameters(result);
		ExtentManager.startTest(result.getTestClass().getName() + "  @TestCase: " + result.getMethod().getMethodName()).assignCategory(params.get("browser"));

	}

	@Override
	public void onTestFailure(ITestResult result) {

		/*
		 * capturing screenshot on test failed
		 */
		File srcFile = ((TakesScreenshot) DriverManager.getInstance().getDriver()).getScreenshotAs(OutputType.FILE);

		/*
		 * Convert srcFile object to Base64 file and put it into encoded variable for
		 * screenshot in our extent report
		 */

		byte[] encoded = null;

		try {
			encoded = Base64.encodeBase64(FileUtils.readFileToByteArray(srcFile));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		
		
		String imagePath = Constants.reportPath  + File.separator
				+ "screenshots" + File.separator + params.get("browser")+ File.separator + result.getMethod().getMethodName() + "_" + "screenshot.png";

		try {
			FileUtils.copyFile(srcFile, new File(imagePath));
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {

			ExtentManager.getTest().fail("Test failed",
					MediaEntityBuilder.createScreenCaptureFromPath(imagePath).build());
			ExtentManager.getTest().fail("Test failed", MediaEntityBuilder
					.createScreenCaptureFromBase64String(new String(encoded, StandardCharsets.UTF_8)).build());

		} catch (Exception e) {
			e.printStackTrace();
		}

		Markup mark = MarkupHelper.createLabel("Test Case Failed", ExtentColor.RED);

		String excepionMessage = Arrays.toString(result.getThrowable().getStackTrace());
		ExtentManager.getTest()
				.fail("<details>" + "<summary>" + "<b>" + "<font color=" + "red>" + "Exception Occured:Click to see"
						+ "</font>" + "</b >" + "</summary>" + excepionMessage.replaceAll(",", "<br>") + "</details>"
						+ " \n");

		ExtentManager.getTest().log(Status.FAIL, mark);

	}



	@Override
	public void onTestSkipped(ITestResult result) {

		String methodName = result.getMethod().getMethodName();
		String logText = "<b>" + "Test Case:- " + methodName + " Skipped" + "</b>";
		Markup mark = MarkupHelper.createLabel(logText, ExtentColor.ORANGE);
		ExtentManager.getTest().skip(mark);

	}

	@Override
	public void onTestSuccess(ITestResult result) {
		String methodName = result.getMethod().getMethodName();
		String logText = "<b>" + "TEST CASE:- " + methodName.toUpperCase() + " PASSED" + "</b>";

		Markup mark = MarkupHelper.createLabel(logText, ExtentColor.GREEN);
		ExtentManager.getTest().log(Status.PASS, mark);

	}

	@Override
	public void onFinish(ITestContext context) {
		ExtentManager.getReporter().flush();

	}
	
	public Map<String, String> getTestNGParameters(ITestResult result) {
		Map <String, String> params = new HashMap<String, String>();
		params = result.getTestContext().getCurrentXmlTest().getAllParameters();
		return params;
	}

}
