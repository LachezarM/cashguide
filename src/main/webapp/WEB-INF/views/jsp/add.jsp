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

<title>Add</title>

<!-- Bootstrap Core CSS -->
<link type="text/css" href="css/bootstrap.min.css" rel="stylesheet">
<!-- css for datepicker -->
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">


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

<!-- jquery datepicker -->
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
 
<script>
	$(document).ready(function() {		
		addSelectOptions("Income");
		/* var hostname = "/" + location.href.split("/")[3];
		//var hostname = "";
		var links = document.getElementById("navigation");
		var lists = links.getElementsByTagName("li");
		lists[0].childNodes[0].setAttribute("href", hostname + "/home");
		lists[1].childNodes[0].setAttribute("href", hostname + "/add");
		lists[2].childNodes[0].setAttribute("href", hostname + "/history");
		lists[3].childNodes[0].setAttribute("href", hostname + "/payment");
		lists[4].childNodes[0].setAttribute("href", hostname + "/shopping");
		lists[5].childNodes[0].setAttribute("href", hostname + "/simulator"); */
	});
	
	$(function() {
	    $( "#datepicker" ).datepicker({
	    	dateFormat:"dd-mm-yy"
	    	
	    });
	  });
	
	//with ajax
	/* function addSelectOptions(type){
		$.get("/Cashguide1/getCategories", {
			"type": type
		}, function(result){
			var select = document.getElementById("sel1");
			select.innerHTML='';
			for(i=0;i<result.length; i++){
				var option = document.createElement("option");
				option.text = result[i];
				select.add(option);
			}
		});
	}	 */
	
	//without ajax: the categories are loaded during the request for add.jsp in HomePageController -> /add
	function addSelectOptions(type){
		var categoryTypes = ${categories};//categories map from controller
		var categories;
		if(type=='Income'){
			categories = categoryTypes["INCOME"];
		}else if(type=='Expense'){
			categories = categoryTypes["EXPENSE"];
		}
		
		var select = document.getElementById("sel1");
		select.innerHTML='';
				
		for(i=0;i<categories.length; i++){
			var option = document.createElement("option");
			option.text = categories[i];
			option.setAttribute("value", categories[i]);
			select.add(option);
		}		
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
					<li class="active"><a href="">Add</a></li>
					<li><a href="history">Show history</a></li>
					<li><a href="payment">Show payments</a></li>
					<li><a href="">Shopping list</a></li>
					<li><a href="simulator">Simulator</a></li>
				</ul>
			</div>

			<div class="col-md-9">
				<div class="panel panel-default">
					<div class="panel-heading">Add</div>
					<div class="panel-body">
						<form class="form-horizontal" method="POST" action="addPayment">
							<div class="form-group">
								<!--amount input-->
								<label class="sr-only" for="exampleInputAmount">Amount</label>
								<div class="input-group">
									<div class="input-group-addon">$</div>
									<input type="text" class="form-control" id="exampleInputAmount"
										placeholder="Amount" name="amount">
									<!--<div class="input-group-addon">.00</div>-->
								</div>

								<!--Radio buttons-->
								<label class="radio-inline"> <input type="radio"
									name="payment_type" id="inlineRadio1" value="income" onclick='addSelectOptions("Income")' checked>
									Income
								</label> <label class="radio-inline"> <input type="radio"
									name="payment_type" id="inlineRadio2" value="expense" onclick='addSelectOptions("Expense")'>
									Expense
								</label>

								<!--Tags-->
								<label for="sel1">Choose tag:</label> 
								<select class="form-control" id="sel1" name="category"></select>

								<!--Date-->								
								<p>Date: <input type="text" name="date" id="datepicker" class="form-control" placeholder="Date"></p>
								
								
								<!--Description-->
								<label for="description">Description:</label>
								<textarea class="form-control" rows="3" id="description" name="description"></textarea>
							</div>
							<button type="submit" class="btn btn-primary">Add</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- /container -->
</body>
</html>