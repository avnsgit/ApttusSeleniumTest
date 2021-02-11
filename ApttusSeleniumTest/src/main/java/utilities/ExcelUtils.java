package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {

	public LinkedHashMap<String, String> readExcel(String excelPath, String sheetName) {
		File file = new File(excelPath);
		XSSFWorkbook workBook = null;
		LinkedHashMap<String, String> testData = new LinkedHashMap<String, String>();
		ArrayList<String> firstRowVal = new ArrayList<String>();
		try {
			FileInputStream fis = new FileInputStream(file);
			workBook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workBook.getSheet(sheetName);
			int totalCellCount = sheet.getRow(0).getLastCellNum();
			for (int j = 0; j < totalCellCount; j++) {
				firstRowVal.add(sheet.getRow(0).getCell(j).getStringCellValue());
			}
			int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
			for (int i = 1; i <= rowCount; i++) {
				int cellCount = sheet.getRow(i).getLastCellNum();
				for (int j = 0; j < cellCount; j++) {
					testData.put(firstRowVal.get(j), (sheet.getRow(i).getCell(j).getStringCellValue()));
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return testData;
	}

	public void writeInExcel(String excelPath, String sheetName, String columnName, String data) {
		File file = new File(excelPath);
		XSSFWorkbook workBook = null;
		ArrayList<String> firstRowVal = new ArrayList<>();
		try {
			FileInputStream fis = new FileInputStream(file);
			workBook = new XSSFWorkbook(fis);
			XSSFSheet sheet = workBook.getSheet(sheetName);
			int totalCellCount = sheet.getRow(0).getLastCellNum();
			for (int j = 0; j < totalCellCount; j++) {
				firstRowVal.add(sheet.getRow(0).getCell(j).getStringCellValue());
			}
			for (int j = 0; j < totalCellCount; j++) {
				if (firstRowVal.get(j).equalsIgnoreCase(columnName)) {
					XSSFCell cell = sheet.getRow(1).createCell(j);
					cell.setCellValue(data);
					break;
				}
			}
			fis.close();
			FileOutputStream fos = new FileOutputStream(file);
			workBook.write(fos);
			fos.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
