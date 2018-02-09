package com.obm.utilities;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Iterator; 
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.obm.stepimplementations.GenericStepImplementations;
public class ExcelUtils {
	
	@SuppressWarnings("deprecation")
	public static Integer getObjectColNum(Workbook workbook, Sheet sheet,String objName) {
		String val = "";
		Integer objColNum = 0;
		String objNameFound = "no";
		Row row = sheet.getRow(0);
		DecimalFormat decimalFormat = new DecimalFormat("################");
        
        Iterator<Cell> cellIterator = row.cellIterator();
                 
        while (cellIterator.hasNext() || objNameFound.equalsIgnoreCase("no")) {
            Cell cell = cellIterator.next();
             
            switch (cell.getCellType()) {
                case Cell.CELL_TYPE_STRING:
                	val = cell.getStringCellValue();
                    if (val.equalsIgnoreCase(objName)) {
                    	objColNum = cell.getColumnIndex() + 1;
                    	//System.out.print("column number:"+cell.getColumnIndex());
                    	objNameFound = "yes";
                    }
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                	val = (String.valueOf(cell.getNumericCellValue())).toString();
                	val = decimalFormat.format(cell.getNumericCellValue());
                    if (val.equalsIgnoreCase(objName)) {
                    	objColNum = cell.getColumnIndex() + 1;
                    	//System.out.print("column number:"+cell.getColumnIndex());
                    	objNameFound = "yes";
                    }
                    break;
                case Cell.CELL_TYPE_BLANK:
                	val = "";
                	break;
                	
            }
           // System.out.print(" - ");
        }
        return objColNum;
		
	}
	
	@SuppressWarnings("deprecation")
	public static Integer getTestIDRowNum(Workbook workbook, Sheet sheet,String testID) {
		Integer testIDRowNum = 0;
		String testIDFound = "no";
		String val ="";
		DecimalFormat decimalFormat = new DecimalFormat("################");
		Iterator<Row> rowIterator = sheet.rowIterator();
        
        while (rowIterator.hasNext() || testIDFound.equalsIgnoreCase("no")) {
        	
            Row nextRow = rowIterator.next();
            switch (nextRow.getCell(0).getCellType()) {
                case Cell.CELL_TYPE_STRING:
                	val = nextRow.getCell(0).getStringCellValue();
                	if (val.equalsIgnoreCase(testID)) {
                		testIDRowNum = nextRow.getRowNum() + 1;
                    	//System.out.print("column number:"+cell.getColumnIndex());
                		testIDFound = "yes";
                    }
                    //System.out.print("ABCD"+nextRow.getCell(0).getStringCellValue());
                    break;
                case Cell.CELL_TYPE_NUMERIC:
                	val = (String.valueOf(nextRow.getCell(0).getNumericCellValue()));
                	val = decimalFormat.format(val);
                    
                    if (val.equalsIgnoreCase(testID)) {
                    	testIDRowNum = nextRow.getRowNum() + 1;
                    	//System.out.print("column number:"+cell.getColumnIndex());
                    	testIDFound = "yes";
                    }
                   // System.out.print(nextRow.getCell(0).getNumericCellValue());
                    break;
                case Cell.CELL_TYPE_BLANK:
                	val = "";
                	break;
                	
            }
        }
        return testIDRowNum;
	}
	
	@SuppressWarnings("deprecation")
	public static String getCellValue(Workbook workbook,Sheet sheet, Integer rowNUm, Integer colNum ) {
		String val = "";
		DecimalFormat decimalFormat = new DecimalFormat("################");
		Cell cell = sheet.getRow(rowNUm-1).getCell(colNum-1);
		
		 switch (cell.getCellType()) {
         case Cell.CELL_TYPE_STRING:
         	val = cell.getStringCellValue();
         	break;
         case Cell.CELL_TYPE_NUMERIC:
         	val = (String.valueOf(cell.getNumericCellValue()));
         	val = decimalFormat.format(val);
             break;
         case Cell.CELL_TYPE_BLANK:
         	val = "";
         	break;         	
     }
		return val;
	}
	
	@SuppressWarnings("deprecation")
	public static String getTestdataValue(String sheetNameObjName, String testID) throws IOException {
		String excelFilePath = "test.xlsx";
		String sheetName = sheetNameObjName.split("::")[0];
		String objName = sheetNameObjName.split("::")[1];
		Integer objColNum = 0; 
		Integer testidRowNum = 0;
		String objValue = "";
		FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
                 
        Workbook workbook = new XSSFWorkbook(inputStream);
        Sheet sheet = workbook.getSheet(sheetName); 
        objColNum = ExcelUtils.getObjectColNum(workbook, sheet, objName);
        System.out.println("In function:"+ ExcelUtils.getObjectColNum(workbook, sheet, objName));
        testidRowNum = getTestIDRowNum(workbook, sheet, testID);
        System.out.println("In function test id row num:"+ testidRowNum);
        objValue = ExcelUtils.getCellValue(workbook, sheet, testidRowNum, objColNum);
        System.out.println("In function object value:"+ objValue);
        
        workbook.close();
        inputStream.close();
		
		return objValue;
	}
	
	@SuppressWarnings("unused")
	public static void main(String[] args) throws IOException {
		String val = ExcelUtils.getTestdataValue("test:obj4", "test125");
		System.out.println("val:"+ val);
    }	
	
}

