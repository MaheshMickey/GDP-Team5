package helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Helper {
	private static Connection jdbcConnection;

	@SuppressWarnings("finally")
	public static Connection getJDBCConnection() {
		try {
			if (jdbcConnection == null) {
				Class.forName("com.mysql.jdbc.Driver");
				jdbcConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/warehousedata", "root", "Mahesh@1");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return jdbcConnection;
		}
	}
	
	public static String getDateAndTime() {
		SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
		Date date = new Date(System.currentTimeMillis());
		return formatter.format(date);
	}
	
}
