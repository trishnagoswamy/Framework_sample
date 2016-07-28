package com.dt.rts.ca.util;

import java.io.*;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import jxl.*;
import jxl.read.biff.*;
import jxl.write.*;

public class utilityExcel {

	private static HSSFRow row   =null;
	private static HSSFCell cell = null;
	
	public static void createExcel(String strDataFilePath, String sheetName, ArrayList data){
		try{
		String filename= strDataFilePath;
		HSSFWorkbook hwb=new HSSFWorkbook();
		HSSFSheet sheet =  hwb.createSheet(sheetName);

		HSSFRow rowhead=   sheet.createRow((short)0);
		for(int i=0;i<data.size();i++){
			rowhead.createCell((short) i).setCellValue(""+data.get(i));
		}
		

		FileOutputStream fileOut =  new FileOutputStream(filename);
		hwb.write(fileOut);
		fileOut.close();
		//System.out.println("Your excel file has been generated!");
		} catch ( Exception ex ) {
		    System.out.println(ex);
		}
	}
	
	public static void updateExcelForCourse(String strDataFilePath,String sheetName, String courseName, ArrayList data){
		try{
		FileInputStream file = new FileInputStream(new File(strDataFilePath));
		HSSFWorkbook workbook = new HSSFWorkbook(file);
	    HSSFSheet sheet = workbook.getSheet(sheetName);
	    Cell cell = null;	 
	
		int i=sheet.getPhysicalNumberOfRows();
		for(int j=0;j<data.size();j++){
				HSSFRow row=sheet.createRow(i);
				row.createCell((short) 0).setCellValue(""+courseName);
				row.createCell((short) 1).setCellValue(""+data.get(j));
				i++;						
		}
		
		FileOutputStream fileOut =  new FileOutputStream(new File(strDataFilePath));
		workbook.write(fileOut);
		fileOut.close();
		//System.out.println("Your excel file has been generated!");
		} catch ( Exception ex ) {
		    System.out.println(ex);

		}
	}
	
	public static void updateExcelSingleRow(String strDataFilePath,String sheetName,  ArrayList data){
		try{
		FileInputStream file = new FileInputStream(new File(strDataFilePath));
		HSSFWorkbook workbook = new HSSFWorkbook(file);
	    HSSFSheet sheet = workbook.getSheet(sheetName);
	    Cell cell = null;	 
	
		int i=sheet.getPhysicalNumberOfRows();
		HSSFRow row=sheet.createRow(i);		
		for(int j=0;j<data.size();j++){
				row.createCell((short) j).setCellValue(""+data.get(j));						
		}
		
		FileOutputStream fileOut =  new FileOutputStream(new File(strDataFilePath));
		workbook.write(fileOut);
		fileOut.close();
		//System.out.println("Your excel file has been generated!");
		} catch ( Exception ex ) {
		    System.out.println(ex);

		}
	}

	public static void addSheet(String strDataFilePath, String sheetName, ArrayList data){
		try{
			FileInputStream file = new FileInputStream(new File(strDataFilePath));
			HSSFWorkbook workbook = new HSSFWorkbook(file);
			HSSFSheet sheet= workbook.createSheet(sheetName);
			HSSFRow rowhead=   sheet.createRow((short)0);
			
			for(int i=0;i<data.size();i++){
				rowhead.createCell((short) i).setCellValue(""+data.get(i));
			}
			FileOutputStream fileOut =  new FileOutputStream(new File(strDataFilePath));
			workbook.write(fileOut);
			fileOut.close();
		} catch ( Exception ex ) {
		    System.out.println(ex);

		}
		
	}

	public static String[][] readDataFromExcel(String strDataFilePath, String sheetName) throws Exception{
		// Read data from excel sheet
		FileInputStream fi = new FileInputStream(strDataFilePath);
		Workbook w = Workbook.getWorkbook(fi);
		Sheet s = w.getSheet(sheetName);
		String a[][] = new String[s.getRows()][s.getColumns()];
		for (int i = 0; i < s.getRows(); i++) {
			for (int j = 0; j < s.getColumns(); j++) {
				a[i][j] = s.getCell(j, i).getContents();
				//System.out.println(a[i][j].toString());
			}
		}
		fi.close();
		return a;
	}

