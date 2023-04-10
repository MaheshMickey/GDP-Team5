package controllers;

import java.sql.SQLIntegrityConstraintViolationException;
import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.User;
import services.UserService;

@WebServlet(name = "UserController", urlPatterns = { "/user/*" })
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService = new UserService();

	protected void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {

			if (!request.getPathInfo().trim().equals("/")) {
				String[] path = request.getPathInfo().split("/");
				String action = path[1];
				if ("login".equals(action)) {
					handleUserLoginGet(request, response);
				} else if ("signup".equals(action)) {
					handleUserSignupGet(request, response);
				} else if ("reset-password".equals(action)) {
					resetPasswordGet(request, response);
				} else if ("logout".equals(action)) {
					logoutGet(request, response);
				} else {
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
				}
			} else {
				response.sendRedirect(request.getContextPath() + "/");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			if (!request.getPathInfo().trim().equals("/")) {
				String[] path = request.getPathInfo().split("/");
				String action = path[1];
				if ("login".equals(action)) {
					handleUserLoginPost(request, response);
				} else if ("signup".equals(action)) {
					handleUserSignupPost(request, response);
				} else if ("reset-password".equals(action)) {
					resetPasswordPost(request, response);
				} else {
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
				}
			} else {
				response.sendRedirect(request.getContextPath() + "/");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// User Login
	private void handleUserLoginGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		response.sendRedirect(request.getContextPath() + "/");
	}

	private void handleUserLoginPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			User user = userService.authenticateUser(email, password);
			if (user != null) {
				HttpSession session = request.getSession();
				session.setAttribute("userId", user.getUserId());
				session.setAttribute("username", user.getFirstName() + " " + user.getLastName());
				session.setAttribute("userType", user.getUserType());
				response.sendRedirect(request.getContextPath() + "/dashboard/");
			} else {
				request.setAttribute("status", "Invalid credentials. Please try again.");
				request.getRequestDispatcher("/views/html/index.jsp").forward(request, response);
			}

		} catch (Exception e) {
			throw e;
		}
	}

	// User Sign Up
	private void handleUserSignupGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/html/signup.jsp");
		dispatcher.forward(request, response);
	}

	private void handleUserSignupPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			User user = userService.saveUserDetails(request);
			if (user != null) {
				request.setAttribute("status", "Account created successfully. Please login now.");
				request.getRequestDispatcher("/").forward(request, response);
			}
		} catch (SQLIntegrityConstraintViolationException e) {
			request.setAttribute("status", "Email already exist.");
			request.getRequestDispatcher("/views/html/signup.jsp").forward(request, response);
		}
	}

	// User Password Reset
	private void resetPasswordGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/html/reset-password.jsp");
		dispatcher.forward(request, response);
	}

	private void resetPasswordPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String email = request.getParameter("email");
		User user = userService.findUserByEmail(email.trim());
		if (user == null) {
			request.setAttribute("status", "Invalid email address");
		} else {
			request.setAttribute("password", user.getPassword());
		}
		request.getRequestDispatcher("/views/html/reset-password.jsp").forward(request, response);
	}

	// User Logout
	private void logoutGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(false);
		if (session != null) {
			session.invalidate();
		}
		response.sendRedirect(request.getContextPath() + "/");
	}

}
