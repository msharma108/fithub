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
	<link rel="stylesheet" href="../../../css/style.css"  >
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
<!-- http://stackoverflow.com/questions/4142631/is-it-possible-to-iterate-two-items-simulataneously-using-foreach-in-jstl/4142885#4142885 -->
<!-- http://bootsnipp.com/snippets/VXAxV -->
<div class="container-fluid">
  <div class="row">
    <div class="col-md-12 col-xs-12">
        <div class="panel panel-primary">
             <div class="panel-heading" id="table-bg">
			 <div class="panel-title">
                <div class="row">
                  <div class="col-md-6">
                  <c:if test="${ not empty displayTopProductsHeading}">
                  <h3 id="topProductListHeadingId">Top Product List</h3>
                  </c:if>
                  <c:if test="${ empty displayTopProductsHeading}">
                    <h3 id="allProductListHeadingId"> Product List</h3>
                    </c:if>
                  </div>
				  <div class="col-md-6">
				    <form action="<c:url value="/shoppingCart/viewCart"/>" method="POST">
				     <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                      <button class="btn btn-danger btn-sm pull-right" type="submit" name="shoppingCart" id="shoppingCartId"  ><span class="glyphicon glyphicon-shopping-cart"></span> </button>
                    </form>
                 </div>
                </div>
			  </div>
             </div>
             <div class="panel-body">
               <c:forEach items="${allProducts}" var="product" varStatus="status">
                   <!-- URL encoding -->
                   <!-- define variables -->
	               <c:url var="formActionIndependentOfUserRole" value="/productOperation/${product.productName}"/>
	               <c:url var="formActionAdminRole" value="/admin/adminProductOperation/${product.productName}"/>
	               <c:url var="viewProduct" value="/viewProduct/${product.productName}"/>      
				        <div class="col-xs-18 col-sm-6 col-md-4">
				          <div class="thumbnail">
				          
				            <!-- construct action depending on user -->

	               		     <sec:authorize access="!hasAuthority('ADMIN')">
	                		    <form action="${formActionIndependentOfUserRole }" method="POST" >
	                	     </sec:authorize>
	                	     <sec:authorize access="hasAuthority('ADMIN')">
	                 	        <form action="${formActionAdminRole }" method="POST" >
	                 	     </sec:authorize>
	                	     <div class="hovereffect">
	                	     <!-- start of image -->
				              <input type="hidden" name="base64imageFile" value="${ListProductDTO[status.index].base64imageFile}"/>
				               <img class="img-rounded" width="385" height="230"  src="${ListProductDTO[status.index].base64imageFile}" alt="${product.productName}"/>
				              <!-- end of image -->
				              <div class="overlay">
				                 <h2><c:out value="${product.productDisplayName}"/></h2>
				                  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
				                 <button class="info" type="submit" name="viewProduct" id="viewProductId" formaction="${viewProduct }">CLICK FOR MORE INFO</button>
				              </div>
				              </div>
				              
				              <div class="caption">
				                <!-- product info -->
				                <p><c:out value="${product.sdesc}"/></p>
				                <p>price: <c:out value="${product.price}"/> Rating: <c:out value="${product.rating}"/> Weight: <c:out value="${product.weight}"/></p>
                                <!-- end of product info -->
                                
                                <!-- Show add to cart if in stock -->
                                <c:choose>
                                <c:when test="${product.stockQuantity >0 }">
                                <!-- add to cart button -->
                                <sec:authorize access="!hasAuthority('ADMIN')">
	                           
			                    <button class="btn btn-primary btn-sm center-block" type="submit" name="addToCart"   >Add<span class="glyphicon glyphicon-share-alt"></span> <span class="glyphicon glyphicon-shopping-cart"></span></button>
			                    </sec:authorize>
			                    <!-- pull right if admin -->
                                <sec:authorize access="hasAuthority('ADMIN')">
	                          
			                    <button class="btn btn-primary btn-sm pull-right" type="submit" name="addToCart"  >Add<span class="glyphicon glyphicon-share-alt"></span> <span class="glyphicon glyphicon-shopping-cart"></span></button>
			                    </sec:authorize>
			                    </c:when>
			                    <c:when test="${product.stockQuantity <=0}">
			                    <img alt="Out of Stock" src="../images/outOfStock.png" />
			                    </c:when>
			                    </c:choose>
			                    <!-- end of add to cart button -->
			                 
			                    <!-- show edit and delete buttong if admin logged in -->
			                    <sec:authorize access="hasAuthority('ADMIN')">
	                	         <input type="hidden" name="productName" value="${product.productName}"/>
	                	         
	                         		<button class="btn btn-warning" title="Edit product"  type = "submit" name="editProduct"><i class="glyphicon glyphicon-edit"></i></button>
	                         		<button class="btn btn-danger" title="Delete product" type = "submit" name="deleteProduct"><i class="glyphicon glyphicon-trash"></i></button>
	                         	</sec:authorize>
	                         	
	                            <!-- end of edit/delete button block -->
				            </div>
				          </div>
				        </div>     	  
	               </form>
				        
				</c:forEach>
				</div>        
             
             <div class="panel-footer" id="table-bg"> 
             </div>
         </div>
     </div>
   </div>
   <br><br>
 </div>
 <br>
 <br>
<!-- contents end here -->
	 
<jsp:include page="../footer.jsp" />
	
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="../../js/bootstrap.min.js"></script>

  </body>
</html>