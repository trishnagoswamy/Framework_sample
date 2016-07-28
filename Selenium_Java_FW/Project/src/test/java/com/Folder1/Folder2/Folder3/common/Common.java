package com.dt.rts.ca.common;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.internal.Locatable;
import org.testng.Assert;
import org.testng.Reporter;

import com.dt.rts.ca.util.*;

public class Common{
	
	public static Configuration config;
	public static Driver driver;
	
	public static String baseurl;
	
	public static void setURL(){
		baseurl= config.getProperty("applicationBaseURL");
	}
	
	public static void getScreenLocation(By locator,WebDriver driver) {
		try {
			WebElement we = driver.findElement(locator);
			System.out.println(we.getText() + " - " + we.isEnabled() + " - "+ we.isDisplayed());
			//((Locatable) we).getCoordinates().getLocationOnScreen();
			((Locatable) we).getCoordinates().inViewPort();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	
}
