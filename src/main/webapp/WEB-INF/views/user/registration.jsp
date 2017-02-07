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
        dateFormat: 'yy-mm-dd',
        minDate: "-120Y", 
        yearRange: "1900:2100" 
    });

});

</script>
  </head>
  <body>
<jsp:include page="../header.jsp" />
	<!-- contents start here -->
<div class="container-fluid">
     
  <div class="panel" id ="userReg1">
     <div class="panel-body" id="userReg3"> 
		    <c:choose>
		     <c:when test="${userDTO.isEditable == false }">
		      <!-- Show Sign Up Form -->
		    <div class="form-title" id="userRegistrationId"><h4>REGISTRATION INFO</h4></div>
		    </c:when>
		     <c:when test="${userDTO.isEditable == true }">
		      <!-- Show Update Profile Form -->
		    <div class="form-title" id="userEditId"><h4>User Update Form</h4></div>
		    </c:when>  
		    </c:choose>
          <!-- Form action variable value based on user role starts here -->
          <sec:authorize access="!hasAuthority('ADMIN')">
          <c:url var="userSave" value="/userSave"/>
          </sec:authorize>
         <sec:authorize access="hasAuthority('ADMIN')">
         <c:url var="userSave" value="/admin/userSave"/>
         </sec:authorize>
           <!-- Form action variable value based on user role ends here -->
	        <form:form modelAttribute="userDTO" method="POST" action="${userSave}">
              <div class="col-xs-12">    
                <div class=" form-group row">
                  <div class="col-sm-4">
                       <form:label path="givenName" class="form-label">Given Name:<span class="glyphicon glyphicon-user"> </span> </form:label><br>
                       <form:input class="form-control  form-field"  type= "text" path="givenName" id="givenNameId" placeHolder= "Enter Given names" />
                       <form:errors  path="givenName" style="color: red;"/>
                  </div>
                  <div class="col-sm-4">
                       <form:label path="familyName" class="form-label">Family Name:<span class="glyphicon glyphicon-user"> </span> </form:label><br>
                       <form:input class="form-control  form-field"  type= "text" path="familyName" id="familyNameId" placeHolder= "Enter family name" />
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
                       <form:label path="sex" class="form-label">Gender:<span class="glyphicon glyphicon-user"> </span></form:label><br>
			           <form:select class="form-control  form-field" path="sex" id="sexId">
			          <form:option value="">--- Select ---</form:option>
			           <form:option value="MALE">MALE</form:option>
			           <form:option value="FEMALE">FEMALE</form:option>
			           <form:option value="UNDISCLOSED">Prefer not to answer</form:option>
			           <form:errors  path="sex" style="color: red;"/>
			           </form:select>
                  </div>
                  <div class="col-sm-4 form-group">
			           <form:label path="dateOfBirth" class="form-label">Date Of Birth:<span class="glyphicon glyphicon-calendar"> </span></form:label><br>
			           <form:input class="form-control  form-field"  type= "text" path="dateOfBirth" id="dateOfBirthId" placeHolder= "Enter date of birth"  />
			           <form:errors  path="dateOfBirth" style="color: red;"/>
                  </div>
                </div>
              </div>
              
              <div class="col-sm-12">    
                <div class="form-group row">
                  <div class="col-sm-4 form-group">
			           <form:label path="address" class="form-label">Address:<span class="glyphicon glyphicon-home"> </span></form:label><br>
			           <form:input class="form-control  form-field" type= "text" path="address" id="addressId" placeHolder= "Enter Streen number and street name"  />
			           <form:errors  path="address" style="color: red;"/>
                  </div>
                  <div class="col-sm-4 form-group">
			           <form:label path="city" class="form-label">City:<span class="glyphicon glyphicon-home"> </span></form:label><br>
			           <form:input class="form-control  form-field" type= "text" path="city" id="cityId" placeHolder= "Enter City"  />
			           <form:errors  path="city" style="color: red;"/>
                  </div>
                </div>
              </div>
              
              <div class="col-sm-12">    
                <div class="form-group row">
                  <div class="col-sm-4 form-group">
			           <form:label path="province" class="form-label">Province:<span class="glyphicon glyphicon-home"> </span></form:label><br>
			           <form:input class="form-control  form-field" type= "text" path="province" id="provinceId" placeHolder= "Enter Province"  />
			           <form:errors  path="province" style="color: red;"/>
                  </div>
                  <div class="col-sm-4 form-group">
			           <form:label path="country" class="form-label">Country:<span class="glyphicon glyphicon-home"> </span></form:label><br>
			           <form:select class="form-control  form-field" type= "select" path="country" id="countryId"  >
			           <form:option value="">--- Select ---</form:option>
			           <form:option value="CANADA">Canada</form:option>
			           <form:option value="USA">USA</form:option>
			           <form:errors  path="country" style="color: red;"/>
			           </form:select>
                  </div>
                </div>
              </div>
              
              <div class="col-sm-12">    
                <div class="form-group row">
                  <div class="col-sm-4 form-group">
			           <form:label path="zipcode" class="form-label">Zip/Postal code:<span class="glyphicon glyphicon-home"> </span></form:label><br>
			           <form:input class="form-control  form-field" path="zipcode" id="zipcodeId" placeHolder= "Enter Zip/postal code"  />
			           <form:errors  path="zipcode" style="color: red;"/>
                  </div>
                  <div class="col-sm-4 form-group">
			           <form:label path="phone" class="form-label">Phone:<span class="glyphicon glyphicon-phone"> </span></form:label><br>
			           <form:input class="form-control  form-field" type="tel" path="phone" id="phoneId" placeHolder= "Enter Phone number" />
			           <form:errors  path="phone" style="color: red;"/>
                  </div>
                </div>
              </div>
              
              <div class="col-sm-12">    
                <div class="form-group row">
                  <div class="col-sm-4 form-group">
			           <form:label path="email" class="form-label">Email:<span class="glyphicon glyphicon-envelope"></span></form:label><br>
			           <form:input class="form-control  form-field" type="email" path="email" id="emailId" placeHolder= "Enter email address"  />
			           <form:errors  path="email" style="color: red;"/>
                  </div>
                  <div class="col-sm-4 form-group">
			           <form:label path="userName" class="form-label">UserName:<span class="glyphicon glyphicon-user"> </span></form:label><br>
			           <form:input class="form-control  form-field" path="userName" id="userNameId" placeHolder= "Choose a username"  />
			           <form:errors  path="userName" style="color: red;" id="userNameExistsErrorId"/>
                  </div>
                </div>
                </div>
              
              <div class="col-sm-12">    
                <div class="form-group row">
                  <div class="col-sm-4 form-group">
			           <form:label path="password" class="form-label">Password:<span class="glyphicon glyphicon-lock"></span></form:label><br>
			           <form:input class="form-control  form-field" type="password" path="password" id="passwordId" placeHolder= "Choose password"  />
			           <form:errors  path="password" style="color: red;"/>
                  </div>
                  <div class="col-sm-4 form-group">
			           <form:label path="repeatPassword" class="form-label">Repeat Password:<span class="glyphicon glyphicon-lock"></span></form:label><br>
			           <form:input class="form-control  form-field" type="password" path="repeatPassword" id="repeatPasswordId" placeHolder= "Re-enter password"  />
			           <form:errors  path="repeatPassword" style="color: red;"/>
                  </div>
                </div>
              </div>
              
                <div class="col-sm-12">    
                <div class="form-group row">
                  <div class="col-sm-4 form-group">
			           <form:label path="password" class="form-label">Security Question:<span class="glyphicon glyphicon-question"></span></form:label><br>
			           <form:input class="form-control  form-field" type="text"  autocomplete="off" path="securityQuestion" id="securityQuestionId" placeHolder= "Security Question for Password Reset"  />
			           <form:errors  path="securityQuestion" style="color: red;"/>
                  </div>
                  <div class="col-sm-4 form-group">
			           <form:label path="repeatPassword" class="form-label">Answer<span class="glyphicon glyphicon-lock"></span></form:label><br>
			           <form:input class="form-control  form-field" type="text"  autocomplete="off" path="securityQuestionAnswer" id="securityQuestionAnswerId" placeHolder= "Re-enter password"  />
			           <form:errors  path="securityQuestionAnswer" style="color: red;"/>
                  </div>
                </div>
              </div>
              
              <div class="col-sm-12">    
                <div class="form-group row">
                  <div class="col-sm-4 form-group">
			          <form:label path="paymentMode" class="form-label">Payment Mode:<span class="glyphicon glyphicon-credit-card"></span></form:label><br>
			          <form:select class="form-control selectbox  form-field"  type= "select" path="paymentMode" id="paymentModeId">
			          <form:option value="">--- Select ---</form:option>
			          <form:option value="DEBIT">Debit</form:option>
			          <form:option value="CREDIT">Credit</form:option>
			          <form:option value="UNDISCLOSED">Decide Later</form:option>
			           <form:errors  path="paymentMode" style="color: red;"/>
			          </form:select>
                  </div>
                  
                    <c:if test="${recaptchaPublicKey !=null }">
                  <!-- Google recaptcha for non-testing profiles of application -->
                
                      	<div class="col-sm-4 form-group">
			           	 <!-- Google recaptcha -->
               			
    			<div id="g-recaptcha"></div>
    			<form:hidden path="recaptchaResponse" id="recaptchaResponseId"/>
    			<script>
        		var captureRecaptchaResponse = function() {
        			console.log("${recaptchaPublicKey}");
          			  	grecaptcha.render('g-recaptcha', {
                			'sitekey' : '<c:out value="${recaptchaPublicKey}" />',
                			'callback' : function(response) {
                   	 	document.getElementById('recaptchaResponseId').value = response;
               					 },
               			 'theme' : 'dark'
           				 });
          			  console.log(document.getElementById('recaptchaResponseId').value);
        			}
   					 </script>
                  		</div>
                  </c:if>
                   <!-- Google recaptcha for non-testing profiles of application ends -->
                   
                    <c:if test="${recaptchaPublicKey ==null }">
                   <!-- Google recaptcha for testing profiles of application with testing site key-->
                   
                    	<div class="col-sm-4 form-group">
			           	 <!-- Google recaptcha -->
			           	 
			           	  <div id="g-recaptcha"></div>
    					<form:hidden path="recaptchaResponse" id="recaptchaResponseId"/>
    					<script>
        				var captureRecaptchaResponse = function() {
          			  	grecaptcha.render('g-recaptcha', {
                			'sitekey' : '<c:out value= "6LeIxAcTAAAAAJcZVRqyHh71UMIEGNQ_MXjiZKhI" />',
                			'callback' : function(response) {
                   	 	document.getElementById('recaptchaResponseId').value = response;
               					 },
               			 'theme' : 'dark'
           				 });
        				}
   					 </script>
                  		</div>
                  	</c:if>
                   
                    <!-- Google recaptcha for testing profiles of application ends -->
                <script src="https://www.google.com/recaptcha/api.js?onload=captureRecaptchaResponse&render=explicit" async defer></script>
                <form:errors path="recaptchaResponse"/> 
                </div>
              </div>
              <c:choose>
              <c:when test="${userDTO.isEditable == false }">
              
              

              
             <!--  Sign Up button for new users -->
              <div class="col-sm-12"> 
                <div class="form-group row">
                  <div class="col-sm-8 form-group">
                       <!-- input class="btn btn-primary btn-block" type="submit" name="userRegister" id="userRegister" value="Submit" /-->
                       <button type="submit" class="btn btn-success btn-block  submit-button" name="userRegister" id="userRegisterId"  ><i class="glyphicon glyphicon-pencil"></i> Sign Up</button>
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
                       <button type="submit" class="btn btn-success btn-block  submit-button" name="userUpdate" id="userUpdateId"  ><i class="glyphicon glyphicon-pencil"></i> Update Profile</button>
                  </div>
                </div> 
              </div>           
             <!--  Profile Edit button -->
             </c:when>
             </c:choose> 
			<br><br>              
            </form:form>
          </div>
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