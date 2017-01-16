# FitHub
FitHub is a Java EE application that provides an eCommerce platform for sale and purchase of fitness products together with hosting content that promotes fitness and wellness. The application will be built in Java leveraging industry standard frameworks that have proven to provide robustness and ease in software development. The application will be built using standard design patterns such as DAO, MVC pattern, etc. in order to provide separation of concerns and loosely coupled components which lead to easy maintenance and re-usability.

Following is a list of features and services that will be provided by the application:
------------------------------------------------------------------------

 - Provides users the ability to view all the products based on categories
 - Display a list of top products
 - Render detailed information pertinent to a product of interest
 - Option to order a product by adding it to cart and payment via a payment gateway
 - Customer registration and authentication
 - Admin options to add\modify products & discounts

Requirements:-

 - Spring STS
 [Download Spring STS](https://spring.io/tools/sts/all)
 - JDK 1.8
 [Download JDK](http://www.oracle.com/technetwork/java/javase/downloads/index-jsp-138363.html)

Following are the maven commands to perform the listed actions:-

 - Runs Integration tests, cucumber tests and generate a surefire report with site css and advance cucumber reports:
 

	> mvn clean verify integration-test surefire-report:report-only site -DgenerateReports=false

 - Run integration tests & e2e tests:
 

	> mvn verify integration-test

 - Generate test reports only:
 

	> surefire-report:report-only site -DgenerateReports=false

 - Run application:
 

	> clean install spring-boot:run

 - Run Cucumber Tests & generate advance reports:
 

	> mvn clean verify
