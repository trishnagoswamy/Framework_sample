package com.dt.rts.ca.util;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Reporter;

import com.dt.rts.ca.common.Common;


@SuppressWarnings("unused")
public class UtilityCommon {


	InputStream inputStream;	
	public static WebDriver driver;
	public static int timeoutSec = 120;
	public static String timeout = "120000";
	public static long wait = 5000 ;
	public static long sleep = 1000;	
	public static int objectpresent = 2;
	public static long pageload = 3000;
	WaitForPageToLoad waitForPageToLoad=new WaitForPageToLoad();
		
	public static class ReadPropertiesFileUtil {
		public class LoadPropertyFile {
			private Properties   propertyLoad ;
			public Properties readPropertyFile(final String pFileName) 
			{   
				InputStream fileSource = null;
				try {
					fileSource   = this.getClass().getResourceAsStream(pFileName);
					propertyLoad = new Properties();
					propertyLoad.load(fileSource);
				} catch (IOException e) 
				{
					e.printStackTrace();
				}
				finally
				{
					if(fileSource!=null)
					{
						try {
							fileSource.close();
						} catch (IOException e)
						{
							e.printStackTrace();
						}
					}
				}
				return propertyLoad;	
			}


		}

		private static Logger log= Logger.getLogger(ReadPropertiesFileUtil.class);

	}

	public static void sleepForGivenTime(long time){
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}	

	/**
	 * The waitForElementPresent function will wait for the element for a
	 * default duration of customized seconds To increase or decrease this time
	 * change the value of the integer 'timeoutSec' in "Common.java"
	 * 
	 * @param Locator
	 * @param driver
	 */
	public static boolean waitForElementPresent(final By locator,WebDriver driver) {                

		boolean exists = false;

		for(int i=0;i<timeoutSec;i=i+1)
		{
			WebDriverWait wait = new WebDriverWait(driver, timeoutSec);
			WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			System.out.println(element);
			sleepForGivenTime(1000);
			if(element!=null)
			{				
				exists = true;
				break;
			} 
			else{
				sleepForGivenTime(1000);
			}
		}
		return exists;

	}       


	/**
	 * This function waits for a particular element on a page to be visible
	 * @param By locator
	 * @param driver
	 *  
	 */
	public static boolean waitForElementVisible(final By locator, WebDriver driver) {

		boolean exists = false;

		for(int i=0;i<timeoutSec;i=i+1)
		{

			Common.getScreenLocation(locator, driver);
			FluentWait<WebDriver> wait = new WebDriverWait(driver, timeoutSec)
			.withTimeout(timeoutSec, TimeUnit.SECONDS)
			.pollingEvery(5, TimeUnit.SECONDS)
			.ignoring( NoSuchElementException.class ) 
			.ignoring( StaleElementReferenceException.class);
			WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));

