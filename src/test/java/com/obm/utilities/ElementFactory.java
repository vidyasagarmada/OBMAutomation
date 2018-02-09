package com.obm.utilities;

import java.lang.reflect.Method;

import org.apache.commons.configuration.XMLConfiguration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ElementFactory {

	private ElementFactory(){}
	
	/**
	 * This method creates and returns a Selenium WebElement based on the identifier provided.
	 * @param identifier (identifier is calculated based on ObjectIdentityConfig.properties)
	 * @return WebElement
	 * @throws Exception 
	 * @throws NoSuchMethodException 
	 */
	public static WebElement getElement(String identifier) {
		WebElement element = null;
		XMLConfiguration config = null;
		try {
			config = new XMLConfiguration("configurations/object-config.xml");
			System.out.println("identifier String:"+config.getString(identifier));
			Method method = By.class.getMethod(config.getString(identifier + "[@locator]"), String.class);
			By by = (By) method.invoke(By.class, config.getString(identifier));
			element = DriverFactory.getDriver().findElement(by);			
		} catch (Exception e) {
			System.err.println("Object Not found. Please verify if "+identifier+ " is configered object-config.xml");
			e.printStackTrace();
		} finally{
			config.clear();
		}
		return element;
	}
}
