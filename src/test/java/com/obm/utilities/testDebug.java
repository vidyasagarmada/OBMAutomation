package com.obm.utilities;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import org.openqa.selenium.firefox.FirefoxDriver;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;
import io.appium.java_client.remote.MobileCapabilityType;



public class testDebug {

	public static void main(String[] args) throws InterruptedException, IOException {
	
		/*System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "//geckodriver.exe");
	    //WebDriver driver = new FirefoxDriver();
	    
	    //driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
	    driver.manage().timeouts().implicitlyWait(120,TimeUnit.SECONDS);
	    //WebDriver driver = new FirefoxDriver();
		driver.get("https://admin.qa1.onboardme.technology/index.html#/login");
		
		driver.findElement(By.xpath("//input[@name='email']")).sendKeys("dxconboardme@gmail.com");
		driver.findElement(By.xpath("//input[@name='password']")).sendKeys("QWerty123!");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		Thread.sleep(10000);
		//driver.findElement(By.xpath("//*[@class='ng-scope']/descendant::span[text()='Starting Soon']")).click();
		driver.findElement(By.xpath("//span[text()='Log Out']")).click();
		
		
		
		Thread.sleep(30000);
		driver.quit();*/
		
		try {
							
				File f = new File("src");
				
				File fs = new File(f,"obm_01242018_v1.0.0.25q.apk" );
				DesiredCapabilities cap = new DesiredCapabilities();
				cap.setCapability(MobileCapabilityType.DEVICE_NAME, "Nexus_6_API_24");
				cap.setCapability(MobileCapabilityType.APP, fs.getAbsolutePath());
				
				@SuppressWarnings("unused")
				AndroidDriver<AndroidElement> anddriver = new AndroidDriver<AndroidElement>(new URL("http://127.0.0.1:4723/wd/hub"), cap);
				Thread.sleep(20000);
				anddriver.findElement(By.xpath("//android.widget.EditText[@text ='Email address']")).sendKeys("spandey34@csc.com");
				Thread.sleep(10000);
		}catch(Exception e){
			e.printStackTrace();
		}

			

		
				
		
		
	}

}



