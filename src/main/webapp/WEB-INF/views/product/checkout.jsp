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
  <!--CREDIT CART PAYMENT-->
                    <div class="panel panel-info">
                        <div class="panel-heading"><span><i class="glyphicon glyphicon-lock"></i></span> Secure Payment</div>
                        <div class="panel-body">
                            <div class="form-group">
                                <div class="col-md-12"><strong>Card Type:</strong></div>
                                <div class="col-md-12">
                                    <select id="CreditCardType" name="CreditCardType" class="form-control">
                                        <option value="5">Visa</option>
                                        <option value="6">MasterCard</option>
                                        <option value="7">American Express</option>
                                        <option value="8">Discover</option>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-12"><strong>Credit Card Number:</strong></div>
                                <div class="col-md-12"><input type="text" class="form-control" name="car_number" value="" /></div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-12"><strong>Card CVV:</strong></div>
                                <div class="col-md-12"><input type="text" class="form-control" name="car_code" value="" /></div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-12">
                                    <strong>Expiration Date</strong>
                                </div>
                                <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
                                    <select class="form-control" name="">
                                        <option value="">Month</option>
                                        <option value="01">01</option>
                                        <option value="02">02</option>
                                        <option value="03">03</option>
                                        <option value="04">04</option>
                                        <option value="05">05</option>
                                        <option value="06">06</option>
                                        <option value="07">07</option>
                                        <option value="08">08</option>
                                        <option value="09">09</option>
                                        <option value="10">10</option>
                                        <option value="11">11</option>
                                        <option value="12">12</option>
                                </select>
                                </div>
                                <div class="col-lg-6 col-md-6 col-sm-6 col-xs-12">
                                    <select class="form-control" name="">
                                        <option value="">Year</option>
                                        <option value="2015">2015</option>
                                        <option value="2016">2016</option>
                                        <option value="2017">2017</option>
                                        <option value="2018">2018</option>
                                        <option value="2019">2019</option>
                                        <option value="2020">2020</option>
                                        <option value="2021">2021</option>
                                        <option value="2022">2022</option>
                                        <option value="2023">2023</option>
                                        <option value="2024">2024</option>
                                        <option value="2025">2025</option>
                                </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-12">
                                    <span>Pay secure using your credit card.</span>
                                </div>
                                <div class="col-md-12">
                                    <ul class="cards">
                                        <li class="visa hand">Visa</li>
                                        <li class="mastercard hand">MasterCard</li>
                                        <li class="amex hand">Amex</li>
                                    </ul>
                                    <div class="clearfix"></div>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-6 col-sm-6 col-xs-12">
                                    <button type="submit" class="btn btn-primary btn-submit-fix">Place Order</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!--CREDIT CART PAYMENT END-->
                
<!-- contents end here -->
	 
<jsp:include page="../footer.jsp" />
	
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="../../js/bootstrap.min.js"></script>

  </body>
</html>