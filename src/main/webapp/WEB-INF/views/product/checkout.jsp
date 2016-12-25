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
	<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
	<!-- JQuery -->
	<link rel="stylesheet" href="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/themes/smoothness/jquery-ui.css">
	 <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.3/jquery.min.js"></script>
	 <script src="../../js/bootstrap.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.4/jquery-ui.min.js"></script> 
	<!-- custom -->
	<link rel="stylesheet" href="../css/style.css"  >
	<!-- Bootstrap -->
	<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
	
	<!-- Stripe.js -->
	<!-- Stripe Integration -->
	<!-- Reference: https://stripe.com/docs/custom-form#step-1-collecting-credit-card-information -->
	
	<script type="text/javascript" src="https://js.stripe.com/v2/"></script>
	<script type="text/javascript">
	
	// Reference: https://stripe.com/docs/custom-form#step-1-collecting-credit-card-information
	// Reference: http://www.larryullman.com/2012/12/05/writing-the-javascript-code-for-handling-stripe-payments/
	
  	Stripe.setPublishableKey('pk_test_4Gv5vV4024ApKYTS3Nkt8oeS');
	</script>
	<script type="text/javascript">
	
	
  	function clientValidationResult(result) {
 		// If errors in the form, display the errors
 		var $form = $('#payment-form');
 		
 		console.log("Inside Client Validation");
  	    $form.find('.payment-errors').text(result);
  	  $form.find('.submit').prop('disabled', false);
  	    return false;
  	}
  	
	// Client side validation & send payment data directly to Stripe
  $(document).ready(function() {
  		
  		console.log( "ready!" );
  	  var $form = $('#payment-form');
  		$form.submit(function(event) {
  	    // Submit button disabled after one click
  	    console.log( "button click!" );
  	  $form.find('.submit').prop('disabled', true);
  	    
  	    // To check for client side validation errors and prevent submission to Stripe
  	    var errorsExist = false;
  	    var ccNum = $('.card-number').val();
  	    var expMonth = $('.card-expiry-month').val();
  	  	var expYear = $('.card-expiry-year').val();
  	  	var cvc = $('.card-cvc').val();
  	    
		// Validate the Card Number:
		if (!Stripe.card.validateCardNumber(ccNum)) {
			errorsExist = true;
			 alert("Invalid Card Number, please correct");
			clientValidationResult('Invalid Card Number, please correct');
		}
  	  	
		// Validate the card expiry date:
		if (!Stripe.card.validateExpiry(expMonth,expYear )) {
			errorsExist = true;
			clientValidationResult('Invalid expiry date, please correct');
		}
  	  	
		// Validate the cvc number:
		if (!Stripe.card.validateCVC(cvc)) {
			errorsExist = true;
			clientValidationResult('Invalid CVC number, please correct');
		}
  	  	
		// Send token request to stripe only in case of successful client data validation
  	  	if(!errorsExist){

  	    // Getting token from stripe
  	    Stripe.card.createToken($form, stripeResponseHandler);
  	    	  	}

  	    // Prevent the form from being submitted:
  	    return false;
  	  });
  	});
  	
  	// Response handler for Stripe response
  	function stripeResponseHandler(status, response) {
  	  // Reference the form
  	  var $form = $('#payment-form');

  	  if (response.error) {

  		 // If errors in the form, display the errors
  	    $form.find('.payment-errors').text(response.error.message);
  		// Enable the submit button again
  	  $form.find('.submit').prop('disabled', false); 

  	  } else {
  		  
  		 alert("Form being submitted");
		// If no errors
  	    // Grab the stripe token from the response
  	    var token = response.id;

  	    // Insert the token as a hidden value into the form for server
  	    $form.append($('<input type="hidden" name="stripeToken">').val(token));

  	    $form.get(0).submit();
  	  }
  	};
  	

  	
  	
	</script>
	
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
<!-- http://bootsnipp.com/snippets/ypqoW -->
 <div class="container wrapper">
   
            <div class="row">

               <form class="form-horizontal" action="/handleOrderCheckout" method="POST" id="payment-form">
                    <!--REVIEW ORDER-->
                    <div class = "col-lg-10" >
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            Order Details 				  
                        </div>
                        <div class="panel-body">
                            <div class="form-group">
                            
                                 <!-- loop starts -->
                                
                                <div class="col-sm-3 col-xs-3">
                                    <img class="img-responsive" src="" />
                                </div>
                                <div class="col-sm-6 col-xs-6">
                                    <div class="col-xs-12">ProductName</div>
                                    <div class="col-xs-12"><small>Quantity:<span>TotalQuantity</span></small></div>
                                </div>
                                <div class="col-sm-3 col-xs-3 text-right">
                                    <h6><span>$</span>price</h6>
                                </div>
                             </div>
                             <div class="form-group"><hr /></div>
                             
                                 <!-- loop ends -->
                             
                            <div class="form-group"><hr /></div>
                            <div class="form-group">
                                <div class="col-xs-12">
                                    <strong>Subtotal</strong>
                                    <div class="pull-right"><span>$</span><span>subTotalAmount</span></div>
                                </div>
                                <div class="col-xs-12">
                                    <small>Shipping</small>
                                    <div class="pull-right"><span>shippingCost: 10$</span></div>
                                </div>
                            </div>
                            <div class="form-group"><hr /></div>
                            <div class="form-group">
                                <div class="col-xs-12">
                                    <strong>Order Total</strong>
                                    <div class="pull-right"><span>$</span><span>totalAmount</span></div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!--REVIEW ORDER END-->
                    </div>
                    <div class = "col-lg-5">
                    <!--SHIPPING METHOD-->
                    <div class="panel panel-info">
                        <div class="panel-heading">Shipping Address</div>
                        <div class="panel-body">
                            <div class="form-group">
                                <div class="col-md-6 col-xs-12">
                                    <strong>First Name:</strong>
                                    <input type="text" name="first_name" class="form-control" value="" />
                                </div>
                                <div class="span1"></div>
                                <div class="col-md-6 col-xs-12">
                                    <strong>Last Name:</strong>
                                    <input type="text" name="last_name" class="form-control" value="" />
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-12"><strong>Address:</strong></div>
                                <div class="col-md-12">
                                    <input type="text" name="address" class="form-control" value="" />
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-6 col-xs-12">
                                    <strong>City:</strong>
                                    <input type="text" name="city" class="form-control" value="" />
                                </div>
                                <div class="span1"></div>
                                <div class="col-md-6 col-xs-12">
                                    <strong>State/Province:</strong>
                                    <input type="text" name="prov" class="form-control" value="" />
                                </div>
                            </div>
                              <div class="form-group">
                                <div class="col-md-6 col-xs-12">
                                    <strong>Country:</strong>
                                    <input type="text" name="country" class="form-control" value="" />
                                </div>
                                <div class="span1"></div>
                                <div class="col-md-6 col-xs-12">
                                    <strong>Zip/Postal Code:</strong>
                                    <input type="text" name="postal" class="form-control" value="" />
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-12"><strong>Phone Number:</strong></div>
                                <div class="col-md-12"><input type="text" name="phone_number" class="form-control" value="" /></div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-12"><strong>Email Address:</strong></div>
                                <div class="col-md-12"><input type="text" name="email_address" class="form-control" value="" /></div>
                            </div>
                        </div>
                    </div>
                    <!--SHIPPING METHOD END-->
                    </div>
                    <div class ="col-md-5">
                   
                    <!--CREDIT CART PAYMENT-->
                    <div class="panel panel-info">
                        <div class="panel-heading"><span><i class="glyphicon glyphicon-lock"></i></span> Secure Payment</div>
                        <div class="panel-body">
                        <span class="payment-errors"></span>
                            <div class="form-row">
                                <div class="col-md-12"><strong>Name On Card:</strong></div>
                                <div class="col-md-12"><input type="text" class="card-name" name="name" autocomplete="off"  required placeholder="John Doe"/></div>
                            </div>
                            <div class="form-row">
                                <div class="col-md-12"><strong>Credit Card Number:</strong></div>
                                <div class="col-md-12"><input type="text" class="card-number"  size="20" data-stripe="number" autocomplete="off" required placeholder="**** **** **** ****"/></div>
                                <span>Enter the number without spaces or hyphens.</span>
                            </div>
                            <div class="form-row">
                                <div class="col-md-12"><strong>Card CVC:</strong></div>
                                <div class="col-md-12"><input type="text" class="card-cvc" size="3" data-stripe="cvc" autocomplete="off" required placeholder="***" /></div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-12">
                                    <strong>Expiration Date</strong>
                                </div>
                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
							<div class="form-row">
    							<label>
      								<span>Expiration (MM/YY)</span>
      									<input type="number" class="card-expiry-month" size="2" data-stripe="exp_month" min="01" max="12"  autocomplete="off" required placeholder="MM">
    							</label>
    								<span> / </span>
    									<input type="number" size="4" class ="card-expiry-year" data-stripe="exp_year" min="2016" max="2030" autocomplete="off" required placeholder="YYYY">
  							</div>
                            </div>
                            <div class="form-group">
                                <div class="col-md-12">
                               <div><img class="pull-right"
                                 src="https://s3.amazonaws.com/hiresnetwork/imgs/cc.png"
                                 style="max-width: 250px; padding-bottom: 20px;">
                              </div>
                                </div>
                            </div>

                        </div>
                    </div>
                    <!--CREDIT CART PAYMENT END-->
                   </div>
                            <div class="form-group">
                                <div class="col-md-10 col-sm-6 col-xs-12">
                                    <input type="submit" class="submit" value="Submit Payment">
                                </div>
                            </div>
                </form>
            </div>
            <div class="row cart-footer">
            </div>
    </div>
    <br><br><br> <br><br>      
<!-- contents end here -->
	 
<jsp:include page="../footer.jsp" />
	
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <!-- Include all compiled plugins (below), or include individual files as needed -->


  </body>
</html>