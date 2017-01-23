# FitHub

**Available @ : [Fithub project on Cloud](https://befithubproject.herokuapp.com/home)**

FitHub is a Java EE application that provides an eCommerce platform for sale and purchase of fitness products together with hosting content that promotes fitness and wellness. The application will be built in Java leveraging industry standard frameworks that have proven to provide robustness and ease in software development. The application will be built using standard design patterns such as DAO, MVC pattern, etc. in order to provide separation of concerns and loosely coupled components which lead to easy maintenance and re-usability.

Following is a list of features and services provided by the application:
------------------------------------------------------------------------

 - Provides users the ability to view all the products based on categories
 - Display a list of top products
 - Render detailed information pertinent to a product of interest
 - Option to order a product by adding it to cart and payment via a payment gateway
 - Customer registration and authentication
 - Admin options to add\modify products & discounts
 - Automatic order receipt, order refund request confirmation, welcome mails

Requirements:-

 - Spring STS
 [Download Spring STS](https://spring.io/tools/sts/all)
 - JDK 1.8
 [Download JDK](http://www.oracle.com/technetwork/java/javase/downloads/index-jsp-138363.html)

Following are the maven commands to perform the listed actions:-

 - **Runs Integration tests, cucumber tests and generate a surefire report with site css and advance cucumber reports:**
 

	> mvn clean verify integration-test surefire-report:report-only site -DgenerateReports=false

 - **Generate build without running tests:**
 

	> mvn clean install -DskipTests

 - **Run integration tests & e2e tests:**
 

	> mvn verify integration-test

 - **Package application as WAR and run:**
 

	> mvn clean package -P war (run in directory containing pom.xml)
	> cd target (to move to target directory)
	> java -jar FitHub-0.0.1.war (can be executed)
	> Or can be deployed to an application/web container like Tomcat

 - **Package application as JAR and run/deploy on cloud:**
 

	> mvn clean package (run in directory containing pom.xml)
	> cd target (to move to target directory)
	> java -jar FitHub-0.0.1.jar 

 - **Run application:**
 

	> mvn clean install spring-boot:run

 - **Generate test reports only:**
 

	> mvn surefire-report:report-only site -DgenerateReports=false

 - **Run Cucumber Tests & generate advance reports:**
 

	> mvn clean verify

Following are the Heroku CLI commands to perform the listed actions:-

 - **Scale down dyno (stops dyno and application)**
 

	> heroku ps:scale web=0

 - **Scale up dyno**
 

	> heroku ps:scale web=1

 - **heroku postgresql through command line**
 

	> heroku pg:psql  (in the directory of the deployed application)

 - **Push code from repository master to Heroku master**
 

	> git push heroku master

 - **Push code from repository local branch to Heroku master**
 

	> git push heroku < Desired Local Branch To Push>:master

 - **Heroku Logs**
 

	> heroku logs --tail

 - **Opened Deployed App**
 

	> heroku open

