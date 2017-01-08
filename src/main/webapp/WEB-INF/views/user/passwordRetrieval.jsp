<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
          <%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
      <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	  <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
      <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Reset Password</title>
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
</head>
<body>
<jsp:include page="../header.jsp" />
 <!-- contents start here -->
 <div class="panel"  id ="passwordReset-panel">
  <div class="panel-body" id="passwordReset-body">
   
	<c:if test="${empty performRetrieval}">

	<form action="<c:url value="/passwordRetrieval"/>" method="post">

	  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
	  <!-- Initiate Security check process-->
	<div class="form-title"><h4>Password Retrieval</h4></div><br>
     <div class="col-sm-12">    
      <div class=" form-group row">
       <div class="col-sm-8">
	     <input class="form-control form-field" type="text" name="userName" placeholder="Enter username" /><br>
		   <c:if test="${exception !=null }">
		   <c:out  value="${exception }" />
		   </c:if>
	   </div>
	   <div class="col-sm-2 form-group">
	      <input class="submit-button" type="submit" value="Submit" name="getSecurityChecks" />
	    </div>
	  </div>
	 </div>
	</form>
	</c:if>

	<c:if test="${not empty performRetrieval}">
	 <form:form modelAttribute="passwordRetrievalDTO" action="/passwordRetrieval" method="post">
	    
		  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
		  <div class="form-title"><h4>Password Retrieval</h4></div>
		  <div><form:label path="securityQuestion">Security Question <span class="glyphicon glyphicon-user"></span>: </form:label></div> 
		  <div>${passwordRetrievalDTO.securityQuestion}?</div><br>
		  
		  <div class="col-sm-12">    
	           <div class=" form-group row">
	            <div class="col-sm-8">
			      <form:input  type="hidden" path="userName" value="${passwordRetrievalDTO.userName}" /><br>
			      <form:input class="form-control form-field" type= "text" path="securityAnswer" id="securityAnswerId" autocomplete="off" placeHolder= "Enter securityAnswer" />
			      <c:if test="${not empty showErrors}"><form:errors  path="securityAnswer" style="color: red;"/></c:if>
			    </div>
			   </div>
	           <div class=" form-group row">
	            <div class="col-sm-8">
			      <form:input class="form-control form-field" type= "text" path="zip" id="securityQuestionAnswerId" autocomplete="off"  placeHolder= "Enter zip/postal code" />
			      <c:if test="${not empty showErrors}"><form:errors  path="zip" style="color: red;"/></c:if>
			    </div>
			   </div>
	           <div class=" form-group row">
	            <div class="col-sm-8">
			      <input class="submit-button" type="submit" value="Submit" name="performRetrieval" />
			    </div>
			   </div>
	      </div>
		    
	 </form:form>
	</c:if>
  </div>
 </div>
	<!-- contents end here -->
	 
<jsp:include page="../footer.jsp" />

</body>
</html>