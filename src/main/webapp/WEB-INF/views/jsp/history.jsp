<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
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

style>table {
	border-collapse: collapse;
	width: 100%;
}

th, td {
	text-align: left;
	padding: 8px;
}

tr:nth-child(even) {
	background-color: #f2f2f2
}

th {
	background-color: #337ab7;
	color: white;
}

label {
	padding: 5px;
	color: #222;
	font-family: corbel, sans-serif;
	font-size: 14px;
	margin: 5px;
}
.select_style {
     background: #FFF;
    overflow: hidden;
    display: inline-block;
    color: #525252;
    font-weight: 300;
    -webkit-border-radius: 5px 4px 4px 5px/5px 5px 4px 4px;
    -moz-border-radius: 5px 4px 4px 5px/5px 5px 4px 4px;
    border-radius: 5px 4px 4px 5px/5px 5px 4px 4px;
    -webkit-box-shadow: 0 0 5px rgba(123, 123, 123, 0.2);
    -moz-box-shadow: 0 0 5px rgba(123,123,123,.2);
    box-shadow: 0 0 5px rgba(123, 123, 123, 0.2);
    border: solid 1px #DADADA;
    font-family: "helvetica neue",arial;
    position: relative;
    cursor: pointer;
    padding-right:20px;
}
.select_style select {
    -webkit-appearance: none;
    appearance:none;
    width:70%;
    background:none;
    background:transparent;
    border:none;
    outline:none;
}
</style>

<!-- jQueryV2.2.2 -->
<script src="js/jquery-2.2.2.min.js"></script>

<!-- BootstrapV3.3.6 Core JavaScript -->
<script src="js/bootstrap.min.js"></script>
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
					<li><a href="info">Info</a></li>
					<li><a href="">Shopping list</a></li>
					<li><a href="simulator">Simulator</a></li>
				</ul>
			</div>

			<div class="col-md-9">
				<div class="panel panel-default">
					<div class="panel-heading">History</div>
					<div class="panel-body">
					<form action="showOnlyCategories" method="GET">
							<div class='select_style'>
								<label for="categories"> Categories:</label>
								<select id="categories" name="categories" onChange='this.form.submit();'>
										<option value="All">All</option>
								<c:forEach  var="categories" items="${currCategories }">
										<option  value="${categories}" >${categories}</option>							
								</c:forEach>
								</select>
							</div>
							</form>
						<form action="showOnlyTypes" method="GET" >
							<input checked="checked" type="radio" name="Show" id="rd1" value="All" ${param.Show == 'ALL' ? 'checked' : ''} onChange='this.form.submit();'/>
								 <label for="rd1">Show All</label><br /> 
								<input  type="radio" name="Show" id="rd2"
								value="EXPENSE" ${param.Show == 'EXPENSE' ? 'checked' : ''}
								onChange='this.form.submit();'/> 
								<label for="rd2">Show Expenses</label><br /> 
								<input type="radio" name="Show" id="rd3"
								value="INCOME" ${param.Show == 'INCOME' ? 'checked' : ''}
								onChange='this.form.submit();' /> 
								<label for="rd3">Show Incomes</label><br />
						</form>
						<c:set var="payments" scope="session" value="${currPayments}" />
						<c:set var="totalCount" scope="session"
							value="${fn:length(payments)}" />
						<c:set var="perPage" scope="session" value="5" />
						<c:set var="pageStart" value="${param.start}" />
						<c:if test="${empty pageStart or pageStart < 0}">
							<c:set var="pageStart" value="0" />
						</c:if>
						<c:if test="${totalCount < pageStart}">
							<c:set var="pageStart" value="${pageStart - perPage}" />
						</c:if>
						<a href="?Show=${param.Show }&start=${pageStart - perPage}"><<</a>${pageStart + 1}	- ${pageStart + perPage} 
						<a href="?Show=${param.Show }&start=${pageStart + perPage}">>></a>
						<table style="width: 100%">
							<thead>
								<th>Type</th>
								<th>Category</th>
								<th>Description</th>
								<th>Amount</th>
								<th>Date</th>
							</thead>
							<c:forEach var="payments" items="${payments}"
								varStatus="paymentsCounter" begin="${pageStart}"
								end="${pageStart + perPage - 1 }">
								<tr>
									<td>${payments.type}</td>
									<td>${payments.category}</td>
									<td>${payments.description}</td>
									<td>${payments.amount}</td>
									<td>${payments.date}</td>
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