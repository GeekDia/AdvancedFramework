package fr.ag2.utilities;

public class DriverFactory {

	private static String chromeDriverExePath;
	private static String geckoDriverExePath;
	private static String ieDriverExePath;
	private static String configPropertiesPath;
	private static String gridPath;
	private static String gridURL;
	private static String stringsPath;
	public static String getGridURL() {
		return gridURL;
	}

	public static void setGridURL(String gridURL) {
		DriverFactory.gridURL = gridURL;
	}

	private static boolean isRemote;

	

	public static String getChromeDriverExePath() {
		return chromeDriverExePath;
	}

	public static void setChromeDriverExePath(String chromeDriverExePath) {
		DriverFactory.chromeDriverExePath = chromeDriverExePath;
	}

	public static String getGeckoDriverExePath() {
		return geckoDriverExePath;
	}

	public static void setGeckoDriverExePath(String geckoDriverExePath) {
		DriverFactory.geckoDriverExePath = geckoDriverExePath;
	}

	public static String getIeDriverExePath() {
		return ieDriverExePath;
	}

	public static void setIeDriverExePath(String ieDriverExePath) {
		DriverFactory.ieDriverExePath = ieDriverExePath;
	}

	public static String getConfigPropertiesPath() {
		return configPropertiesPath;
	}

	public static void setConfigPropertiesPath(String configPropertiesPath) {
		DriverFactory.configPropertiesPath = configPropertiesPath;
	}

	public static String getGridPath() {
		return gridPath;
	}

	public static void setGridPath(String gridPath) {
		DriverFactory.gridPath = gridPath;
	}

	public static boolean isRemote() {
		return isRemote;
	}

	public static void setRemote(boolean isRemote) {
		DriverFactory.isRemote = isRemote;
	}

	public static String getStringsPath() {
		return stringsPath;
	}

	public static void setStringsPath(String stringsPath) {
		DriverFactory.stringsPath = stringsPath;
	}

}
