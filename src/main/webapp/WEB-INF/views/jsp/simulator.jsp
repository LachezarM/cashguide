<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>Simulator</title>

<!-- Bootstrap Core CSS -->
<link type="text/css" href="css/bootstrap.min.css" rel="stylesheet">

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

/*adding hrefs to <a> in the menu, because the name of the project may differ, i.e my project name is Cashguide1*/
/*this should be in external js file*/
	$(document).ready(function() {
		/* console.log(location.href);
		console.log(location.href.split("/")); */
	/* 	var hostname = "/" + location.href.split("/")[3];
		var links = document.getElementById("navigation");
		var lists = links.getElementsByTagName("li");
		lists[1].childNodes[0].setAttribute("href", hostname + "/add");
		lists[2].childNodes[0].setAttribute("href", hostname + "/history");
		lists[3].childNodes[0].setAttribute("href", hostname + "/payment");
		lists[4].childNodes[0].setAttribute("href", hostname + "/shopping");
		lists[5].childNodes[0].setAttribute("href", hostname + "/simulator"); */
	});
</script>
<!--Only chrome supports type=date, so for firefox i added this datepicker from jquery-->
<!-- DOES NOT WORK-->
<script>
	/* if ( $('[type="date"]').prop('type') != 'date' ) {
		$('[type="date"]').datepicker();
	} */
</script>

</head>
<body>
	<div class="container">

		<!-- Fixed navbar -->
		<c:import url="header.jsp"></c:import>

		<div class="row" name="content" style="margin-top: 50px;">
			<div class="col-md-3">
				<ul id="navigation" class="nav nav-pills nav-stacked">
					<li><a href="home">Home</a></li>
					<li><a href="add">Add</a></li>
					<li><a href="history">Show history</a></li>
					<li><a href="payment">Show payments</a></li>
					<li><a href="">Shopping list</a></li>
					<li class="active"><a href="simulator">Simulator</a></li>
				</ul>
			</div>

			<div class="col-md-9">
				<div class="panel panel-default">
					<div class="panel-heading">History</div>
					<div class="panel-body">
						<div class="panel-group" id="accordion">
							<div class="panel panel-default">
								<div class="panel-heading">
									<h4 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion"
											href="#collapse1"> Collapsible Group 1</a>
									</h4>
								</div>
								<div id="collapse1" class="panel-collapse collapse in">
									<div class="panel-body">Lorem ipsum dolor sit amet,
										consectetur adipisicing elit, sed do eiusmod tempor incididunt
										ut labore et dolore magna aliqua. Ut enim ad minim veniam,
										quis nostrud exercitation ullamco laboris nisi ut aliquip ex
										ea commodo consequat.</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h4 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion"
											href="#collapse2"> Collapsible Group 2</a>
									</h4>
								</div>
								<div id="collapse2" class="panel-collapse collapse">
									<div class="panel-body">Lorem ipsum dolor sit amet,
										consectetur adipisicing elit, sed do eiusmod tempor incididunt
										ut labore et dolore magna aliqua. Ut enim ad minim veniam,
										quis nostrud exercitation ullamco laboris nisi ut aliquip ex
										ea commodo consequat.</div>
								</div>
							</div>
							<div class="panel panel-default">
								<div class="panel-heading">
									<h4 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion"
											href="#collapse3"> Collapsible Group 3</a>
									</h4>
								</div>
								<div id="collapse3" class="panel-collapse collapse">
									<div class="panel-body">Lorem ipsum dolor sit amet,
										consectetur adipisicing elit, sed do eiusmod tempor incididunt
										ut labore et dolore magna aliqua. Ut enim ad minim veniam,
										quis nostrud exercitation ullamco laboris nisi ut aliquip ex
										ea commodo consequat.</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- /container -->
</body>
</html>