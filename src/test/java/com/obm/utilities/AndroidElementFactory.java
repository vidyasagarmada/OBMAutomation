package com.obm.utilities;

import java.lang.reflect.Method;

import org.apache.commons.configuration.XMLConfiguration;
import org.openqa.selenium.By;

import io.appium.java_client.android.AndroidElement;

public class AndroidElementFactory {
	
	private AndroidElementFactory(){}
	
	/**
	 * This method creates and returns a Appium Android Element based on the identifier provided.
	 * @param identifier (identifier is calculated based on ObjectIdentityConfig.properties)
	 * @return WebElement
	 * @throws Exception 
	 * @throws NoSuchMethodException 
	 */
	
	public static AndroidElement getElement(String identifier) {
		AndroidElement androidElement = null;
		XMLConfiguration config = null;
		try {
			config = new XMLConfiguration("configurations/object-config.xml");
			System.out.println("identifier String:"+config.getString(identifier));
			Method method = By.class.getMethod(config.getString(identifier + "[@locator]"), String.class);
			By by = (By) method.invoke(By.class, config.getString(identifier));
			androidElement = AndroidDriverFactory.getAndroidDriver().findElement(by);	
			
		} catch (Exception e) {
			System.err.println("Object Not found. Please verify if "+identifier+ " is configered object-config.xml");
			e.printStackTrace();
		} finally{
			config.clear();
		}
		return androidElement;
	}

}
