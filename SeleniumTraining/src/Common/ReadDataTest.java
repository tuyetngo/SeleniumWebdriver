package Common;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadDataTest {

	public static HashMap<String, ArrayList<String>> readfile(String namefile, int index_sheet){
		HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();

		try {

			// Define new path file
			File newfilePath = new File(Constant.project_dir, namefile);

			// Complete path file for excel file
			String filePath = newfilePath.getAbsolutePath();

			// Read specific file by path
			FileInputStream file = new FileInputStream(new File(filePath));

			// Create Workbook instance holding reference to excel file
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			// Get desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(index_sheet);

			// Count row of sheet
			int rowIterator = sheet.getLastRowNum();

			// Count column of sheet
			int cellIterator = sheet.getRow(0).getPhysicalNumberOfCells();

			// Loop row by row in sheet
			for (int i = 0; i <= rowIterator; i++) {

				// Get each row
				Row row = sheet.getRow(i);

				// Refresh key, value
				String key = null;
				String value = null;

				// Define an empty Array
				ArrayList<String> values = new ArrayList<String>();

				// Loop column on each row
				key = row.getCell(0).toString();
				for (int j = 1; j < cellIterator; j++) {

					// Get each column on row
					Cell col = row.getCell(j);
					
					//Check value of each column
					value = (col != null) ? value = col.toString(): "";


					// Add value to Array
					values.add(value);

				}
				// Push key, value to the map
				map.put(key, values);
			}
			// Close file
			file.close();
		}
		// Catch exception
		catch (Exception e) {
			e.printStackTrace();
		}

		return map;

	}
}
