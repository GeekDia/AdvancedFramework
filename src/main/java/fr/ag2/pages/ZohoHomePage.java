package fr.ag2.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import fr.ag2.base.Base;

public class ZohoHomePage extends Base {

	/**
	 * Constructor
	 */
	public ZohoHomePage() {
		super();
	}


	@FindBy(xpath = "//div[@class='zh-user-account']/a[@class='zh-login']")
	@CacheLookup
	private WebElement login;

	public ZohoLoginPage doLogin() {

		click(login, "Login link");

		return new ZohoLoginPage();
	}

}
