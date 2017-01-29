Narrative:
As a registered user
I want to be logged in to the system
so that I can carry out tasks on the system

Scenario: Login Success
Given I am on home page
Given a stock of symbol STK1 and a threshold of 10.0
Given I am registeredUser registeredUser
Then I am authenticated successfully