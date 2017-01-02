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
 <div class="col-md-12 col-xs-12">
  <div class="row">
    <div class="panel panel-primary">
          <div class="panel-heading">
                <h3 class="panel-title">Order list</h3>
          </div>
	      <table id="orderList" class="table table-striped">
    				<thead>
						<tr>
							<th style="width:5%">OrderId</th>
							<th style="width:10%">Name</th>
							<th style="width:10%">Order Amount</th>
							<th style="width:10%">Status</th>
							<th style="width:10%">Payment Status</th>
							<th style="width:15%">Order Creation Date</th>
							
							<th style="width:10%" class="text-center"></th>
						</tr>
					</thead>
					<tbody>
							<c:if test="${exception !=null }">
							<c:out value="${exception }"/>
							</c:if>
							

							
					  <c:forEach items="${orderList}" var="salesOrder">
					  				  
						<tr>
							<!-- link to Order Id & user profile page based on user role -->
							
							<sec:authorize access="hasAuthority('ADMIN')">
							<c:url var="adminViewOrder" value="/admin/viewOrder/${salesOrder.user.userName }/${salesOrder.salesOrderId}"/>
							<td data-th="OrderId" class="text-left"><a href="${adminViewOrder}">${salesOrder.salesOrderId}</a></td>							
							 </sec:authorize>
							<sec:authorize access="hasAuthority('CUSTOMER')">
							<c:url var="userViewOrder" value="/viewOrder/${salesOrder.user.userName }/${salesOrder.salesOrderId}"/>
							<td data-th="OrderId" class="text-left"><a href="${userViewOrder}">${salesOrder.salesOrderId}</a></td>							
							 </sec:authorize>
							 
							 <sec:authorize access="hasAuthority('ADMIN')">
							<td data-th="Name" class="text-left"><a href="<c:url value="/admin/viewUser/${salesOrder.user.userName}"/>">${salesOrder.user.givenName} ${salesOrder.user.familyName}</a></td>
							</sec:authorize>
							
							<sec:authorize access="hasAuthority('CUSTOMER')">
							<td data-th="Name" class="text-left"><a href="<c:url value="/viewUser/${salesOrder.user.userName}"/>">${salesOrder.user.givenName} ${salesOrder.user.familyName}</a></td>
							 </sec:authorize>
							
							<td data-th="OrderAmount" class="text-left">${salesOrder.salesOrderTotalCost} </td>
							<td data-th="Status" class="text-left">${salesOrder.status} </td>
							<td data-th="PaymentStatus" class="text-left">${salesOrder.paymentStatus} </td>
							<td data-th="Order Creation Date" class="text-left">${salesOrder.salesOrderCreationDate}</td>
							
							<sec:authorize access="hasAuthority('ADMIN')">
							<td class="actions" data-th="">
							   <form action="<c:url value="/admin/cancelOrder/${salesOrder.salesOrderId}"/>" method="post">
							    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							    							    						   
							    <button class="btn btn-danger btn-sm" title="Cancel Order" name="cancelOrder" ><span class="glyphicon glyphicon-remove"></span></button>
							   </form>
							</td>
							</sec:authorize>
						</tr>
				      </c:forEach>
					</tbody>
	      </table>
	      <hr style="height:1px;"/>
    </div>
  </div>
 </div>
</div>
<br><br><br><br><br>

<!-- contents end here -->
	 
<jsp:include page="../footer.jsp" />
	
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="../../js/bootstrap.min.js"></script>

  </body>
</html>