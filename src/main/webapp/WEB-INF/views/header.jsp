	<!-- start of header -->
	<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <div class="container-fluid" id="navbarcontainer">
       <div class="navbar navbar-fixed-top" id = "topfixedbar" role="navigation">
         <div class="navbar-inner">
		  <ul class="nav navbar-nav navbar-right">
		  
		   <sec:authorize access="isAnonymous()">
		   <li class="nav-item">
		     <span style="color: #f0ffff; font-size: 15px">Welcome<span class="glyphicon glyphicon-user"></span>Guest!</span>
		   </li>
		   <li class="nav-item">
		     <a class="btn  btn-xs" href="<c:url value="/userRegister"/>" ><span class="glyphicon glyphicon-pencil"></span>Sign up</a>
		   </li> 
		   <li class="nav-item">
			 <a class="btn btn-xs" href="<c:url value="/login"/>"><span class="glyphicon glyphicon-log-in"></span> Sign In</a>
		   </li>
		   </sec:authorize>
		   <sec:authorize access="isAuthenticated()">
		   <!-- Displaying LoggedIn User's Name and link to profile -->
		   <sec:authentication var="loggedInUserName" property="principal.userName" />
		   <li class="nav-item">
		      <a href="<c:url value="/viewUser/${loggedInUserName}"/>">Welcome <span class="glyphicon glyphicon-user"></span> <b>${loggedInUserName}</b></a>
           </li>
		   <li class="nav-item">
		   <c:url var="logout" value="/logout"/>
				<form action="${logout}" name="logout" method="POST">        
                  	<div class="col-sm-4 form-group">
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                       	<button class="btn btn-xs btn-primary" type="submit" name="logout" id="logout" value="Logout">Logout <span class="glyphicon glyphicon-log-out"></span></button> 
                  	</div>
             	</form>
		   </li>
		   </sec:authorize>
		  </ul>
         </div>
       </div>
	   <div class="navbar"  role="navigation" id="bottombar">
		 <div class="navbar-inner">
           <a class="navbar-brand" href="#">FitHub.com</a>
         </div>
		  <ul class="nav navbar-nav">
		   <li class="nav-item">
			 <a class="nav-link" href="<c:url value="/home"/>" ><span class="glyphicon glyphicon-home"> </span> Home</a>
		   </li>
		   	<li class="dropdown">
             <a class="dropdown-toggle" data-toggle="dropdown" href="#"><span class="glyphicon glyphicon-user" id="productListDropdownId"></span> Product
             <span class="caret"></span></a>
             <ul class="dropdown-menu" >
             	<li class="nav-item">
		         <a class="nav-link" href="<c:url value="/viewProducts"/>"><span class="glyphicon glyphicon-shopping-cart"></span>All Products</a>
		       	</li>
		       	<li class="nav-item">
		         <a class="nav-link" href="<c:url value="/viewProducts/topProducts/top5"/>"><span class="glyphicon glyphicon-shopping-cart"></span>Top 5 Products</a>
		       	</li>
             </ul>
             </li>
		   <li class="nav-item">
			 <a class="nav-link" href="#"><span class="glyphicon glyphicon-envelope"></span> Contact us</a>
		   </li>
		   <sec:authorize access="isAuthenticated()">
		   	<li class="nav-item">
			 <a class="nav-link" href="<c:url value="/viewUserAllOrders/${loggedInUserName}"/>"><span class="glyphicon glyphicon-envelope"></span> My Orders</a>
		   </li>
		   </sec:authorize>
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
		      	<li class="nav-item">
		         <a class="nav-link" data-toggle="modal" href="<c:url value="/admin/viewAllOrders"/>"><span class="glyphicon glyphicon-eye-open"></span> View all Orders</a>
		      </li>
             </ul>
           </li>
		   </sec:authorize>

			<!-- Display all categories -->
			
			<li class="dropdown">
             <a class="dropdown-toggle" data-toggle="dropdown" href="#"><span class="glyphicon glyphicon-user"></span> Product Categories
             <span class="caret"></span></a>
             <ul class="dropdown-menu">
             <c:forEach items="${categoryList}" var="categoryList">
		   	   <li class="nav-item">
		         <a class="nav-link" data-toggle="modal" href="<c:url value="/viewProducts/${categoryList.category}"/>"><span class="glyphicon glyphicon-pencil"></span> ${categoryList.category}</a>
		       </li>
		       </c:forEach>
             </ul>
           </li>

		  </ul>
		  <!-- Admin view user link creation -->
		  <c:url var="adminViewUser" value="/admin/urlConstructionBasedOnOperation"/>
		  <sec:authorize access="hasAuthority('ADMIN')">
        <div class="col-sm-3  pull-right">
            <form class="navbar-form" role="search" action="${adminViewUser}"method="POST" >
                <div class="input-group">
                    <input id="userNameId" type="text" name="userName" class="form-control form-field" placeholder="Enter UserName">
                  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                    <div class="input-group-btn">
                        <button class="btn btn-success submit-button" type="submit" name="userView">Search <i class="glyphicon glyphicon-search"></i></button>
                    </div>
                </div>
            </form>
        </div>
        </sec:authorize>
        <br>
        <div class="col-sm-3  pull-right">
                <c:url var="searchProduct" value="/searchProduct"/>
            <form class="navbar-form" role="search" action="${searchProduct}"method="GET" >
                <div class="input-group">
                    <input id="productSearchStringId" type="text" name="productSearchString" class="form-control form-field" placeholder="ProductName or Description"><br>
                    <div class="input-group-btn">
                        <button class="btn btn-success submit-button" type="submit" name="userView">Search <i class="glyphicon glyphicon-search"></i></button>
                    </div>
                </div>
            </form>
        </div>
        
        
        
       </div>
    </div>