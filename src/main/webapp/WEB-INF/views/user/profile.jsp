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

	<!-- Font Awesome -->
	<link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
	<!-- JQuery -->
	<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script> 
	<!-- custom -->
	<link rel="stylesheet" href="../../css/style.css"  >
	<!-- Bootstrap -->
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
<jsp:include page="../header.jsp" />
	<!-- contents start here -->
<div class="container-fluid">
        
  <div class="panel panel-primary">
    <div class="panel-heading"><b>Profile INFO</b></div>
     <div class="panel-body"> 

           <!-- Form action variable value based on user role-->
           <sec:authorize access="hasAuthority('ADMIN')">
           <c:url var="userTask" value="/admin/userTask/${userDTO.userName}"/>
           </sec:authorize>
           
           <sec:authorize access="hasAuthority('CUSTOMER')">
           <c:url var="userTask" value="/userTask/${userDTO.userName}"/>
           </sec:authorize>
           
	        <form:form modelAttribute="userDTO" action="${userTask}" method="POST" style="color: green;">
              <div class="col-xs-12">    
                <div class=" form-group row">
                  <div class="col-sm-4">
                       <form:label path="givenName">Given Name:<span class="glyphicon glyphicon-user"> </span> </form:label><br>
                       <form:label path="givenName">${userDTO.givenName}</form:label>
                  </div>
                  <div class="col-sm-4">
                       <form:label path="familyName">Family Name:<span class="glyphicon glyphicon-user"> </span> </form:label><br>
                       <form:label path="familyName">${userDTO.familyName}</form:label> 
                  </div>
                </div>
              </div>
              
              <div class="col-sm-12">    
                <div class="form-group row">
                  <div class="col-sm-4 form-group">
                       <form:label path="sex">Gender:<span class="glyphicon glyphicon-user"> </span></form:label><br>
                       <form:label path="sex">${userDTO.sex}</form:label> 
                  </div>
                  <div class="col-sm-4 form-group">
			           <form:label path="dateOfBirth">Date Of Birth:<span class="glyphicon glyphicon-calendar"> </span></form:label><br>
			           <form:label path="dateOfBirth">${userDTO.dateOfBirth}</form:label> 
                  </div>
                </div>
              </div>
              
              <div class="col-sm-12">    
                <div class="form-group row">
                  <div class="col-sm-4 form-group">
			           <form:label path="address">Address:<span class="glyphicon glyphicon-home"> </span></form:label><br>
			           <form:label path="address">${userDTO.address}</form:label> 
                  </div>
                  <div class="col-sm-4 form-group">
			           <form:label path="city">City:<span class="glyphicon glyphicon-home"> </span></form:label><br>
			           <form:label path="city">${userDTO.city}</form:label> 
                  </div>
                </div>
              </div>
              
              <div class="col-sm-12">    
                <div class="form-group row">
                  <div class="col-sm-4 form-group">
			           <form:label path="province">Province:<span class="glyphicon glyphicon-home"> </span></form:label><br>
                       <form:label path="province">${userDTO.province}</form:label> 
                  </div>
                  <div class="col-sm-4 form-group">
			           <form:label path="country">Country:<span class="glyphicon glyphicon-home"> </span></form:label><br>
			           <form:label path="country">${userDTO.country}</form:label>  
                  </div>
                </div>
              </div>
              
              <div class="col-sm-12">    
                <div class="form-group row">
                  <div class="col-sm-4 form-group">
			           <form:label path="zipcode">Zip/Postal code:<span class="glyphicon glyphicon-home"> </span></form:label><br>
			           <form:label path="zipcode">${userDTO.zipcode}</form:label>  
                  </div>
                  <div class="col-sm-4 form-group">
			           <form:label path="phone">Phone:<span class="glyphicon glyphicon-phone"> </span></form:label><br>
			           <form:label path="phone">${userDTO.phone}</form:label> 
                  </div>
                </div>
              </div>
              
              <div class="col-sm-12">    
                <div class="form-group row">
                  <div class="col-sm-4 form-group">
			           <form:label path="email">Email:<span class="glyphicon glyphicon-envelope"></span></form:label><br>
			           <form:label path="email">${userDTO.email}</form:label> 
                  </div>
                  <div class="col-sm-4 form-group">
			           <form:label path="userName">UserName:<span class="glyphicon glyphicon-user"> </span></form:label><br>
			           <form:label path="userName">${userDTO.userName}</form:label> 
			           <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                  </div>
                </div>
                </div>
              
              
              <div class="col-sm-12">    
                <div class="form-group row">
                  <div class="col-sm-4 form-group">
			          <form:label path="paymentMode">Payment Mode:<span class="glyphicon glyphicon-credit-card"></span></form:label><br>
                      <form:label path="paymentMode">${userDTO.paymentMode}</form:label>
                  </div>
                </div>
              </div>
              
			<br><br> 
			<button type="submit" class="btn btn-primary btn-block" name="userEdit" id="userEdit"  ><i class="glyphicon glyphicon-pencil"></i> Edit Profile</button>             
            </form:form>
          </div>
        </div>
        <br><br><br><br><br><br>
  </div>
	<!-- contents end here -->
	 
<jsp:include page="../footer.jsp" />
	
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="../../js/bootstrap.min.js"></script>

  </body>
</html>