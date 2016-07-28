package com.dt.rts.ca.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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
