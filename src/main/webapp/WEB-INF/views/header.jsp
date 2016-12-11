	<!-- start of header -->
	<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <div class="container-fluid">
	   <div class="navbar navbar-default navbar-fixed-top navbar-fnt navbar-backgrnd" role="navigation">
		 <div class="navbar-header">
           <a class="navbar-brand" href="#">FitHub.com</a>
         </div>
		  <ul class="nav navbar-nav">
		   <li class="nav-item">
			 <a class="nav-link" href="home.jsp"><span class="glyphicon glyphicon-home"> </span> Home</a>
		   </li>
		   <li class="nav-item">
			 <a class="nav-link" href="#"><span class="glyphicon glyphicon-shopping-cart"></span> Products</a>
		   </li>
		   <li class="nav-item">
			 <a class="nav-link" href="#"><span class="glyphicon glyphicon-envelope"></span> Contact us</a>
		   </li>
		   <li class="nav-item">
			 <a class="nav-link" href="#"><span class="glyphicon glyphicon-info-sign"></span> About us</a>
		   </li>
		   <sec:authorize access="isAnonymous()">
		   <li class="nav-item">
		     <a class="nav-link" href="<c:url value="/userRegister"/>" ><span class="glyphicon glyphicon-pencil"></span> Register</a>
		   </li>
		   </sec:authorize>
		   <sec:authorize url="/admin">
		   	<li class="nav-item">
		      <a class="nav-link" data-toggle="modal" href="<c:url value="/admin/userRegister"/>"><span class="glyphicon glyphicon-pencil"></span> Register</a>
		   </li>
		   </sec:authorize>
		   <sec:authorize access="isAnonymous()">
		   <li class="nav-item">
			 <a class="nav-link" href="<c:url value="/login"/>"><span class="glyphicon glyphicon-log-in"></span> Sign In</a>
		   </li>
		   </sec:authorize>
		   <sec:authorize access="isAuthenticated()">
		   <li class="nav-item">
		   <c:url var="logout" value="/logout"/>
				<form action="${logout}" name="logout" method="POST">        
                  	<div class="col-sm-4 form-group">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                       	<input class="btn btn-success" type="submit" name="logout" id="logout" value="Logout" />
                  	</div>
             	</form>
		   </li>
		   </sec:authorize>
		  </ul>
       </div>
    </div>