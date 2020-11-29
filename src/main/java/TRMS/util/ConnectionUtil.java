package TRMS.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {

	private Connection conn;
	
	private final static String USERNAME = System.getenv("DATA_USERNAME");
	private final static String PASSWORD = System.getenv("DATA_PASSWORD");

	public Connection createConnection() throws SQLException {
		
		Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres", USERNAME, PASSWORD);
		return conn;
	}
	
	public ConnectionUtil() {
		
	}

}
