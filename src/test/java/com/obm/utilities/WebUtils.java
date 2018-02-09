package com.obm.utilities;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.google.common.primitives.Ints;

public class WebUtils{

	
	public static void switchpopups(String parentwindowhandle, WebDriver driver)
	{
		
		Set<String> windowhandles = driver.getWindowHandles();
		for(String windowhandle:windowhandles)
		{
			if(!windowhandle.equalsIgnoreCase(parentwindowhandle))
			{
				driver.switchTo().window(windowhandle);
				System.out.println("Popup title:"+driver.getTitle());
				driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
			}
		}
	}
	public static void switchparentwindow(WebDriver driver)
	{
		
		Set<String> windowhandles = driver.getWindowHandles();
		for(String windowhandle:windowhandles)
		{
			{
				driver.switchTo().window(windowhandle);
				driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
			}
		}
	}
	
	public static String getTableRowAsCommaSeperatedString(WebElement row, int[] columnsToIgnore)
	{
		System.out.println("row Text:"+row.getText());
		String returnValue = "";
		List<WebElement> TDs = row.findElements(By.tagName("td"));
		for(int i=0;i<TDs.size();i++)
		{
			if(Ints.contains(columnsToIgnore, i+1))
			{
				continue;
			}
			WebElement td = TDs.get(i);
			returnValue+=" "+td.getText();
		}
		returnValue = returnValue.trim();
//		returnValue = returnValue.substring(returnValue.length()-1).equals(",")?returnValue.substring(0, returnValue.length()-1):returnValue;
		return returnValue;
	}
	
	public static void setProxies()
	{
		System.getProperties().put("http.proxyHost", "20.201.204.111");
		System.getProperties().put("https.proxyHost", "20.201.204.111");
		System.getProperties().put("http.proxyPort", "80");
		System.getProperties().put("https.proxyPort", "80");
		System.getProperties().put("http.proxyUser", "vbeerakam");
		System.getProperties().put("http.proxyPassword", "Indian~123");
	}
}
