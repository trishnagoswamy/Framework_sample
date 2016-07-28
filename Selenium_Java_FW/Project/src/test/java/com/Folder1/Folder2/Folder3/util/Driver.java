package com.dt.rts.ca.util;


import java.io.File;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.log4testng.Logger;

public class Driver {
    private static WebDriver driver;
    
   //private WebDriver driver;

    public WebDriver initializeDriver() {
        //Logger.info("Initializing driver...");
   
        try {
    
            switch(Configuration.browser()) {
                case InternetExplorer:
                    //Logger.info("(Initializing Internet Explorer...");                	
                	System.setProperty("webdriver.ie.driver", "src/test/resources/test/IEDriverServer.exe");
                	 DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();   
                	ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                    driver = new InternetExplorerDriver(ieCapabilities);
                   // driver.navigate().to("http://www.msn.com");
                    break;
                case Firefox:
                   // Logger.info("(Initializing Firefox...");
                    driver = new FirefoxDriver();
                    break;
                case Chrome:
                	System.setProperty("webdriver.chrome.driver", "src/test/resources/test/chromedriver.exe");
                	 DesiredCapabilities chromeCapabilities = DesiredCapabilities.chrome(); 
                    driver = new ChromeDriver(chromeCapabilities);
                    break;
               /* case Opera:
                	System.setProperty("webdriver.chrome.driver", "src/test/resources/test/chromedriver.exe");
                	driver = new OperaDriver();  
                    break;*/
                case Safari:
                	//System.setProperty("webdriver.safari.driver", "src/test/resources/test/chromedriver.exe");
                	 driver = new SafariDriver();
                    break;
                default:
                   // Logger.error("Unsupported Browser !!! Falling back to Firefox as the default browser...");
                    driver = new FirefoxDriver();
            }
        } catch(Exception e) {
        	e.printStackTrace();
            //Logger.error("Exception occurred while initializing browser !!!");
            //Logger.fatal(e);
        }
		return driver;

       // Logger.info("Initialized driver.");
    }
    
    public synchronized static WebDriver driver() {
        return driver;
    }

    public synchronized static void quit() {
        //Logger.info("Quit browser");
        driver.quit();
    }

    public static void sleep(int seconds) {
        try {
           // Logger.info("Sleeping for " + seconds + " seconds...");
            Thread.currentThread().wait(seconds * 1000);
        } catch(Exception e) {
           // Logger.error("Following exception occurred while sleeping...");
            e.printStackTrace();
        }
    }
}