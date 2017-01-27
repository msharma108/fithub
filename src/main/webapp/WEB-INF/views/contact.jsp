<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
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
	<link rel="stylesheet" href="/css/style.css"  >
	<!-- Bootstrap -->
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-formhelpers-countries.flags.css">


    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

<!-- Google maps -->

<style>
      #map {
        height: 500px;
        width: 500px;
		align: right;
       }
    </style>
  </head>

 <script>
      function loadMap() {
		var fitHubLocation = {lat: 45.3569769, lng: -75.800047};
        var fitHubMap = new google.maps.Map(document.getElementById('map'), {
          zoom: 13,
          center: fitHubLocation
        });
        var marker = new google.maps.Marker({
          position: fitHubLocation,
          map: fitHubMap,
          animation: google.maps.Animation.BOUNCE
        });
        
        var infowindow = new google.maps.InfoWindow({
            content: "BeFitHub"
          });
        
        infowindow.open(fitHubMap,marker);
        
      }
    </script>
  <body>
<jsp:include page="header.jsp" />

    
  <body>
    <h3>Contact us:</h3>
    <div id="map"></div>
    <script async defer
    src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBE1ruz3NIxLcigdPBtoGhk1BvIfsqdjsQ&callback=loadMap">
    </script>
    
<jsp:include page="footer.jsp" />
	
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="/js/bootstrap.min.js"></script>

  </body>
</html>