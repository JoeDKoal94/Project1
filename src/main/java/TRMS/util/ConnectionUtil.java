package TRMS.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

	private Connection conn;
	
	private final static String USERNAME = System.getenv("DATA_USERNAME");
	private final static String PASSWORD = System.getenv("DATA_PASSWORD");
	private final static String URL = System.getenv("DATA_URL");

	public Connection createConnection() throws SQLException {
		
		Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		return conn;
	}
	
	public ConnectionUtil() {
		
	}

}
