Feature: 
  As a guest
  I should be able to sign up for an account on the application
  so that I can conduct various tasks as a registered user

  Background: 
    Given I am on home page

  Scenario: Registration Success
    Given I decide to Sign up for an account
    When I enter valid details
    Then I am registered successfully

  Scenario: Registration Failure trying to sign up with existing userName
    Given I decide to Sign up for an account
    When I enter invalid details like existing userName "registeredUser"
    Then I see error message
