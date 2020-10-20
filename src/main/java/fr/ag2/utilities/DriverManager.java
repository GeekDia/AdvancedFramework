package fr.ag2.utilities;

import org.openqa.selenium.WebDriver;

public class DriverManager {

	/*
	 * Singleton design pattern We Make Constructor as Private so other class will
	 * not create object
	 */

	private DriverManager() {

	}

	private static DriverManager instance = new DriverManager();

	/*
	 * Factory design pattern
	 */

	public static DriverManager getInstance() {
		return instance;
	}

	public static ThreadLocal<WebDriver> dr = new ThreadLocal<WebDriver>();

	public  WebDriver getDriver() {

		return dr.get();

	}

	public void setDriver(WebDriver driver) {

		dr.set(driver);

	}

}
