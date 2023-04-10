<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
	    <meta charset="ISO-8859-1">
	    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
		<title>Add Product</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/views/styles/add-product.css">
	</head>
	<body>
		<%@ page import="java.util.List" %>
		<%@ page import="models.User" %>
		<nav class="navbar navbar-expand-lg navbar-light bg-light">
		  <a class="navbar-brand" href="#">WHI</a>
		  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo02" aria-controls="navbarTogglerDemo02" aria-expanded="false" aria-label="Toggle navigation">
		    <span class="navbar-toggler-icon"></span>
		  </button>
		  <div class="collapse navbar-collapse" id="navbarTogglerDemo02">
		    <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
		      <li class="nav-item active">
		        <a class="nav-link" href="${pageContext.request.contextPath}/dashboard/">Home</a>
		      </li>
		      <li class="nav-item">
		        <a class="nav-link" href="${pageContext.request.contextPath}/dashboard/addProduct">Check-In Product</a>
		      </li>
		      <li class="nav-item">
		        <a class="nav-link" href="${pageContext.request.contextPath}/orders/pending-orders">Order Requests</a>
		      </li>
		      <li class="nav-item dropdown">
		        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
		          <%= session.getAttribute("username") %>
		        </a>
		        <div class="dropdown-menu" aria-labelledby="navbarDropdownMenuLink">
		        	<a class="dropdown-item" href="${pageContext.request.contextPath}/messages/">Messages</a>
		        	<a class="dropdown-item" href="${pageContext.request.contextPath}/orders/history">Order History</a>
		          	<a class="dropdown-item" href="${pageContext.request.contextPath}/user/logout">Logout</a>
		        </div>
		      </li>
		    </ul>
		  </div>
		</nav>
		<div class="container">
			<h1 class="main-heading">Add Product</h1>
			<form action="${pageContext.request.contextPath}/dashboard/addProduct" method="post">
				<label for="upc">UPC:</label> 
				<input type="text" name="upc" id="upc" required>
				<br><br><br> 
				<label for="product_name">Product Name:</label> 
				<input type="text" name="product_name" id="product_name" required>
				<br><br><br>
				<label for="quantity">Quantity:</label>
				<input type="text" name="quantity" id="quantity" required>
				<br><br><br>
				<label for="description">Description:</label>
				<textarea name="description" id="description" required></textarea>
				<br><br><br>
				<label for="owner_id">Owner:</label>
				<select name="owner_id" id="owner_id" required>
					<%	List<User> users = (List<User>) request.getAttribute("users");
			            if (users != null) {
			                for (User user : users) {	%>
				  				<option value="<%= user.getUserId() %>" selected><%= user.getFirstName() + " " +  user.getLastName() %></option>
			  			<% }
					} %>
				</select>
				<br><br><br>
				<button type="submit">Add Product</button>
			</form>
		</div>
		<div id="status">
			<% if (request.getAttribute("status") != null) { %>
			<p><%= request.getAttribute("status") %></p>
			<% } %>
		</div>
		<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
	    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
	    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
	</body>
</html>