<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta charset="utf-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge">
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<meta name="description" content="">
		<meta name="author" content="">
		
		<title>Home page</title>
		
		<!-- Bootstrap Core CSS -->
		<link type="text/css"  href="css/bootstrap.min.css" rel="stylesheet">
		
		<style>
		body {
		  padding-top: 20px;
		  padding-bottom: 20px;
		}
		</style>
		
		<!-- jQueryV2.2.2 -->
		<script src="js/jquery-2.2.2.min.js"></script>
		
		<!-- BootstrapV3.3.6 Core JavaScript -->
		<script src="js/bootstrap.min.js"></script>
		<script>
		
		$(document).ready(function(){
			/* console.log(location.href);
			console.log(location.href.split("/")); */
			/* var hostname = "/" + location.href.split("/")[3];
			var links = document.getElementById("navigation");
			var lists = links.getElementsByTagName("li");	
			lists[1].childNodes[0].setAttribute("href", hostname+"/add" );
			lists[2].childNodes[0].setAttribute("href", hostname+"/history" );
			lists[3].childNodes[0].setAttribute("href", hostname+"/payment" );
			lists[4].childNodes[0].setAttribute("href", hostname+"/shopping" );
			lists[5].childNodes[0].setAttribute("href", hostname+"/simulator" );		 */
		});
	
	
		</script>
	</head>
	<body>
	    <div class="container">
		
		 <!-- Fixed navbar -->
  		<c:import url="header.jsp"></c:import>
  	
  
	    <div class="row" name="content" style="margin-top:50px;">
			<div class="col-md-3">
			  <ul id = "navigation" class="nav nav-pills nav-stacked">
				<li class="active"><a href="home">Home</a></li>
				<li><a  href="add">Add</a></li>
				<li><a  href="history">Show history</a></li>
				<li><a  href="payment">Show payments</a></li>
				<li><a  href="">Shopping list</a></li>
				<li><a  href="simulator">Simulator</a></li>
			  </ul>
			</div>
			
			<div class="col-md-9">
						<div class="panel panel-default">
							<div class="panel-heading">Home</div>
							<div class="panel-body">Home info
								<a href="history">HISTORY - tva samo go probvah s link kam index jsp i kontroler /history</a>
								<p>${logedUser.username}</p>
							</div>
						</div>
			</div>
		</div>
    </div> <!-- /container -->
	</body>
</html>