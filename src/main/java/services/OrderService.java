package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import helpers.Helper;
import models.Order;
import models.Product;
import models.User;

public class OrderService {
	private Connection con = Helper.getJDBCConnection();
	private UserService userService = new UserService();
	private DashboardService dashboardService = new DashboardService();
	
	public Order saveOrder(HttpServletRequest request) throws Exception {
		Order orders = null;
		HttpSession session = request.getSession(false);
		int productId = Integer.parseInt(request.getParameter("product_id"));
		int ownerId = (int) session.getAttribute("userId");
		int quantity = Integer.parseInt(request.getParameter("quantity_req"));
		Product product = dashboardService.getProductByProductId(productId);
		if(quantity > product.getQuantity() || quantity < 0) {
			return null;
		} else {
			dashboardService.updateProductQuantityById(productId, product.getQuantity()-quantity);
		}
		String timestamp = Helper.getDateAndTime();
		int workerId = Integer.parseInt(request.getParameter("worker_id"));
		String clientName = request.getParameter("client_name");
		String clientAddress = request.getParameter("client_address");
		PreparedStatement stmt = con.prepareStatement("insert into orders (product_id, owner_id, quantity, approval, timestamp, worker_id, client_name, client_address) values(?, ?, ?, default,?, ?, ?, ?)");
		stmt.setInt(1, productId);
		stmt.setInt(2, ownerId);
		stmt.setInt(3, quantity);
		stmt.setString(4, timestamp);
		stmt.setInt(5, workerId);
		stmt.setString(6, clientName);
		stmt.setString(7, clientAddress);
		int count = stmt.executeUpdate();
		if(count > 0) {
			ResultSet res = con.createStatement().executeQuery("select * from orders WHERE order_id = LAST_INSERT_ID()");
			if(res.next())
				orders = mapOrderResultSet(res);
		}
		return orders;
	}
	
	public List<Order> getOrdersByIdAndType(int userId, String userType) throws Exception {
		List<Order> orders = new ArrayList<Order>();
		ResultSet res = null;
		if(userType.equals("Owner")) {
			res = con.createStatement().executeQuery("select * from orders where owner_id =" + userId);
		} else {
			// Only select approved Order to show in worker history
			res = con.createStatement().executeQuery("select * from orders where worker_id =" + userId + " and approval = " + true);
		}
		while(res.next()) {
			orders.add(mapOrderResultSet(res));
		}
		return orders;
	}
	
	public List<Order> getOrdersPendingforWorker(int workerId) throws Exception {
		List<Order> orders = new ArrayList<Order>();
		ResultSet res = con.createStatement().executeQuery("select * from orders where worker_id =" + workerId + " and approval = " + false);
		while(res.next()) {
			orders.add(mapOrderResultSet(res));
		}
		return orders;
	}
	
	public boolean approveOrder(int workerId, int orderId) throws Exception {
		String sql = "UPDATE orders SET approval = ? WHERE worker_id = ? and order_id = ?";
		PreparedStatement statement = con.prepareStatement(sql);
		statement.setBoolean(1, true);
		statement.setInt(2, workerId);
		statement.setInt(3, orderId);
		int result = statement.executeUpdate();
		if(result > 0) {
			return true;
		}
		return false;
	}
	
	public Order mapOrderResultSet(ResultSet result) throws Exception {
		Order order = new Order();
		order.setOrderId(result.getInt("order_id"));
		order.setProductId(result.getInt("product_id"));
		Product product = dashboardService.getProductByProductId(order.getProductId());
		order.setProductName(product.getProductName());
		order.setOwnerId(result.getInt("owner_id"));
		order.setQuantity(result.getInt("quantity"));
		order.setWorkerId(result.getInt("worker_id"));
		order.setApproval(result.getBoolean("approval"));
		order.setTimestamp(result.getString("timestamp"));
		order.setClientName(result.getString("client_name"));
		order.setClientAddress(result.getString("client_address"));
		User user = userService.findUserById(order.getOwnerId());
		order.setOwnerName(user.getFirstName() + " " + user.getLastName());
		user = userService.findUserById(order.getWorkerId());
		order.setWorkerName(user.getFirstName() + " " + user.getLastName());
		return order;
	}
	
}
