package com.dt.rts.ca.filesTest;

import java.util.concurrent.TimeUnit;

import junit.framework.Assert;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.dt.rts.ca.util.Configuration;
import com.dt.rts.ca.util.UtilityCommon;
import com.dt.rts.ca.util.utilityExcel;
import com.dt.rts.ca.util.utilityPDF;


public class ReadPDF extends utilityPDF{
	
	WebDriver driver;
	String inputFilePath, reportData;
	Configuration config= new Configuration();
	
	@BeforeClass
	public void setUp() {
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		config.loadConfiguration("global");
		
	}
	
	/**
	 * To verify Online PDF content in the pdf document
	 * @throws Exception 
	 
	@Test
	public void testVerifyPDFTextInBrowser() {
		
		driver.get("http://www.princexml.com/samples/");
		driver.findElement(By.linkText("PDF flyer")).click();
		UtilityCommon.waitForPageToLoad(driver);
		Assert.assertTrue(verifyPDFContent(driver.getCurrentUrl(), "Prince Cascading"));
	}
	
	/**
	 * To verify Local PDF content in the pdf document
	 */
	
	@Test
	public void testVerifyPDFTextLocal() throws Exception {
		
		/*
		 * inputFilePath = getClass().getResource(config.getProperty("testData")).toString().replace("file:/", "");
		 
		String[][] data=utilityExcel.readDataFromExcel(inputFilePath, "Sheet1");
		*/
		driver.get("file:///C:/Users/P7165028/Desktop/DT/Sample Reports/DealerDebit_UserReport.pdf");
		UtilityCommon.waitForFifteenSeconds();
		/*
		for(int i=1;i<data.length;i++){
			reportData = data[i][1];
			System.out.print(reportData);
			Assert.assertTrue(verifyPDFContent(driver.getCurrentUrl(), reportData));
		}
		*/
		//utilityExcel.getCellData("C:/Users/P7165028/Desktop/DT/Sample Reports/TestData.xls", "Sheet 1", 1, 1);
		
		Assert.assertTrue(verifyPDFContent(driver.getCurrentUrl(), "Report Date: 08/10/2015"));
	}
	
 
	/**
	 * To verify pdf in the URL 
	 
	@Test
	public void testVerifyPDFInURL() {
		driver.get("http://www.princexml.com/samples/");
		driver.findElement(By.linkText("PDF flyer")).click();
		String getURL = driver.getCurrentUrl();
		Assert.assertTrue(getURL.contains(".pdf"));
	} */

	
	
	
	@AfterClass
	public void tearDown() {
		driver.quit();
	}
	
}