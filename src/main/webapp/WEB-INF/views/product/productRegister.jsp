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
	  $("#expiryDateId, #manufactureDateId").each(function() {
 	     $(this).datepicker({
 	        changeMonth: true,
 	        changeYear: true,
 	        dateFormat: 'yy/mm/dd',
 	        minDate: "-120Y", 
 	        yearRange: "1900:2016" 
 	     });
 	  });
});

function openProductModal(){
    $('#productModal').modal();
}; 
</script>
  </head>
  <body>
<jsp:include page="../header.jsp" />
	<!-- contents start here -->
<div class="container-fluid">
 
  <!-- Modal -->
  <div class="modal fade" id="productModal" role="dialog">
    <div class="modal-dialog">
    
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          <h4 class="modal-title"><span class="glyphicon glyphicon-info-sign"> </span> Product Message</h4>
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
     
  <div class="panel panel-primary" style="border: 2px solid maroon">
    <c:choose>
     <c:when test="${productDTO.isEditable == false }">
      <!-- Show Add product Form -->
    	<div class="panel-heading" style="color: white; background-color: maroon;"><b>PRODUCT INFO</b></div>
    </c:when>
     <c:when test="${productDTO.isEditable == true }">
      <!-- Show Update product Form -->
    <div class="panel-heading" style="color: white; background-color: maroon;"><b>Product Update Form</b></div>
    </c:when>  
    </c:choose>
     <div class="panel-body" style="background-color: #C1E1A6;"> 
         
         <c:url var="productSave" value="/admin/productSave"/>
	        <form:form modelAttribute="productDTO" method="POST" action="${productSave}" style="color: green;" enctype="multipart/form-data">
              <div class="col-xs-12">    
                <div class=" form-group row">
                  <div class="col-sm-4">
                       <form:label path="productName">Product Name:</form:label><br>
                       <form:input class="form-control" type="text" path="productName" id="productNameId" placeHolder= "Enter product name" />
                       <form:errors  path="productName" style="color: red;"/>
                  </div>
                  <div class="col-sm-4">
                       <form:label path="ldesc">Description: </form:label><br>
                       <form:input class="form-control" type="text" path="ldesc" id="ldescId" placeHolder= "Enter Product Description" />
                       <form:errors  path="ldesc" style="color: red;"/>
                  </div>
                </div>
              </div>
              
         <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
              
              <div class="col-sm-12">    
                <div class="form-group row">
                  <div class="col-sm-4 form-group">
                       <form:label path="productCategory">Product Category:</form:label><br>
			           <form:select class="form-control" path="productCategory" id="productCategoryId">
			           <form:option value="Others">--- Select ---</form:option>
			           <form:option value="Protein">Protein</form:option>
			           <form:option value="Vitamins">Vitamins</form:option>
			           <form:errors  path="productCategory" style="color: red;"/>
			           </form:select>
                  </div>
                  <div class="col-sm-4 form-group">
			           <form:label path="manufactureDate">Manufacture Date:<span class="glyphicon glyphicon-calendar"> </span></form:label><br>
			           <form:input class="form-control" type="text" path="manufactureDate" id="manufactureDateId" placeHolder= "Enter Manufacture Date"  />
			           <form:errors  path="manufactureDate" style="color: red;"/>
                  </div>
                </div>
              </div>
              
              <div class="col-sm-12">    
                <div class="form-group row">
                  <div class="col-sm-4 form-group">
			           <form:label path="price">Price:</form:label><br>
			           <form:input class="form-control" type="text" path="price" id="priceId" placeHolder= "Enter Price"  />
			           <form:errors  path="price" style="color: red;"/>
                  </div>
                  
                  <div class="col-sm-12">    
                <div class="form-group row">
                  <div class="col-sm-4 form-group">thumbImage<br>
			           <input class="form-control" type="file" name="thumbImage" id="thumbImage" placeHolder= "Thumbnail"  />
                  </div>
                  
                  <div class="col-sm-4 form-group">
			           <form:label path="weight">Weight:</form:label><br>
			           <form:input class="form-control" type="text" path="weight" id="weightId" placeHolder= "Enter Weight"  />
			           <form:errors  path="weight" style="color: red;"/>
                  </div>
                </div>
              </div>
              
              <div class="col-sm-12">    
                <div class="form-group row">
                  <div class="col-sm-4 form-group">
			           <form:label path="stockQuantity">Quantity:</form:label><br>
			           <form:input class="form-control" type="text" path="stockQuantity" id="stockQuantityId" placeHolder= "Enter Quantity"  />
			           <form:errors  path="stockQuantity" style="color: red;"/>
                  </div>
                  <div class="col-sm-4 form-group">
			           <form:label path="expiryDate">Expiry Date:<span class="glyphicon glyphicon-calendar"> </span></form:label><br>
			           <form:input class="form-control" type="text" path="expiryDate" id="expiryDateId" placeHolder= "Enter Expiry Date"  />
			           <form:errors  path="expiryDate" style="color: red;"/>
                  </div>
                  
                </div>
              </div>
              
              <div class="col-sm-12">    
                <div class="form-group row">
                  <div class="col-sm-4 form-group">
			           <form:label path="flavor">Flavor:</form:label><br>
			           <form:input class="form-control" type="text" path="flavor" id="flavorId" placeHolder= "Enter Flavor"  />
			           <form:errors  path="flavor" style="color: red;"/>
                  </div>
                  <div class="col-sm-4 form-group">
                       <form:label path="rating">Rating:<span class="glyphicon glyphicon-user"> </span></form:label><br>
			           <form:select class="form-control" path="rating" id="ratingId">
			          <form:option value="">--- Select ---</form:option>
			           <form:option value="Bad">Bad</form:option>
			           <form:option value="Average">Average</form:option>
			           <form:option value="Good">Good</form:option>
			           <form:option value="Awesome">Awesome</form:option>
			           <form:errors  path="rating" style="color: red;"/>
			           </form:select>
                  </div>
                </div>
              </div>
              
              <div class="col-sm-12">    
                <div class="form-group row">
                  <div class="col-sm-4 form-group">
			           <form:label path="sdesc">Short description:</form:label><br>
			           <form:input class="form-control" type="text" path="sdesc" id="sdescId" placeHolder= "Enter short description"  />
			           <form:errors  path="sdesc" style="color: red;"/>
                  </div>

                </div>
                </div>
              
              <c:choose>
              <c:when test="${productDTO.isEditable == false }">
             <!--  New Product -->
              <div class="col-sm-12"> 
                <div class="form-group row">
                  <div class="col-sm-8 form-group">
                       <!-- input class="btn btn-primary btn-block" type="submit" name="productRegister" id="productRegisterId" value="Submit" /-->
                       <button type="submit" class="btn btn-primary btn-block" name="productRegister" id="productRegister"  ><i class="glyphicon glyphicon-plus"></i> Add Product</button>
                  </div>
                </div> 
              </div>
              <!--  Edit existing product -->
              </c:when>
              <c:when test="${productDTO.isEditable == true }">
             <!--  Profile Edit button -->  
               <div class="col-sm-12"> 
                <div class="form-group row">
                  <div class="col-sm-8 form-group">
                       <!-- input class="btn btn-primary btn-block" type="submit" name="productUpdate" id="productUpdateId" value="Submit" /-->
                       <button type="submit" class="btn btn-primary btn-block" name="productUpdate" id="productUpdateId"  ><i class="glyphicon glyphicon-pencil"></i> Update Product</button>
                  </div>
                </div> 
              </div>           
             <!--  Profile Edit button -->
             </c:when>
             </c:choose> 
			<br><br>              
            </form:form>
          </div>
          <div class="panel-footer" style="color: white; background-color: maroon;"></div>
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