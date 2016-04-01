<%@ taglib prefix="c" 
           uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/menu.css" />
</head>
<body>

<div class="menu_simple" style="position:absolute; top:250px; left:0px">
<ul>
<li><a href="addBalance">Add Balance</a></li>
<li><a href="changeUsername">Change Username</a></li>
<li><a href="changePassword">Change Password </a></li>
<li><a href="back">Back</a></li>
<c:if test="${changeUsername != null}">
	<div>
		<input type="text" placeholder="New Username Here">
	</div>	
</c:if>
<c:if test="${changePassword != null}">
	<div>
		<input type="password" placeholder="Input old password">
		<input type="password" placeholder="Input new password">	
	</div>
</c:if>	
<c:if test="${addBalance != null}">
	<div>
		<input type="text" placeholder="Your balance">	
	</div>
</c:if>


</ul>
</div>
</body>
</html>