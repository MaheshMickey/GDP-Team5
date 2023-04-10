package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import helpers.Helper;
import models.Product;
import models.User;

public class DashboardService {
	private Connection con = Helper.getJDBCConnection();
	private UserService userService = new UserService();
	
	public Product saveProduct(HttpServletRequest request) throws Exception {
		Product product = null;
		HttpSession session = request.getSession(false);
		int workerId = (int) session.getAttribute("userId");
		String productName = request.getParameter("product_name");
		String upc = request.getParameter("upc");
		int qty = Integer.parseInt(request.getParameter("quantity"));
		String desc = request.getParameter("description");
		int ownerId = Integer.parseInt(request.getParameter("owner_id"));
		PreparedStatement stmt = con.prepareStatement("insert into products (product_name, worker_id, quantity, description, upc_code, owner_id) values(?,?,?,?,?,?)");
		stmt.setString(1, productName);
		stmt.setInt(2, workerId);
		stmt.setInt(3, qty);
		stmt.setString(4, desc);
		stmt.setString(5, upc);
		stmt.setInt(6, ownerId);
		int count = stmt.executeUpdate();
		if(count > 0) {
			ResultSet res = con.createStatement().executeQuery("select * from products WHERE product_id = LAST_INSERT_ID()");
			if(res.next())
				product = mapProductResultSet(res);
		}
		return product;
	}
	
	public Product updateProduct(HttpServletRequest request) throws Exception {
		Product product = null;
		int productId = Integer.parseInt(request.getParameter("product_id"));
		String productName = request.getParameter("product_name");
		String upc = request.getParameter("upc");
		int qty = Integer.parseInt(request.getParameter("quantity"));
		String desc = request.getParameter("description");
		PreparedStatement stmt = con.prepareStatement("update products SET product_name = ?, quantity = ?, description = ?, upc_code =? WHERE product_id = ?");
		stmt.setString(1, productName);
		stmt.setInt(2, qty);
		stmt.setString(3, desc);
		stmt.setString(4, upc);
		stmt.setInt(5, productId);
		int count = stmt.executeUpdate();
		if(count > 0) {
			ResultSet res = con.createStatement().executeQuery("select * from products WHERE product_id = "+ productId);
			if(res.next())
				product = mapProductResultSet(res);
		}
		return product;
	}
	
	public void updateProductQuantityById(int productId, int quantity) throws Exception {
		con.createStatement().executeUpdate("update products SET quantity = " + quantity +"  WHERE product_id = "+ productId);
	}
	
	public List<Product> getAllProducts() throws Exception {
		List<Product> product = new ArrayList<Product>();
		ResultSet res = con.createStatement().executeQuery("select * from products");
		while(res.next()) {
			product.add(mapProductResultSet(res));
		}
		return product;
	}
	
	public List<Product> getProductsByIdAndType(int userId, String userType) throws Exception {
		List<Product> product = new ArrayList<Product>();
		String query = "";
		if(userType.equals("Worker")) {
			query = "select * from products where worker_id =" + userId;
		} else {
			query = "select * from products where owner_id =" + userId;
		}
		ResultSet res = con.createStatement().executeQuery(query);
		while(res.next()) {
			product.add(mapProductResultSet(res));
		}
		return product;
	}
	
	public Product getProductByProductId(int productId) throws Exception {
		Product product = null;
		ResultSet res = con.createStatement().executeQuery("select * from products where product_id =" + productId);
		while(res.next()) {
			product = mapProductResultSet(res);
		}
		return product;
	}
	
	public Product mapProductResultSet(ResultSet result) throws SQLException {
		Product product = new Product();
		product.setProductId(result.getInt("product_id"));
		product.setProductName(result.getString("product_name"));
		product.setWorkerId(result.getInt("worker_id"));
		product.setQuantity(result.getInt("quantity"));
		product.setDescription(result.getString("description"));
		product.setUpcCode(result.getString("upc_code"));
		product.setOwnerId(result.getInt("owner_id"));
		User user = userService.findUserById(product.getOwnerId());
		product.setOwnerName(user.getFirstName() + " " + user.getLastName());
		user = userService.findUserById(product.getWorkerId());
		product.setWorkerName(user.getFirstName() + " " + user.getLastName());
		return product;
	}
}
