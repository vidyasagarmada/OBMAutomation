Feature: TestID-1234 OBM Login Tests 

@smoke @regression
Scenario: TestID-1234 OBM Login scenario 
	Given I open "adminURL" URL in the browser 
	And I enter "adminLogin::EmailaddressTxt" into "adminLogin.EmailaddressTxt" text field 
	And I enter "dxconboardme@gmail.com" into "adminLogin.EmailaddressTxt" text field
	And I enter "QWerty123!" into "adminLogin.PasswordTxt" text field
	Then I wait for "2" seconds
	And I click on "adminLogin.LOGINBtn" button
	Then I wait for "5" seconds
	And I click on "adminDashboard.LOGOUTLnk" button
	Then I wait for "10" seconds
	Then close browser window
Hello
