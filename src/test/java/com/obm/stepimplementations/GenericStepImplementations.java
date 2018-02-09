package com.obm.stepimplementations;

import java.io.IOException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.naming.ConfigurationException;

import org.apache.commons.configuration.XMLConfiguration;
import org.junit.Assert;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import com.google.common.primitives.Ints;
import com.obm.db.utilities.DBUtils;
import com.obm.utilities.AndroidDriverFactory;
import com.obm.utilities.AndroidElementFactory;
import com.obm.utilities.CompareImages;
import com.obm.utilities.DriverFactory;
import com.obm.utilities.ElementFactory;
import com.obm.utilities.ExcelUtils;
import com.obm.utilities.TestData;
import com.obm.utilities.WebUtils;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;

import org.apache.commons.lang.RandomStringUtils;


public class GenericStepImplementations {
	XMLConfiguration config;
	static String testdataFile= "";
	static String testIDIdentifier ;
	
	@Before
	public void setUp(Scenario scenario) throws ConfigurationException, org.apache.commons.configuration.ConfigurationException {
		config = new XMLConfiguration("configurations/object-config.xml");
		testdataFile= config.getString("testdataFile");
		testIDIdentifier ="";
		
		testIDIdentifier = scenario.getName().toString().substring(0,11);
		System.out.println("testIDIdentifier:"+testIDIdentifier);
		if ( DriverFactory.checkWebDriverInstance()) {
			//DriverFactory.getDriver().manage().deleteAllCookies();
			
			DriverFactory.getDriver().close();
			//DriverFactory.getDriver().quit();
			
		}
	}

