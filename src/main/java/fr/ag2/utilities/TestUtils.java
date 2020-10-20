package fr.ag2.utilities;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class TestUtils {

	// SET WAIT IN SECONDS

	final static private long LOAD_TIMEOUT = 10;
	final static private int AJAX_ELEMENT_TIMEOUT = 10;
	final static private long POLLING_DURATION = 5;

	public static long getPollingDuration() {
		return POLLING_DURATION;
	}

	static Properties properties = new Properties();

	public static long getLOAD_TIMEOUT() {
		return LOAD_TIMEOUT;
	}

	public static int getAJAX_ELEMENT_TIMEOUT() {
		return AJAX_ELEMENT_TIMEOUT;
	}

	// GET DATE TIME
	public static String getDate() {
		Date date = new Date();

		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

		return dateFormat.format(date);
	}

	// GET parameter value from properties file
	public static String readProperties(String parameter) throws Exception {
		loadProperties();

		return properties.getProperty(parameter);

	}

	public static void loadProperties() throws Exception, IOException {
		InputStream inputStream = null;
		try {
			inputStream = TestUtils.class.getClassLoader().getResourceAsStream(DriverFactory.getConfigPropertiesPath());

		} catch (Exception e) {
			throw e;
		}

		try {
			properties.load(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			if (inputStream != null)
				inputStream.close();

		}
	}

	/*
	 * Get static text (string) for assertion from xml file
	 */

	public HashMap<String, String> parseStringXML(InputStream file) throws Exception {
		HashMap<String, String> stringMap = new HashMap<String, String>();
		// Get Document Builder
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();

		// Build Document
		Document document = builder.parse(file);

		// Normalize the XML Structure; It's just too important !!
		document.getDocumentElement().normalize();

		// Here comes the root node
		// Use it for debugging 
		
		  Element root = document.getDocumentElement();
		  System.out.println(root.getNodeName());
		 

		// Get all elements
		NodeList nList = document.getElementsByTagName("string");

		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node node = nList.item(temp);
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) node;
				// Store each element key value in map
				stringMap.put(eElement.getAttribute("name"), eElement.getTextContent());
			}
		}
		return stringMap;
	}

}
