package com.obm.utilities;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

public class TestData{
	//private TestData(){}
	private static String TEST_DATA = "";
	static String testdataFile ="";
	
	
	
	public static String getTestData(String keyName){
		Properties prop = new Properties();
		TEST_DATA="";
		try{	
			try {
				XMLConfiguration config = new XMLConfiguration("configurations/object-config.xml");
				testdataFile = config.getString("testdataFile");
			} catch (ConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			prop.load(new FileInputStream(testdataFile));
			TEST_DATA = prop.getProperty(keyName);
			prop.clear();
		} catch(FileNotFoundException e){
				e.printStackTrace();
			}
			catch(IOException e){
			e.printStackTrace();
		}
			return TEST_DATA;
	}
	@SuppressWarnings("deprecation")
	public static void setTestData(String keyName, String value ){
		
		Properties prop = new Properties();
		TEST_DATA="";
		try{	
			try {
				XMLConfiguration config = new XMLConfiguration("configurations/object-config.xml");
				testdataFile = config.getString("testdataFile");
			} catch (ConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			prop.load(new FileInputStream(testdataFile));
			prop.setProperty(keyName, value);
			prop.save(new FileOutputStream(testdataFile),"");
			prop.clear();
		} catch(FileNotFoundException e){
				e.printStackTrace();
			}
			catch(IOException e){
			e.printStackTrace();
		}
	}
		
}