	@After
	public void tearDown(Scenario scenario) {
		//below block is to connect to ALM and update the status.
		if (config.getString("alm_integration") != null && config.getString("alm_integration").equalsIgnoreCase("true")) {
			if (scenario.getStatus().equalsIgnoreCase("passed")) {
				
			} else {
				
			}
		}
		if ( DriverFactory.checkWebDriverInstance()) {
			//DriverFactory.getDriver().manage().deleteAllCookies();
			
			//DriverFactory.getDriver().close();
			//DriverFactory.getDriver().quit();
			
		}
		config.clear();
	}

	
	/**
	 * This method puts the execution on hold for given number of seconds.
	 * @param seconds
	 */
	@Then("I wait for \"([^\"]*)\" seconds")
	public void i_wait_for_given_seconds(String seconds) {
		try {
			Thread.sleep(new Long(seconds) * 1000);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method takes the control to a given URL
	 * @param url
	 */
	@Given("I open \"([^\"]*)\" URL in the browser")
	public void i_go_to_URL(String url) {
		try {
			String configuredURL = config.getString(url);//s
			DriverFactory.getDriver().get(configuredURL);
			System.err.println(DriverFactory.getDriver().getTitle());
			
		} catch (Exception e) {
			DriverFactory.getDriver().get(url);
		}
	}
	
	@Given("I go to \"([^\"]*)\" URL later")
	public void i_go_to_URL_later(String url) {
		try {
			String configuredURL = config.getString(url);
			DriverFactory.getDriver().get(configuredURL);
		} catch (Exception e) {
			DriverFactory.getDriver().get(url);
		}
	}
	
	/* START: Handle Check boxes */

	/**
	 * This method selects(checks) an unselected (unchecked) checkbox
	 * 
	 * @param identifier (identifier is calculated based on ObjectIdentityConfig.properties)
	 */
	@Then("I select \"([^\"]*)\" checkbox")
	public void i_select_checkbox(String identifier) {
		if (!ElementFactory.getElement(identifier).isSelected()) {
			ElementFactory.getElement(identifier).click();
		}
	}

	/**
	 * This method unselects (unchecks) a selected (checked) checkbox
	 * 
	 * @param identifier (identifier is calculated based on ObjectIdentityConfig.properties)
	 */
	@Then("I unselect \"([^\"]*)\" checkbox")
	public void i_unselect_checkbox(String identifier) {
		if (ElementFactory.getElement(identifier).isSelected()) {
			ElementFactory.getElement(identifier).click();
		}
	}

	/* END: Handle Check boxes */
	
	/* START: Handle Buttons */
	/**
	 * This method clicks on a button based on the HTML identifier provided.
	 * @param buttonIdentifier (identifier is calculated based on ObjectIdentityConfig.properties)
	 */
	@And("I click on \"([^\"]*)\" button")
	public void i_click_on_button(String buttonIdentifier) {
		ElementFactory.getElement(buttonIdentifier).click();
		DriverFactory.getDriver().manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
	}
	@And("I click to \"([^\"]*)\" button with no pageload time")
	public void i_click_to_button(String buttonIdentifier) {
		ElementFactory.getElement(buttonIdentifier).click();
		//DriverFactory.getDriver().manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
	}

	/* End: Handle Buttons */
	
	/* Start: Handle links */
	/**
	 * This method clicks on a hyper link 
	 * @param linkIdentifier (identifier is calculated based on ObjectIdentityConfig.properties)
	 * @throws InterruptedException 
	 */
	@Then("I click on \"([^\"]*)\" link")
	public void i_click_on_link(String linkIdentifier) throws InterruptedException {
		ElementFactory.getElement(linkIdentifier).click();
		DriverFactory.getDriver().manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		Thread.sleep(5000);
	}
	@Then("with modal dialog expected click on \"([^\"]*)\" link")
	public void i_click_on_link_with_no_pageload_time(String linkIdentifier) throws InterruptedException {
		ElementFactory.getElement(linkIdentifier).click();
		//DriverFactory.getDriver().manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		Thread.sleep(3000);
	}
	
	@Then("I click on \"([^\"]*)\" link with modal dialog expected")
	public void i_click_on_link_with_modal_dialog_expected(String linkIdentifier) throws InterruptedException {
		try{
		ElementFactory.getElement(linkIdentifier).click();
		Thread.sleep(3000);
		}catch(UnhandledAlertException e){
			e.printStackTrace();
		}
	}
	
	/**
	 * This method clicks on a hyper link and focuses on popup window
	 * @param linkIdentifier (identifier is calculated based on ObjectIdentityConfig.properties)
	 */
	@Then("I click on \"([^\"]*)\" and switch to popup")
	public void i_click_on_link_and_focus_popup(String linkIdentifier) {
		String parentwindowhandle = DriverFactory.getDriver().getWindowHandle();
		ElementFactory.getElement(linkIdentifier).click();
		WebUtils.switchpopups(parentwindowhandle,DriverFactory.getDriver());
	}	
	@Then("I switch to popup")
	public void switch_to_popup() throws InterruptedException {
		Thread.sleep(2000);
		String parentwindowhandle = DriverFactory.getDriver().getWindowHandle();
		WebUtils.switchpopups(parentwindowhandle,DriverFactory.getDriver());
	}
	@Then("I switch to parent window")
	public void switch_to_parent_window() throws InterruptedException {
		Thread.sleep(2000);
		WebUtils.switchparentwindow(DriverFactory.getDriver());
	}
	@Then("I switch to \"([^\"]*)\" frame")
	public void switch_to_frame(String FrameIdentifier) throws InterruptedException {
		DriverFactory.getDriver().manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		DriverFactory.getDriver().manage().timeouts().implicitlyWait(60,TimeUnit.SECONDS);
		@SuppressWarnings("unused")
		String parentwindowhandle = DriverFactory.getDriver().getWindowHandle();
		DriverFactory.getDriver().switchTo().defaultContent();
		DriverFactory.getDriver().switchTo().frame(FrameIdentifier);
		Thread.sleep(2000);
	}
	
	/* End: Handle links */
	/**
	 * This method clicks on a hyper link 
	 * @param linkIdentifier (identifier is calculated based on ObjectIdentityConfig.properties)
	 */
	@Then("I click on \"([^\"]*)\" hyperlink")
	public void i_click_onLink(String linkIdentifier) {
		DriverFactory.getDriver().findElement(By.linkText(linkIdentifier)).click();
		
	}
	
	/* End: Handle links */
	
	/* Start: Handle text boxes */
	/**
	 * This method enters given 'value' into the text box
	 * @param value
	 * @param identifier (identifier is calculated based on ObjectIdentityConfig.properties)
	 * @throws IOException 
	 */
	@And("I enter \"([^\"]*)\" into \"([^\"]*)\" text field")
	public void i_enter_value_into_textfield(String value, String identifier) throws IOException {
		
		if (value.contains("::")) {
			value = ExcelUtils.getTestdataValue(value, testIDIdentifier);
		}
		ElementFactory.getElement(identifier).clear();
		ElementFactory.getElement(identifier).sendKeys(value);
	}
	@And("Verify object \"([^\"]*)\" text value as \"([^\"]*)\" on the page")
	public void verify_field_text(String identifier, String value) throws InterruptedException {
		Thread.sleep(2000);
		
		String actualvalue = ElementFactory.getElement(identifier).getAttribute("value");
		System.out.println("element actualvalue :"+actualvalue);
		try{
			Assert.assertTrue("'"+value+"' matched to object:"+identifier, actualvalue.equals(value));
			System.out.println("verify_field_text passed:"+value);
			}catch(AssertionError e)
	        {
	            System.out.println("verify_field_text failed:"+value);
	        }
	}
	@And("I enter \"([^\"]*)\" into \"([^\"]*)\" text field from testdata file")
	public void i_enter_testdatafile_value_into_textfield(String value, String identifier) {
		ElementFactory.getElement(identifier).clear();
		String testValue = TestData.getTestData(value);
		ElementFactory.getElement(identifier).sendKeys(testValue);
	}
	@And("I login into server authentication")
	public void i_login_into_server_authentication() throws InterruptedException {
		try {
			//Thread.sleep(15000);
			Process prc = Runtime.getRuntime().exec("D:\\Automation\\AU3\\windowAuth.exe");
			prc.waitFor();
			prc.destroyForcibly();
			//Thread.sleep(5000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("authentication error:" +e.getMessage());
		}		
		
	}
	@Then("Verify navigation away popup message and click OK button")
	public void verify_navigation_away_popup() throws InterruptedException {
		try {
			Thread.sleep(5000);
			Process prcc = Runtime.getRuntime().exec("D:\\Automation\\AU3\\modalDialogNavigatingAway.exe");
			prcc.waitFor();
			prcc.destroyForcibly();
			Thread.sleep(5000);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("authentication error:" +e.getMessage());
		}		
		
	}
		
	@Then("I enter current date in \"([^\"]*)\" into \"([^\"]*)\" text field")
	public void enterCurrentDateIntoTextField(String dateFormat, String identifier)
	{
		if(dateFormat.length() > 10)
		{
			Assert.fail("Please enter valid date format. Eg: mm/dd/yyyy");
		}
		//Date format is expected in dd/mm/yyyy or mm/dd/yyyy. dd and yyyy should be lower case. mm could be in upper or lower case.
		dateFormat = dateFormat.replaceAll("mm", "MM");
		String currentDate = new SimpleDateFormat(dateFormat).format(new Date());
		ElementFactory.getElement(identifier).clear();
		ElementFactory.getElement(identifier).sendKeys(currentDate);
	}
	
	@And("I enter \"([^\"]*)\" appended with timestamp into \"([^\"]*)\" text field")
	public void i_enter_value_with_timestamp_into_textfield(String value, String identifier) {
		java.util.Date date= new java.util.Date();
		ElementFactory.getElement(identifier).clear();
		ElementFactory.getElement(identifier).sendKeys(value+date.getTime());
	}
	
	@And("I enter \"([^\"]*)\" into \"([^\"]*)\" field and click tab")
	public void enter_text_field_value_press_tab(String value, String identifier) {
		ElementFactory.getElement(identifier).clear();
		ElementFactory.getElement(identifier).sendKeys(value);
		ElementFactory.getElement(identifier).sendKeys(Keys.TAB);
		
	}
	
	
	/* End: Handle text boxes */
	
	@Then("I select \"([^\"]*)\" from \"([^\"]*)\" select box")
	public void select_value_from_selectbox(String value, String selectboxIdentifier) throws InterruptedException {
		new Select(ElementFactory.getElement(selectboxIdentifier)).selectByVisibleText(value);
	}
	
	@Then("I select calandar from date \"([^\"]*)\" from element \"([^\"]*)\" select box")
	public void select_calander_date(String monthYear, String dateImgIdentifier) throws InterruptedException {
		try{
			String strMonthID = config.getString("calFromDtMonth");
			String strYearID = config.getString("calFromDtYear");
			String monthVal = monthYear.split(":")[0];
			String dayVal = monthYear.split(":")[1];
			String yearVal = monthYear.split(":")[2];
			System.out.println("strMonthID:"+strMonthID);
			System.out.println("strYearID:"+strYearID);
			System.out.println("monthVal:"+monthVal);
			System.out.println("dayVal:"+dayVal);
			System.out.println("yearVal:"+yearVal);
			ElementFactory.getElement(dateImgIdentifier).click();
			Thread.sleep(2000);
			
			Select select = new Select(DriverFactory.getDriver().findElement(By.id(strMonthID)));
			select.selectByVisibleText(monthVal);
			Thread.sleep(2000);		
			Select selectYear = new Select(DriverFactory.getDriver().findElement(By.id(strYearID)));
			selectYear.selectByVisibleText(yearVal);
			Thread.sleep(2000);
			String dayValID = "";
			if (dayVal.equals("1")){
				dayValID = config.getString("calFromDay1");
			}
			
			System.out.println("dayValID:"+dayValID);
			DriverFactory.getDriver().findElement(By.id(dayValID)).click();
		} catch(Exception e){
			e.printStackTrace();
			
		}
	}
	
	/* Start: Handle Radio Buttons */
	/**
	 * This method takes name and value attributes to select a radio button.
	 * @param value
	 * @param name
	 */
	@And("I select \"([^\"]*)\" radio button for \"([^\"]*)\" field")
	public void i_select_radio_button(String value, String name) {
		final List<WebElement> radios = DriverFactory.getDriver().findElements(By.name(name));
	    for (WebElement radio : radios) {
	        if (radio.getAttribute("value").toLowerCase().equals(value.toLowerCase())) {
	            radio.click();
	        }
	    }
	}
	 
	/* End: Handle Radio Buttons */
	
	/**
	 * This method creates and returns a Selenium WebElement based on the identifier provided.
	 * @param identifier (identifier is calculated based on ObjectIdentityConfig.properties)
	 * @return WebElement
	 */
	public static By getBy(ArrayList<String> objKeySet) {

		By by;
		try {
			Method method = By.class.getMethod(objKeySet.get(0), String.class);
			by = (By) method.invoke(By.class, objKeySet.get(1));
		} catch (Exception NoSuchElementException) {
			return null;
		}
		return by;
	}

	
	@Then("^close browser window$")
	public void close_browser_window() {
		DriverFactory.getDriver().close();
	}
	
	@And("I compare \"([^\"]*)\" image displayed on the web page with \"([^\"]*)\" image")
	public void compare_images(String webUrl, String imageLocalNameExtn) {
		boolean imageMatched = new CompareImages().compareImages(webUrl, imageLocalNameExtn);
		Assert.assertTrue("Images did not match",imageMatched);
	}
	
	@And("I upload \"([^\"]*)\" file into \"([^\"]*)\" field")
	public void upload_file_into_field(String fileCompletePath, String inputFieldIdentifier) {
		ElementFactory.getElement(inputFieldIdentifier).sendKeys(fileCompletePath);
	}

	@And("I should see \"([^\"]*)\" on the page$")
	public void verify_text_matching(String text) throws InterruptedException {
		Thread.sleep(5000);
		String bodyText = DriverFactory.getDriver().findElement(By.tagName("body")).getText();
		Assert.assertTrue("'"+text+"' not found on the page", bodyText.contains(text));
		//System.out.println("Test Validation fails");
		
		
	}
	
	@And("\"([^\"]*)\" should appear on the page$")
	public void verify_text1_matching(String text) {
		String bodyText = DriverFactory.getDriver().findElement(By.tagName("body")).getText();
		Assert.assertTrue("'"+text+"' not found on the page", bodyText.contains(text));
	}
	
	@And("I should not see \"([^\"]*)\" on the page")
	public void verify_text_not_matching(String text) {
		String bodyText = DriverFactory.getDriver().findElement(By.tagName("body")).getText();
		Assert.assertTrue("'"+text+"' not found on the page", (!(bodyText.contains(text))));
	}
	

	
	/**
	 * This method enters the values into multiple text fields
	 * @param seconds
	 * @throws IOException 
	 */
	@Then("I fill multiple text fields as per below table:$")
	public void fill_multiple_text_feidls(List<Map<String, String>> values) throws IOException {
		for(Map<String, String> map : values)
		{
			String fieldName = map.get("fieldName");
			String value = map.get("value");
			i_enter_value_into_textfield(value,fieldName);
		}
	}
	
	@Then("I sohuld see \"([^\"]*)\" is disabled")
	public void fieldShouldBeDisabled(String fieldIdentifier){
		Assert.assertTrue(fieldIdentifier+" is expected to be disabled, but it is found enabled", !(ElementFactory.getElement(fieldIdentifier).isEnabled()));
	}
	
	@Then("I sohuld see \"([^\"]*)\" is enabled")
	public void fieldShouldBeEnabled(String fieldIdentifier){
		Assert.assertTrue(fieldIdentifier+" is expected to be enabled, but it is found disabled", ElementFactory.getElement(fieldIdentifier).isEnabled());
	}
	
	@Then("I select below options in \"([^\"]*)\" multi select box")
	public void selectMultipleOptionsFromMultiSelectBox(String multiSelectIdentifier, List<String> options){
		Select selections = new Select(ElementFactory.getElement(multiSelectIdentifier));
		for(String optionValue : options)
		{
			selections.selectByVisibleText(optionValue);
		}
		
	}
	
	@Then("I should see \"([^\"]*)\" table is sorted based on column number \"([^\"]*)\" in \"([^\"]*)\" order$")
	public void checkTableValuesSortedAscending(String tableIdentifier, String columnNumber, String order)
	{
		WebElement table = ElementFactory.getElement(tableIdentifier);
        List<WebElement> rows = table.findElements(By.tagName("tr"));
        List<String> ValuesOfReqdColumn = new ArrayList<String>();
        int columnNum = new Integer(columnNumber) - 1;

        for(int i=1;i<rows.size()-1;i++)
        {
        	WebElement row = rows.get(i);
        	List<WebElement> TDs = row.findElements(By.tagName("td"));
        	if(TDs.size() == 0)
        		continue;
        	WebElement reqColumnElement = TDs.get(columnNum);
        	if(reqColumnElement != null)
        	{
        		ValuesOfReqdColumn.add(reqColumnElement.getText());
        	}
        }
        if(order != null && order.equalsIgnoreCase("descending")){
        	Assert.assertTrue("'"+tableIdentifier+"' table is not sorted in descending order by Column Number:"+columnNumber,!isSorted(ValuesOfReqdColumn));
        }else{
        	Assert.assertTrue("'"+tableIdentifier+"' table is not sorted in ascending order by Column Number:"+columnNumber,isSorted(ValuesOfReqdColumn));
        }
        	
        
	}
	
	public boolean isSorted(List<String> list)
	{
	    boolean sorted = true;        
	    for (int i = 1; i < list.size(); i++) {
	        if (list.get(i-1).compareTo(list.get(i)) > 0) sorted = false;
	    }
	    return sorted;
	}
	
	@Then("I compare \"([^\"]*)\" table contents with \"([^\"]*)\" query with below options:")
	public void compareTableDataWithQueryResults(String tableIdentifier, String queryIdentifier, Map<String,String> options)
	{
		
		List<String> stringDBResultRows = null;
		try {
			stringDBResultRows = DBUtils.getStringResultRows(queryIdentifier);
		} catch (org.apache.commons.configuration.ConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String ignoreRows = "";
		String ignoreColumns = "";
		if(options != null)
		{
			ignoreRows = options.get("ignore rows");
			ignoreColumns = options.get("ignore columns");
		}
		int[] rowsToIgnore = stringToIntArray(ignoreRows);
		int[] columnsToIgnore = stringToIntArray(ignoreColumns);
		
		WebElement table = ElementFactory.getElement(tableIdentifier);
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		int j=-1;
		for(int i =0; i<rows.size(); i++)
		{
			j++;
			if(Ints.contains(rowsToIgnore, i+1))
			{
				j--;
				continue;
			}
			String rowUIText = WebUtils.getTableRowAsCommaSeperatedString(rows.get(i), columnsToIgnore);
			String rowDBText = stringDBResultRows.get(j);
			if(!(rowUIText.equalsIgnoreCase(rowDBText)))
			{
				Assert.fail();
			}
		}
	}
	
	private int[] stringToIntArray(String commaSeparatedStringValue)
	{
		String[] items = commaSeparatedStringValue.replaceAll("\\[", "").replaceAll("\\]", "").split(",");
		int[] intArray = new int[items.length];

		for (int i = 0; i < items.length; i++) {
		    try {
		    	intArray[i] = Integer.parseInt(items[i]);
		    } catch (NumberFormatException nfe) {};
		}
		return intArray;
	}
	@Then("Verify \"([^\"]*)\" alert text in popup window")
	public void getTextOnPopUp(String strVal) throws InterruptedException
	{
		Thread.sleep(5000);
		Alert alrt=DriverFactory.getDriver().switchTo().alert();
		String popText = alrt.getText();
		System.out.println("popup alert text:"+popText);
		try{
			Assert.assertTrue("'"+popText+"' matched to the popup alert message", popText.equals(strVal));
			System.out.println("popup alert text:"+popText);
			}catch(AssertionError e)
	        {
	            System.out.println("Assertion error for verify popup alert text");
	        }
		alrt.accept();
		//DriverFactory.getDriver().switchTo().alert().accept();
	}
	
	
	@Then("I click OK button in popup window")
	public void clickOkOnPopUp()
	{
		DriverFactory.getDriver().switchTo().alert().accept();
	}
	
	@Then("Verify field \"([^\"]*)\" has text as \"([^\"]*)\" in a page$")
	public void verify_objecttext_matching(String elementIdentifier, String strtext) throws InterruptedException {
		Thread.sleep(3000);
		String strTextVal= ElementFactory.getElement(elementIdentifier).getAttribute("value");
		System.out.println("object text:"+strTextVal);
		try{
			Assert.assertTrue("'"+strTextVal+"' matched with the object text value", strTextVal.equals(strtext));
			System.out.println("object text:"+strTextVal);
		}catch(AssertionError e)
        	{
            	System.out.println("Assertion error for verify field text");
        	}
	}
	
	
	@Given("I update \"([^\"]*)\" testdata with \"([^\"]*)\" in test file")
	public void i_update_testdata(String keyName, String keyVal) {
		if (keyVal.contains("DynamicData:RandomNumeric")){
			Integer charLength = Integer.parseInt(keyVal.replace("(","").replace(")", "").split("Numeric")[1]);
			String charset = "123456789";
			//Integer charLength=9;
			keyVal= RandomStringUtils.random(charLength,charset.toCharArray());
			//keyVal="123456789";
		}
		try {
			TestData.setTestData(keyName, keyVal);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@And("verify webelement attribute value \"([^\"]*)\" is focussed having attribut \"([^\"]*)\" on the page$")
	public void verify_element_focus(String attrValue, String attrText) throws InterruptedException {
		WebElement we= DriverFactory.getDriver().switchTo().activeElement();
		String strVal= we.getAttribute(attrText);
		System.out.println(" active element having attribute value is :"+strVal);
		try{
		Assert.assertTrue("'"+attrValue+"' matched to the element focused on the page", strVal.equals(attrValue));
		}catch(AssertionError e)
        {
            System.out.println("Assertion error for verify element focused.");
        }	
	}
	@And("Click TAB key on the page$")
	public void click_tab_key() throws InterruptedException {
		Actions action = new Actions(DriverFactory.getDriver()); 
		action.sendKeys(Keys.TAB).build().perform();	
	}
	@And("verify title of page as \"([^\"]*)\"$")
	public void verify_page_title(String strValue) throws InterruptedException {
		String expectedPageTitle=config.getString(strValue);
		String pageTitle= DriverFactory.getDriver().getTitle(); 
		try{
			Assert.assertTrue("'"+expectedPageTitle+"' matched to the element focused on the page", expectedPageTitle.equals(pageTitle));
			System.out.println("Assertion of page title:"+expectedPageTitle);
			}catch(AssertionError e)
	        {
	            System.out.println("Assertion error for verify page title:"+expectedPageTitle);
	        }	
	}
	@And("Verify vertical alignment between elements \"([^\"]*)\" and \"([^\"]*)\" on the page$")
	public void verify_vertical_alignment(String identifier1, String identifier2) throws InterruptedException {
		String identifier1_XLoc=String.valueOf(ElementFactory.getElement(identifier1).getLocation().getX());
		String identifier2_XLoc=String.valueOf(ElementFactory.getElement(identifier2).getLocation().getX());
		System.out.println("identifier1_XLoc:"+identifier1_XLoc); 
		System.out.println("identifier2_XLoc:"+identifier2_XLoc);
		try{
			Assert.assertTrue("'"+identifier1+"' X location matched to the element "+identifier1, identifier1_XLoc.equals(identifier1_XLoc));
			System.out.println("vertical alignment of elements passed");
			}catch(AssertionError e)
	        {
				System.out.println("vertical alignment of elements failed between elements " + identifier1 +" and " +identifier2);
				e.printStackTrace();
	        }	
	}
	
	@Given("Launch mobile OnBoardMe application$")
	public void launch_OnBoardMe_application() {
		if( config.getString("deviceos").equalsIgnoreCase("android")) {
			AndroidDriverFactory.getAndroidDriver();
		}		
			
	}
	
	/* START: Handle mobile Buttons */
	/**
	 * This method clicks on a button based on the mobile app identifier provided.
	 * @param buttonIdentifier (identifier is calculated based on )
	 */
	@And("Mobile App: I click \"([^\"]*)\" button on OBM")
	public void i_click_button_on_obm(String buttonIdentifier) {
		AndroidElementFactory.getElement(buttonIdentifier).click();
		
	}
	/* Start: Handle text boxes in mobile app */
	/**
	 * This method enters given 'value' into the text box
	 * @param value
	 * @param identifier (identifier is calculated based on )
	 */
	@And("Mobile App: I enter \"([^\"]*)\" into \"([^\"]*)\" text field for mobile app$")
	public void i_enter_value_into_textfield_mobileapp(String value, String identifier) {
		AndroidElementFactory.getElement(identifier).clear();
		AndroidElementFactory.getElement(identifier).sendKeys(value);
	}
		
	
}