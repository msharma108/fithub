<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
      <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
      <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	  <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
      <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
  <head>
    <meta charset="utf-8">
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
      <h1 class="well">Login Form</h1>
        <div class="col-lg-12 well">
	        <form:form modelAttribute="userDTO" method="POST" enctype="utf8">
 
              <div class="col-sm-12">    
                <div class="form-group row">
                  <div class="col-sm-4 form-group">
			           <form:label path="userName">UserName:</form:label><br>
			           <form:input class="form-control" path="userName" id="userNameId" />
                  </div>
                </div>
                <div class="form-group row">
                  <div class="col-sm-4 form-group">
			           <form:label path="password" >Password:</form:label><br>
			           <form:input class="form-control" type="password" path="password" id="passwordId" />
                  </div>
                </div>
                </div>
            
                <div class="row">
                  <div class="col-sm-4 form-group">
                       <input class="btn btn-success" type="submit" value="Sign In" />
                  </div>
                </div> 
              <br><br>
              
            </form:form>
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