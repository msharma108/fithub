	<!-- start of header -->
    <div class="container-fluid">
	   <div class="navbar navbar-default navbar-fixed-top navbar-fnt navbar-backgrnd" role="navigation">
		 <div class="navbar-header">
           <a class="navbar-brand" href="#">FitHub.com</a>
         </div>
		  <ul class="nav navbar-nav">
		   <li class="nav-item">
			 <a class="nav-link" href="home.jsp">Home</a>
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
		     <a class="nav-link" href="<c:url value="/userRegister"/>" >Register</a>
		   </li>
		   </sec:authorize>
		   <sec:authorize url="/admin">
		   	<li class="nav-item">
		      <a class="nav-link" data-toggle="modal" href="<c:url value="/admin/userRegister"/>">Register</a>
		   </li>
		   </sec:authorize>
		   <sec:authorize access="isAnonymous()">
		   <li class="nav-item">
			 <a class="nav-link" href="<c:url value="/userLogin"/>">Sign In</a>
		   </li>
		   </sec:authorize>
		  </ul>
       </div>
    </div>