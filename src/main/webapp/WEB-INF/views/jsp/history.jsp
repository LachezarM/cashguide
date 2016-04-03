
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="description" content="">
<meta name="author" content="">

<title>History</title>

<!-- Bootstrap Core CSS -->
<link type="text/css" href="css/bootstrap.min.css" rel="stylesheet">

<style>
body {
	padding-top: 20px;
	padding-bottom: 20px;
}
<style>
table {
    border-collapse: collapse;
    width: 100%;
}

th, td {
    text-align: left;
    padding: 8px;
}

tr:nth-child(even){background-color: #f2f2f2}

th {
    background-color: #337ab7;
    color: white;
}
</style>
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
					<li class="active"><a href="history">Show history</a></li>
					<li><a href="payment">Show payments</a></li>
					<li><a href="">Shopping list</a></li>
					<li><a href="simulator">Simulator</a></li>
				</ul>
			</div>

			<div class="col-md-9">
				<div class="panel panel-default">
					<div class="panel-heading">History</div>
					<div class="panel-body">
						<table style="width: 100%">
						<thead>
								<th>Type</th> 
								<th>Category</th> 
 								<th>Description</th> 
 								<th>Amount</th> 
								<th>Date</th> 
						</thead>
						<c:forEach var="listValue" items="${payments}">
							<tr >
								<td>${listValue.type}</td> 
								<td>${listValue.category}</td>
								<td>${listValue.description}</td> 
								<td>${listValue.amount}</td> 
								<td>${listValue.date}</td>	 
							</tr>
							</c:forEach>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- /container -->
</body>
</html>