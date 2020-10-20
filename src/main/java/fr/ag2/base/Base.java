package fr.ag2.base;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.ThreadContext;
import org.apache.logging.log4j.core.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.Status;

import fr.ag2.extentReport.ExtentManager;
import fr.ag2.utilities.DriverFactory;
import fr.ag2.utilities.DriverManager;
import fr.ag2.utilities.TestUtils;

/*
 * This class initialize common utilities:
 * Webdriver
 * Log4j2
 * properties
 * Extent report
 * xml for strings
 * Mail 
 */
public class Base {

	private WebDriver driver;
	private Boolean grid = false;
	protected static HashMap<String, String> strings = new HashMap<String, String>();
	protected Logger log = (Logger) LogManager.getLogger(Thread.currentThread().getStackTrace()[2].getClassName());

	/*
	 * Initialize page objects element
	 */
	public Base() {

		PageFactory.initElements(DriverManager.getInstance().getDriver(), this);
		PageFactory.initElements(new AjaxElementLocatorFactory(DriverManager.getInstance().getDriver(),
				TestUtils.getAJAX_ELEMENT_TIMEOUT()), this);

	}

	/*
	 * this method set all required configurations before the test suite
	 * 
	 * Get configuration path file Get executables path file Get Grid path file
	 * 
	 */
	@BeforeSuite
	public void setUpFramework() {
		
		InputStream fis = null;

		DriverFactory.setGridURL("http://localhost:4446/wd/hub");
		DriverFactory.setConfigPropertiesPath("properties/config.properties");
		if (System.getProperty("os.name").contains("Mac")) {

			DriverFactory.setChromeDriverExePath(
					System.getProperty("user.dir") + "/src/test/resources/executables/chromedriver");
			DriverFactory.setGeckoDriverExePath(
					System.getProperty("user.dir") + "/src/test/resources/executables/geckodriver");

		} else {
			DriverFactory.setChromeDriverExePath(
					System.getProperty("user.dir") + "/src/test/resources/executables/chromedriver.exe");
			DriverFactory.setGeckoDriverExePath(
					System.getProperty("user.dir") + "/src/test/resources/executables/geckodriver.exe");
		}

		// Load the xml file that contains strings
		DriverFactory.setStringsPath("strings/strings.xml");

		try {
			fis = getClass().getClassLoader().getResourceAsStream(DriverFactory.getStringsPath());
			strings = new TestUtils().parseStringXML(fis);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	/*
	 * This method open the browser
	 */

	@Parameters({ "browser" })
	@BeforeMethod
	public void openBrowser(String browser) {

		// route log file for each browser
		String strFile = "logs" + File.separator + browser;
		File logFile = new File(strFile);
		if (!logFile.exists()) {
			logFile.mkdirs();
		}
		// route logs to separate file for each thread
		ThreadContext.put("ROUTINGKEY", strFile);
		log.info("log path: " + strFile);

		if (System.getenv("ExecutionType") != null && System.getenv("ExecutionType").equalsIgnoreCase("Grid")) {

			setGrid(true);
		}
		// Set execution type Grid or Local
		DriverFactory.setRemote(getGrid());

		if (DriverFactory.isRemote() == true) {

			log.info("Executing tests on Grid !!!");

			DesiredCapabilities caps = null;

			if (browser.equalsIgnoreCase("chrome")) {

				caps = DesiredCapabilities.chrome();
				caps.setBrowserName("chrome");
				caps.setPlatform(Platform.ANY);
				log.info("Lauching Chrome !!!");

			} else if (browser.equalsIgnoreCase("firefox")) {

				caps = DesiredCapabilities.firefox();
				caps.setBrowserName("firefox");
				caps.setPlatform(Platform.ANY);

				log.info("Lauching Firefox !!!");

			} else if (browser.equalsIgnoreCase("ie")) {

				caps = DesiredCapabilities.internetExplorer();
				caps.setBrowserName("internet explorer");
				caps.setPlatform(Platform.WIN10);
				log.info("Lauching Internet Explorer !!!");

			}

			try {
				driver = new RemoteWebDriver(new URL(DriverFactory.getGridURL()), caps);
				log.info("Grid set successfully !!!");
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
		} else {

			if (browser.equalsIgnoreCase("chrome")) {

				System.setProperty("webdriver.chrome.driver", DriverFactory.getChromeDriverExePath());
				// disable push notifications
				Map<String, Object> preferences = new HashMap<String, Object>();
				preferences.put("profile.default_content_setting_values.notifications", 2);
				preferences.put("credentials_enable_service", false);
				preferences.put("profile.password_manager_enabled", false);

				ChromeOptions options = new ChromeOptions();
				options.setExperimentalOption("prefs", preferences);
				options.addArguments("--disable-extensions");
				options.addArguments("--disable-infobars");
				options.addArguments("--incognito");

				driver = new ChromeDriver(options);
				log.info("Create driver instance as chrome driver !!!");

			} else if (browser.equalsIgnoreCase("firefox")) {
				System.setProperty("webdriver.gecko.driver", DriverFactory.getGeckoDriverExePath());

				FirefoxOptions firefoxOpts = new FirefoxOptions();
				firefoxOpts.addArguments("--private");

				driver = new FirefoxDriver(firefoxOpts);
				log.info("Create driver instance as Gecko driver !!!");
			} else if (browser.equalsIgnoreCase("ie")) {

				System.setProperty("webdriver.ie.driver", DriverFactory.getIeDriverExePath());

				InternetExplorerOptions ieOpts = new InternetExplorerOptions();
				ieOpts.addCommandSwitches("-private");
				driver = new InternetExplorerDriver(ieOpts);
				log.info("Create driver instance as Internet Explorer driver !!!");

			}

		}

		DriverManager.getInstance().setDriver(driver);
		DriverManager.getInstance().getDriver().manage().window().maximize();
		DriverManager.getInstance().getDriver().manage().deleteAllCookies();
		try {
			DriverManager.getInstance().getDriver().navigate().to(TestUtils.readProperties("env"));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("!!! ERROR to launch the enironement");
		}

	}

	/*
	 * Adding wait method called before every keywords
	 */

	public void waitForVisibilityOfElement(WebElement e) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(DriverManager.getInstance().getDriver())
				.withTimeout(Duration.ofSeconds(TestUtils.getLOAD_TIMEOUT()))
				.pollingEvery(Duration.ofSeconds(TestUtils.getPollingDuration()))
				.ignoring(NoSuchElementException.class);
		wait.until(ExpectedConditions.visibilityOf(e));
	}

	/*
	 * Adding some keywords
	 */
	public void click(WebElement element, String elementName) {
		waitForVisibilityOfElement(element);
		element.click();
		ExtentManager.getTest().log(Status.INFO, "Clicking on : " + elementName);
		log.info("Clicking on : " + elementName);

	}

	public void type(WebElement element, String value, String elementName) {
		waitForVisibilityOfElement(element);
		element.sendKeys(value);
		ExtentManager.getTest().log(Status.INFO, "Typing in : " + elementName + " entered the value as : " + value);
		log.info("Typing in : " + elementName + " entered the value as : " + value);

	}
	/*
	 * 
	 * Quit the driver after test completed
	 */

	public void quit() {
		DriverManager.getInstance().getDriver().quit();
		log.info("Test completed succesfully !!!");
	}

	public Boolean getGrid() {
		return grid;
	}

	public void setGrid(Boolean grid) {
		this.grid = grid;
	}

}
