package com.obm.utilities;

import java.lang.reflect.Method;

import org.apache.commons.configuration.XMLConfiguration;
import org.openqa.selenium.By;

import io.appium.java_client.ios.IOSElement;

public class IOSElementFactory {
	private IOSElementFactory(){}
	
	/**
	 * This method creates and returns a Appium IOS Element based on the identifier provided.
	 * @param identifier (identifier is calculated based on ObjectIdentityConfig.properties)
	 * @return IOSElement
	 * @throws Exception 
	 * @throws NoSuchMethodException 
	 */
	
	public static IOSElement getElement(String identifier) {
		IOSElement iosElement = null;
		XMLConfiguration config = null;
		try {
			config = new XMLConfiguration("configurations/object-config.xml");
			System.out.println("identifier String:"+config.getString(identifier));
			Method method = By.class.getMethod(config.getString(identifier + "[@locator]"), String.class);
			By by = (By) method.invoke(By.class, config.getString(identifier));
			iosElement = IOSDriverFactory.getIOSDriver().findElement(by);			
		} catch (Exception e) {
			System.err.println("Object Not found for IOS Element. Please verify if "+identifier+ " is configered object-config.xml");
			e.printStackTrace();
		} finally{
			config.clear();
		}
		return iosElement;
	}

}
