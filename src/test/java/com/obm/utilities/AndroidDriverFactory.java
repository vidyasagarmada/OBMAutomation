package com.obm.utilities;

import java.io.File;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.apache.commons.configuration.XMLConfiguration;
import org.openqa.selenium.remote.DesiredCapabilities;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;

public class AndroidDriverFactory {
	private static AndroidDriver<AndroidElement> androidDriver;
	    
    private AndroidDriverFactory(){
    	//config = new XMLConfiguration("configurations/object-config.xml");
    }
    
    public static boolean checkAndroidDriverInstance() {
    	if (androidDriver != null){
    		return true;
    	}
    	return false;
    }
    
    
    public static AndroidDriver<AndroidElement> getAndroidDriver(){
    	if(androidDriver==null || (androidDriver != null && androidDriver.toString().contains("null"))){
            try{
            	XMLConfiguration config2 = new XMLConfiguration("configurations/object-config.xml");
            	String androidAppName = config2.getString("androidAppName");
            	System.out.println("Android App Name:" + androidAppName);
            	String androidDeviceName = config2.getString("androidDeviceName");
            	System.out.println("Android Device Name:" + androidDeviceName);
            	if(androidAppName != null)
            	{
            		File f = new File("src/test/app");
            		File fs = new File(f,androidAppName );
            		DesiredCapabilities cap = new DesiredCapabilities();
            		cap.setCapability(MobileCapabilityType.DEVICE_NAME, androidDeviceName);
            		cap.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
            		cap.setCapability(MobileCapabilityType.APP, fs.getAbsolutePath());    		
					androidDriver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), cap);
					androidDriver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
					//androidDriver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
					//androidDriver.manage().timeouts().setScriptTimeout(60, TimeUnit.SECONDS);
					
            	}else{
            			//            		
            		
            	}   
            	
            }catch(Exception e){
                throw new RuntimeException("Exception occured in creating androidDriver object");
            }
    	}
        return androidDriver;
    }
    
    public static void quit()
    {
    	if(!(androidDriver==null || (androidDriver != null && androidDriver.toString().contains("null")))){
    		androidDriver.quit();
    	}
    }
}