	public static void updateExcel(String strDataFilePath, String sheetName,String data[][]){
		try {
			FileInputStream file= new FileInputStream(new File(strDataFilePath));
			HSSFWorkbook workbook = new HSSFWorkbook(file);
		    HSSFSheet sheet = workbook.getSheet(sheetName);
		   
		    int rownumber=sheet.getPhysicalNumberOfRows();
			for(int i=0;i<data.length;i++){
				 HSSFRow rowhead=   sheet.createRow((short)rownumber);
				for(int j=0;j<data[i].length;j++){
					//System.out.println(data[i][j]);
					rowhead.createCell((short) j).setCellValue(""+data[i][j]);
				}
				rownumber++;
				
			}
			FileOutputStream fileOut =  new FileOutputStream(new File(strDataFilePath));
			workbook.write(fileOut);
			fileOut.close();
			System.out.println("Your excel file has been generated!");
		} catch (Exception e) {
			e.getMessage();
		}
		
		
	}
	
	/**
	  * This function reads Data from an Excel Sheet in Hash Map Type
	  * 
	  * @parameter is: InputStream file
	  * @parameter sheetName: sheetName
	  */
	 public static List<HashMap<String, String>> getTestDataFromExcel(InputStream is,String sheetName) {


	  Workbook workbook = null;
	  try {
	   workbook = Workbook.getWorkbook(is);
	  } catch (BiffException e) {
	   e.printStackTrace();
	  } catch (Exception e) {
	   e.printStackTrace();
	  }
	  Sheet sheet = workbook.getSheet(sheetName);

	  int lastRow = sheet.getRows();
	  int lastcolumn = sheet.getColumns();

	  List<HashMap<String, String>> result = new ArrayList<HashMap<String, String>>(
	    lastRow - 1);

	  for (int i = 1; i < lastRow; i++) {
	   HashMap<String, String> testdata = new HashMap<String, String>();
	   for (int j = 0; j < lastcolumn; j++)
	    testdata.put(sheet.getCell(j, 0).getContents(), sheet.getCell(
	      j, i).getContents());
	   result.add(testdata);
	  }

	  return result;
	 }
	 
	 public static void createExcelFile(String strDataFilePath){
			try{
			String filename= strDataFilePath;
			HSSFWorkbook hwb=new HSSFWorkbook();

			FileOutputStream fileOut =  new FileOutputStream(filename);
			hwb.write(fileOut);
			fileOut.close();
			//System.out.println("Your excel file has been generated!");
			} catch ( Exception ex ) {
			    System.out.println(ex);
			}
		}
	 
	 public static void addSheetInWorkbook(String strDataFilePath, String sheetName){
			try{
				FileInputStream file = new FileInputStream(new File(strDataFilePath));
				HSSFWorkbook workbook = new HSSFWorkbook(file);
				HSSFSheet sheet= workbook.createSheet(sheetName);

				FileOutputStream fileOut =  new FileOutputStream(new File(strDataFilePath));
				workbook.write(fileOut);
				fileOut.close();
			} catch ( Exception ex ) {
			    System.out.println(ex);

			}
			
		}
	 
