Narrative:
As a registered user
I want to be logged in to the system
so that I can carry out tasks on the system

Scenario: Login Success
Given I am on home page
Given I am user registeredUser
Then I am authenticated successfully


Scenario: Login Failure
Given I am on home page
Given I am user xyzUser
Then I see login failure