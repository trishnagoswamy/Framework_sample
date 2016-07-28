package com.dt.rts.ca.util;




import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
import java.util.logging.Logger;

public class Configuration {
    
    private static Properties properties;

    public enum Browser {
        InternetExplorer, Firefox, Chrome, Safari,Opera
    }
    
    public static void loadConfiguration(String environment) {
        try {
            properties = new Properties();
            properties.load(new FileInputStream(new File("src/test/resources/config/" +environment +".properties")));
        } catch(Exception e) {
           // Logger.fatal(e);
        }
    }
    
    public static String getProperty(String propertyName) {
        return properties.getProperty(propertyName);
    }
    
    
     public static Browser browser() {
    	
    	 String browserFromConfig = getProperty("default.browser1");
    	 Browser browser = null;
    	 
        try {
        	
                browser = determineBrowser(browserFromConfig);
               
        } catch(Throwable t) {
            //Logger.fatal(t);
        }

        return browser;
    }

    private static Browser determineBrowser(String browserName) throws Throwable {
        if(browserName.equalsIgnoreCase("firefox")) {
            return Browser.Firefox;
        } else if(browserName.equalsIgnoreCase("ie")) {
            return Browser.InternetExplorer;
        } else if(browserName.equalsIgnoreCase("chrome")) {
            return Browser.Chrome;
        } else if(browserName.equalsIgnoreCase("safari")) {
                return Browser.Safari;
        }  else if(browserName.equalsIgnoreCase("opera")) {
            return Browser.Opera;
        }
        else {
            throw new Throwable("Unsupported Browser : " + browserName);
        }
    }
}
