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

<title>Info</title>

<!-- Bootstrap Core CSS -->
<link type="text/css" href="css/bootstrap.min.css" rel="stylesheet">

<style>
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
	-moz-box-shadow: 0 0 5px rgba(123, 123, 123, .2);
	box-shadow: 0 0 5px rgba(123, 123, 123, 0.2);
	border: solid 1px #DADADA;
	font-family: "helvetica neue", arial;
	position: relative;
	cursor: pointer;
	padding-right: 20px;
}

.select_style select {
	-webkit-appearance: none;
	appearance: none;
	width: 70%;
	background: none;
	background: transparent;
	border: none;
	outline: none;
}

#chartPayments {
	position: relative;
	left: 350px;
	top: 0px;
}

body {
	padding-top: 20px;
	padding-bottom: 20px;
}
</style>

<!-- jQueryV2.2.2 -->
<script src="js/jquery-2.2.2.min.js"></script>
<script src="js/Chart.min.js"></script>
<script src="js/Chart.js"></script>

<!-- BootstrapV3.3.6 Core JavaScript -->
<script src="js/bootstrap.min.js"></script>
<script>
	/*
	function selectColor(colorNum, colors){
	if (colors < 1) colors = 1; // defaults to one color - avoid divide by zero
	return "hsl(" + (colorNum * (360 / colors) % 360) + ",100%,50%)";
	}*/
	//currently the best way to get distinguishable colors
	Colors = {};
	Colors.names = {
		aqua : "#00ffff",
		azure : "#f0ffff",
		beige : "#f5f5dc",
		blue : "#0000ff",
		brown : "#a52a2a",
		cyan : "#00ffff",
		darkblue : "#00008b",
		darkcyan : "#008b8b",
		darkgrey : "#a9a9a9",
		darkgreen : "#006400",
		darkkhaki : "#bdb76b",
		darkmagenta : "#8b008b",
		darkolivegreen : "#556b2f",
		darkorange : "#ff8c00",
		darkorchid : "#9932cc",
		darkred : "#8b0000",
		darksalmon : "#e9967a",
		darkviolet : "#9400d3",
		fuchsia : "#ff00ff",
		gold : "#ffd700",
		green : "#008000",
		indigo : "#4b0082",
		khaki : "#f0e68c",
		lime : "#00ff00",
		magenta : "#ff00ff",
		maroon : "#800000",
		navy : "#000080",
		olive : "#808000",
		orange : "#ffa500",
		pink : "#ffc0cb",
		purple : "#800080",
		violet : "#800080",
		red : "#ff0000",
		yellow : "#ffff00"
	};
	Colors.random = function() {

		var result;
		var count = 0;
		for ( var prop in this.names)
			if (Math.random() < 1 / ++count)
				result = prop;
		return result;
	};
</script>
<script>
	function showPayments(type) {
		var currMonthIncomes = ${currMonthIncomesJson};
		var currMonthExpenses = ${currMonthExpensesJson};
		var lastYearMonthlyExpenses = ${lastYearMonthlyExpenses};
		var lastYearMonthlyIncomes = ${lastYearMonthlyIncomes};
		if (type == "INCOMES") {
			var payments = currMonthIncomes["payments"];
			createPieChart(payments);
		} else if(type == "EXPENSES"){
			var payments = currMonthExpenses["payments"];
			createPieChart(payments);
		}
		else if(type == "YEARSINCOMES") {
			var payments = lastYearMonthlyIncomes["amounts"];
			createLineChart(payments);
		}
		else {
			var payments = lastYearMonthlyExpenses["amounts"];
			createLineChart(payments);
		}
		
	}
</script>
<script>

var myPieChart = null;
function createPieChart(payments) {
	
	if(payments.length == 0) {
		var pieData;
		var dataSet = {
				value : 1,
				color : Colors.random(),
				label : "NOT ENOUGHT DATA"
			};
		var pieOptions = {
				animateScale : true
		};
		destroyCharts();
		var ctx = document.getElementById("chartPayments")
				.getContext("2d");
		myPieChart = new Chart(ctx).Pie(pieData, pieOptions);

		myPieChart.addData(dataSet);

	} else {
	var pieData;
	var pieOptions = {
		showTooltips : true,
		animationEasing : "easeInOutQuart",
		animationSteps : 70,
		percentageInnerCutout : 0,
		segmentShowStroke : false,
		animateScale : true
	};
	
	destroyCharts();
	var ctx = document.getElementById("chartPayments")
			.getContext("2d");
	myPieChart = new Chart(ctx).Pie(pieData, pieOptions);

	for (i = 0; i < payments.length; i++) {
		var dataSet = {
			value : payments[i]["amount"],
			color : Colors.random(),
			label : payments[i]["category"]
		};
		myPieChart.addData(dataSet);
	}
	}
}
</script>
<script>
var myLineChart = null;
function createLineChart(payments) { 
<<<<<<< HEAD
	if(payments.length == 0) {
		var data = {
				labels : "NOT ENOUGHT DATA",
				datasets: [
				           {  data: 0}
				           ]
		}
	}
	else {
		var data =  {
=======
	console.log(payments);
	var data =  {
>>>>>>> 481c5f73de8b950be760e74ac8f4b656b5f9c4fd
			labels: generateLabels(payments),
			datasets:[
			  {
				  fillColor : "rgba(95,137,250,0.5)",
                  strokeColor : "rgba(95,137,250,0.9)",
                  highlightFill: "rgba(95,137,250,0.75)",
                  highlightStroke: "rgba(95,137,250,1)",
                  data : generateData(payments)
			  }        
			         ]
	}
	}
	destroyCharts();
	var ctx = document.getElementById("chartPayments")
	.getContext("2d");
	myLineChart = new Chart(ctx).Line(data);
	
}
</script>
<script>
function generateLabels(payments) {
	var labels = [];
		for(i = 0 ; i < payments.length ; i++){
			var key = Object.keys(payments[i]);
			labels.push(key);
		}
		return labels;
}

</script>
<script>
function generateData(payments) {
	var data = [];
	for(i=0;i < payments.length;i++){
		
		for(key in payments[i]){
		data.push(payments[i][key]);
		}
	}
	return data;
	
}
</script>
<script>
	function destroyCharts(){
		if(myLineChart != null) {
			myLineChart.destroy();
		}
		if(myPieChart != null) {
			myPieChart.destroy();
		}
	}
</script>
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
					<li><a href="history">Show history</a></li>
					<li class="active"><a href="info">Info</a></li>
					<li><a href="simulator">Simulator</a></li>
				</ul>
			</div>

			<div class="col-md-9">
				<div class="panel panel-default">
					<div class="panel-heading">Charts</div>
					<div class="panel-body">
							<select id="typeChart" name="typeChart" onchange="showPayments(value);">
								<option selected="selected" >Choose</option>
								<option  value="EXPENSES">Expenses</option>
								<option   value="INCOMES">Incomes</option>
								<option value="YEARSEXPENSES">YearBackExpenses</option>
								<option value="YEARSINCOMES">YearBackIncomes</option>
						</select>
						<canvas id="chartPayments" width="300" height="300"></canvas>
						
	

					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- /container -->
</body>
</html>