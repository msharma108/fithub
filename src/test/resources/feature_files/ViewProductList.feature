Feature:
As a guest or registered user
I want to view the products in the application

@viewProducts
Scenario: Retrieval of product list

Given I am on the home page
When I decide to view all products
Then I see list of all the products


