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
import models.Order;
import models.Product;
import services.DashboardService;
import services.OrderService;

@WebServlet(name = "OrderController", urlPatterns = {"/orders/*"})
public class OrderController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private OrderService orderService = new OrderService();
	private DashboardService dashboardService = new DashboardService();
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			if(session == null ||  session.getAttribute("userId") == null) {
				response.sendRedirect(request.getContextPath() + "/");
				return;
			}
			if (!request.getPathInfo().trim().equals("/")) {
				String[] path = request.getPathInfo().split("/");
				String action = path[1];
				if ("history".equals(action)) {
					handleOrdersHistoryGet(request, response);
				} else if ("pending-orders".equals(action)) {
					handlePendingOrdersGet(request, response);
				} else if ("order-product".equals(action) && request.getQueryString() != null) {
					handleOrderProductGet(request, response);
				} else if("approve".equals(action) && request.getQueryString() != null) {
					handleOrdersApproveGet(request, response);
				} else {
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
				}
			} else {
				response.sendRedirect(request.getContextPath() + "/dashboard/");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			HttpSession session = request.getSession(false);
			if(session == null ||  session.getAttribute("userId") == null) {
				response.sendRedirect(request.getContextPath() + "/");
				return;
			}
			if (!request.getPathInfo().trim().equals("/")) {
				String[] path = request.getPathInfo().split("/");
				String action = path[1];
				if ("history".equals(action)) {
					handleOrdersHistoryPost(request, response);
				} else if ("order-product".equals(action)) {
					handleOrderProductPost(request, response);
				} else {
					response.sendError(HttpServletResponse.SC_NOT_FOUND);
				}
			} else {
				response.sendRedirect(request.getContextPath() + "/dashboard/");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void handleOrdersHistoryGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(false);
		String userType = (String) session.getAttribute("userType");
		int userId = (int) session.getAttribute("userId");
		List<Order> orders = orderService.getOrdersByIdAndType(userId, userType);
		request.setAttribute("orders", orders);
		request.getRequestDispatcher("/views/html/order-history.jsp").forward(request, response);
	}
	
	private void handleOrdersHistoryPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Order order = orderService.saveOrder(request);
		if(order != null) {
			request.setAttribute("status", "Order added successfully.");
			RequestDispatcher req = request.getRequestDispatcher("/views/html/order-history.jsp");
			req.forward(request, response);
		} else {
			request.setAttribute("status", "Something is not right while adding Order. Please check your inputs.");
			RequestDispatcher req = request.getRequestDispatcher("/views/html/order-history.jsp");
			req.forward(request, response);
		}
	}
	
	private void handlePendingOrdersGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(false);
		int userId = (int) session.getAttribute("userId");
		List<Order> orders = orderService.getOrdersPendingforWorker(userId);
		request.setAttribute("orders", orders);
		request.getRequestDispatcher("/views/html/order-pending.jsp").forward(request, response);
	}
	
	private void handleOrderProductGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		int productId = Integer.valueOf(request.getParameter("product_id"));
		Product product = dashboardService.getProductByProductId(productId);
		request.setAttribute("product", product);
		RequestDispatcher req = request.getRequestDispatcher("/views/html/order-product.jsp");
		req.forward(request, response);
	}
	
	private void handleOrderProductPost(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Order order = orderService.saveOrder(request);
		if(order != null) {
			Product product = dashboardService.getProductByProductId(order.getProductId());
			request.setAttribute("status", "Order added successfully.");
			request.setAttribute("product", product);
			RequestDispatcher req = request.getRequestDispatcher("/views/html/order-product.jsp");
			req.forward(request, response);
		} else {
			Product product = dashboardService.getProductByProductId(Integer.parseInt(request.getParameter("product_id")));
			request.setAttribute("status", "Something is not right while adding Order. Please check your inputs.");
			request.setAttribute("product", product);
			RequestDispatcher req = request.getRequestDispatcher("/views/html/order-product.jsp");
			req.forward(request, response);
		}
		
	}
	
	private void handleOrdersApproveGet(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(false);
		int userId = (int) session.getAttribute("userId");
		int workerId = Integer.valueOf(request.getParameter("worker_id"));
		if(workerId == userId) {
			int orderId = Integer.valueOf(request.getParameter("order_id"));
			if(orderService.approveOrder(userId, orderId)) {
				request.setAttribute("status", "Order approved successfully.");
			} else {
				request.setAttribute("status", "Something went worng while updating the approval status.");
			}
			request.getRequestDispatcher("/views/html/order-pending.jsp").forward(request, response);
		} else {
			response.sendRedirect(request.getContextPath() + "/dashboard/");
		}
		
	}
	
}
