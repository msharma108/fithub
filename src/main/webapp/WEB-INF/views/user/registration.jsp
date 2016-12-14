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
<script>


$(document).ready(function() {
    $.datepicker.setDefaults( $.datepicker.regional["es"] );
    $("#dateOfBirthId").datepicker(
    {
        changeMonth: true,
        changeYear: true,
        dateFormat: 'yy/mm/dd',
        minDate: "-120Y", 
        yearRange: "1900:2016" 
    });

});

</script>
  </head>
  <body>
<jsp:include page="../header.jsp" />
	<!-- contents start here -->
<div class="container-fluid">
     
  <div class="panel panel-primary" style="border: 2px solid maroon">
    <c:choose>
     <c:when test="${userDTO.isEditable == false }">
      <!-- Show Sign Up Form -->
    	<div class="panel-heading" style="color: white; background-color: maroon;"><b>REGISTRATION INFO</b></div>
    </c:when>
     <c:when test="${userDTO.isEditable == true }">
      <!-- Show Update Profile Form -->
    <div class="panel-heading" style="color: white; background-color: maroon;"><b>User Update Form</b></div>
    </c:when>  
    </c:choose>
 
     <div class="panel-body" style="background-color: #C1E1A6;"> 
          <!-- Form action variable value based on user role starts here -->
          <sec:authorize access="hasAnyRole('CUSTOMER', 'ROLE_ANONYMOUS')">
          <c:url var="userSave" value="/userSave"/>
          </sec:authorize>
         <sec:authorize access="hasAuthority('ADMIN')">
         <c:url var="userSave" value="/admin/userSave"/>
         </sec:authorize>
           <!-- Form action variable value based on user role ends here -->
	        <form:form modelAttribute="userDTO" method="POST" action="${userSave}" style="color: green;">
              <div class="col-xs-12">    
                <div class=" form-group row">
                  <div class="col-sm-4">
                       <form:label path="givenName">Given Name:<span class="glyphicon glyphicon-user"> </span> </form:label><br>
                       <form:input class="form-control" path="givenName" id="givenNameId" placeHolder= "Enter Given names" />
                       <form:errors  path="givenName" style="color: red;"/>
                  </div>
                  <div class="col-sm-4">
                       <form:label path="familyName">Family Name:<span class="glyphicon glyphicon-user"> </span> </form:label><br>
                       <form:input class="form-control" path="familyName" id="familyNameId" placeHolder= "Enter family name" />
                       <form:errors  path="familyName" style="color: red;"/>
                  </div>
                </div>
              </div>
              
            <sec:authorize access="isAuthenticated()">
         <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
         </sec:authorize>
              
              <div class="col-sm-12">    
                <div class="form-group row">
                  <div class="col-sm-4 form-group">
                       <form:label path="sex">Gender:<span class="glyphicon glyphicon-user"> </span></form:label><br>
			           <form:select class="form-control" path="sex" id="sexId">
			          <form:option value="">--- Select ---</form:option>
			           <form:option value="MALE">MALE</form:option>
			           <form:option value="FEMALE">FEMALE</form:option>
			           <form:option value="UNDISCLOSED">UNDISCLOSED</form:option>
			           <form:errors  path="sex" style="color: red;"/>
			           </form:select>
                  </div>
                  <div class="col-sm-4 form-group">
			           <form:label path="dateOfBirth">Date Of Birth:<span class="glyphicon glyphicon-calendar"> </span></form:label><br>
			           <form:input class="form-control" path="dateOfBirth" id="dateOfBirthId" placeHolder= "Enter date of birth"  />
			           <form:errors  path="dateOfBirth" style="color: red;"/>
                  </div>
                </div>
              </div>
              
              <div class="col-sm-12">    
                <div class="form-group row">
                  <div class="col-sm-4 form-group">
			           <form:label path="address">Address:<span class="glyphicon glyphicon-home"> </span></form:label><br>
			           <form:input class="form-control" path="address" id="addressId" placeHolder= "Enter Streen number and street name"  />
			           <form:errors  path="address" style="color: red;"/>
                  </div>
                  <div class="col-sm-4 form-group">
			           <form:label path="city">City:<span class="glyphicon glyphicon-home"> </span></form:label><br>
			           <form:input class="form-control" path="city" id="cityId" placeHolder= "Enter City"  />
			           <form:errors  path="city" style="color: red;"/>
                  </div>
                </div>
              </div>
              
              <div class="col-sm-12">    
                <div class="form-group row">
                  <div class="col-sm-4 form-group">
			           <form:label path="province">Province:<span class="glyphicon glyphicon-home"> </span></form:label><br>
			           <form:input class="form-control" path="province" id="provinceId" placeHolder= "Enter Province"  />
			           <form:errors  path="province" style="color: red;"/>
                  </div>
                  <div class="col-sm-4 form-group">
			           <form:label path="country">Country:<span class="glyphicon glyphicon-home"> </span></form:label><br>
			           <form:select class="form-control input-medium bfh-countries" data-country="US" path="country" id="countryId"  >
			           <form:option value="">--- Select ---</form:option>
			           <form:option value="can">Canada</form:option>
			           <form:option value="usa">USA</form:option>
			           <form:errors  path="country" style="color: red;"/>
			           </form:select>
                  </div>
                </div>
              </div>
              
              <div class="col-sm-12">    
                <div class="form-group row">
                  <div class="col-sm-4 form-group">
			           <form:label path="zipcode">Zip/Postal code:<span class="glyphicon glyphicon-home"> </span></form:label><br>
			           <form:input class="form-control" path="zipcode" id="zipcodeId" placeHolder= "Enter Zip/postal code"  />
			           <form:errors  path="zipcode" style="color: red;"/>
                  </div>
                  <div class="col-sm-4 form-group">
			           <form:label path="phone">Phone:<span class="glyphicon glyphicon-phone"> </span></form:label><br>
			           <form:input class="form-control" type="tel" path="phone" id="phoneId" placeHolder= "Enter Phone number" />
			           <form:errors  path="phone" style="color: red;"/>
                  </div>
                </div>
              </div>
              
              <div class="col-sm-12">    
                <div class="form-group row">
                  <div class="col-sm-4 form-group">
			           <form:label path="email">Email:<span class="glyphicon glyphicon-envelope"></span></form:label><br>
			           <form:input class="form-control" type="email" path="email" id="emailId" placeHolder= "Enter email address"  />
			           <form:errors  path="email" style="color: red;"/>
                  </div>
                  <div class="col-sm-4 form-group">
			           <form:label path="userName">UserName:<span class="glyphicon glyphicon-user"> </span></form:label><br>
			           <form:input class="form-control" path="userName" id="userNameId" placeHolder= "Choose an username"  />
			           <form:errors  path="userName" style="color: red;"/>
                  </div>
                </div>
                </div>
              
              <div class="col-sm-12">    
                <div class="form-group row">
                  <div class="col-sm-4 form-group">
			           <form:label path="password" >Password:<span class="glyphicon glyphicon-lock"></span></form:label><br>
			           <form:input class="form-control" type="password" path="password" id="passwordId" placeHolder= "Choose password"  />
			           <form:errors  path="password" style="color: red;"/>
                  </div>
                  <div class="col-sm-4 form-group">
			           <form:label path="repeatPassword">Repeat Password:<span class="glyphicon glyphicon-lock"></span></form:label><br>
			           <form:input class="form-control" type="password" path="repeatPassword" id="repeatPasswordId" placeHolder= "Re-enter password"  />
			           <form:errors  path="repeatPassword" style="color: red;"/>
                  </div>
                </div>
              </div>
              
              <div class="col-sm-12">    
                <div class="form-group row">
                  <div class="col-sm-4 form-group">
			          <form:label path="paymentMode">Payment Mode:<span class="glyphicon glyphicon-credit-card"></span></form:label><br>
			          <form:select class="form-control" path="paymentMode" id="paymentModeId">
			          <form:option value="">--- Select ---</form:option>
			          <form:option value="debit">Debit</form:option>
			          <form:option value="credit">Credit</form:option>
			          <form:option value="paypal">Paypal</form:option>
			           <form:errors  path="paymentMode" style="color: red;"/>
			          </form:select>
                  </div>
                </div>
              </div>
              <c:choose>
              <c:when test="${userDTO.isEditable == false }">
             <!--  Sign Up button for new users -->
              <div class="col-sm-12"> 
                <div class="form-group row">
                  <div class="col-sm-8 form-group">
                       <!-- input class="btn btn-primary btn-block" type="submit" name="userRegister" id="userRegister" value="Submit" /-->
                       <button type="submit" class="btn btn-primary btn-block" name="userRegister" id="userRegister"  ><i class="glyphicon glyphicon-pencil"></i> Sign Up</button>
                  </div>
                </div> 
              </div>
              <!--  Sign Up button for new users Ends -->
              </c:when>
              <c:when test="${userDTO.isEditable == true }">
             <!--  Profile Edit button -->  
               <div class="col-sm-12"> 
                <div class="form-group row">
                  <div class="col-sm-8 form-group">
                       <!-- input class="btn btn-primary btn-block" type="submit" name="userUpdate" id="userUpdate" value="Submit" /-->
                       <button type="submit" class="btn btn-primary btn-block" name="userUpdate" id="userUpdate"  ><i class="glyphicon glyphicon-pencil"></i> Update Profile</button>
                  </div>
                </div> 
              </div>           
             <!--  Profile Edit button -->
             </c:when>
             </c:choose> 
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