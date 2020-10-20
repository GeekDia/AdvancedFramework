package fr.ag2.listeners;

import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.testng.ISuite;
import org.testng.ISuiteListener;
import org.testng.ITestListener;

import fr.ag2.utilities.Constants;
import fr.ag2.utilities.MonitoringMail;

public class CustomListener implements ITestListener, ISuiteListener {

	public static String messageBody;

	@Override
	public void onFinish(ISuite suite) {

		try {
			messageBody = "http://" + InetAddress.getLocalHost().getHostAddress()
					+ ":8080/job/AdvancedFramework/HTML_20Extent_20Report/";
		} catch (UnknownHostException e1) {
			e1.printStackTrace();
		}

		MonitoringMail mail = new MonitoringMail();

		try {
			mail.sendMail(Constants.server, Constants.from, Constants.to, Constants.subject, messageBody);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

}
