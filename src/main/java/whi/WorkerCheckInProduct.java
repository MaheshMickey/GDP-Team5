package whi;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class WorkerCheckInProduct
 */
@WebServlet("/WorkerCheckInProduct")
public class WorkerCheckInProduct extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WorkerCheckInProduct() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
//		String productID = request.getParameter("product_id");
		String customerName = request.getParameter("customer_name");
		String productName = request.getParameter("product_name");
		String upc = request.getParameter("upc");
		String qty = request.getParameter("quantity");
		String desc = request.getParameter("description");
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/warehousedata", "root", "Mahesh@1");
			PreparedStatement stmt = con.prepareStatement("insert into checkinproduct (productName,customerName,quantity,productDescription,UPC) values(?,?,?,?,?) ");
//			stmt.setString(1, productID);
			stmt.setString(1, productName);
			stmt.setString(2, customerName);
			stmt.setString(3, qty);
			stmt.setString(4,desc);
			stmt.setString(5, upc);
			
			try {
			int count = stmt.executeUpdate();
			}catch(SQLException e)
			{
				 e.printStackTrace();
				  System.out.println("Error executing update: " + e.getMessage());
			}
//       	out.print("Hello World!");
//	       	System.out.println(count);
//       	out.print(count);
//			if (count > 0) {
				RequestDispatcher req = request.getRequestDispatcher("workerPage.html");
				request.setAttribute("status", "Success");
				req.forward(request, response);
				out.println("<script> alert('Check In Successful!');</script>");
//			} else {
//				out.println(
//						"<script>document.getElementById('container1').innerHTML='Sorry! Please enter correct details';</script>");
//			}
		} catch (Exception e) {
			out.println(
					"<script>document.getElementById('container1').innerHTML='Sorry! Please enter correct details';</script>");
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
