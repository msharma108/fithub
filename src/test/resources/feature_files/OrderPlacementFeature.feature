
Feature:
As a registered user
I want to be logged in to the system
and I want to view the list of all products
so that I can place order for a product

Background:
Given I am on home page

Scenario: Place Order Success

Given I am "registeredUser"
And I decide to view all products
And I decide to add a product to cart
And I decide to checkout
And I enter shipping and payment details
When I hit pay now
Then I see order placement successful message

Scenario: Place Order Failure

Given I am "xyzUser"
And I decide to view all products
And I decide to add a product to cart
When I decide to checkout
Then I am redirected to Login page
