Feature: TestID-1234 OBM Login Tests 

@smoke @regression
Scenario: TestID-1234 OBM Login scenario 
	Given Launch mobile OnBoardMe application 
	Then I wait for "10" seconds
	And Mobile App: I enter "spandey34@csc.com" into "OBMLogin.EmailaddressTxt" text field for mobile app
	And Mobile App: I enter "Test1234@" into "OBMLogin.PasswordTxt" text field for mobile app
	Then I wait for "2" seconds
	And Mobile App: I click "OBMLogin.LOGINBtn" button on OBM
	Then I wait for "5" seconds
	#And Mobile App: I click "OBMTermsAndCondition.ACCEPTBtn" button on OBM 
	Then I wait for "20" seconds
	