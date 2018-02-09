package com.obm.utilities;

import java.io.File;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.commons.configuration.XMLConfiguration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.AutomationName;
import io.appium.java_client.remote.MobileCapabilityType;

public class IOSDriverFactory {
	private static IOSDriver<IOSElement> iosDriver;
	//private static XMLConfiguration config;
    
    private IOSDriverFactory(){
    	//config = new XMLConfiguration("configurations/object-config.xml");
    }
    
    public static boolean checkISODriverInstance() {
    	if (iosDriver != null){
    		return true;
    	}
    	return false;
    }
    
    
    public static IOSDriver<IOSElement> getIOSDriver(){
    	if(iosDriver==null || (iosDriver != null && iosDriver.toString().contains("null"))){
            try{
            	XMLConfiguration config3 = new XMLConfiguration("configurations/object-config.xml");
            	String iosAppName = config3.getString("iosAppName");
            	System.out.println("IOS App Name:" + iosAppName);
            	String iosDeviceName = config3.getString("iosDeviceName");
            	System.out.println("IOS Device Name:" + iosDeviceName);
            	if(iosAppName != null)
            	{
            		File f = new File("src/app");
            		File fs = new File(f,iosAppName );
            		DesiredCapabilities cap = new DesiredCapabilities();
            		cap.setCapability(MobileCapabilityType.DEVICE_NAME, iosDeviceName);
            		cap.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
            		cap.setCapability(MobileCapabilityType.AUTOMATION_NAME, AutomationName.IOS_XCUI_TEST);
            		cap.setCapability(MobileCapabilityType.APP, fs.getAbsolutePath());    		
					iosDriver = new IOSDriver<IOSElement>(new URL("http://127.0.0.1:4723/wd/hub"), cap);
            	}else{
            			//            		
            		
            	}   
            	
            }catch(Exception e){
                throw new RuntimeException("Exception occured in creating iosDriver object");
            }
    	}
        return iosDriver;
    }
    
    public static void quit()
    {
    	if(!(iosDriver==null || (iosDriver != null && iosDriver.toString().contains("null")))){
    		iosDriver.quit();
    	}
    }
}
