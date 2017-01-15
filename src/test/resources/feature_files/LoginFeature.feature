@Login
Feature:
As a registered user
I want to be logged in to the system
so that I can carry out tasks on the system


Scenario: Login Success

Given I am "registeredUser"
When I login with username "registeredUser" passowrd "password"
Then I am authenticated successfully

Scenario: Login Failure

Given I am "xyzUser"
When I login with username "xyzUser" passowrd "password"
Then I see login failure
