package whi;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Signup
 */
@WebServlet("/Signup")
public class Signup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Signup() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		doGet(request, response);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		//out.print("Hello world");
		 String fname = request.getParameter("firstname");
         String lname = request.getParameter("lastname");
         String email = request.getParameter("email");
         String password = request.getParameter("password");
         String userType = request.getParameter("userType");
         
         try {
        	 Class.forName("com.mysql.jdbc.Driver");
        	 Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/warehousedata","root","Mahesh@1");
        	 PreparedStatement stmt = con.prepareStatement("insert into signupdetails values(?,?,?,?,?)");
        	 stmt.setString(1, fname);
        	 stmt.setString(2, lname);
        	 stmt.setString(3, email);
        	 stmt.setString(4, password);
        	 stmt.setString(5, userType);
        	 int count = stmt.executeUpdate();
        	 System.out.println(count);
        	 if(count>0) {
        		 RequestDispatcher req = request.getRequestDispatcher("index.html");
        		 request.setAttribute("status", "Success");
        		 req.forward(request, response);
        	 }
        	 else
        	 {
        		 out.println("<script>document.getElementById('signupbox').innerHTML='Sorry! Duplicate Email address';</script>");
        	 }
         }
         catch(Exception r)
         {
    		 out.println("<script>document.getElementById('signupbox').innerHTML='Sorry! Duplicate Email address';</script>" + r);

         }
	}

}
