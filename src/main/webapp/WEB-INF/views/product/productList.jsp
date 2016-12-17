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

<div class="container">
    <div class="row">
        <div class="col-md-12 col-xs-12">
            <div class="panel panel-info">
              <div class="panel-heading">
			  <div class="panel-title">
                <div class="row">
                  <div class="col-md-6">
                    <h5> Product List</h5>
                  </div>
				  <div class="col-md-6">
                    <button class="btn btn-danger btn-sm pull-right" type="submit" name="shoppingcart" id="shoppingcartId"  ><span class="glyphicon glyphicon-shopping-cart"></span> </button>
                  </div>
                </div>
			  </div>
              </div>
              <div class="panel-body">
              
			    <!-- start of product  -->
			    <!-- http://stackoverflow.com/questions/4142631/is-it-possible-to-iterate-two-items-simulataneously-using-foreach-in-jstl/4142885#4142885 -->
			    
			    <c:forEach items="${allProducts}" var="product" varStatus="status">
	                <div class = "row">
					   <div class ="col-md-2 col-xs-12">
					     <img class="img-responsive"  src="${ListProductDTO[status.index].base64imageFile}" alt=""/>
					   </div>
					   <div class="col-md-4 col-xs-12">
					     <h3><c:out value="${product.productName}"/> </h3>
						 <h4><c:out value="${product.sdesc}"/></h4>
					   </div>
					   <div class="col-md-6 col-xs-12">
					      <button class="btn btn-primary btn-sm pull-right" type="submit" name="shoppingcart" id="shoppingcartId"  >Add<span class="glyphicon glyphicon-share-alt"></span> <span class="glyphicon glyphicon-shopping-cart"></span></button>
					   </div>
					</div>
				</c:forEach>
				<!-- end of product  -->
              </div>
              <div class="panel-footer">
              </div>
            </div>

        </div>
    </div>
</div>

<!-- contents end here -->
	 
<jsp:include page="../footer.jsp" />
	
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="../../js/bootstrap.min.js"></script>

  </body>
</html>