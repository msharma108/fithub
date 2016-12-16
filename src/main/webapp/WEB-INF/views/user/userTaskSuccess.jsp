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
	<link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">

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
    <div class="row">
     <c:if test="${userRegisterSuccess == true}">
        <div class="col-sm-6 col-md-6">
            <div class="alert alert-success">
                <span class="glyphicon glyphicon-info-sign"> </span><strong>User Registration Success Message</strong>
                <hr class="message-inner-separator">
                <h3>User registration has been completed Successfully.<span class="glyphicon glyphicon-ok"></span></h3>
            </div>
        </div>
     </c:if>
      
     <c:if test="${productRegisterSuccess == true}">
        <div class="col-sm-6 col-md-6">
            <div class="alert alert-success">
                <span class="glyphicon glyphicon-info-sign"> </span><strong>Product</strong>
                <hr class="message-inner-separator">
                <h3>Product has been added Successfully.<span class="glyphicon glyphicon-ok"></span></h3>
            </div>
        </div>
      </c:if>
     <c:if test="${userUpdateSuccess == true}">
        <div class="col-sm-6 col-md-6">
            <div class="alert alert-success">
                <span class="glyphicon glyphicon-info-sign"> </span><strong>Profile update</strong>
                <hr class="message-inner-separator">
                <h3>User Profile has been updated Successfully. <span class="glyphicon glyphicon-ok"></span></h3>
            </div>
        </div>
      </c:if>
      
     <c:if test="${userRoleChangeSuccess == true}">
        <div class="col-sm-6 col-md-6">
            <div class="alert alert-success">
                <span class="glyphicon glyphicon-info-sign"> </span><strong>User Role Change</strong>
                <hr class="message-inner-separator">
                <h3>User Role has been changed Successfully.<span class="glyphicon glyphicon-ok"></span></h3>
            </div>
        </div>
      </c:if>
      
     <c:if test="${userDeleteSuccess == true}">
        <div class="col-sm-6 col-md-6">
            <div class="alert alert-success">
                <span class="glyphicon glyphicon-info-sign"> </span><strong>User Deletion</strong>
                <hr class="message-inner-separator">
                <h3>User has been deleted Successfully. <span class="glyphicon glyphicon-ok"></span></h3>
            </div>
        </div>
      </c:if>
      
      
    </div>
</div>

	<!-- contents end here -->
	 
	<jsp:include page="../footer.jsp" />
	
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>

  </body>
</html>