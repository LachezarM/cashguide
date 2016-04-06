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
				<li><a  href="info">Info</a></li>
				<li><a  href="">Shopping list</a></li>
				<li><a  href="simulator">Simulator</a></li>
			  </ul>
			</div>
			
			<div class="col-md-9">
						<div class="panel panel-default">
							<div class="panel-heading">Home</div>
							<div class="panel-body">Home info
							<c:set var="budgetCash" value="${logedUser.budget.income*logedUser.budget.percentageOfIncome}" scope="page"></c:set>
								<p>Hello, ${logedUser.username}. You can change your default budget percentage for current month <a href="changeBudgetPercentage">here.</a></p>
								<p>Your income is: ${logedUser.budget.income}</p>
								<p>Your budget cash is: ${budgetCash}</p>
								<p>Your balance is: ${logedUser.budget.balance}</p>
								<p>Your percentage is: ${logedUser.budget.percentageOfIncome}</p>
								
							</div>
						</div>
			</div>
		</div>
    </div> <!-- /container -->
	</body>
</html>