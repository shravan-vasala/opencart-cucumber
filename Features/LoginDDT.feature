Feature: Login Data Driven

Scenario Outline: Login Data Driven
	Given  User Launch browser
	And opens URL "http://localhost/opencart/upload/"
	When User navigate to MyAccount menu
	And click on Login
	And User enters Email as "<email>" and Password as "<password>"
	And Click on Login button
	Then User navigates to My Account Page 
	
	Examples: 
		| email													| password | 
		|	shravanvasala@gmail.com     	| qwerty   |
		|	shravankumarvasala@gmail.com	|	test@123 |