<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
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
  <li><a href="addBalance">Add Balance</a></li>
  <li><a href="changeUsername">Change Username</a></li>
  <li><a href="changePassword">Change Password</a></li>
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
	
	

</body>
</html>