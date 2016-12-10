<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
      <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
      <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	  <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
      <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
  <head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>FitHub.com</title>

    <!-- Bootstrap -->
    <!-- link rel="stylesheet" href="../../css/bootstrap.min.css" -->
    <link href="<c:url value="../../css/bootstrap.min.css" />" rel="stylesheet">
	<!-- Font Awesome -->
	<link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
	<!-- custom -->
	<link rel="stylesheet" href="../../css/style.css"  >
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-formhelpers-countries.flags.css">
	

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

  </head>
  <body>
<%@ include file="header.jsp" %>


	<!-- contents start here -->
	<div class="container-fluid">
      <h1 class="well">Registration Form</h1>
        <div class="col-lg-12 well">
          <fieldset class="form-group">
          <legend>Please enter your information below</legend>
          
          <!-- Form action variable value based on user role starts here -->
          <sec:authorize access="isAnonymous()">
          <c:url var="userSave" value="/userSave"/>
          </sec:authorize>
         <sec:authorize access="hasRole('ADMIN')">
         <c:url var="userSave" value="/admin/userSave"/>
         </sec:authorize>
           <!-- Form action variable value based on user role ends here -->
	        <form:form modelAttribute="userDTO" method="POST" action="${userSave}">
              <div class="col-xs-12">    
                <div class=" form-group row">
                  <div class="col-sm-4">
                       <form:label path="givenName">Given Name: </form:label><br>
                       <form:input class="form-control" path="givenName" id="givenNameId" />
                       <form:errors  path="givenName"/>
                  </div>
                  <div class="col-sm-4">
                       <form:label path="familyName">Family Name: </form:label><br>
                       <form:input class="form-control" path="familyName" id="familyNameId" />
                       <form:errors  path="familyName"/>
                  </div>
                </div>
              </div>
              
              <div class="col-sm-12">    
                <div class="form-group row">
                  <div class="col-sm-4 form-group">
                       <form:label path="sex">Gender:</form:label><br>
			           <form:select class="form-control" path="sex" id="sexId">
			          <form:option value=""></form:option>
			           <form:option value="MALE">MALE</form:option>
			           <form:option value="FEMALE">FEMALE</form:option>
			           <form:option value="UNDISCLOSED">UNDISCLOSED</form:option>
			           <form:errors  path="sex"/>
			           </form:select>
                  </div>
                  <div class="col-sm-4 form-group">
			           <form:label path="dateOfBirth">Date Of Birth:</form:label><br>
			           <form:input class="form-control" path="dateOfBirth" id="dateOfBirthId" />
			           <form:errors  path="dateOfBirth"/>
                  </div>
                </div>
              </div>
              
              <div class="col-sm-12">    
                <div class="form-group row">
                  <div class="col-sm-4 form-group">
			           <form:label path="address">Address:</form:label><br>
			           <form:input class="form-control" path="address" id="addressId" />
			           <form:errors  path="address"/>
                  </div>
                  <div class="col-sm-4 form-group">
			           <form:label path="city">City:</form:label><br>
			           <form:input class="form-control" path="city" id="cityId" />
			           <form:errors  path="city"/>
                  </div>
                </div>
              </div>
              
              <div class="col-sm-12">    
                <div class="form-group row">
                  <div class="col-sm-4 form-group">
			           <form:label path="province">Province:</form:label><br>
			           <form:input class="form-control" path="province" id="provinceId" />
			           <form:errors  path="province"/>
                  </div>
                  <div class="col-sm-4 form-group">
			           <form:label path="country">Country:</form:label><br>
			           <form:select class="form-control input-medium bfh-countries" data-country="US" path="country" id="countryId">
			           <form:option value=""></form:option>
			           <form:option value="can">Canada</form:option>
			           <form:option value="usa">USA</form:option>
			           <form:errors  path="country"/>
			           </form:select>
                  </div>
                </div>
              </div>
              
              <div class="col-sm-12">    
                <div class="form-group row">
                  <div class="col-sm-4 form-group">
			           <form:label path="zipcode">Zip/Postal code:</form:label><br>
			           <form:input class="form-control" path="zipcode" id="zipcodeId" />
			           <form:errors  path="zipcode"/>
                  </div>
                  <div class="col-sm-4 form-group">
			           <form:label path="phone">Phone:</form:label><br>
			           <form:input class="form-control" type="tel" path="phone" id="phoneId" />
			           <form:errors  path="phone"/>
                  </div>
                </div>
              </div>
              
              <div class="col-sm-12">    
                <div class="form-group row">
                  <div class="col-sm-4 form-group">
			           <form:label path="email">Email:</form:label><br>
			           <form:input class="form-control" type="email" path="email" id="emailId" />
			           <form:errors  path="email"/>
                  </div>
                  <div class="col-sm-4 form-group">
			           <form:label path="userName">UserName:</form:label><br>
			           <form:input class="form-control" path="userName" id="userNameId" />
			           <form:errors  path="userName"/>
                  </div>
                </div>
                </div>
              
              <div class="col-sm-12">    
                <div class="form-group row">
                  <div class="col-sm-4 form-group">
			           <form:label path="password" >Password:</form:label><br>
			           <form:input class="form-control" type="password" path="password" id="passwordId" />
			           <form:errors  path="password"/>
                  </div>
                  <div class="col-sm-4 form-group">
			           <form:label path="repeatPassword">Repeat Password:</form:label><br>
			           <form:input class="form-control" type="password" path="repeatPassword" id="repeatPasswordId" />
                  </div>
                </div>
              </div>
              
              <div class="col-sm-12">    
                <div class="form-group row">
                  <div class="col-sm-4 form-group">
			          <form:label path="paymentMode">Payment Mode:</form:label><br>
			          <form:select class="form-control" path="paymentMode" id="paymentModeId">
			          <form:option value=""></form:option>
			          <form:option value="debit">Debit</form:option>
			          <form:option value="credit">Credit</form:option>
			          <form:option value="paypal">Paypal</form:option>
			          </form:select>
                  </div>
                </div>
                <div class="row">
                  <div class="col-sm-4 form-group">
                       <input class="btn btn-success" type="submit" name="userRegister" id="userRegister" value="Save" />
                  </div>
                </div> 
              </div>
              <br><br>
              
            </form:form>
          </fieldset>
        </div>
    
	</div>
	<!-- contents end here -->
	 
<%@ include file="footer.jsp" %>
	
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="../../js/jquery-3.1.1.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="../../js/bootstrap.min.js"></script>

  </body>
</html>