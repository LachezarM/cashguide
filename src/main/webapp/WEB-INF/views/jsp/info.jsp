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
<!--  css for datepicker -->
<link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
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
	left: 50px;
	top: 0px;
}

body {
	padding-top: 20px;
	padding-bottom: 20px;
}

.ui-datepicker-calendar {
	display:none;
}

</style>

<!-- jQueryV2.2.2 -->
<script src="js/Chart.min.js"></script>
<script src="js/Chart.js"></script>

<!-- BootstrapV3.3.6 Core JavaScript -->
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
		destroyCharts();
		var ctx = document.getElementById("chartPayments").getContext("2d");
		myLineChart = new Chart(ctx).Line(data);
	}
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
		if(lineChartComparison != null) {
			lineChartComparison.destroy();
		}
	}
</script>

<!-- jQueryV2.2.2 -->
<script src="js/jquery-2.2.2.min.js"></script>
<script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
<script src="js/bootstrap.min.js"></script>

<script>
/* $(function() {
	$("#datepicker1").datepicker({
		dateFormat : "dd-mm-yy"
	});
	$("#datepicker2").datepicker({
		dateFormat : "dd-mm-yy"
	});
});
 */


$(function() {
	$("#datepicker1")
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
	
	$("#datepicker2")
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
<script>
function opa() {
	var date1 = document.getElementById('datepicker1').value;
	var date2 = document.getElementById('datepicker2').value;
	var type = $('input[name="choise"]:checked').val();
	
	var dates = {"date1":date1,
    	 	"date2":date2
 		};
	 $.ajax({
         type: "POST",
         url: "compare",
         data: dates,
         success: function (result) {
        	 createLineChartComparison(result,type);
         },
         error: function (result) {
               alert("oops");
         }
     });

}
</script>
<script>
var lineChartComparison = null;
function createLineChartComparison(result,type){
	obj = JSON.parse(result);
	var labels = [];
	var dataFirst = [];
	var dataSecond = [];
	if(type == "Incomes") {
		var array = obj.firstDateIncomesJson.payments;
		labels = getLabels(array);
		dataFirst = getData(array);
		var array = obj.secondDateIncomesJson.payments;
		dataSecond = getData(array);
	} else if(type == "Expenses"){
		var array = obj.firstDateExpensesJson.payments;
		labels = getLabels(array);
		dataFirst = getData(array);
		var array = obj.secondDateExpensesJson.payments;
		dataSecond = getData(array);
	}
	var data =  {
			labels: labels,
			datasets:[
			  {
				  label: "First Month",
		            fillColor: "rgba(220,220,220,0.2)",
		            strokeColor: "rgba(220,220,220,1)",
		            pointColor: "rgba(220,220,220,1)",
		            pointStrokeColor: "#fff",
		            pointHighlightFill: "#fff",
		            pointHighlightStroke: "rgba(220,220,220,1)",
                  data : dataFirst
			  }   ,
			 { 
				  label: "Second Month",
		            fillColor: "rgba(151,187,205,0.2)",
		            strokeColor: "rgba(151,187,205,1)",
		            pointColor: "rgba(151,187,205,1)",
		            pointStrokeColor: "#fff",
		            pointHighlightFill: "#fff",
		            pointHighlightStroke: "rgba(151,187,205,1)",
                 	 data : dataSecond
			  }       
			        ]
	}
	destroyCharts();
	var ctx = document.getElementById("chartPayments")
	.getContext("2d");
	lineChartComparison = new Chart(ctx).Line(data);
	
}
function getLabels(array){
	var labels = [];
	for(i = 0;i < array.length;i++) {
		var category = array[i].category;
		labels.push(category);
	}
	return labels;
}
function getData(array){
	var data = [];
	for(i = 0;i < array.length;i++) {
		var amount = array[i].amount;
		data.push(amount);
	}
	return data;
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
					<li class="active"><a href="info">Charts</a></li>
					<li><a href="simulator">Simulator</a></li>
				</ul>
			</div>

			<div class="col-md-9">
				<div class="panel panel-default">
					<div class="panel-heading">Charts</div>
					<div class="panel-body">
						<div class="col-md-3">
						Choose :
							<select id="typeChart" name="typeChart" onchange="showPayments(value);">
								<option selected="selected" >Choose</option>
								<option  value="EXPENSES">Expenses</option>
								<option   value="INCOMES">Incomes</option>
								<option value="YEARSEXPENSES">YearBackExpenses</option>
								<option value="YEARSINCOMES">YearBackIncomes</option>
							</select>
						</div>
						<div class="col-md-1">
						OR
						</div>
						<div class="col-md-8">
							<div id="choise" >
							Compare By Month:<br/> 
							First Month<input type="text" name="date1" id="datepicker1" class="form-control" placeholder="Month"> 
							Second Month<input type="text" name="date2" id="datepicker2" class="form-control" placeholder="Month"> 
								
							Expenses <input checked="checked" type="radio" name="choise" value="Expenses" '>
							Incomes <input type="radio" name="choise" value="Incomes"> 		
								<button onclick="opa();">Compare : </button>
							</div>
						</div>
						<div class="col-md-12" style="margin-top:30px;">
							<canvas style="margin-left:100px;" id="chartPayments" width="300" height="300"></canvas>
						</div>
						
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- /container -->
</body>
</html>