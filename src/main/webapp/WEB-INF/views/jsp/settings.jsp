<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- <!DOCTYPE html>
<html>
<head>
<style>
ul {
    list-style-type: none;
    margin: 0;
    padding: 0;
    overflow: hidden;
    background-color: #333;
}

li {
    float: left;
}

li a {
    display: block;
    color: white;
    text-align: center;
    padding: 14px 16px;
    text-decoration: none;
}

li a:hover {
    background-color: #337ab7;
}
input[type=text] ,input[type=password]{
    width: 100%;
    padding: 6px 10px;
    margin: 8px 0;
    box-sizing: border-box;
}
input[type=button], input[type=submit], input[type=reset] {
    background-color: #337ab7;
    border: none;
    color: white;
    padding: 8px 10px;
    text-decoration: none;
    margin: 4px 2px;
    cursor: pointer;
}
</style>
</head>
<body bgcolor="#B0C4DE">

<ul>
 <!--  <li><a href="addBalance">Add Balance</a></li> -->
<!--   <li><a href="changeUsername">Change Username</a></li>
 -->  <li><a href="changePassword">Change Password</a></li>
   <li><a href="changeBudgetPercentage">Change budgetPercentage</a></li>
   <li><a href="deleteCategory">Delete custom category</a></li>
  <li><a href="back">Back</a></li>
</ul>

  			<c:if test="${changeUsername != null}">
				<div class="change">
				<form action="changeUsername" method="post">
					<input type="text" placeholder="New Username Here" name="username">
					<input type="submit" value="change">
					</form>
					<c:out value="${change }"></c:out>
				</div>	
			</c:if>
			<c:if test="${changePassword != null}">
				<div>
				<form action="changePasswordUser" method="get">
					 <input type="password" placeholder="Input new password" name="newPassword">
					
						<input type="submit" value="change">
					</form>
					<c:out value="${change }"></c:out>
				</div>
			</c:if>
			<c:if test="${addBalance != null}">
				<div>
					<form>
						<input type="text" placeholder="Your balance">
						 <input type="submit" value="add">
					</form>
				</div>
			</c:if>
			
			<c:if test="${changeBudgetPercentage != null}">
				<div>
				<p>${errorMessage}</p>
					<form method="POST" action="changeBudgetPercentage">
						<input type="text" name = "percentage" placeholder="Your percentage">
						 <input type="submit" value="change">
					</form>
				</div>
			</c:if>

			
			<c:if test="${deleteCategory != null}">
				<div>
				<p>${errorMessage}</p>
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

</body>
</html> --%>



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

/*START JQUERY VALIDATE STYLE*/
/*the error message from jquery validate function will hava this styles*/
.error {
	color: #a94442;
}

input.error {
	/*color: #a94442;*/
	background-color: #f2dede;
	border-color: #a94442;
}
/*END JQUERY VALIDATE STYLE*/
</style>

<!-- jQueryV2.2.2 -->
<script src="js/jquery-2.2.2.min.js"></script>

<!-- BootstrapV3.3.6 Core JavaScript -->
<script src="js/bootstrap.min.js"></script>

<!-- jquery datepicker -->
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>

<!-- JQuery Validation plugin -->
<!-- Plugins for Form validation with jquery -->
<script
	src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.9/jquery.validate.min.js"></script>
<script
	src="http://ajax.aspnetcdn.com/ajax/jquery.validate/1.9/additional-methods.min.js"></script>


