package com.dt.rts.ca.home;

import org.openqa.selenium.By;

public class RtsHomePageObjects {
	
	public enum RtsHomePage{
		
		USERID(By.id("userId"),By.cssSelector("input[ng-model='flexUserID']"),By.xpath("//input[@ng-model='flexUserID']"),By.name("userId"),By.linkText("")),
		PASSWORD(By.id("userPwd"),By.cssSelector("input[ng-model='flexPassword']"),By.xpath("//input[@ng-model='flexPassword']"),By.name("userPwd"),By.linkText("")),
		LOGIN(By.id(""),By.cssSelector("button[ng-click='flexLogin()']"),By.xpath("//button[@ng-click='flexLogin()']"),By.name(""),By.linkText("")),
		FORGOT_PASSWORD(By.id(""),By.cssSelector("button[ng-click='forgotPassword()']"),By.xpath("//button[@ng-click='forgotPassword()']"),By.name(""),By.linkText("")),
		
		
		LOGOUT(By.id(""),By.cssSelector(".LogOutLink"),By.xpath("//a[@class='LogOutLink']"),By.name(""),By.linkText("Log Out")),
		
		;
		
		public By getId() {
			return id;
		}

		public By getCssPathc() {
			return cssPath;
		}

		public By getxPath() {	
			return xPath;
		}

		public By getName() {
			return name;
		}

		public By getLinkText() {
			return linkText;
		}

		private  By id;
		private By cssPath;
		private By xPath;
		private By name;
		private By linkText;
		
		private RtsHomePage(By idLoc, By cssPathLoc, By xPathLoc, By nameObj,By LinkText) {
			id=idLoc;
			cssPath=cssPathLoc;
			xPath=xPathLoc;
			name=nameObj;
			linkText=LinkText;
		}
		
		public By byLocator() {
			if(id.equals(By.id(""))){
				if(cssPath.equals(By.cssSelector(""))){
					if(name.equals(By.name(""))){
						if(linkText.equals(By.linkText("")))
							return(xPath);
						else
							return linkText;
					}else{
						return(name);
					}
				}else{
					return(cssPath);
				}
			}else{
				return(id);
			}
		
		}
	
	}

}
