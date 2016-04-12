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

<link rel="stylesheet"
	href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">


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
a#dateLink {
 color: white;
}
label {
	padding: 3px;
	color: #222;
	font-family: corbel, sans-serif;
	font-size: 14px;
	margin: 5px;
}
.select_style {
     background: #FFF;
    display: inline-block;
    color: #525252;
    font-weight: 300;
    position: relative;
    padding-right:10px;
    left:140px;
    top: 120px
}
.select_style select {
    width:70%;
    background:none;
    background:transparent;
}
#sortOptions{
   	position: relative;
 }
</style>

<!-- jQueryV2.2.2 -->
<script src="js/jquery-2.2.2.min.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<script src="js/bootstrap.min.js"></script>

<script>
$(function() {
	 $("#datepicker1").datepicker({
		dateFormat : "dd-mm-yy",
		onClose: function(selectedDate) {
			$("#datepicker2").datepicker("option","minDate",selectedDate);
		}
	});
	 
	$("#datepicker2").datepicker({
		minDate: document.getElementById('datepicker1').value,
		dateFormat : "dd-mm-yy"
	});
});
</script>
<!-- BootstrapV3.3.6 Core JavaScript -->
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
					<li><a href="info">Charts</a></li>
					<li><a href="simulator">Simulator</a></li>
				</ul>
			</div>
			<div class="col-md-9">
				<div class="panel panel-default">
					<div class="panel-heading">History</div>
					<div class="panel-body">
					<div id="sortOptions">
						<h3>Choose period :</h3> 
							<form action="showByDate" method="GET">
								<div class="col-md-6">
									From:<input type="text" name="date1" id="datepicker1" class="form-control" placeholder="Date From">
								</div>
								<div class="col-md-6">
										To:<input type="text" name="date2" id="datepicker2" class="form-control" placeholder="Date To">
								</div>
								<input type="submit" value="show" style="margin-top: 15px; margin-left:15px; width:80px;"/>
							
							</form>
							
						<div class="col-md-6">
						<h3>Choose type:</h3>
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
						</div>
							
						<div class="col-md-6" style="height: 200px;">
							<h3>Choose category:</h3>
							<form action="showOnlyCategories" method="GET">
								<!-- <div class='select_style'> -->
									<label for="categories"> Categories:</label>
									<select id="categories" name="categories" onChange='this.form.submit();'>
											<option selected="selected" value="All" >All</option>
									<c:forEach  var="categories" items="${currCategories }">
											<option  value="${categories}" >${categories}</option>							
									</c:forEach>
									</select>
								<!-- </div> -->
							</form>	
						</div>
						
					</div>
					
					
					<div class="col-md-12"  >
						<p >Total : ${totalAmount}</p>
					</div>
				
				
					<c:set var="payments" scope="session" value="${currPayments}" />
					<c:set var="totalCount" scope="session" value="${fn:length(payments)}" />
						<c:set var="perPage" scope="session" value="5" />
						<c:set var="pageStart" value="${param.start}" />
						<c:if test="${empty pageStart or pageStart < 0}">
							<c:set var="pageStart" value="0" />
						</c:if>
						<c:if test="${totalCount < pageStart}">
							<c:set var="pageStart" value="${pageStart - perPage}" />
						</c:if>
						
						<div class="col-md-12"  >
							<table style="margin-top:10px; width: 100%">
								<thead>
									<th>Type</th>
									<th>Category</th>
									<th>Description</th>
									<th>Amount</th>
									<th><a id="dateLink" href ="sortByDate">Date</a></th>
								</thead>
								<c:forEach var="payment" items="${payments}"
									varStatus="paymentsCounter" begin="${pageStart}"
									end="${pageStart + perPage - 1 }">
									<tr>
										<td>${payment.type}</td>
										<td>${payment.category}</td>
										<td>${payment.description}</td>
										<td>${payment.amount}</td>
										<td>${payment.date}</td>
									</tr>
								</c:forEach>
							</table>
						</div>
				
								
						<div class="col-md-12 text-center" style="margin-top: 30px; font-size:20px;">
							<a href="?categories=${param.categories }&Show=${param.Show }&start=${pageStart - perPage}"><<</a>${pageStart + 1}	- ${pageStart + perPage} 
							<a href="?categories=${param.categories }&Show=${param.Show }&start=${pageStart + perPage}">>></a>
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