	// returns the data from a cell
		public static String getCellData(String strDataFilePath,String sheetName,String colName,int rowNum) throws IOException,FileNotFoundException{
			FileInputStream file = new FileInputStream(new File(strDataFilePath));
			HSSFWorkbook workbook = new HSSFWorkbook(file);
			HSSFSheet sheet= workbook.getSheet(sheetName);
			

			try{
				if(rowNum <=0)
					return "";
			
			int index = workbook.getSheetIndex(sheetName);
			int col_Num=-1;
			if(index==-1)
				return "";
			
			sheet = workbook.getSheetAt(index);
			row=sheet.getRow(0);
			for(int i=0;i<row.getLastCellNum();i++){
				//System.out.println(row.getCell(i).getStringCellValue().trim());
				if(row.getCell(i).getStringCellValue().trim().equals(colName.trim()))
					col_Num=i;
			}
			if(col_Num==-1)
				return "";
			
			sheet = workbook.getSheetAt(index);
			row = sheet.getRow(rowNum-1);
			if(row==null)
				return "";
			cell = row.getCell(col_Num);
			
			if(cell==null)
				return "";
			//System.out.println(cell.getCellType());
			if(cell.getCellType()==Cell.CELL_TYPE_STRING)
				  return cell.getStringCellValue();
			else if(cell.getCellType()==Cell.CELL_TYPE_NUMERIC || cell.getCellType()==Cell.CELL_TYPE_FORMULA ){
				  
				  String cellText  = String.valueOf(cell.getNumericCellValue());
				  if (HSSFDateUtil.isCellDateFormatted(cell)) {
			           // format in form of M/D/YY
					  double d = cell.getNumericCellValue();

					  Calendar cal =Calendar.getInstance();
					  cal.setTime(HSSFDateUtil.getJavaDate(d));
			            cellText =
			             (String.valueOf(cal.get(Calendar.YEAR))).substring(2);
			           cellText = cal.get(Calendar.DAY_OF_MONTH) + "/" +
			                      cal.get(Calendar.MONTH)+1 + "/" + 
			                      cellText;
			           
			           //System.out.println(cellText);

			         }

				  
				  
				  return cellText;
			  }else if(cell.getCellType()==Cell.CELL_TYPE_BLANK)
			      return ""; 
			  else 
				  return String.valueOf(cell.getBooleanCellValue());
			
			}
			catch(Exception e){
				
				e.printStackTrace();
				return "row "+rowNum+" or column "+colName +" does not exist in xls";
			}
		}
	 
	 
		//column count from a given excel
		
		public static int columnCountExcludingHeader(String strDataFilePath,String sheetName) throws FileNotFoundException,IOException{
			 int count=0;
			    try {
			        FileInputStream file = new FileInputStream(new File(strDataFilePath));
			         HSSFWorkbook workbook = new HSSFWorkbook(file);
					HSSFSheet sheet= workbook.getSheet(sheetName);
			        Iterator<Row> rowIterator = sheet.iterator();
			        while(rowIterator.hasNext()) {
			            Row row = rowIterator.next();
			            Iterator<Cell> cellIterator = row.cellIterator();
			            while(cellIterator.hasNext()) {
			                Cell cell = cellIterator.next();
			                switch(cell.getCellType()) {
			                    case Cell.CELL_TYPE_BOOLEAN:
			                        System.out.print(cell.getBooleanCellValue() + "\t\t");
			                        break;
			                    case Cell.CELL_TYPE_NUMERIC:
			                        System.out.print(cell.getNumericCellValue() + "\t\t");
			                        break;
			                    case Cell.CELL_TYPE_STRING:
			                        System.out.print(cell.getStringCellValue() + "\t\t");
			                        break;
			                }
			            }
			            System.out.println("");
			        }

			        file.close();    
			    } catch (FileNotFoundException e) {
			        e.printStackTrace();
			    } catch (IOException ae) {
			        ae.printStackTrace();
			    }
				return count;
			}
		
		// returns the row count in a sheet
		public static int getRowCount(String strDataFilePath,String sheetName) throws FileNotFoundException,IOException{
			
			FileInputStream file = new FileInputStream(new File(strDataFilePath));
	         HSSFWorkbook workbook = new HSSFWorkbook(file);
			HSSFSheet sheet= workbook.getSheet(sheetName);
			int index = workbook.getSheetIndex(sheetName);
			if(index==-1)
				return 0;
			else{
			sheet = workbook.getSheetAt(index);
			int number=sheet.getLastRowNum()+1;
			
			
			return number;
			}
			
		}
			
