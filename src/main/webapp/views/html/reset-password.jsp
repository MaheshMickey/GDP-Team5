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
			<h1>Get your password</h1>
			<form action="${pageContext.request.contextPath}/user/reset-password" method="post">
				<p>Your Email</p>
				<input type="email" name="email" placeholder="Enter Email">
				<input type="submit" name="submit" value="Get Password">
			</form>
			<a href="${pageContext.request.contextPath}/user/login">Login</a>
			<br>
			<a href="${pageContext.request.contextPath}/user/signup">Sign Up</a>
			<div id="status">
				<% if (request.getAttribute("status") != null) { %>
				<p><%= request.getAttribute("status") %></p>
				<% } %>
			</div>
			<div id="password">
				<% if (request.getAttribute("password") != null) { %>
				<p>Your password is: <%= request.getAttribute("password") %></p>
				<% } %>
			</div>
		</div>
	</body>
</html>