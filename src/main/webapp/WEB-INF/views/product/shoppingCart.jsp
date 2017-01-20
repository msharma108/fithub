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
<!-- Ref: http://bootsnipp.com/snippets/featured/responsive-shopping-cart -->

<div class="container-fluid">
	<table id="shoppingcart" class="table table-striped">
    				<thead id="table-bg">
						<tr class="table-header">
							<th style="width:50%">Product</th>
							<th style="width:10%">Price</th>
							<th style="width:10%">Quantity</th>
							<th style="width:20%" class="text-center">Subtotal</th>
							<th style="width:10%"></th>
						</tr>
					</thead>
					<tbody>
							<c:if test="${exception !=null }">
							<c:out value="${exception }"/>
							</c:if>
					  <c:forEach items="${shoppingCart.cartProductList}" var="cartItem">
					  <c:url var="cartOperation" value="/constructUrlForProductOperations/${cartItem.productName}"/>
					  <form action="${cartOperation }" method="POST">
						<tr>
							<td data-th="Product">
								<div class="row">
									<div class="col-sm-2 hidden-xs"><img class="img-responsive"  src="${cartItem.base64imageFile}" alt="${cartItem.productName}"/></div>
									<div class="col-sm-10">
										<h4 class="nomargin">${cartItem.productDisplayName}</h4>
										<p>${cartItem.sdesc}</p>
									</div>
								</div>
							</td>
							<td data-th="Price">${cartItem.price}</td>
							<td data-th="Quantity">
								<input type="number" class="form-control text-center" name="quantityInCart" value="${cartItem.quantityInCart }" min ="0" max="${cartItem.stockQuantity }">
							</td>

							<c:set var="subTotal" value="${cartItem.price * cartItem.quantityInCart}"/>
							<td data-th="Subtotal" class="text-center">${subTotal} </td>
							
							<td class="actions" data-th="">
							 <input type="hidden" name="subTotal" value="${subTotal}"/>
							   <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							   
								<button class="btn btn-primary btn-sm" title="Update Shopping cart" name="refreshCart"><span class="glyphicon glyphicon-refresh"></span></button>
								<button class="btn btn-danger btn-sm" title="Delete Item" name="removeFromCart" ><span class="glyphicon glyphicon-trash"></span></button>
							   </form>
							</td>
							
						</tr>
						
						</c:forEach>
					  
					</tbody>
					<tfoot id="table-bg">
						<tr class="table-header"> 
							<td> <form action="/viewProducts" method="POST">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							<button name="continueShopping" class="btn btn-primary"><span class="glyphicon glyphicon-backward"></span> Continue Shopping</button></form></td>
							
							<td colspan="2" class="hidden-xs"></td>
							<td class="hidden-xs text-center"><strong>${shoppingCart.cartTotalCost } </strong></td>
							
							<td> <form action="/orderCheckout" method="POST">
							 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							<button class="btn btn-success btn-block" id="checkoutId">Checkout <span class="glyphicon glyphicon-forward"></span></button></form></td>
						</tr>
					</tfoot>
	</table>
</div>


<!-- contents end here -->
	 
<jsp:include page="../footer.jsp" />
	
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="../../js/bootstrap.min.js"></script>

  </body>
</html>