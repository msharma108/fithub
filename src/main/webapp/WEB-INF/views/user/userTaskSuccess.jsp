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
	<link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">

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

  <!-- Add product success Modal -->
  <div class="modal" id="AddProductSuccessModal">
    <div class="modal-dialog">
    
      <div class="modal-content">
        <div class="modal-header">
          <h4 class="modal-title"><span class="glyphicon glyphicon-info-sign"> </span> Product</h4>
        </div>
        <div class="modal-body">
          <p>Product has been added Successfully.  <span class="glyphicon glyphicon-ok" style="color:green;"> </span> </p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-success" data-dismiss="modal">Close</button>
        </div>
      </div>
      
    </div>
  </div>   
 
 
  <!-- Profile update success Modal -->
  <div class="modal" id="userProfileUpdateSuccessModal">
    <div class="modal-dialog">
    
      <div class="modal-content">
        <div class="modal-header">
          <h4 class="modal-title"><span class="glyphicon glyphicon-info-sign"> </span> Profile Update</h4>
        </div>
        <div class="modal-body">
          <p>User Profile has been updated Successfully.  <span class="glyphicon glyphicon-ok" style="color:green;"> </span> </p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-success" data-dismiss="modal">Close</button>
        </div>
      </div>
      
    </div>
  </div>   
     
   <!-- User registration success Modal -->
  <div class="modal fade" id="userRegistrationSuccessModal" role="dialog">
    <div class="modal-dialog">
    
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title"><span class="glyphicon glyphicon-info-sign"> </span> User Registration</h4>
        </div>
        <div class="modal-body">
          <p>User registration has been completed Successfully.  <span class="glyphicon glyphicon-ok" style="color:green;"> </span> </p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-success" data-dismiss="modal">Close</button>
        </div>
      </div>
      
    </div>
  </div>
  
     <!-- User registration success Modal -->
  <div class="modal fade" id="userRoleChangeSuccessModal" role="dialog">
    <div class="modal-dialog">
    
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title"><span class="glyphicon glyphicon-info-sign"> </span> User Role Change</h4>
        </div>
        <div class="modal-body">
          <p>User Role has been changed Successfully.  <span class="glyphicon glyphicon-ok" style="color:green;"> </span> </p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-success" data-dismiss="modal">Close</button>
        </div>
      </div>
      
    </div>
  </div>
  
  
     <!-- User deletion: success Modal -->
  <div class="modal fade" id="userDeletionSuccessModal" role="dialog">
    <div class="modal-dialog">
    
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title"><span class="glyphicon glyphicon-info-sign"> </span> User Deletion</h4>
        </div>
        <div class="modal-body">
          <p>User has been deleted Successfully.  <span class="glyphicon glyphicon-ok" style="color:green;"> </span> </p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-success" data-dismiss="modal">Close</button>
        </div>
      </div>
      
    </div>
  </div>
  
    <!-- Password reset success Modal -->
  <div class="modal" id="userPasswordResetSuccessModal">
    <div class="modal-dialog">
    
      <div class="modal-content">
        <div class="modal-header">
          <h4 class="modal-title"><span class="glyphicon glyphicon-info-sign"> </span>Password Reset</h4>
        </div>
        <div class="modal-body">
          <p>Password Reset Successfully, Check your Email  <span class="glyphicon glyphicon-ok" style="color:green;"> </span> </p>
        </div>
        <div class="modal-footer">
          <button type="button" class="btn btn-success" data-dismiss="modal">Close</button>
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
    <script type="text/javascript">
    
    
    $(document).ready(function(){ 
    	
    	var userRegisterSuccess =1;
    	var userDeleteSuccess =2;
    	var userRoleChangeSuccess =3;
    	var userUpdateSuccess =4;
    	var userPasswordResetSuccess =5;
    	console.log( "ready!" );
    	if ( ${userTaskTypeCompleted} == userRegisterSuccess)
    		{
    		 console.log("userRegisterSuccess value",typeof(${userTaskTypeCompleted}));
	        $('#userRegistrationSuccessModal').modal('show');
    		}
    	else if ( ${userTaskTypeCompleted} == userDeleteSuccess)
			{
			 console.log("userDeleteSuccess value",typeof(${userTaskTypeCompleted}));
        	$('#userDeletionSuccessModal').modal('show');
			}
		else if ( ${userTaskTypeCompleted} == userRoleChangeSuccess)
			{
			 console.log("userRoleChangeSuccess value",typeof(${userTaskTypeCompleted}));
       		 $('#userRoleChangeSuccessModal').modal('show');
			}
    	else if ( ${userTaskTypeCompleted} == userUpdateSuccess)
			{
			 console.log("userUpdateSuccess value",typeof(${userTaskTypeCompleted}));
        	$('#userProfileUpdateSuccessModal').modal('show');
			}
    	else if ( ${userTaskTypeCompleted} == userPasswordResetSuccess)
		{
		 console.log("userPasswordResetSuccess value",typeof(${userTaskTypeCompleted}));
    	$('#userPasswordResetSuccessModal').modal('show');
		}
    	
		else
			{
				console.log("all false");
			}
    });
    </script>
	

  </body>
</html>