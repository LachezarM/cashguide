<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
<!-- css for datepicker -->
<link rel="stylesheet"
	href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
<style>
body {
	padding-top: 20px;
	padding-bottom: 20px;
}

.ui-datepicker-calendar {
	display:none;
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



</style>

<!-- jQueryV2.2.2 -->
<script src="js/jquery-2.2.2.min.js"></script>

<!-- BootstrapV3.3.6 Core JavaScript -->
<script src="js/bootstrap.min.js"></script>

<!-- jquery datepicker -->
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>


<script>
	$(function() {
		$("#datepicker")
				.datepicker(
					{
						changeMonth : true,
						changeYear : true,
						dateFormat : "dd-mm-yy",
						showButtonPanel : true,
						onClose : function() {
							var iMonth = $("#ui-datepicker-div .ui-datepicker-month :selected").val();
							var iYear = $("#ui-datepicker-div .ui-datepicker-year :selected").val();
							console.log(iMonth);
							console.log(iYear);
							$(this).datepicker('setDate', new Date(iYear, iMonth, 1));
						}
					});
	});
	
	
	function selectAll(){
		var incomes = document.getElementsByName("income");
		var expenses = document.getElementsByName("expense");
		$('[name="income"]').each(function(){
			$(this).prop("checked", true);
		});
		
		$('[name="expense"]').each(function(){
			$(this).prop("checked", true);
		});
		
	}
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
					<li><a href="info">Info</a></li>
					<li><a href="">Shopping list</a></li>
					<li class="active"><a href="simulator">Simulator</a></li>
				</ul>
			</div>

			<div class="col-md-9">
				<div class="panel panel-default">
					<div class="panel-heading">Simulator</div>
					<div class="panel-body">
					<%-- <c:if test="${showBudget==null}"> --%>
						<form  method="POST" action="getBudget">
							Date: <input type="text" name="date" id="datepicker" class="form-control" placeholder="Date"> 
								<input type="submit" value="show" />						
						</form>
					 <%-- </c:if> --%>
					 
					 <c:if test="${error!=null}">
						<div class="alert alert-danger" role="alert"><c:out value="${error}"></c:out></div>
					</c:if>
						<c:if test="${showBudget!=null}">
							Balance:<c:out value="${showBudget.balance}"></c:out>
							Income:<c:out value="${showBudget.income}"></c:out>
							<c:set var="incomes" value='${showBudget.payments.get("INCOME")}'></c:set>
							<c:set var="expenses" value='${showBudget.payments.get("EXPENSE")}'></c:set>
							<c:set var="incomeSize" value="${incomes.size()}"></c:set>
							<c:set var="expensesSize" value="${expenses.size()}"></c:set>
							<form method="POST" action="calculate" id="calculation">
								<table>
										<tr>
											<th>Type</th>
											<th>Category</th>
											<th>Description</th>
											<th>Amount</th>
											<th>Select</th>
										</tr>
			
										<c:forEach var="income" items="${incomes}" varStatus="loop">
										<tr>
											<td>${income.type }</td>
											<td>${income.category}</td>
											<td>${income.description}</td>
											<td>${income.amount}</td>
											<td><input type="checkbox" name="income" value="${loop.count-1}"></td>
										</tr>
										</c:forEach>
								
										<c:forEach var="expense" items="${expenses}" varStatus="loop">
										<tr>
											<td>${expense.type }</td>
											<td>${expense.category}</td>
											<td>${expense.description}</td>
											<td>${expense.amount}</td>
											<td><input type="checkbox" name="expense" value="${loop.count-1}"></td>
										</tr>
										</c:forEach>
								</table>
								<div style="margin-left:20px; margin-bottom:20px">
								<input type="text" name="period"/>
								<input type="submit" value="calculate" />
								</div>
							</form>
							</c:if>
							<c:if test="${result!=nul}">
							<c:choose>
								<c:when test="${positiveSaving}">
									<div class="alert alert-success" role="alert" ><c:out value="${result}"></c:out></div>
								</c:when>
								<c:otherwise>
									<div class="alert alert-danger" role="alert"><c:out value="${result}"></c:out></div>
								</c:otherwise>
							</c:choose>
							</c:if>
					</div>
				</div>
			</div>
		</div>
	</div>
	
	<script>
	$('#calculation').submit(function () {

		//get period value
	    var period = $.trim($('[name="period"]').val());

	    // Check if empty
	    if (period  === ''){
	    	$('[name="period"]').val("empty");
	    	return false;
	    }
	    if(!$.isNumeric(period)) {
	    	$('[name="period"]').val("number");
	    	return false;
	    }
	});
	
	</script>
	
	<!-- /container -->
</body>
</html>