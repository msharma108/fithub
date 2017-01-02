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
<title>Insert title here</title>
	<!-- custom -->
	<link rel="stylesheet" href="../../css/test.css"  >
</head>
<body>

<c:if test="${empty performRetrieval}">

 <form class="form-container" action="<c:url value="/passwordRetrieval"/>" method="post">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
<!-- Initiate Security check process-->
<div class="form-title"><h2>Password Retrieval</h2></div>
<input class="form-field" type="text" name="userName" placeholder="Enter username" /><br>
<div class="submit-container">
<input class="submit-button" type="submit" value="Submit" name="getSecurityChecks" />
</div>
</form>
</c:if>

<c:if test="${not empty performRetrieval}">
 <form:form modelAttribute="passwordRetrievalDTO" class="form-container" action="/passwordRetrieval" method="post">

<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
<div class="form-title"><h2>Password Retrieval</h2></div>
<div class="form-title"><form:label path="securityQuestion">Security Question<span class="glyphicon glyphicon-user"> </span> </form:label></div><br>
<div class="form-title">${passwordRetrievalDTO.securityQuestion}?</div><br>
<form:input  type="hidden" path="userName" value="${passwordRetrievalDTO.userName}" /><br>
<form:input class="form-field" type= "text" path="securityAnswer" id="securityAnswerId" autocomplete="off" placeHolder= "Enter securityAnswer" />
<c:if test="${not empty showErrors}"><form:errors  path="securityAnswer" style="color: red;"/></c:if>

<form:input class="form-field" type= "text" path="zip" id="securityQuestionAnswerId" autocomplete="off"  placeHolder= "Enter zip" />
<c:if test="${not empty showErrors}"><form:errors  path="zip" style="color: red;"/></c:if>

<div class="submit-container">
<input class="submit-button" type="submit" value="Submit" name="performRetrieval" />
</div>
</form:form>
</c:if>



</body>
</html>