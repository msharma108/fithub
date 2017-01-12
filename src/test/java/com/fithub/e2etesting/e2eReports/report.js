$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("com/fithub/e2etesting/feature/Authentication.feature");
formatter.feature({
  "line": 1,
  "name": "",
  "description": "As a guest or registered user\r\nI want to view the products in the application",
  "id": "",
  "keyword": "Feature"
});
formatter.before({
  "duration": 62811380,
  "status": "passed"
});
formatter.scenario({
  "line": 6,
  "name": "Test retrieval of all products",
  "description": "",
  "id": ";test-retrieval-of-all-products",
  "type": "scenario",
  "keyword": "Scenario",
  "tags": [
    {
      "line": 5,
      "name": "@viewProducts"
    }
  ]
});
formatter.step({
  "line": 8,
  "name": "I am on the home page",
  "keyword": "Given "
});
formatter.step({
  "line": 9,
  "name": "I decide to view all products",
  "keyword": "When "
});
formatter.step({
  "line": 10,
  "name": "I see list of all the products",
  "keyword": "Then "
});
formatter.match({
  "location": "ViewProductListStepDefinition.i_am_on_the_home_page()"
});
formatter.result({
  "duration": 3225421639,
  "status": "passed"
});
formatter.match({
  "location": "ViewProductListStepDefinition.i_decide_to_view_all_products()"
});
formatter.result({
  "duration": 13250876,
  "status": "passed"
});
formatter.match({
  "location": "ViewProductListStepDefinition.i_see_list_of_all_the_products()"
});
formatter.result({
  "duration": 7604452,
  "error_message": "cucumber.api.PendingException: TODO: implement me\r\n\tat com.fithub.e2etesting.step_definition.ViewProductListStepDefinition.i_see_list_of_all_the_products(ViewProductListStepDefinition.java:70)\r\n\tat ✽.Then I see list of all the products(com/fithub/e2etesting/feature/Authentication.feature:10)\r\n",
  "status": "pending"
});
formatter.after({
  "duration": 3624478,
  "status": "passed"
});
});