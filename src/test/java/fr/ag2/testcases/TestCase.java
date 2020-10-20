package fr.ag2.testcases;

import java.lang.reflect.Method;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import fr.ag2.base.Base;
import fr.ag2.pages.ZohoHomePage;
import fr.ag2.pages.ZohoLoginPage;

public class TestCase extends Base {

	ZohoHomePage home;
	ZohoLoginPage login;

	@BeforeMethod
	public void beforeMethod(Method m) {

		log.info("\n" + "****** starting test:" + m.getName() + "******" + "\n");

	}

	@Test
	public void loginTest() {

		SoftAssert sas = new SoftAssert();
		home = new ZohoHomePage();

		login = home.doLogin();

		login.invalidLogin("bdia.sne@gmail.com", "gdhjqsgdhjq");

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String expectedTxt = strings.get("test");

		sas.assertEquals("CECI EST UN TEST", expectedTxt);
		sas.assertAll();
	}

	@AfterMethod
	public void tearDown() {
		quit();
	}

}
