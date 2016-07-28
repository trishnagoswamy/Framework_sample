package com.dt.rts.ca.home;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import com.dt.rts.ca.home.RtsHomePageObjects.RtsHomePage;
import com.dt.rts.ca.util.Configuration;
import com.dt.rts.ca.util.Driver;
import com.dt.rts.ca.util.TestBase;
import com.dt.rts.ca.util.UtilityCommon;
import com.dt.rts.ca.util.utilityExcel;


public class RtsHomeCommon {
	
	public static String username, password, baseurl;
	public static Configuration config;
	public static Driver driver1;
	
	/**
	 * This method is used for Login to Rts Application
	 * @param username
	 * @param password
	 * @param driver
	 * @throws Exception 
	 */
	@SuppressWarnings("static-access")
	public static void rtsLogin(String username, String password, WebDriver driver) throws Exception{
		
		baseurl= config.getProperty("applicationBaseURL");
		driver.navigate().to(baseurl);
		
		if (!UtilityCommon.waitForElementPresent(RtsHomePage.USERID.byLocator(), driver)){
			Assert.fail("<font color=red>Home page is not been loaded.</font><br>");
		}	
		
		driver.findElement(RtsHomePage.USERID.byLocator()).clear();
		driver.findElement(RtsHomePage.USERID.byLocator()).sendKeys(username);
		driver.findElement(RtsHomePage.PASSWORD.byLocator()).clear();
		driver.findElement(RtsHomePage.PASSWORD.byLocator()).sendKeys(password);
				
		driver.findElement(RtsHomePage.LOGIN.byLocator()).click();
				
	}
	
	public static void mclaneLogout(WebDriver driver){
		
		UtilityCommon.clickAndWait(RtsHomePage.LOGOUT.byLocator(), driver);
	}

}
