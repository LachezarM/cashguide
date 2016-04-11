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
		setActiveTab();
		addSelectOptions("Income");
	});

	function setActiveTab(){
		$('.nav-tabs li').removeClass('active');
		$('.tab-pane').removeClass('active in');
		var tab = '${panel}'
		$('#'+tab).addClass('active in');
		var href = '#'+tab;
		console.log(href)
		$('a[href="'+href+'"]').parent().addClass("active");
		console.log($('a[href="#payment"]').parent());
		
	}
	
	function addSelectOptions(type) {
		var categoryTypes = ${categories}//categories map in model from controller
		console.log(categoryTypes)
		var categories;
		if (type == 'Income') {
			categories = categoryTypes["INCOME"];
		} else if (type == 'Expense') {
			categories = categoryTypes["EXPENSE"];
		}

		var select = document.getElementById("sel1");
		select.innerHTML = '';

		for (i = 0; i < categories.length; i++) {
			var option = document.createElement("option");
			option.text = categories[i];
			option.setAttribute("value", categories[i]);
			option.setAttribute("style", "border-right: 1px solid #A9A9A9");
			select.add(option);
			console.log(option);
		}
	}
	
	$(function() {
		$("#datepicker").datepicker({
			dateFormat : "dd-mm-yy",
			appendText : "(dd-mm-yyyy)"
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
					<li class="active"><a href="add">Add</a></li>
					<li><a href="history">Show history</a></li>
					<li><a href="info">Info</a></li>
					<li><a href="simulator">Simulator</a></li>
				</ul>
			</div>
			<!-- Menu end-->
			<!-- Content start-->
			<div class="col-md-9">
				<div class="panel panel-default">
					<div class="panel-heading">Add</div>
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

						<div class="balance">Your balance is:${logedUser.budget.balance}</div>
						<div class="income">Income: ${logedUser.budget.income}</div>
						
						<!-- NAVIGATION add payment|add category| delete payment| delete category -->
						<ul class="nav nav-tabs">
							<li><a data-toggle="tab" href="#payment">Add Payment</a></li>
							<li><a data-toggle="tab" href="#add-category">Add Category</a></li>
							<!-- <li><a data-toggle="tab" href="#delete-category">Delete Category</a></li>
							<li><a data-toggle="tab" href="#delete-payment">Delete paymet</a></li> -->
						</ul>
						
						<div class="tab-content">	
							<div id="payment" class="tab-pane fade in active">
								<h1 style="margin-left: 20px; margin-bottom: 20px;">Add Payment</h1>
								<div class="col-md-6" id="payment-form" >
									<c:if test="${successPayment!=null}">
										<div class="alert alert-success" role="alert">
											${successPayment}
										</div>
									</c:if>
									
									<c:if test="${errorPayment!=null}">
										<div class="alert alert-danger" role="alert">
											${errorPayment}
										</div>
									</c:if>
									
									<form id="addForm" class="form-horizontal" method="POST" action="addPayment">
										<div class="form-group">
											<label>Money:</label>
											<!--amount input-->
											<div class="input-group">
												<div class="input-group-addon">$</div>
												<input type="text" class="form-control" id="amount" placeholder="Amount" name="amount">
											</div>
			
											<label>Choose type</label>
											<!--Radio buttons-->
											<div class="input-group">
												<label class="radio-inline"> <input type="radio" name="payment_type" id="inlineRadio1" value="income" onclick='addSelectOptions("Income")' checked> Income
												</label> <label class="radio-inline"> <input type="radio" name="payment_type" id="inlineRadio2" value="expense" onclick='addSelectOptions("Expense")'> Expense
												</label>
											</div>
											
											<!--Tags-->
											<label for="sel1">Choose category:</label> 
											<select class="form-control" id="sel1" name="category" >
												<!-- options are automatically generated with js -->
											</select>
																						
											<!--Date-->
											<p>
												Date: <input type="text" name="date" id="datepicker" class="form-control" placeholder="Date">
											</p>
											
											<!--Description-->
											<label for="description">Description:</label>
											<textarea class="form-control" rows="4" id="description" name="description"></textarea>
										</div>
											
										<button type="submit" class="btn btn-primary">Add</button>
									</form>
								</div>	
							</div>
							
							
							<div id="add-category" class="tab-pane fade">
								<div class="row" id="custom-category-form">
									<div class="col-md-6" style="margin-top:8px">
										<!--New category-->
										<h1 style="margin-left: 20px; margin-bottom: 20px;">Add Category</h1>
										
										<c:if test="${successCategory!=null}">
											<div class="alert alert-success" role="alert">
												${successCategory}
											</div>
										</c:if>
										<c:if test="${errorCategory!=null}">
											<div class="alert alert-danger" role="alert">
												${errorCategory}
											</div>
										</c:if>
										
										<form action="customCategory" method="POST">
											<div class="input-group">
												<span>Category type:</span>
												<label class="radio-inline"> 
													<input type="radio" name="type" id="inlineRadio1" value="income" checked>
													 Income
												</label> 
												<label class="radio-inline">
													<input type="radio" name="type" id="inlineRadio2" value="expense"> 
													 Expense
												</label>
											</div>
											<div class="input-group">
											Category name:<input type="text" name="customCategory">
												<input type="submit" value="Add new category">
											</div>
										</form>
									</div>
								</div>
							</div>
							
							<%-- <div id="delete-category" class="tab-pane fade">
							
							
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
							</div> --%>							
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