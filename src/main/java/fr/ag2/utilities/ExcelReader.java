/*
 *  This class is an utility for reading test data from excel file
 *  
 */
package fr.ag2.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Hashtable;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.testng.annotations.DataProvider;

public class ExcelReader {
	public static Workbook workbook;
	public static Sheet sheet;

	public static String testDatasheetPath = System.getProperty("user.dir") + "/src/test/resources/testDataExcel/testData.xlsx";

	/*
	 * Get data from excel as Object[] []
	 * 
	 */
	@DataProvider(name = "getData", parallel = true)
	public Object[][] getExcelData(Method m) throws IOException {
		String sheetName = m.getName();
		// declare ObjectData

		Object[][] data = null;

		File file = new File(testDatasheetPath);

		// Get our Excel path with FileInputStream

		FileInputStream inputStream = new FileInputStream(file);

		// Workbook workbook = null;

		workbook = WorkbookFactory.create(inputStream);

		// workbook = new XSSFWorkbook(inputStream);

		Sheet sheet = workbook.getSheet(sheetName); // we access to our sheet name data

		data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];

		for (int j = 0; j < sheet.getLastRowNum(); j++) {

			for (int k = 0; k < sheet.getRow(0).getLastCellNum(); k++) {

				data[j][k] = sheet.getRow(j + 1).getCell(k).toString();
			}

		}
		return data;
	}

	/*
	 * Get data from excel as HashTable
	 */
	@DataProvider(name = "getTestData", parallel = true)
	public Object[][] getData(Method m) throws IOException {

		String sheetName = m.getName();

		File file = new File(testDatasheetPath);

		Object[][] data = null;

		// Get our Excel path with FileInputStream

		FileInputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);

			// Workbook workbook = null;

			workbook = WorkbookFactory.create(inputStream);

			Sheet sheet = workbook.getSheet(sheetName); // we access to our sheet name data

			/*
			 * int rows = excel.getRowCount(sheetName); int cols =
			 * excel.getColumnCount(sheetName);
			 * 
			 */

			int rowCount = sheet.getLastRowNum();
			int colCount = sheet.getRow(0).getLastCellNum();

			data = new Object[rowCount][1];

			Hashtable<String, String> table = null;

			for (int rowNum = 0; rowNum < rowCount; rowNum++) {

				table = new Hashtable<String, String>();

				for (int colNum = 0; colNum < colCount; colNum++) {

					table.put(sheet.getRow(0).getCell(colNum).toString(),
							sheet.getRow(rowNum + 1).getCell(colNum).toString());

				}

				data[rowNum][0] = table;

			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null)
				inputStream.close();
		}

		return data;

	}

}
