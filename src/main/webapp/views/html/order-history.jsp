<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
	    <meta charset="ISO-8859-1">
	    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
		<title>Order History</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/views/styles/order-history.css">
		<script>
			window.addEventListener('DOMContentLoaded', (event) => {
				document.getElementById("search-input").addEventListener(`keyup`, function (e) {
				    const term = e.target.value.toLowerCase();
				    const tableRows = document.querySelectorAll('#order-table tbody tr');
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
		<%@ page import="models.Order" %>
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
			        <a class="nav-link" href="${pageContext.request.contextPath}/dashboard/addProduct">Add Products</a>
			      </li>
			      <li class="nav-item">
			        <a class="nav-link" href="${pageContext.request.contextPath}/orders/pending-orders">Pending Orders</a>
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
			<h1 class="main-heading">Order History</h1>
			<table id="order-table">
				<thead>
					<tr>
						<th>Order ID</th>
						<th>Product Name</th>
						<% if(userType.equals("Worker")) { %>
							<th>Owner Name</th>
						<% } else { %>
							<th>Worker Name</th>
						<% } %>
						<th>Quantity</th>
						<th>Client Name</th>
						<th>Client Address</th>
						<th>Time/Date</th>
						<th>Approval</th>
					</tr>
				</thead>
				<tbody>
					<%	List<Order> orders = (List<Order>) request.getAttribute("orders");
			            if (orders != null) {
			                for (Order order : orders) {	%>
								<tr>
									<td><%= order.getOrderId() %></td>
									<td><%= order.getProductName() %></td>
									<% if(userType.equals("Worker")) { %>
										<td><%= order.getOwnerName() %></td>
									<% } else { %>
										<td><%= order.getWorkerName() %></td>
									<% } %>
									<td><%= order.getQuantity() %></td>
									<td><%= order.getClientName() %></td>
									<td><%= order.getClientAddress() %></td>
									<td><%= order.getTimestamp() %></td>
									<td><%= order.getApproval() ? "Approved" : "Not Approved" %></td>
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