<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/menu.css" />
</head>
<body>

	<div class="menu_simple"
		style="position: absolute; top: 250px; left: 0px">
		<ul>
			<li><a href="addBalance">Add Balance</a></li>
			<li><a href="changeUsername">Change Username</a></li>
			<li><a href="changePassword">Change Password </a></li>
			<li><a href="back">Back</a></li>
			<c:if test="${changeUsername != null}">
			
				<div >
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


		</ul>
	</div>
</body>
</html>