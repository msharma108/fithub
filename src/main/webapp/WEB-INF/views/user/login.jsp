<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	  <div class="panel panel-primary" style="border: 2px solid maroon">
        <div class="panel-heading" style="color: white; background-color: maroon;"><b>Please Login</b></div>
         <div class="panel-body" style="background-color: #C1E1A6;"> 
	        <form action="/login" method="POST" >
 				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
              <div class="col-sm-12">    
                <div class="form-group row">
                  <div class="col-sm-4 form-group">
			           <label for="email">UserName <span class="glyphicon glyphicon-user"> </span></label><br>
			           <input class="form-control" name="userName" id="userNameId" placeholder="Enter UserName" autofocus required />
			           
                  </div>
                </div>
                <div class="form-group row">
                  <div class="col-sm-4 form-group">
			           <label for="password">Password <span class="glyphicon glyphicon-lock"></span></label><br>
			           <input class="form-control" type="password" name="password" id="passwordId" placeholder="Enter Password" required />
                  </div>
                </div>
                </div>
              <div class="col-sm-12">  
                <div class="form-group row">
                  <div class="col-sm-4 form-group">
                    <button type="submit" class="btn btn-primary"><i class="glyphicon glyphicon-log-in"></i> Sign In</button>
                  </div>
                </div> 
              <br><br>
              		<c:if test="${not empty invalidCredentials}">
				 		<div class="col-sm-4">${invalidCredentials}</div>
					</c:if>
					<c:if test="${not empty logoutMessage}">
						<div class="col-sm-4">${logoutMessage}</div>
					</c:if>
              </div>
            </form>
           </div>
           <div class="panel-footer" style="color: white; background-color: maroon;"></div>
        </div>
      </div> 
    
	<!-- contents end here -->
	 
<jsp:include page="../footer.jsp" />
	
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="../../js/bootstrap.min.js"></script>

  </body>
</html>