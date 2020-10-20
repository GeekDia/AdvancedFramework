package fr.ag2.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

import fr.ag2.base.Base;

public class ZohoLoginPage extends Base {

	/**
	 * Constructor
	 */
	public ZohoLoginPage() {
		super();

	}

	@FindBy(id = "login_id")
	@CacheLookup
	private WebElement email;

	@FindBy(id = "nextbtn")
	@CacheLookup
	private WebElement nextBtn;

	@FindBy(id = "password")
	@CacheLookup
	private WebElement password;

	public ZohoLoginPage invalidLogin(String email, String password) {

		type(this.email, email, "Email");

		click(nextBtn, "Next Btn");

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		type(this.password, password, "PassWord");

		return this;
	}

}