<script>
	$(document).ready(function() {

	});

	$(function() {
		$("#datepicker").datepicker({
			dateFormat : "dd-mm-yy",
			appendText : "(dd-mm-yyyy)"
		});
	});
	$(function() {
		$("#datepickerMonth")
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
	
	//form validation -> javascript
	$(function() {

		$.validator.addMethod("customNumber", function(value, event) {
			return (value.match(/^[+]?\d+(\.)?\d*$/));
		}, "Please enter a correct positive number.");

		$.validator.addMethod("customDate", function(value, event) {
			return (value.match(/^\d\d?\-\d\d?\-\d\d\d\d$/));
		}, "Please enter a date in the format dd-mm-yyyy.");

		$("#addForm")
				.validate(
						{
							// Specify the validation rules
							rules : {
								amount : {
									required : true,
									customNumber : true
								},
								date : {
									required : true,
									customDate : true
								},
								description : {
									maxlength : 100
								}
							},
		
							// Specify the validation error messages
							messages : {
								amount : {
									required : "Please enter amount of money",
									number : "Amount must be a number"
								},
								date : {
									required : "Please enter a date",
								},
								description : {
									manlength : "Your description must be at less than 100 characters long"
								}
							},
							//if error occurs the request won't be send
							submitHandler : function(form) {
								form.submit();
							}
						});

	});
	
	//server side validation->java
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
				</ul>
			</div>
			<!-- Menu end-->
			
			<!-- Content start-->
			<div class="col-md-9">
				<div class="panel panel-default">
					<div class="panel-heading"></div>
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
											<form action="changePasswordUser" method="POST">
												<input type="password" placeholder="Input old password" name="oldPassword">	
												<input type="password" placeholder="Input new password" name="newPassword">		
												<input type="submit" value="change">
											</form>
											<c:out value="${change}"></c:out>
										</div>
									</c:if>
								
									<c:if test="${panel=='changePercentage'}">
										<div>
											<form method="POST" action="changeBudgetPercentage">
												<input type="text" name = "percentage" placeholder="Your percentage">
												<input type="submit" value="change">
											</form>
										</div>
									</c:if>

			
									<c:if test="${panel=='deleteCategory'}">
										<div>
										<p>${errorMessage}</p>
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
										<form  method="POST" action="getBudgetForDeleting">
											Date: <input type="text" name="date" id="datepickerMonth" class="form-control" placeholder="Date"> 
												<input type="submit" value="show" />						
										</form>
										<form method="POST" action="deletePayment" id="calculation">
											<table>
												<tr>
													<th>Type</th>
													<th>Category</th>
													<th>Description</th>
													<th>Amount</th>
													<th>Date</th>
													<th>Select</th>
												</tr>
					
												<c:forEach var="income" items="${incomes}" varStatus="loop">
												<tr>
													<td>${income.type }</td>
													<td>${income.category}</td>
													<td>${income.description}</td>
													<td>${income.amount}</td>
													<td>${income.date}</td>
													<td><input type="checkbox" name="income" value="${loop.count-1}"></td>
												</tr>
												</c:forEach>
										
												<c:forEach var="expense" items="${expenses}" varStatus="loop">
												<tr>
													<td>${expense.type }</td>
													<td>${expense.category}</td>
													<td>${expense.description}</td>
													<td>${expense.amount}</td>
													<td>${expense.date}</td>
													<td><input type="checkbox" name="expense" value="${loop.count-1}"></td>
												</tr>
												</c:forEach>
											</table>
											<input type="submit" value="delete" />
										</form>
										
										
										
									</c:if>
									
									
									
								</div>							
							<%-- 
							<div id="delete-payment" class="tab-pane fade">
								<div class="row" id="delete-payment-form">
									<div class="col-md-6" style="margin-top:8px">
										<!--New category-->
										<h1 style="margin-left: 20px; margin-bottom: 20px;">Delete Payment</h1>
										
										<c:if test="${successDeletingPayment!=null}">
											<div class="alert alert-success" role="alert">
												${successDeletingPayment}
											</div>
										</c:if>
										<c:set var="incomes" value='${budget.payments.get("INCOME")}'></c:set>
										<c:set var="expenses" value='${budget.payments.get("EXPENSE")}'></c:set>
										
										<form  method="POST" action="getBudgetForDeleting">
											Date: <input type="text" name="date" id="datepickerMonth" class="form-control" placeholder="Date"> 
												<input type="submit" value="show" />						
										</form>
										
										
										<form method="POST" action="deletePayment" id="calculation">
											<table>
												<tr>
													<th>Type</th>
													<th>Category</th>
													<th>Description</th>
													<th>Amount</th>
													<th>Date</th>
													<th>Select</th>
												</tr>
					
												<c:forEach var="income" items="${incomes}" varStatus="loop">
												<tr>
													<td>${income.type }</td>
													<td>${income.category}</td>
													<td>${income.description}</td>
													<td>${income.amount}</td>
													<td>${income.date}</td>
													<td><input type="checkbox" name="income" value="${loop.count-1}"></td>
												</tr>
												</c:forEach>
										
												<c:forEach var="expense" items="${expenses}" varStatus="loop">
												<tr>
													<td>${expense.type }</td>
													<td>${expense.category}</td>
													<td>${expense.description}</td>
													<td>${expense.amount}</td>
													<td>${expense.date}</td>
													<td><input type="checkbox" name="expense" value="${loop.count-1}"></td>
												</tr>
												</c:forEach>
											</table>
											<input type="submit" value="delete" />
										</form>
									</div>
								</div>
							</div>  --%>
							
							
										
					
					</div>
				</div>
			</div>
			<!-- Content end-->
		</div>
	</div>
	<!-- /container -->
</body>
</html>




















