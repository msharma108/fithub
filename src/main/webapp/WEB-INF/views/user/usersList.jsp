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
	<link rel="stylesheet" href="../css/style.css"  >
	<!-- Bootstrap -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-formhelpers-countries.flags.css">

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

<div class="container">
    <div class="row">
        <div class="col-md-12">

            <div class="panel panel-primary panel-table">
              <div class="panel-heading">
                <div class="row">
                  <div class="col col-xs-6">
                    <h3 class="panel-title">User List</h3>
                  </div>
                </div>
              </div>
              <div class="panel-body">
                <table class="table table-striped table-bordered table-list">
                  <thead>
                    <tr>
                        <th><em class="fa fa-cog"></em></th>
                        <th>UserName</th>
                        <th>Name</th>
                        <th>Email</th>
                        <th>Role</th>
                    </tr> 
                  </thead>
                    <tbody>
                        <c:forEach items="${allUsers}" var="user">
                          <c:if test="${user.isUserDeleted == false}" >
                          <tr>
                            <td align="center">
                            
                            <!-- Url creation for Admin operations on usersList page  -->
                            
                            <c:url var="urlReconstructBasedOnOperation" value="/admin/urlConstructionBasedOnOperation"/>
                             <form action="${urlReconstructBasedOnOperation}" method="POST" >
                             <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                             <input type="hidden" name="userName" value="${user.userName}"/>
                             <input type="hidden" name="userRole" value="${user.role}"/>
                              <button class="btn btn-primary" title="View User Details" name="userView"><i class="glyphicon glyphicon-eye-open"></i></button>
                              <button class="btn btn-Success" title="Edit User" name="userEdit"><i class="glyphicon glyphicon-pencil"></i></button>
                              <button class="btn btn-default" title="Change User Role" name="userRoleChange"><i class="glyphicon glyphicon-retweet"></i></button>
                              <button class="btn btn-danger" title="Delete User" name="userDelete"><i class="glyphicon glyphicon-trash"></i></button>
                             </form>
                            </td>
                            <td><c:out value="${user.userName}"/></td>
                            <td><c:out value="${user.givenName}"/> <c:out value="${user.familyName}"/></td>
                            <td><c:out value="${user.email}"/></td>
                            <td><c:out value="${user.role}"/></td>
                          </tr>
                         </c:if>
                        </c:forEach>
                    </tbody>
                </table>
              </div>
              <div class="panel-footer">
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

  </body>
</html>