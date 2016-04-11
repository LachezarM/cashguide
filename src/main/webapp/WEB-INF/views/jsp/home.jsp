<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
		$(function(){
			var percentage = ${logedUser.budget.percentageOfIncome};
			percentage *= 100;
			if(percentage<50){
				$("#budgetLine").css("margin-left", "25px");
			}
			else if(percentage>50){
				$("#budgetLine").css("margin-left", "-25px");
			}else{
				$("#budgetLine").css("margin-left", "0px;");
			}
			
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
				<li><a  href="simulator">Simulator</a></li>
			  </ul>
			</div>
		
			<div class="col-md-9">
				<div class="panel panel-default">
					<div class="panel-heading">Home</div>
						<div class="panel-body">
							
							<div class="user">
								<h1>Hello, ${logedUser.username}</h1>
								<!-- <p>You can change your default budget percentage for current month <a href="changeBudgetPercentage">here.</a></p>-->
							</div>
							<!-- INIT -->			
							<c:choose>
								<c:when test="${logedUser.budget.expense==0&&logedUser.budget.income==0}">
									<c:set var="expensePercent" value="0"></c:set>
									<c:set var="remainingPercent" value="0"></c:set>
									<c:set var="budgetPercent" value="0"></c:set>
								</c:when>
								<c:otherwise>
									<c:set var="expensePercent" value="${(logedUser.budget.expense/logedUser.budget.income)*100}"></c:set>
									<c:set var="budget" value="${(logedUser.budget.income*logedUser.budget.percentageOfIncome)}"></c:set>
									<c:set var="remainingPercent" value="${100-expensePercent}"></c:set> 
									<c:set var="budgetPercent" value="${logedUser.budget.percentageOfIncome*100}"></c:set>
								</c:otherwise>
							
							</c:choose>
							
							
							<p>TOTAL INCOME ${logedUser.budget.income}</p>
							<p>TOTAL EXPENSES ${logedUser.budget.expense}</p>
							<div class="progress" style="height:40px; font-size: 20px; margin-top: 10px; margin-bottom: 100px;" >	
								
								<c:if test="${expensePercent>0&&expensePercent<=100}">
									<div id="expense" class="progress-bar progress-bar-danger progress-bar-striped" role="progressbar" 
			  						aria-valuenow="${expensePercent}" aria-valuemin="0" aria-valuemax="100" 
			  						style="width: ${expensePercent}%">
			  							<span style="font-size: 20px;"><fmt:formatNumber value="${expensePercent}" type="number" maxFractionDigits="1"></fmt:formatNumber>% expenses</span>
			  						</div>
								</c:if>
								
								<c:if test="${remainingPercent>0&&remainingPercent<=100}">
			  						<div id="remaining"class="progress-bar progress-bar-success progress-bar-striped" role="progressbar" 
			  						aria-valuenow="${remainingPercent}" aria-valuemin="0" aria-valuemax="100" 
			  						style="width: ${remainingPercent}%">
			  							<span style="font-size: 20px;"><fmt:formatNumber value="${remainingPercent}" type="number" maxFractionDigits="1"></fmt:formatNumber>% remaining</span>
			  						</div>
		  						</c:if>
		  						<c:if test="${budgetPercent>0&&budgetPercent<100}">
			  						<div id="budgetLine" style="width: 2px; height: 100px; padding-top: 0px; position:absolute; background: black; left: ${budgetPercent}%;">
			  							  <div style="padding-top: 40px; margin-left:5px;">Budget: ${budget}</div>
			  						</div>
								</c:if>
							</div>
							
							<div class="col-sm-6 col-md-6">
							    <div class="thumbnail">
							      <div class="caption">
							        <h3>INCOME</h3>
							        <p><span>Your incomes are: ${logedUser.budget.income}</span></p>
							        <p><a href="history" class="btn btn-primary" role="button">Show more payments</a></p>
							      </div>
							    </div>
							</div>
							
							<div class="col-sm-6 col-md-6">
							    <div class="thumbnail">
							      <div class="caption">
							        <h3>BUDGET</h3>
							        <span>Your budget is: ${logedUser.budget.income*logedUser.budget.percentageOfIncome}</span>
							        <h3>Percentage</h3>
							        <p>Your percentage is: ${logedUser.budget.percentageOfIncome}</p>
							    	<p><a href="changeBudgetPercentage" class="btn btn-primary" role="button">Change percentage</a></p>
							      </div>
							    </div>
							  </div>
							  
							  <div class="col-sm-6 col-md-6">
							    <div class="thumbnail">
							      <div class="caption">
							        <h3>EXPENSE</h3>
							        <p><span>Your expeses are: ${logedUser.budget.expense}</span></p>
							        <p><a href="history" class="btn btn-primary" role="button">Show more payments</a> <a href="#" class="btn btn-default" role="button">Button</a></p>
							      </div>
							    </div>
							  </div>
							  
							  <div class="col-sm-6 col-md-6">
							    <div class="thumbnail">
							      <div class="caption">
							        <h3>BALANCE</h3>
							        <p><span>Your balance is: ${logedUser.budget.balance}</span></p>
							       <%--  <p><span>Your last income was is: ${logedUser.budget.income}</span></p> --%>
							         <p><a href="info" class="btn btn-primary" role="button">Info</a> <a href="history" class="btn btn-default" role="button">History</a></p>
							      </div>
							    </div>
							  </div>
							  
							  
							   <div class="col-sm-6 col-md-6">
							    <div class="thumbnail">
							      <div class="caption">
							        <h3>SETTINGS</h3>
							        <p><span>You can change your email, password or percentage from your settings</span></p>
							        <p><a href="settings" class="btn btn-primary" role="button">Button</a> </p>
							      </div>
							    </div>
							  </div> 
							
						</div>
					</div>
				</div>
			</div>
	    </div> <!-- /container -->
	</body>
</html>