		/**
		 *  This Method is used to return the Course Value
		 * @param strDataFilePath
		 * @param sheetName
		 * @return
		 * @throws IOException
		 * @throws FileNotFoundException
		 * @throws BiffException
		 */
		
		
	public static List<String> readCourseValue(String strDataFilePath,String sheetName) throws IOException, FileNotFoundException,BiffException {
		int counter = 0;
		List<String> courseName=new ArrayList<String>();
		@SuppressWarnings("unused")
		FileInputStream file = new FileInputStream(new File(strDataFilePath));
		// Read data from excel sheet
		FileInputStream fi = new FileInputStream(strDataFilePath);
		Workbook w = Workbook.getWorkbook(fi);
		Sheet s = w.getSheet(sheetName);
		String a[][] = new String[s.getRows()][s.getColumns()];

		for (int i = 1; i < s.getRows(); i++)
			for (int j = (s.getColumns() - 1); j < s.getColumns(); j++) {
				if (!s.getCell(j, i).getContents().isEmpty()) {
					//counter++;
					//System.out.println(counter);
					a[i][j] = s.getCell(j, i).getContents();
					courseName.add(a[i][j]);
					System.out.println(a[i][j].toString());
				}

			}
		return courseName;
	}
	
	
	
	
	/**
	 *  This Method is used to return the Course Value
	 * @param strDataFilePath
	 * @param sheetName
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 * @throws BiffException
	 */
	
	
public static List<String> readCourseValue1(String strDataFilePath,String sheetName) throws IOException, FileNotFoundException,BiffException {
	int counter = 0;
	List<String> courseName=new ArrayList<String>();
	@SuppressWarnings("unused")
	FileInputStream file = new FileInputStream(new File(strDataFilePath));
	// Read data from excel sheet
	FileInputStream fi = new FileInputStream(strDataFilePath);
	Workbook w = Workbook.getWorkbook(fi);
	Sheet s = w.getSheet(sheetName);
	String a[] = new String[s.getRows()];
	
	for (int i = 1; i <s.getRows(); i++){	
		//for (int j = (s.getColumns() - 1); j < =s.getColumns(); j++) {
			if (!s.getCell(2,i).getContents().isEmpty()) {
				//counter++;
				//System.out.println(counter);
				a[i] = s.getCell(2,i).getContents();
				courseName.add(a[i]);
				System.out.println(a[i].toString());
			}
		//}
		}
	return courseName;
}
		
	public static String getSheetName(String sheetName){
		
		if(sheetName.contains("?")){
			sheetName=sheetName.replace("?", "");
		}	
		if(sheetName.contains(":")){
			sheetName=sheetName.replace(":", "");
		}	
		
		/*if(sheetName1.contains("Test"))
		sheetName1 = sheetName1.replace("Test", "");*/

		sheetName = sheetName+"_"+new SimpleDateFormat("MMddhhmmss").format(new Date());
		if(sheetName.length()>29){					
			sheetName = sheetName.substring(sheetName.length()-29,sheetName.length());
		}
		return sheetName;
	}
	 
	/**
	 *  Returns the sheetName value from the summary sheet 
	 * @param fileName
	 * @param type is first column name in summary sheet 
	 * @param courseName
	 * @return
	 * @throws Exception
	 */
	public static String getSheetNameSummary(String fileName,String type, String courseName) throws Exception {
		String sheetData[][]= readDataFromExcel(fileName, "Summary");
		String sheetName=null;
		for(int n=1;n<sheetData.length;n++){							
			if((sheetData[n][0].equals(type))&&(sheetData[n][1].equals(courseName))){
				sheetName=sheetData[n][2];
				break;
			}
		}
		return sheetName;
	}

	/**
	 * This function overwrites the existing rows in the excel.
	 * @param strDataFilePath
	 * @param sheetName
	 * @param rowNum
	 * @param data
	 */
	public static void updateExcelSingleRowFromRow(String strDataFilePath,String sheetName,int rowNum,  ArrayList data){
		try{
		FileInputStream file = new FileInputStream(new File(strDataFilePath));
		HSSFWorkbook workbook = new HSSFWorkbook(file);
	    HSSFSheet sheet = workbook.getSheet(sheetName);
	    Cell cell = null;	 
	
		HSSFRow row=sheet.createRow(rowNum);		
		for(int j=0;j<data.size();j++){
				row.createCell((short) j).setCellValue(""+data.get(j));						
		}
		
		FileOutputStream fileOut =  new FileOutputStream(new File(strDataFilePath));
		workbook.write(fileOut);
		fileOut.close();
		System.out.println("Your excel file has been generated!");
		} catch ( Exception ex ) {
		    System.out.println(ex);

		}
	}
	
}
