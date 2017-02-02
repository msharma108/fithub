Narrative:
As a user
I want to search for products in the application based on name or short description
so that I can view details of the products or purchase them

Scenario: Product search successful
Given I am on home page
When I search product with name or description like protein
Then I see list of all products with name or description containing protein

Scenario: Product search unsuccessful
Given I am on home page
When I search product with name or description like guitar
Then I see unsuccessful search message for search string guitar
