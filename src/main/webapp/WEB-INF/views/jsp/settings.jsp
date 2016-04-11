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

<title>Add</title>

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

.ui-datepicker-calendar {
	display:none;
}


/*START CSS VALIDATE STYLE*/
/*the error message from jquery validate function will hava this styles*/
.error {
	color: #a94442;
}

input.error {
	/*color: #a94442;*/
	background-color: #f2dede;
	border-color: #a94442;
}
/*END CSS VALIDATE STYLE*/
</style>

<!-- jQueryV2.2.2 -->
<script src="js/jquery-2.2.2.min.js"></script>

<!-- BootstrapV3.3.6 Core JavaScript -->
<script src="js/bootstrap.min.js"></script>

<!-- jquery datepicker -->
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>

<script>
	$(document).ready(function() {

	});

/* 	
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
		
		selectAll();
	}); */
	
	
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
</script>
</head>
<body>


	<div class="container">

		<!-- Fixed navbar -->
		<c:import url="header.jsp"></c:import>


		<div class="row" name="content" style="margin-top: 50px;">
			<!-- Menu start-->
			<div class="col-md-3">
				<ul id="navigation" class="nav nav-pills nav-stacked">
					<li><a href="home">Home</a></li>
					<li><a href="changePassword">Change Password</a></li>
					<li><a href="changeBudgetPercentage">Change budgetPercentage</a></li>
					<li><a href="deleteCategory">Delete custom category</a></li>
					<li><a href="deletePayment">Delete payment</a></li>
					<li><a href="changeEmail">Change Email</a></li>
				</ul>
			</div>
			<!-- Menu end-->
			
			<!-- Content start-->
			<div class="col-md-9">
				<div class="panel panel-default">
					<div class="panel-heading">
					</div>
					<div class="panel-body">
						<!-- FOR BROWSERS WITHOUT JAVASCRIPT -->
						<noscript>
							<div class="alert alert-warning" role="alert">
								For full functionality of this site it is necessary to enable
								JavaScript. Here are the <a
									href="http://www.enable-javascript.com/" target="_blank">
									instructions how to enable JavaScript in your web browser</a>.
							</div>
						</noscript>
						<!-- END -->
							<div class="col-md-6" style="margin-top:8px">
							
								<c:if test="${successMessage!=null}">
									<div class="alert alert-success" role="alert">
										${successMessage}
									</div>
								</c:if>
								<c:if test="${errorMessage!=null}">
									<div class="alert alert-danger" role="alert">
										${errorMessage}
									</div>
								</c:if>
										
								
									<c:if test="${panel == 'changePassword'}">
										<div>
										<h2>Change password</h2>
											<form action="changePasswordUser" method="POST">
											Old password: <input type="password" placeholder="Input old password" name="oldPassword"><br/>
											New password: <input type="password" placeholder="Input new password" name="newPassword"><br/>	
												<input type="submit" value="change">
											</form>
											<c:out value="${change}"></c:out>
										</div>
									</c:if>
								
									<c:if test="${panel=='changePercentage'}">
										<div>
										<h2>Change budget percentage</h2>
										<span>Enter percentage between 0 and 100</span>
											<form method="POST" action="changeBudgetPercentage">
												<input type="text" name = "percentage" placeholder="Your percentage">
												<input type="submit" value="change">
											</form>
										</div>
									</c:if>

			
									<c:if test="${panel=='deleteCategory'}">
										<div>
										<p>${errorMessage}</p>
										<h2>Delete category</h2>
										<span>Choose category:</span>
											
											<form method="POST" action="deleteCategory">
												<select name="category">
													<c:forEach var="category" items="${categories}">
														<option><c:out value="${category}"></c:out></option>
													</c:forEach>
												</select>	
											<input type="submit" value="delete">
											</form>
										</div>
									</c:if>
									
									
									<c:if test="${panel=='deletePayment'}">
									<h2>Delete payment</h2>
									<span>Choose month:</span>
										<form  method="POST" action="getBudgetDel">
											<input type="text" name="date" id="datepicker" class="form-control" placeholder="Date"> 
											<input type="submit" value="show" />						
										</form>
									
										
										<c:if test="${month!=null}">
										<span>Choose payments</span>
											<c:set var="incomes" value='${delBudget.payments.get("INCOME")}'></c:set>
											<c:set var="expenses" value='${delBudget.payments.get("EXPENSE")}'></c:set>
											<form method="POST" action="deletePayment">
												<input type="hidden" value="${delBudget.id}" name="budgetId"/>
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
														<td>${income.type}</td>
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
												
												<input type="submit" value="delete" />
											</form>
										</c:if>
									</c:if>
									
									
									<c:if test="${panel=='changeEmail'}">
										<div>
										<h2>Change email</h2>									
											<form method="POST" action="changeEmail">
											New email <input type="text" name="email"/><br/>
											<input type="submit" value="change">
											</form>
										</div>
									</c:if>
								</div>					
					</div>
				</div>
			</div>
			<!-- Content end-->
		</div>
	</div>
	<!-- /container -->
</body>
</html>