			if(element!=null)
			{				
				exists = true;
				break;
			} 
			else{
				sleepForGivenTime(1000);
			}
		}
		return exists;
	}	


	/**
	 * This function waits for a particular element on a page to be Not visible
	 * @param By locator
	 * @param driver
	 *  
	 */
	public static boolean waitForElementNOTVisible(final By locator, WebDriver driver) {

		boolean exists = false;

		for(int i=0;i<timeoutSec;i=i+1)
		{
			FluentWait<WebDriver> wait = new WebDriverWait(driver, timeoutSec)
			.withTimeout(timeoutSec, TimeUnit.SECONDS)
			.pollingEvery(5, TimeUnit.SECONDS)
			.ignoring( NoSuchElementException.class ) 
			.ignoring( StaleElementReferenceException.class);
			Boolean element = wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));

			if(element!=false)
			{				
				exists = true;
				break;
			} 
			else{
				sleepForGivenTime(1000);
			}
		}
		return exists;
	}		

	/**
	 * The waitForTextPresent function will wait for the text for a
	 * default duration of customized seconds To increase or decrease this time
	 * change the value of the integer 'timeoutSec' in "Common.java"
	 * 
	 * @param locator
	 * @param selenium
	 * @return boolean
	 */
	public static boolean waitForTextPresent(String text, WebDriver driver) {

		boolean exists = false;
		System.out.println("wait for text present is reached");

		for (int second = 0;; second++) {

			if (second >= timeoutSec) Assert.fail("timeout");

			try { 

				if (driver.findElement(By.tagName("body")).getText().contains(text)){
					exists = true;
					break;
				}
				else{
					sleepForGivenTime(1000);
				}	

			}
			catch (Exception e) {
				e.printStackTrace();
			}

		}

		System.out.println(driver.findElement(By.tagName("body")).getText().contains(text));
		System.out.println("eneded: wait for text present is reached");
		return exists;
	}

	/**
	 * This function checks if element is available on page.
	 * 
	 * @param locator
	 * @param driver
	 * @return boolean
	 * @throws Exception 
	 */
	public static boolean isElementPresent(By locator,WebDriver driver) {

		if(driver.findElements(locator).size()!= 0){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * This function checks if element is visible on page.
	 * @param locator
	 * @param driver
	 * @return
	 */
	public static boolean isElementVisible(By locator,WebDriver driver) {
			try {
			
				WebElement myDynamicElement = (new WebDriverWait(driver, 10))
				  .until(ExpectedConditions.visibilityOf(driver.findElement(locator)));
				return true;
			} catch (NoSuchElementException e) {
				return false;
			}
		}

	/**
	 * Wait for a page to loaded it takes timeout as argument if page is not loaded in given timeout
	 * it throws an exception which is handled here it takes two arguments
	 * @param timeout
	 * @param selenium
	 */
	public static void waitForPageToLoadUsingParameter(By locator,WebDriver driver)
	{
		boolean status = true;
		while(status){
			if(driver.findElement(locator) != null){
				status = false;
			}else{
				sleepForGivenTime(1000);
			}
		}	
	}

	/**
	 * This function checks whether particular text over a page is present or not
	 * @param string
	 * @param driver
	 * @return boolean
	 */

	public static boolean isTextPresent(String string, WebDriver driver) {

		for (WebElement e : driver.findElements(By.tagName("body"))) {

			if (e.isDisplayed() && e.getText().contains(string)) {
				return true;

			}

		}
		return false;
	}

	/**
	 * The clickAndWait function will wait for a default time of customized
	 * milliseconds To increase or decrease this time change the value of the
	 * string 'timeout' in "Common.java"
	 * 
	 * @param locator
	 * @param selenium
	 */

	public static void clickAndWait(By locator, WebDriver driver) {
		//waitForElementPresent(locator, driver);
		waitForElementVisible(locator, driver);
		driver.findElement(locator).click();
		WaitForPageToLoad waitForPageToLoad=new WaitForPageToLoad();
		waitForPageToLoad.getReadyStateUsingWait(driver);
	}

	/**
	 * This function generates random number 
	 * @param digits
	 * @return
	 */
	public static int getRandomNumber(){
		Random rand = new Random();
		int randNumber =1;
		randNumber = rand.nextInt(1000000000);
		return(randNumber);
	}


	/*
	 * This function checks is the element displayed is a link or not. Returns true if the element is a link false if otherwise
	 */
	public static boolean isThisLink(By by,String aCssLocator, WebDriver driver)
	{
		boolean flag=false;
		try
		{
			if(!driver.findElement(by).getAttribute(aCssLocator+"@href").isEmpty())
			{
				flag=true;
			}
		}
		catch(Exception e)
		{
			flag=false;
		}
		return flag;
	}


	/**
	 * This function pauses the running thread according to the wait time
	 * specified.
	 */
	public static void pause() {	
		UtilityCommon.sleepForGivenTime(wait);
	}	


	/**
	 * This  function returns the count of links
	 * 
	 *
	 */

	public static int getCssCount(By locator,WebDriver driver)  {

			return driver.findElements(locator).size();
	}


	/**
	 * Gets a new generated name which is sum of text+system date+ 3 random digits
	 * 
	 * @param title
	 * @return
	 */
	public static String getNewName(String title){
		Random rand = new Random();
		int value = rand.nextInt(100);
		title = title+new SimpleDateFormat("yyyyMMdd").format(new Date())+"-"+Integer.toString(value);
		return title;
	}


	/**
	 * Get the current Time stamp in the format EEE,ddMMMyyyy-HHmmss
	 * example Tue,13Mar2012-155048

	 */
	public static String getTimeStamp()
	{
		DateFormat dateFormat = new SimpleDateFormat("EEE,ddMMMyyyy-HHmmss");
		Calendar cal = Calendar.getInstance();
		return dateFormat.format(cal.getTime());
	}



	/**
	 * The waitForTimer function will wait for the text for a
	 * default duration of customized seconds To increase or decrease this time
	 * change the value of the integer 'timeoutSec' in "Common.java"
	 * 
	 * @param text
	 * @param driver
	 * @throws InterruptedException 
	 */
	public static void waitForTimer(String text, WebDriver driver) throws Exception {
		for (int second = 0;; second++) {
			if (second >= timeoutSec) Assert.fail("timeout");
			try { 
				if (UtilityCommon.isTextPresent(text,driver)){}
				else{
					break; 
				}
			} catch (Exception e) {}
			try {
				Thread.sleep(sleep);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}



	/**
	 * Method wait for a visible item to hide 
	 * @param locator
	 * @param selenium
	 * @throws Exception
	 */
	public static boolean waitForItemToHide(By locator,WebDriver driver) throws Exception
	{
		boolean visible=false;
		//first if element is not visible wait for some time to become it visible
		if(!UtilityCommon.waitForElementVisible(locator, driver))
		{
			UtilityCommon.pause();
		}
		for(int i=0;i<60;i++)
		{
			if(UtilityCommon.waitForElementVisible(locator, driver)){
				Thread.sleep(1000);
				visible=true;
			}

			else
				break;
		}
		return visible;
	}



	/**
	 * This function checks locator is present & then selects the label from the locator.
	 * 
	 * @param by
	 * @param text
	 * @param driver
	 */
	public static void selectOption(By by, String text,WebDriver driver) {
		waitForElementVisible(by, driver);
		UtilityCommon.waitForElementVisible(by, driver);
		Select select = new Select(driver.findElement(by));		
		select.selectByVisibleText(text);

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

	public static StringBuffer stringGenerator() {

		final int STRING_LENGTH = 3;

		StringBuffer sb = new StringBuffer();

		for (int x = 0; x < STRING_LENGTH; x++) {
			sb.append((char) ((int) (Math.random() * 26) + 97));
		}
		System.out.println(sb.toString());
		return (sb);

	}





	/*
	 * This function checks if the elements in the array are arranged in alphabetical order or not. It returns true if the elements  are arranged in alphabetical order and false if not.	
	 */
	public static boolean verifyAscendingSort(String args[])
	{
		boolean flag=false;
		for (int i=0; i<(args.length-1);i++)
		{
			if(args[i].compareTo(args[i+1])<0){
				flag=true;			
			}
			else{
				flag=false;
				break;
			}
		}
		return flag;

	}

	/**
	 * This function is used to read object value from properties file 
	 * @param is    path of the properties file
	 * @param key   objectname
	 * @return  the objectvalue
	 * @throws Exception
	 */

	public static String getValue(InputStream is, String key) throws Exception{


		Properties prop = new Properties();
		prop.load(is);		
		String value = prop.getProperty(key).trim();		
		return(value);
	}


	/*
	 * Get all data provided in a TESTNG table of excel
	 */
	public static String[][] getTableArray(InputStream xlFilePath, String sheetName, String startTable,String endTable){
		String[][] tabArray=null;
		try{
			Workbook workbook = Workbook.getWorkbook(xlFilePath);
			Sheet sheet = workbook.getSheet(sheetName);
			int startRow,startCol, endRow, endCol,ci,cj;
			Cell tableStart=sheet.findCell(startTable);
			startRow=tableStart.getRow();
			startCol=tableStart.getColumn();

			Cell tableEnd= sheet.findCell(endTable);
			endRow=tableEnd.getRow();
			endCol=tableEnd.getColumn();
			System.out.println("startRow="+startRow+", endRow="+endRow+", " +
					"startCol="+startCol+", endCol="+endCol);
			tabArray=new String[endRow-startRow-1][endCol-startCol-1];
			ci=0;

			for (int i=startRow+1;i<endRow;i++,ci++){
				cj=0;
				for (int j=startCol+1;j<endCol;j++,cj++){
					tabArray[ci][cj]=sheet.getCell(j,i).getContents();
				}
			}
		}
		catch (Exception e)    {
			System.out.println("error in getTableArray()");
			System.out.println(e.getStackTrace());
		}

		return(tabArray);
	}

	/**
	 * This Test Script writes Test status in Excel File.
	 * @param updateFiles
	 * @param updateFileSheet
	 * @param status
	 * @param testName
	 * @throws Exception
	 * @throws Exception
	 */

	public static void updateInExcel(URL updateFiles,String updateFileSheet,String status,String testName) throws Exception
	{
		String filePath1 = "";
		try{

			//URL updateFiles = getClass().getResource(updateStatusFile);
			filePath1=updateFiles.toString().replace("file:/", " ").replace("%20", " ").trim();

			Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			String myDB = "jdbc:odbc:Driver={Microsoft Excel Driver (*.xls)};DBQ="+filePath1+ ";DriverID=22;READONLY=false";
			Connection con = DriverManager.getConnection(myDB);
			Statement stat=con.createStatement();
			//ResultSet rs = stat.executeQuery( "select * from ["+updateFileSheet+"$] " );
			stat.executeUpdate("update ["+updateFileSheet+"$] set [Execution Status]='"+status+"' where [AutomationTestCaseID] ='"+testName+"';");
			stat.close();
			con.close();
		}
		catch (Exception e) 
		{
			if(e.getMessage().contains("Too few parameters"))
			{
				Reporter.log("File "+filePath1+" does not has Site Title column");
			}
			else
			{
				Reporter.log("Sheet name not updated in file : "+filePath1);
				Reporter.log(e.getMessage());
			}
		}
	}



	/**
	 * This function selects any random option for the Select Locator
	 * @param selectLocator
	 * @param selenium
	 */
	public static void selectOptionRandomly(By selectLocator,WebDriver driver){

		UtilityCommon.waitForElementPresent(selectLocator, driver);
		WebElement droplist = driver.findElement(selectLocator);
		List<WebElement> options = droplist.findElements(By.tagName("option"));
		System.out.println(options.size());
		System.out.println(options.toString());
		Random rand = new Random();
		int randomSelect = 0;
		randomSelect = rand.nextInt(options.size());
		Select select = new Select(driver.findElement(selectLocator));
		select.selectByIndex(randomSelect);
	}




	/**
	 * This function selects any random option for the Select Locator
	 * @param selectLocator
	 * @param selenium
	 */
	public static void selectOptionRandomlyDemo(By arrowClick,String selectLocator,WebDriver driver){

		//UtilityCommon.waitForElementPresent(selectLocator, driver);
		WebElement droplist = driver.findElement(By.cssSelector(selectLocator));
		List<WebElement> options = droplist.findElements(By.tagName("li"));
		System.out.println(options.size());
		System.out.println(options.toString());
		Random rand = new Random();
		int randomSelect = 0;
		//randomSelect = rand.nextInt(options.size());

		randomSelect = rand.nextInt(4)+1;
		randomSelect=(randomSelect+1);
		System.out.println(randomSelect+1);
		driver.findElement(arrowClick).click();
		//String finalLocator=selectLocator+">"+"li:nth-child("+randomSelect+")";
		UtilityCommon.pause();
		driver.findElement(By.cssSelector(selectLocator+">"+"li:nth-child("+randomSelect+")")).click();


	}





	/**
	 * This function is used to select specific values from drop down
	 * @param valToBeSelected
	 * @param driver
	 */
	public static void selectValue(By arrowClick,String valToBeSelected,WebDriver driver){
		try{

			driver.findElement(arrowClick).click();
			UtilityCommon.waitForElementPresent(By.xpath("//li[contains(text(),'"+valToBeSelected+"')]"), driver);
			driver.findElement(By.xpath("//li[contains(text(),'"+valToBeSelected+"')]")).click();
			UtilityCommon.waitForPageToLoad(driver);
		} catch (Exception e) {
			e.getMessage();
		}		
	}



	public static void selectValueJava(By arrowClick,String valToBeSelected,WebDriver driver)
	{
		try {
			JavascriptExecutor jse = (JavascriptExecutor)driver;
			jse.executeScript("window.scrollBy(0,300)", "");
			driver.findElement(arrowClick).click();
			WebElement targetElement = driver.findElement(By.xpath("//div[@id='assignment_create_assignmentSettings_numberAttempts_chzn']/div[@class='chzn-drop']/div[@class='slimScrollDiv']/ul[@class='chzn-results']/li[contains(text(),'"+valToBeSelected+"')]"));  
			JavascriptExecutor js = (JavascriptExecutor) driver;  
			String mouseOverScript = "if(document.createEvent){var evObj = document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false); arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";  
			js.executeScript(mouseOverScript, targetElement);  
			driver.findElement(By.xpath("//div[@id='assignment_create_assignmentSettings_numberAttempts_chzn']/div[@class='chzn-drop']/div[@class='slimScrollDiv']/ul[@class='chzn-results']/li[contains(text(),'"+valToBeSelected+"')]")).click();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}






	/**
	 * This function is used to select specific values from drop down
	 * @param valToBeSelected
	 * @param driver
	 */
	public static void selectValueTest(String valToBeSelected,WebDriver driver){
		List <WebElement> options = driver.findElements(By.tagName("option"));
		for (WebElement option : options) {
			if (valToBeSelected.equalsIgnoreCase(option.getText())){
				option.click();
				break;
			}
		}
	}


	//dummy method

	public static String getselectedValueTest(By selectLocator,WebDriver driver){
		String dropdown = driver.findElement(selectLocator).getText();
		String[] drop=dropdown.split("\n");
		String firstOption=drop[0];
		return firstOption;
	}





	public static String getselectedValue(By selectLocator,WebDriver driver){
		Select select = new Select(driver.findElement(selectLocator));
		String firstOption=select.getFirstSelectedOption().getText();
		System.out.println(select.getFirstSelectedOption().getText());

		return firstOption;
	}





	/**
	 * 
	 * @param selectLocator
	 * @param driver
	 * @return All the values in the drop-down are displayed.
	 */
	public static  List<String> optionsValues(By clicArrow,By selectLocator,WebDriver driver){

		//driver.findElement(clicArrow).click();	
		String dropdown = driver.findElement(selectLocator).getText();
		String[] drop=dropdown.split("\n");
		String abc="";
		ArrayList<String> data = new ArrayList<String>();
		for(int i=0;i<drop.length;i++)  
		{  
			abc=drop[i];
			System.out.println(drop[i]);  
			data.add(abc);
		} 
		return data;
	}


	/**
	 *   This method gets you the all the values from the dropdown
	 * @param cssPath
	 * @param driver
	 * @return
	 */
	public static  List<String> getValuesFromDropdown(String cssPath,WebDriver driver){
		int cssCount = UtilityCommon.getCssCount(By.cssSelector(cssPath), driver);
		ArrayList<String> data = new ArrayList<String>(cssCount);
		for(int i=1;i<=cssCount;i++)  
		{  
			System.out.println(driver.findElement(By.cssSelector(cssPath+":nth-child("+i+")")).getText().toString());
			data.add(driver.findElement(By.cssSelector(cssPath+":nth-child("+i+")")).getText().toString());
		}
		return data;
	}

	/**
	 * This function selects any random option except the specified one for the Select Locator
	 * @param selectLocator
	 * @param selenium
	 */
	public static void doNotselectSpecificOption(By selectLocator,String option,WebDriver driver){

		UtilityCommon.waitForElementPresent(selectLocator, driver);
		WebElement droplist = driver.findElement(selectLocator);
		List<WebElement> options = droplist.findElements(By.tagName("option"));
		System.out.println(options.size());
		System.out.println(options.toString());
		Random rand = new Random();
		int randomSelect;
		randomSelect =0;
		randomSelect = rand.nextInt(options.size());
		UtilityCommon.waitForElementPresent(selectLocator, driver);

		do{	
			Select select = new Select(driver.findElement(selectLocator));
			select.selectByIndex(randomSelect );
		}while(options.get(randomSelect).equals(option));
	}

	/**
	 * This Function drags And Drop elements from from one location to another
	 * @param driver
	 * @param from
	 * @param to
	 */

	public static void dragAndDrop(By ByFrom, By ByTo,WebDriver driver) {

		WebElement LocatorFrom = driver.findElement(ByFrom);

		WebElement LocatorTo = driver.findElement(ByTo);

		(new Actions(driver)).dragAndDrop(LocatorFrom, LocatorTo).perform();   

	}

	/**
	 * Function to retrieve current date in MM-DD-YYYY format.
	 * @return
	 */
	public static String getCurrentTime(){

		String s;
		Calendar calendar = Calendar.getInstance();

		Date date = calendar.getTime();
		Format formatter = new SimpleDateFormat("dd MMM yyyy");
		s = formatter.format(date);

		return(s);
	}

	
	public static void setClipboardData(String string) {
		StringSelection stringSelection = new StringSelection(string);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
	}


	/*
		public String placeMouseOverToolTipandGetText() throws InterruptedException{ 
			builder.clickAndHold(driver.findElement(By.className("locator of pie chart"))).build().perform(); 
			Thread.sleep(2000); 
			return driver.findElement(By.xpath("locator of tooltip")).getText(); 
			}
	 */

	public static final int MIN_STRING_LENGTH = 6;  
	public static final int MIN_INTEGER_LENGTH = 9; //USN will be 9 digits. Do not specify length more than 9.
	protected static java.util.Random r = new java.util.Random();  

	/* Set of characters that is valid. Must be printable, memorable, 
	 * and "won't break HTML" (i.e., not '<', '>', '&', '=', ...). 
	 * or break shell commands (i.e., not '<', '>', '$', '!', ...). 
	 * I, L and O are good to leave out, as are numeric zero and one. 
	 */  
	protected static char[] characters = { 
		'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'j', 'k', 'm', 'n',  
		'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',  
	};  

	protected static char[] integers = {  

		'1', '2', '3', '4', '5', '6', '7', '8', '9', '0'
	};

	/* Generate a Password object with a random password. */  
	public static String getRandomString() {  
		return getRandomString(MIN_STRING_LENGTH);  
	}  


	/* Generate a Password object with a random password. */  
	public static String getRandomString(int length) {  
		if (length < 1) {  
			throw new IllegalArgumentException(  
					"Ridiculous password length " + length);  
		}  
		StringBuffer sb = new StringBuffer();  
		for (int i = 0; i < length; i++) {  
			sb.append(characters[r.nextInt(characters.length)]);  
		}  
		return sb.toString();  
	} 

	public static int getNextInteger() {
		return getNextInteger(MIN_INTEGER_LENGTH);  
	}  

	/* Generate a Password object with a random password. */  
	public static int getNextInteger(int length) {  
		if (length < 1) {  
			throw new IllegalArgumentException(  
					"Ridiculous password length " + length);  
		}  
		StringBuffer sb = new StringBuffer();  
		for (int i = 0; i < length; i++) {  
			sb.append(integers[r.nextInt(integers.length)]);  
		}  
		return Integer.parseInt(sb.toString());  
	} 





	public static boolean WaitForpageLoadToLoad( WebDriver driver,long seconds,By by){
		WebDriverWait wait = new WebDriverWait(driver, seconds);
		WebElement returned=wait.until(ExpectedConditions.visibilityOfElementLocated(by));			   
		return returned.isDisplayed();
	}

	/**
	 * This method waits for 15 seconds
	 */
	public static void waitForFifteenSeconds(){
		for(int i=1; i<=3;i++){
			pause();
		}
	}

	/**
	 *  this method waits for timeoutSec before it works on next line of code
	 * @param timeoutSec
	 * @param driver
	 * @throws Exception
	 */
	public static void implictWait(long timeoutSec,WebDriver driver) throws Exception{

		driver.manage().timeouts().implicitlyWait(timeoutSec, TimeUnit.SECONDS);

	}



		/**
	 * The function returns true if the element is displayed
	 * else returns false.
	 * @param driver
	 * @param locator
	 * @return
	 */
	public static boolean isElementDisplayed(WebDriver driver, By locator){
		try{
			driver.findElement(locator);
			return true; 
		}catch(org.openqa.selenium.NoSuchElementException  ne){
		}
		return false;
	}

	public static void clearAndEnterValuesForTxtBox(String text,By locator,WebDriver driver){
		UtilityCommon.waitForElementPresent(locator, driver);
		driver.findElement(locator).clear();
		driver.findElement(locator).sendKeys(text);
	}


	/**
	 *  Drags & drop the value
	 * @param ByFrom
	 * @param ByTo
	 * @param driver
	 */
	public static void dragDrop(By ByFrom, By ByTo,WebDriver driver) {
		UtilityCommon.waitForElementPresent(ByFrom, driver);
		WebElement LocatorFrom = driver.findElement(ByFrom);
		WebElement LocatorTo = driver.findElement(ByTo);
		Actions builder = new Actions(driver);
		Action dragAndDrop = builder.clickAndHold(LocatorFrom)
		.moveToElement(LocatorTo)
		.release(LocatorTo)
		.build();
		dragAndDrop.perform();


	}




	/**
	 *  This method is used to select Number of attempts before answer button is shown drop down values
	 * @param arrowClick
	 * @param value
	 * @param driver
	 * @throws Exception
	 */


	public static void selectValueNOTBABIS(By arrowClick,final String value,WebDriver driver) throws Exception {
		driver.findElement(arrowClick).click();
		int dropDownValues=UtilityCommon.getCssCount(By.cssSelector("div#assignment_create_assignmentSettings_numberAttemptsBeforeCorrect_chzn>div.chzn-drop>div>ul.chzn-results>li.active-result"),driver);
		for (int i=0;i<dropDownValues;i++) {
			String valueToselect=driver.findElements(By.cssSelector("div#assignment_create_assignmentSettings_numberAttemptsBeforeCorrect_chzn>div.chzn-drop>div>ul.chzn-results>li.active-result")).get(i).getText();
			if (value.equals(valueToselect)) {
				driver.findElements(By.cssSelector("div#assignment_create_assignmentSettings_numberAttemptsBeforeCorrect_chzn>div.chzn-drop>div>ul.chzn-results>li.active-result")).get(i).click();
				break;
			}
		}

	}





	/**
	 *  This method is used to select Number of attempts for activity drop down values
	 * @param arrowClick
	 * @param value
	 * @param driver
	 * @throws Exception
	 */


	public static void selectValueNOAFA(By arrowClick,final String value,WebDriver driver) throws Exception {
		driver.findElement(arrowClick).click();
		int dropDownValues=UtilityCommon.getCssCount(By.cssSelector("div#assignment_create_assignmentSettings_numberAttempts_chzn>div>div>ul.chzn-results>li.active-result"),driver);
		for (int i=0;i<dropDownValues;i++) {
			String valueToselect=driver.findElements(By.cssSelector("div#assignment_create_assignmentSettings_numberAttempts_chzn>div>div>ul.chzn-results>li.active-result")).get(i).getText();
			if (value.equals(valueToselect)) {
				driver.findElements(By.cssSelector("div#assignment_create_assignmentSettings_numberAttempts_chzn>div>div>ul.chzn-results>li.active-result")).get(i).click();
				break;
			}
		}

	}







	/**
	 *  This method is used to select Change View drop down values
	 * @param arrowClick
	 * @param value
	 * @param driver
	 * @throws Exception
	 */


	public static void selectValueChangeView(By arrowClick,final String value,WebDriver driver) throws Exception {
		driver.findElement(arrowClick).click();
		UtilityCommon.waitForElementPresent(By.cssSelector("div#changeView_chzn>div>div>ul.chzn-results>li.active-result"), driver);
		int dropDownValues=UtilityCommon.getCssCount(By.cssSelector("div#changeView_chzn>div>div>ul.chzn-results>li.active-result"),driver);
		for (int i=0;i<dropDownValues;i++) {
			UtilityCommon.waitForElementPresent(By.cssSelector("div#changeView_chzn>div>div>ul.chzn-results>li.active-result"), driver);
			String valueToselect=driver.findElements(By.cssSelector("div#changeView_chzn>div>div>ul.chzn-results>li.active-result")).get(i).getText();
			if (value.equals(valueToselect)) {
				driver.findElements(By.cssSelector("div#changeView_chzn>div>div>ul.chzn-results>li.active-result")).get(i).click();
				break;
			}
		}

	}



	/**
	 *  This method is used to select Number of attempts for activity drop down values
	 * @param arrowClick
	 * @param value
	 * @param driver
	 * @throws Exception
	 */


	public static void selectValueRandom(By arrowClick,By dropDownTypeObject,WebDriver driver) throws Exception {
		try{
			driver.findElement(arrowClick).click();
			int dropDownValues=UtilityCommon.getCssCount(dropDownTypeObject,driver);
			Random rand = new Random();
			int randomSelect;
			randomSelect =0;
			randomSelect = rand.nextInt(dropDownValues);	
			driver.findElements(dropDownTypeObject).get(randomSelect).click();
		}catch (NoSuchElementException e) {
			System.out.println(e.getMessage());

		}
	}

	public static void selectValueFromScroll(By arrowClick,By unorderedList, final String value, WebDriver driver){
		driver.findElement(arrowClick).click();
		List<WebElement> options = driver.findElement(unorderedList).findElements(By.tagName("li"));

		for (WebElement option : options) {
			if (value.equals(option.getText())) {
				option.click();
				break;
			}
		}
	}

	/**
	 * Highlight element.
	 * @param by
	 */
	public static void highlightElement(WebDriver driver, By by) { 
		WebElement element= driver.findElement(by);
		for (int i = 0; i < 2; i++) { 
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);",
					element, "color: yellow; border: 2px solid yellow;"); 
			js.executeScript("arguments[0].setAttribute('style', arguments[1]);", element, "");
		} 
	}


	public static void statusUpdate(boolean flag,String str) throws Exception{

		if(flag)
			Reporter.log("<br><b><font color='green'>Test Case "+ str+" Pass</b><br>");
		else
			Reporter.log("<br><b><font color='red'> Test Case "+str+" Fail</b><br>");

	}

	/**
	 * Wait for the element to be displayed and enter value.
	 * @param by
	 * @param value
	 * @param driver
	 */
	public static void enterValue(By by,String value,WebDriver driver){
		UtilityCommon.waitForElementPresent(by, driver);
		driver.findElement(by).clear();
		driver.findElement(by).sendKeys(value);
	}
	
	
	public static void waitForPageToLoad(WebDriver driver){
		ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {
        public Boolean apply(WebDriver driver) {
          return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
        }
      };
      WebDriverWait wait = new WebDriverWait(driver, 30);
      try {
              wait.until(expectation);
      } catch(Throwable error) {
    	  error.getMessage();
      }
      }
	
	public static void scrollToElementAndClick(By sliderLocator, By locator, WebDriver driver){
		WebElement slider=driver.findElement(sliderLocator) ;
		Actions builder=new Actions(driver);
		slider=driver.findElement(sliderLocator) ;
		int y=40;
		boolean flag= false;
		try{
			do{
				
				builder.clickAndHold(slider).moveByOffset(0, y).build().perform();
				y=y+40;
				UtilityCommon.pause();
				builder.release().perform();
				if(isElementVisible(locator, driver)){
					driver.findElement(locator).click();
					flag=true;
				}
				//if(isElementDisplayed(driver, locator))
			}while(flag==false);
		}catch (Exception e) {
			// TODO: handle exception
		}

	} 
	
	/**
	 * This function is to get the next alphabet in english eg : if you give A it will give B
	 * 
	 */
	public static String  nextAlphabetInEnglish(String alphabhet) {
	
		String value = alphabhet;
		int charValue = value.charAt(0);
		String next = String.valueOf( (char) (charValue + 1));
		System.out.println(next);
		return next;
	}
}
