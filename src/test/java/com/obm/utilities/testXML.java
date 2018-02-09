package com.obm.utilities;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

public class testXML{
public static void main(String argv[]) {
	
	//TestData.setTestData("name","Sona");
	//System.out.println(System.getProperty("user.dir"));
	//String keyVal  ="DynamicData:RandomNumeric(9)";
	//keyVal.split("(")[1].replace(")", "")
	//keyVal.split("(")
	//System.out.println(keyVal.replace("(","").replace(")", "").split("Numeric")[1]);
	
	try {
		XMLConfiguration config = new XMLConfiguration("configurations/object-config.xml");
		
		String val = config.getString("OBMTermsAndCondition.ACCEPTBtn"  + "[@device]");
		System.out.println(val);
		String[] val2 = config.getStringArray("OBMTermsAndCondition.ACCEPTBtn");
		System.out.println(val2[0]);
		System.out.println(val2[1]);
	} catch (ConfigurationException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}
   }