Feature: Login with Valid Credentials

  @sanity
  Scenario: Successful login with Valid Credentials
    Given User Launch browser
    And opens URL "http://localhost/opencart/upload/"
    When User navigate to MyAccount menu
    And click on Login
    And User enters Email as "shravanvasala@gmail.com" and Password as "qwerty"
    And Click on Login button
    Then User navigates to My Account Page
    
 
