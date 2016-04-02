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
			/* console.log(location.href);
			console.log(location.href.split("/")); */
			/* var hostname = "/" + location.href.split("/")[3];
			var links = document.getElementById("navigation");
			var lists = links.getElementsByTagName("li");	
			lists[1].childNodes[0].setAttribute("href", hostname+"/add" );
			lists[2].childNodes[0].setAttribute("href", hostname+"/history" );
			lists[3].childNodes[0].setAttribute("href", hostname+"/payment" );
			lists[4].childNodes[0].setAttribute("href", hostname+"/shopping" );
			lists[5].childNodes[0].setAttribute("href", hostname+"/simulator" );		 */
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
				<li class="active"><a data-toggle="pill" href="home">Home</a></li>
				<li><a  href="add">Add</a></li>
				<li><a  href="history">Show history</a></li>
				<li><a  href="payment">Show payments</a></li>
				<li><a  href="">Shopping list</a></li>
				<li><a  href="simulator">Simulator</a></li>
			  </ul>
			</div>
			
			<div class="col-md-9">
				<div class="tab-content">
					<div class="tab-pane fade in active" id="home">
						<div class="panel panel-default">
							<div class="panel-heading">Home</div>
							<div class="panel-body">Home info
								<a href="history">HISTORY - tva samo go probvah s link kam index jsp i kontroler /history</a>
								<p>${logedUser.username}</p>
							</div>
						</div>
					</div>
					
					<div class="tab-pane fade" id="add">
						<div class="panel panel-default">
							<div class="panel-heading">Add</div>
							<div class="panel-body">
							
								<form class="form-horizontal">
									<div class="form-group">
										<!--amount input-->
										<label class="sr-only" for="exampleInputAmount">Amount</label>
											<div class="input-group">
												<div class="input-group-addon">$</div>
												<input type="text" class="form-control" id="exampleInputAmount" placeholder="Amount">
												<!--<div class="input-group-addon">.00</div>-->
											</div>
											
										<!--Radio buttons-->
										<label class="radio-inline">
										  <input type="radio" name="payment_type" id="inlineRadio1" value="income"> Income
										</label>
										<label class="radio-inline">
										  <input type="radio" name="payment_type" id="inlineRadio2" value="expense"> Expense
										</label>
										
										<!--Tags-->
										<label for="sel1">Choose tag:</label>
										<select class="form-control" id="sel1">
											<option>1</option>
											<option>2</option>
											<option>3</option>
											<option>4</option>
										</select>
										
										<!--Date-->
										
										<input type="date" class="form-control" placeholder="Date">
										
										<!--Description-->
										<label for="description">Description:</label>
										<textarea class="form-control" rows="3" id="description"></textarea>
										
									</div>
									<button type="submit" class="btn btn-primary">Add</button>
								</form>
							
							</div>
						</div>
					</div>
					<div class="tab-pane fade" id="history">
						<div class="panel panel-default">
							<div class="panel-heading">History</div>
							<div class="panel-body">
							 <div class="panel-group" id="accordion">
								  <div class="panel panel-default">
									<div class="panel-heading">
									  <h4 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion" href="#collapse1">
										Collapsible Group 1</a>
									  </h4>
									</div>
									<div id="collapse1" class="panel-collapse collapse in">
									  <div class="panel-body">Lorem ipsum dolor sit amet, consectetur adipisicing elit,
									  sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad
									  minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea
									  commodo consequat.</div>
									</div>
								  </div>
								  <div class="panel panel-default">
									<div class="panel-heading">
									  <h4 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion" href="#collapse2">
										Collapsible Group 2</a>
									  </h4>
									</div>
									<div id="collapse2" class="panel-collapse collapse">
									  <div class="panel-body">Lorem ipsum dolor sit amet, consectetur adipisicing elit,
									  sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad
									  minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea
									  commodo consequat.</div>
									</div>
								  </div>
								  <div class="panel panel-default">
									<div class="panel-heading">
									  <h4 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion" href="#collapse3">
										Collapsible Group 3</a>
									  </h4>
									</div>
									<div id="collapse3" class="panel-collapse collapse">
									  <div class="panel-body">Lorem ipsum dolor sit amet, consectetur adipisicing elit,
									  sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad
									  minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea
									  commodo consequat.</div>
									</div>
								  </div>
								</div> 
							
							
							
							</div>
						</div>
					</div>
					
					<div class="tab-pane fade" id="payments">
						<div class="panel panel-default">
							<div class="panel-heading">Payments</div>
							<div class="panel-body">
							 <div class="panel-group" id="accordion1">
								  <div class="panel panel-default">
									<div class="panel-heading">
									  <h4 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion1" href="#collapse21">
										Collapsible Group 1</a>
									  </h4>
									</div>
									<div id="collapse21" class="panel-collapse collapse in">
									  <div class="panel-body">Lorem ipsum dolor sit amet, consectetur adipisicing elit,
									  sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad
									  minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea
									  commodo consequat.</div>
									</div>
								  </div>
								  <div class="panel panel-default">
									<div class="panel-heading">
									  <h4 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion1" href="#collapse22">
										Collapsible Group 2</a>
									  </h4>
									</div>
									<div id="collapse22" class="panel-collapse collapse">
									  <div class="panel-body">Lorem ipsum dolor sit amet, consectetur adipisicing elit,
									  sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad
									  minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea
									  commodo consequat.</div>
									</div>
								  </div>
								  <div class="panel panel-default">
									<div class="panel-heading">
									  <h4 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion1" href="#collapse23">
										Collapsible Group 3</a>
									  </h4>
									</div>
									<div id="collapse23" class="panel-collapse collapse">
									  <div class="panel-body">Lorem ipsum dolor sit amet, consectetur adipisicing elit,
									  sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad
									  minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea
									  commodo consequat.</div>
									</div>
								  </div>
								</div> 
							
							
							</div>
						</div>
					</div>
					
					<div class="tab-pane fade" id="shopping">
						<div class="panel panel-default">
							<div class="panel-heading">Shopping List</div>
							<div class="panel-body">
							<div class="panel-group" id="accordion3">
								  <div class="panel panel-default">
									<div class="panel-heading">
									  <h4 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion3" href="#collapse31">
										Collapsible Group 1</a>
									  </h4>
									</div>
									<div id="collapse31" class="panel-collapse collapse in">
									  <div class="panel-body">Lorem ipsum dolor sit amet, consectetur adipisicing elit,
									  sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad
									  minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea
									  commodo consequat.</div>
									</div>
								  </div>
								  <div class="panel panel-default">
									<div class="panel-heading">
									  <h4 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion3" href="#collapse32">
										Collapsible Group 2</a>
									  </h4>
									</div>
									<div id="collapse32" class="panel-collapse collapse">
									  <div class="panel-body">Lorem ipsum dolor sit amet, consectetur adipisicing elit,
									  sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad
									  minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea
									  commodo consequat.</div>
									</div>
								  </div>
								  <div class="panel panel-default">
									<div class="panel-heading">
									  <h4 class="panel-title">
										<a data-toggle="collapse" data-parent="#accordion3" href="#collapse33">
										Collapsible Group 3</a>
									  </h4>
									</div>
									<div id="collapse33" class="panel-collapse collapse">
									  <div class="panel-body">Lorem ipsum dolor sit amet, consectetur adipisicing elit,
									  sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad
									  minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea
									  commodo consequat.</div>
									</div>
								  </div>
								</div> 
							
							
							</div>
						</div>
					</div>
					
					<div class="tab-pane fade" id="simulator">
						<div class="panel panel-default">
							<div class="panel-heading">Simulator</div>
							<div class="panel-body">Panel Content</div>
						</div>
					</div>
				</div>				
			</div>
		</div>
    </div> <!-- /container -->
	</body>
</html>