package whi;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String username = request.getParameter("username");
		String userpassword = request.getParameter("password");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/warehousedata", "root",
					"Mahesh@1");
			Statement stmt = con.createStatement();
			ResultSet count = stmt.executeQuery(
					"select * from signupdetails where email='" + username + "' and userPassword='" + userpassword + "'");
			if (count.next()) {
				
				Statement stmt1 = con.createStatement();
				ResultSet result1 = stmt1.executeQuery(
						"select * from productdetails ");
				
				RequestDispatcher dispatcher = request.getRequestDispatcher("/home.html");
				request.setAttribute("status", "Success");
				
				
				request.getRequestDispatcher("home.html").include(request, response);
				ResultSetMetaData rsMetaData = result1.getMetaData();
				int count2 = rsMetaData.getColumnCount();
				for(int i =1; i<=count2; i++) {
					out.println(
							"<script>document.getElementById('details').innerHTML= " + rsMetaData.getColumnName(i) + "");
				}
				
				dispatcher.forward(request, response);
			} else {
				request.getRequestDispatcher("index.html").include(request, response);
				out.println(
						"<script>document.getElementById('loginbox').innerHTML='Sorry Invalid Credentials';</script>");
			}
		} catch (Exception r) {
			System.out.println(r.getMessage());
		}
	}

}
