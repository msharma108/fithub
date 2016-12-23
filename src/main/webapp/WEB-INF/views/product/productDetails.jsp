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
	<link rel="stylesheet" href="../css/style.css"  >
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

     <div class="panel-body" style="background-color: #C1E1A6;"> 
         
         <c:url var="productSave" value="/admin/productSave"/>
	        <form:form modelAttribute="productDTO" method="POST" action="" style="color: green;">
              <div class="col-xs-12">    
                <div class=" form-group row">
                  <div class="col-sm-4">
                       <form:label path="productName">Product Name:</form:label><br>
                       <form:label path="productName">${productDTO.productName}</form:label>
                  </div>
                  <div class="col-sm-4">
                       <form:label path="ldesc">Description: </form:label><br>
                       <form:label path="ldesc">${productDTO.ldesc}</form:label>
                  </div>
                </div>
              </div>
             <sec:authorize access="isAuthenticated()">
         <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
              </sec:authorize>
              <div class="col-sm-12">    
                <div class="form-group row">
                  <div class="col-sm-4 form-group">
                       <form:label path="productCategory">Product Category:</form:label><br>
                       <form:label path="productCategory">${productDTO.productCategory}</form:label>
                  </div>
                  <div class="col-sm-4 form-group">
			           <form:label path="manufactureDate">Manufacture Date:<span class="glyphicon glyphicon-calendar"> </span></form:label><br>
			           <form:label path="manufactureDate">${productDTO.manufactureDate}</form:label>
                  </div>
                </div>
              </div>
              
              <div class="col-sm-12">    
                <div class="form-group row">
                  <div class="col-sm-4 form-group">
			           <form:label path="price">Price:</form:label><br>
			           <form:label path="price">${productDTO.price}</form:label>
                  </div>
                  </div>
                  </div>
                  
                  <div class="col-sm-12">    
                <div class="form-group row">
                  <img class="img-responsive"  src="${productDTO.base64imageFile}" alt="${product.productName}"/>
                  </div>
                  
                  <div class="col-sm-4 form-group">
			           <form:label path="weight">Weight:</form:label><br>
			           <form:label path="weight">${productDTO.weight}</form:label>
                  </div>
                </div>
              </div>
              
              <div class="col-sm-12">    
                <div class="form-group row">
                  <div class="col-sm-4 form-group">
			           <form:label path="stockQuantity">Quantity:</form:label><br>
			           <form:label path="stockQuantity">${productDTO.stockQuantity}</form:label>
                  </div>
                  <div class="col-sm-4 form-group">
			           <form:label path="expiryDate">Expiry Date:<span class="glyphicon glyphicon-calendar"> </span></form:label><br>
			           <form:label path="expiryDate">${productDTO.expiryDate}</form:label>
                  </div>
                  
                </div>
              </div>
              
              <div class="col-sm-12">    
                <div class="form-group row">
                  <div class="col-sm-4 form-group">
			           <form:label path="flavor">Flavor:</form:label><br>
			           <form:label path="flavor">${productDTO.flavor}</form:label>
                  </div>
                  <div class="col-sm-4 form-group">
                       <form:label path="rating">Rating:<span class="glyphicon glyphicon-user"> </span></form:label><br>
                       <form:label path="rating">${productDTO.rating}</form:label>
                  </div>
                </div>
              </div>
              
              <div class="col-sm-12">    
                <div class="form-group row">
                  <div class="col-sm-4 form-group">
			           <form:label path="sdesc">Short description:</form:label><br>
			           <form:label path="sdesc">${productDTO.sdesc}</form:label>
                  </div>

                </div>
                </div>
              
			<br><br>              
            </form:form>
          </div>
          <div class="panel-footer" style="color: white; background-color: maroon;"></div>
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