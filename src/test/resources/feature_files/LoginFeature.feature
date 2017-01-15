

Feature:
As a registered user
I want to be logged in to the system
so that I can carry out tasks on the system

Scenario: Login Success

Given I am on the home page
Given I am "registeredUser"
Then I am authenticated successfully

Scenario: Login Failure

Given I am on the home page
Given I am "xyzUser"
When I login with username "xyzUser" password "adminadmin"
Then I see login failure
