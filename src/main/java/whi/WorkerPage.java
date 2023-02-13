package whi;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class WorkerPage
 */
@WebServlet("/WorkerPage")
public class WorkerPage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public WorkerPage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/warehousedata","root","Mahesh@1");
			Statement stmt = con.createStatement();
			PrintWriter out = response.getWriter();
			ResultSet rs = stmt.executeQuery("select product_ID,productName,quantity,upc,customerName from checkinproduct");
			StringBuilder sb = new StringBuilder();
			System.out.println(rs);
			while(rs.next())
			{
				sb.append( rs.getString("UPC")+","+ rs.getString("product_ID")+","+ rs.getString("productName")+","+rs.getString("customerName")+","+rs.getString("quantity")+"|");
			}
			response.getWriter().write(sb.toString());
		}catch(Exception e)
		{
			System.out.println("Exception"+ e.getMessage());
			e.printStackTrace();
		}
		
		String searchTerm = request.getParameter("term");
		String query = "select * from checkinproduct where customerName like '%"+ searchTerm + "%'";
		List<String> results = new ArrayList<>();
		ResultSet searchRS = null;
		Statement stmt1 = null;
		StringBuilder searchSB = new StringBuilder();
		
		try {
			stmt1 = con.createStatement();
			searchRS = stmt1.executeQuery(query);
			while(searchRS.next())
			{
				String result = searchRS.getString("UPC")+","+ searchRS.getString("product_ID")+","+searchRS.getString("productName")
				+","+searchRS.getString("customerName")+","+searchRS.getString("quantity")+"|";
				
				searchSB.append( searchRS.getString("UPC")+","+ searchRS.getString("product_ID")+","+ searchRS.getString("productName")+","+searchRS.getString("customerName")+","+searchRS.getString("quantity")+"|");

				results.add(result);
				
			}
			response.getWriter().write(searchSB.toString());
		}catch(SQLException e)
		{
			System.out.println("Exception:"+e.getMessage());
			e.printStackTrace();
		}
		finally {
			try {
			      if (searchRS != null) {
			        searchRS.close();
			      }
			      if (stmt1 != null) {
			        stmt1.close();
			      }
			    } catch (SQLException e) {
			      e.printStackTrace();
			    }
			  }
		response.setContentType("text/html");
		  response.setCharacterEncoding("UTF-8");
		  PrintWriter out = response.getWriter();
		  for (String result : results) {
		    out.println(result + "<br>");
		  }
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}
}
