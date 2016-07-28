package com.dt.rts.ca.Regression;

import org.fest.swing.annotation.GUITest;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.dt.rts.ca.home.RtsHomeCommon;
import com.dt.rts.ca.util.Configuration;
import com.dt.rts.ca.util.Driver;

@GUITest
public class HomePageLogin extends RtsHomeCommon{
	
	public static String username, password, baseurl;
	WebDriver driver;
	 
	
	@SuppressWarnings("static-access")
	@BeforeMethod
	public void setUp(){
		Configuration config= new Configuration();
		config.loadConfiguration("global");
		Driver driver1= new Driver();
		driver = driver1.initializeDriver();
		baseurl = config.getProperty("applicationBaseURL");
		driver.navigate().to(baseurl);
		driver.manage().window().maximize();
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void RtsLogin() throws Exception{
			
		rtsLogin(config.getProperty("username"), config.getProperty("password"), driver);
			
	}
	
	/*
	@AfterMethod
	public void tearDown(){
		driver.quit();
	}
	*/
	

}
