<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
	<head>
	 	<meta charset="ISO-8859-1">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
		<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.0.0/dist/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
		<title>Messages</title>
		<link rel="stylesheet" href="${pageContext.request.contextPath}/views/styles/messages.css">
		<script type="text/javascript" src="${pageContext.request.contextPath}/views/javascripts/messages.js"></script>
	</head>
	<body>
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
			<div class="main-wrapper">
				<section class="msger-list">
					<div class="msger-list-wrapper">
				      <div class="search">
				        <input type="text" placeholder="search" />
				      </div>
				      <ul class="list">
				        <li class="clearfix">
				          <img src="https://s3-us-west-2.amazonaws.com/s.cdpn.io/195612/chat_avatar_01.jpg" alt="avatar" />
				          <div class="about">
				            <div class="name">Vincent Porter</div>
				        	<div class="status">
				              <i class="fa fa-circle online"></i> online
				            </div>
				          </div>
				        </li>
				      </ul>
				    </div>
			    </section>
				<section class="msger">
				  <header class="msger-header">
				    <div class="msger-header-title">
				      <i class="fas fa-comment-alt"></i> SimpleChat
				    </div>
				    <div class="msger-header-options">
				      <span><i class="fas fa-cog"></i></span>
				    </div>
				  </header>
				
				  <main class="msger-chat">
				    <div class="msg left-msg">
				      <div
				       class="msg-img"
				       style="background-image: url(https://image.flaticon.com/icons/svg/327/327779.svg)"
				      ></div>
				
				      <div class="msg-bubble">
				        <div class="msg-info">
				          <div class="msg-info-name">BOT</div>
				          <div class="msg-info-time">12:45</div>
				        </div>
				
				        <div class="msg-text">
				          Hi, welcome to SimpleChat! Go ahead and send me a message. 😄
				        </div>
				      </div>
				    </div>
				
				    <div class="msg right-msg">
				      <div
				       class="msg-img"
				       style="background-image: url(https://image.flaticon.com/icons/svg/145/145867.svg)"
				      ></div>
				
				      <div class="msg-bubble">
				        <div class="msg-info">
				          <div class="msg-info-name">Sajad</div>
				          <div class="msg-info-time">12:46</div>
				        </div>
				
				        <div class="msg-text">
				          You can change your name in JS section!
				        </div>
				      </div>
				    </div>
				  </main>
				
				  <form class="msger-inputarea">
				    <input type="text" class="msger-input" placeholder="Enter your message...">
				    <button type="submit" class="msger-send-btn">Send</button>
				  </form>
				</section>
			</div>
		</div>
	</body>
</html>