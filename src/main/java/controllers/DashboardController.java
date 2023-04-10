package controllers;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Product;
import models.User;
import services.DashboardService;
import services.UserService;

@WebServlet(name = "DashboardController", urlPatterns = {"/dashboard/*"})
public class DashboardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DashboardService dashboardService = new DashboardService();
	private UserService userService = new UserService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			if(session == null ||  session.getAttribute("userId") == null) {
				response.sendRedirect(request.getContextPath() + "/");
				return;
			}
			
			if(!request.getPathInfo().trim().equals("/")) {
				String[] path = request.getPathInfo().split("/");
				String action = path[1];
				if ("addProduct".equals(action)) {
					handleAddProductGet(request, response);
				} else if ("update-product".equals(action)  && request.getQueryString() != null) {
					handleUpdateProductGet(request, response);
				} else {
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
				}
			} else {
				String userType = (String) session.getAttribute("userType");
				List<Product> products = null;
				if(userType.equals("Worker")) {
					products = dashboardService.getProductsByIdAndType((int) session.getAttribute("userId"), "Worker");
				} else {
					products = dashboardService.getProductsByIdAndType((int) session.getAttribute("userId"), "Owner");
				}
				request.setAttribute("products", products);
				request.getRequestDispatcher("/views/html/dashboard.jsp").forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			if(session == null ||  session.getAttribute("userId") == null) {
				response.sendRedirect(request.getContextPath() + "/");
				return;
			}
			if(!request.getPathInfo().trim().equals("/")) {
				String[] path = request.getPathInfo().split("/");
				String action = path[1];
				if ("addProduct".equals(action)) {
					handleAddProductPost(request, response);
				}  else if ("update-product".equals(action)) {
					handleUpdateProductPost(request, response);
				}	else {
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
				}
			} else {
				response.sendRedirect(request.getContextPath() + "/dashboard/");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Products
	private void handleAddProductGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		List<User> users = userService.getAllUsersByType("Owner");
		request.setAttribute("users", users);
		RequestDispatcher req = request.getRequestDispatcher("/views/html/add-product.jsp");
		req.forward(request, response);
	}
	
	private void handleAddProductPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Product product = dashboardService.saveProduct(request);
		List<User> users = userService.getAllUsersByType("Owner");
		request.setAttribute("users", users);
		if(product != null) {
			response.sendRedirect(request.getContextPath() + "/dashboard/");
		} else {
			request.setAttribute("status", "Something is not right while adding product. Please check your inputs.");
			RequestDispatcher req = request.getRequestDispatcher("/views/html/add-product.jsp");
			req.forward(request, response);
		}
		
	}
	
	// Update Products
	private void handleUpdateProductGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int productId = Integer.valueOf(request.getParameter("product_id"));
		Product product = dashboardService.getProductByProductId(productId);
		request.setAttribute("product", product);
		RequestDispatcher req = request.getRequestDispatcher("/views/html/update-product.jsp");
		req.forward(request, response);
	}
	
	private void handleUpdateProductPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Product product = dashboardService.updateProduct(request);
		if(product != null) {
			request.setAttribute("product", product);
			request.setAttribute("status", "Product updated successfully.");
			RequestDispatcher req = request.getRequestDispatcher("/views/html/update-product.jsp");
			req.forward(request, response);
		} else {
			request.setAttribute("status", "Something is not right while updating the product. Please check your inputs.");
			RequestDispatcher req = request.getRequestDispatcher("/views/html/update-product.jsp");
			req.forward(request, response);
		}
		
	}
}
