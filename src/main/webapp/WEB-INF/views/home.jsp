<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
      <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
      <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>FitHub.com</title>

    <!-- Bootstrap -->
    <link rel="stylesheet" href="../../css/bootstrap.min.css" >
	<!-- Font Awesome -->
	<link href="http://maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet">
	<!-- custom -->
	<link rel="stylesheet" href="../../css/style.css"  >
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
	  <style>
  .carousel-inner > .item > img,
  .carousel-inner > .item > a > img {
      width: 70%;
      margin: auto;
  }
  </style>
  </head>
  <body>
	<!-- start of header -->
    <div class="container-fluid">
	   <div class="navbar navbar-default navbar-fixed-top navbar-fnt navbar-backgrnd" role="navigation">
		 <div class="navbar-header">
           <a class="navbar-brand" href="#">FitHub.com</a>
         </div>
		  <ul class="nav navbar-nav">
		   <li class="nav-item">
			 <a class="nav-link" href="#">Home</a>
		   </li>
		   <li class="nav-item">
			 <a class="nav-link" href="#">Products</a>
		   </li>
		   <li class="nav-item">
			 <a class="nav-link" href="#">Contact us</a>
		   </li>
		   <li class="nav-item">
			 <a class="nav-link" href="#">About us</a>
		   </li>
		   <sec:authorize access="isAnonymous()">
		   <li class="nav-item">
		   <a class="nav-link" href="<c:url value="/userRegister"/>" data-toggle="modal">Register</a>
		   </li>
		   </sec:authorize>
		   <sec:authorize url="/admin">
		   	<li class="nav-item">
		   <a class="nav-link" data-toggle="modal" href="<c:url value="/admin/userRegister"/>">Register</a>
		   </li>
		   </sec:authorize>
		  </ul>
  	
		  <form class="navbar-form navbar-right" role="form">
			<div class="input-group">
			   <span class="input-group-addon">
			     <span class = "glyphicon glyphicon-user">
				 </span>
			   </span>
				<input type="text" class="form-control" name="username" placeholder="Username">
			</div>
			<div class="input-group">
			   <span class="input-group-addon">
			     <span class = "glyphicon glyphicon-lock">
				 </span>
			   </span>
				<input type="text" class="form-control" name="password" placeholder="Password">
			</div>
			<button type="submit" class="btn btn-primary">Sign In</button>
          </form>
		 </nav>
       </div>
    </div>
<!-- Modal -->
<div id="register" class="modal fade" role="dialog">
  <div class="modal-dialog">

    <!-- Modal content-->
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal">&times;</button>
        <h4 class="modal-title">Registration Form</h4>
      </div>
<br>	  
<form class="form-horizontal">
<fieldset>
<!-- Text input-->
<div class="form-group">
  <label class="col-md-4 control-label" for="fn">First name</label>  
  <div class="col-md-4">
  <input id="fn" name="fn" type="text" placeholder="first name" class="form-control input-md" required="">
    
  </div>
</div>

<!-- Text input-->
<div class="form-group">
  <label class="col-md-4 control-label" for="ln">Last name</label>  
  <div class="col-md-4">
  <input id="ln" name="ln" type="text" placeholder="last name" class="form-control input-md" required="">
    
  </div>
</div>

<!-- Text input-->
<div class="form-group">
  <label class="col-md-4 control-label" for="cmpny">Company</label>  
  <div class="col-md-4">
  <input id="cmpny" name="cmpny" type="text" placeholder="company" class="form-control input-md" required="">
    
  </div>
</div>

<!-- Text input-->
<div class="form-group">
  <label class="col-md-4 control-label" for="email">Email</label>  
  <div class="col-md-4">
  <input id="email" name="email" type="text" placeholder="email" class="form-control input-md" required="">
    
  </div>
</div>

<!-- Text input-->
<div class="form-group">
  <label class="col-md-4 control-label" for="add1">Address 1</label>  
  <div class="col-md-4">
  <input id="add1" name="add1" type="text" placeholder="" class="form-control input-md" required="">
    
  </div>
</div>

<!-- Text input-->
<div class="form-group">
  <label class="col-md-4 control-label" for="add2">Address 2</label>  
  <div class="col-md-4">
  <input id="add2" name="add2" type="text" placeholder="" class="form-control input-md">
    
  </div>
</div>

<!-- Text input-->
<div class="form-group">
  <label class="col-md-4 control-label" for="city">City</label>  
  <div class="col-md-4">
  <input id="city" name="city" type="text" placeholder="city" class="form-control input-md" required="">
    
  </div>
</div>

<!-- Text input-->
<div class="form-group">
  <label class="col-md-4 control-label" for="zip">Zip Code</label>  
  <div class="col-md-4">
  <input id="zip" name="zip" type="text" placeholder="Zip Code" class="form-control input-md" required="">
    
  </div>
</div>

<!-- Text input-->
<div class="form-group">
  <label class="col-md-4 control-label" for="ctry">Country</label>  
  <div class="col-md-4">
  <input id="ctry" name="ctry" type="text" placeholder="Country" class="form-control input-md" required="">
    
  </div>
</div>


</fieldset>
</form>

      <div class="modal-footer">
        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
		<input class="btn btn-primary" type="submit" value="Submit">
      </div>
    </div>

  </div>
</div>
	 <!-- end of header -->

	 <!-- contents start here -->
	 <div class="container-fluid">
	    Welcome to FitHub.com 
	 </div>
	 <!-- contents end here -->
	 
	<!-- start of footer -->
	<div class="container-fluid">
	   <div class="navbar navbar-default navbar-fixed-bottom navbar-fnt navbar-backgrnd" role="navigation">
	      <div class="navbar-text pull-left">
              <p class="txt-railway"> &#169; 2016 FitHub.com</p>
          </div>
		  <div class="navbar-text pull-right">
                <a href="https://www.facebook.com/fithub"><i class="fa fa-facebook-square fa-3x social"></i></a>
	            <a href="https://twitter.com/fithub"><i class="fa fa-twitter-square fa-3x social"></i></a>
		  </div>
       </div> 
    </div>
	<!-- end of footer -->
	
		<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="../../js/jquery-3.1.1.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="../../js/bootstrap.min.js"></script>
    <script type="text/javascript">
    
    
    $(document).ready(function(){ 
    	console.log( "ready!" );
    	if ( ${showRegister} == 1)
    		{
    		 console.log("register value",${showRegister});
	        $('#register').modal('show');
    		}
    });
    </script>
	

  </body>
</html>