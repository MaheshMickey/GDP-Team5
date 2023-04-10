package services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import helpers.Helper;
import models.User;

public class UserService {
	private Connection con = Helper.getJDBCConnection();
	
	public  User authenticateUser(String email,String password) throws SQLException {
		User user = null;
		Statement stmt = con.createStatement();
		ResultSet result = stmt.executeQuery("select * from users where email='" + email + "' and password='" + password + "'");
		if(result.next()) {
			user = mapUserResultSet(result);
		}
		return user;
	}
	
	public  User findUserByEmail(String email) throws SQLException {
		User user = null;
		Statement stmt = con.createStatement();
		ResultSet result = stmt.executeQuery("select * from users where email='" + email + "'");
		if(result.next()) {
			user = mapUserResultSet(result);
		}
		return user;
	}
	
	public  User findUserById(int userId) throws SQLException {
		User user = null;
		Statement stmt = con.createStatement();
		ResultSet result = stmt.executeQuery("select * from users where user_id='" + userId + "'");
		if(result.next()) {
			user = mapUserResultSet(result);
		}
		return user;
	}
	
	public List<User> getAllUsersByType(String userType) throws Exception {
		List<User> users = new ArrayList<User>();
		ResultSet res = con.createStatement().executeQuery("select * from users where user_type='" + userType + "'");
		while(res.next()) {
			users.add(mapUserResultSet(res));
		}
		return users;
	}
	
	public User saveUserDetails(HttpServletRequest request) throws Exception {
		User user = null;
		String fname = request.getParameter("firstname");
		String lname = request.getParameter("lastname");
		String email = request.getParameter("email");
		String password = request.getParameter("password");
		String userType = request.getParameter("userType");
		PreparedStatement stmt = con.prepareStatement("insert into users (first_name, last_name, email, password, user_type) values(?,?,?,?,?)");
		stmt.setString(1, fname);
		stmt.setString(2, lname);
		stmt.setString(3, email);
		stmt.setString(4, password);
		stmt.setString(5, userType);
		int count = stmt.executeUpdate();
		if(count > 0) {
			ResultSet res = con.createStatement().executeQuery("select * from users WHERE user_id = LAST_INSERT_ID()");
			if(res.next())
				user = mapUserResultSet(res);
		}
		return user;
	}
	
	public User mapUserResultSet(ResultSet result) throws SQLException {
		User user = new User();
		user.setUserId(result.getInt("user_id"));
		user.setEmail(result.getString("email"));
		user.setFirstName(result.getString("first_name"));
		user.setLastName(result.getString("last_name"));
		user.setPassword(result.getString("password"));
		user.setUserType(result.getString("user_type"));
		return user;
	}
}
