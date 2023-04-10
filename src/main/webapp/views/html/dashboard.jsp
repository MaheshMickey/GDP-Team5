<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
	    <meta charset="ISO-8859-1">
	    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
		<title>Dashboard</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/views/styles/dashboard.css">
		<script>
			window.addEventListener('DOMContentLoaded', (event) => {
				document.getElementById("search-input").addEventListener(`keyup`, function (e) {
				    const term = e.target.value.toLowerCase();
				    const tableRows = document.querySelectorAll('#product-table tbody tr');
				    if (tableRows) {
				    	tableRows.forEach((row) => {
				        const text = row.textContent.toLocaleLowerCase();
				        if (text.includes(term)) {
				          row.classList.remove('hidden');
				        } else {
				          row.classList.add('hidden');
				        }
				      });
				    }
				 });
			}); 
		</script>
	</head>
	<body>
		<%@ page import="java.util.List" %>
		<%@ page import="models.Product" %>
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
		      <% String userType = (String) session.getAttribute("userType"); 
		      	if(userType.equals("Worker")) {  %>
			      <li class="nav-item">
			        <a class="nav-link" href="${pageContext.request.contextPath}/dashboard/addProduct">Check-In Product</a>
			      </li>
			      <li class="nav-item">
			        <a class="nav-link" href="${pageContext.request.contextPath}/orders/pending-orders">Order Requests</a>
			      </li>
			  <% } %>
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
		    <form class="form-inline my-2 my-lg-0">
		      <input class="form-control mr-sm-2" type="search" placeholder="Search" id="search-input">
		    </form>
		  </div>
		</nav>
		<div class="container">
			<% if(userType.equals("Worker")) {  %>
				<h1 class="main-heading">Inventory Products</h1>
			<% } else { %>
				<h1 class="main-heading">Inventory Products</h1>
			<% } %>
			<table id="product-table">
				<thead>
					<tr>
						<th>Product ID</th>
						<th>Product Name</th>
						<th>Quantity</th>
						<th>Description</th>
						<th>UPC code</th>
						<% if(userType.equals("Owner")) { %>
							<th>Worker Name</th>
							<th>Click To Order</th>
						<% } else { %>
							<th>Owner Name</th>
							<th>Update Data</th>
						<% } %>
					</tr>
				</thead>
				<tbody>
					<%	List<Product> products = (List<Product>) request.getAttribute("products");
			            if (products != null) {
			                for (Product product : products) {	%>
								<tr>
									<td><%= product.getProductId() %></td>
									<td><%= product.getProductName() %></td>
									<td><%= product.getQuantity() %></td>
									<td><%= product.getDescription() %></td>
									<td><%= product.getUpcCode() %></td>
									<% if(userType.equals("Worker")) { %>
										<td><%= product.getOwnerName() %></td>
										<td><a href="${pageContext.request.contextPath}/dashboard/update-product?product_id=<%= product.getProductId() %>">Update</a></td>
									<% } else { %>
										<td><%= product.getWorkerName() %></td>
										<td><a href="${pageContext.request.contextPath}/orders/order-product?product_id=<%= product.getProductId() %>">Order</a></td>
									<% } %>
								</tr>
							<% } 
						} %>
				</tbody>
			</table>
		</div>
		<script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
	    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
	    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
	</body>
</html>