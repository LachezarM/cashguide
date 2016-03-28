<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- <html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Demo page</title>
</head>

<body>
	<h1> ${text} </h1> 
	<p> Last added product: ${product.name}</p>
</body>
</html> --%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script type="text/javascript">

	function task2()
	{
		$.post("LoginManager", 
				{ 
					username: "userIvan", 
					password : "pass123"
				}
				, function(result){
       			document.getElementById("taskNumber").innerHTML  = "task 2";
       			document.getElementById("taskResponse").innerHTML  =result;
	    });
	}

	function task3()
	{
		$.post("RegisterManager", 

				JSON.stringify(
						{
							username : "Reaver",
							password : "123456",
							email : "reaver@abv.bg",
							age : 25,
							address : "Sofia, Infinity Tower"
						}
				)	
				, function(result){
       			document.getElementById("taskNumber").innerHTML = "task 3";
       			document.getElementById("taskResponse").innerHTML =result;
	    });
	}

	function task4()
	{
		$.get("LevelManager",
				{ 
					id: 312, 
					levelNo : 7
				}
		, function(result){
       			document.getElementById("taskNumber").innerHTML = "task 4";
       			document.getElementById("taskResponse").innerHTML =result;
	    });
	}

	function task5()
	{
		$.post("LevelManager", 
				JSON.stringify(
						{
							id : 312,
							levelNo : 22,
							points : 22567
						}
				)
				, function(result){
       			document.getElementById("taskNumber").innerHTML = "task 5";
       			document.getElementById("taskResponse").innerHTML =result;
	    });
	}

	function task6()
	{
		$.get("HighScoreManager"
				, function(result){
       			document.getElementById("taskNumber").innerHTML = "task 6";
       			document.getElementById("taskResponse").innerHTML =result;
	    });
	}
</script>
<body>
Task 2 test: <button onclick="task2()">Send data</button><br>
Task 3 test: <button onclick="task3()">Send data</button><br>
Task 4 test: <button onclick="task4()">Send data</button><br>
Task 5 test: <button onclick="task5()">Send data</button><br>
Task 6 test: <button onclick="task6()">Send data</button><br>
<br>
<h3>Results:</h3>
<h2 id="taskNumber"></h2>
<p id= "taskResponse"></p>
</body>
</html>