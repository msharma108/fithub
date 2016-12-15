	<!-- start of header -->
	<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <div class="container-fluid">
       <div class="navbar navbar-fixed-top" role="navigation">
         <div class="navbar-inner">
		  <ul class="nav navbar-nav navbar-right">
		  
		   <sec:authorize access="isAnonymous()">
		   <li class="nav-item">
		     <a class="nav-link" href="<c:url value="/userRegister"/>" ><span class="glyphicon glyphicon-pencil"></span> Sign up</a>
		   </li>
		   <li class="nav-item">
			 <a class="nav-link" href="<c:url value="/login"/>"><span class="glyphicon glyphicon-log-in"></span> Sign In</a>
		   </li>
		   </sec:authorize>
		   <sec:authorize access="isAuthenticated()">
		   <li class="nav-item">
		      <a href="#ProfileLink">Welcome <span class="glyphicon glyphicon-user"></span> <b>User</b></a>
           </li>
		   <li class="nav-item">
		   <c:url var="logout" value="/logout"/>
				<form action="${logout}" name="logout" method="POST">        
                  	<div class="col-sm-4 form-group">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                       	<button class="btn btn-default" type="submit" name="logout" id="logout" value="Logout">Logout <span class="glyphicon glyphicon-log-out"></span></button> 
                  	</div>
             	</form>
		   </li>
		   </sec:authorize>
		  </ul>
         </div>
       </div>
	   <div class="navbar navbar-default"  role="navigation" width:100%>
		 <div class="navbar-inner">
           <a class="navbar-brand" href="#">FitHub.com</a>
         </div>
		  <ul class="nav navbar-nav">
		   <li class="nav-item">
			 <a class="nav-link" href="<c:url value="/home"/>" ><span class="glyphicon glyphicon-home"> </span> Home</a>
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
		   
		   <sec:authorize access="hasAuthority('ADMIN')">
		   <li class="dropdown">
             <a class="dropdown-toggle" data-toggle="dropdown" href="#"><span class="glyphicon glyphicon-user"></span> Admin Menu
             <span class="caret"></span></a>
             <ul class="dropdown-menu">
		   	   <li class="nav-item">
		         <a class="nav-link" data-toggle="modal" href="<c:url value="/admin/userRegister"/>"><span class="glyphicon glyphicon-pencil"></span> Register a new user</a>
		       </li>
		        <li class="nav-item">
		         <a class="nav-link" data-toggle="modal" href="<c:url value="/admin/productRegister"/>"><span class="glyphicon glyphicon-pencil"></span> Add Product</a>
		       </li>
		   	   <li class="nav-item">
		         <a class="nav-link" data-toggle="modal" href="<c:url value="/admin/viewUsers"/>"><span class="glyphicon glyphicon-eye-open"></span> View all Users</a>
		      </li>
             </ul>
           </li>
		   </sec:authorize>

		  </ul>
		  <!-- Admin view all users link creation -->
		  <c:url var="adminViewUser" value="/admin/urlConstructionBasedOnOperation"/>
		  <sec:authorize access="hasAuthority('ADMIN')">
        <div class="col-sm-3  pull-right">
            <form class="navbar-form" role="search" action="${adminViewUser}"method="POST" >
                <div class="input-group">
                    <input id="userNameId" type="text" name="userName" class="form-control" placeholder="Enter UserName">
                  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    <div class="input-group-btn">
                        <button class="btn btn-primary" type="submit" name="viewUser">Search <i class="glyphicon glyphicon-search"></i></button>
                    </div>
                </div>
            </form>
        </div>
        </sec:authorize>
       </div>
    </div>