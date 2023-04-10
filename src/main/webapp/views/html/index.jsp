<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
	    <meta charset="ISO-8859-1">
	    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	   	<title>Login</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/views/styles/index.css">
	</head>
	<body>
		<div class="loginbox" id="">
			<img src="${pageContext.request.contextPath}/views/images/avatar2.png" class="avatar" alt="img-avatar" width=10px height=10px>
			<h1>Login Here</h1>
			<form action="${pageContext.request.contextPath}/user/login" method="post">
				<p>Email</p>
				<input type="email" name="email" placeholder="Enter Email">
				<p>Password</p>
				<input type="password" name="password" placeholder="Enter Password">
				<input type="submit" name="submit" value="Login">
			</form>
			<a href="${pageContext.request.contextPath}/user/reset-password">Forgot Your Password?</a>
			<br>
			<a href="${pageContext.request.contextPath}/user/signup">Sign Up</a>
			<div id="status">
				<% if (request.getAttribute("status") != null) { %>
				<p><%= request.getAttribute("status") %></p>
				<% } %>
			</div>
		</div>
	</body>
</html>