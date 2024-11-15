package DealershipInventorySubsystem;
import java.sql.*;

public class JDBCMySQLConnection {
	//STEPS TO INSTALL:
	//1) Install https://dev.mysql.com/downloads/connector/j/
	//2) Depends on IDE, but I clicked on the project -> properties -> java build path -> Add external jars -> choose the downloaded jar
	private static JDBCMySQLConnection instance = new JDBCMySQLConnection();
	public static final String DRIVER_CLASS = "com.mysql.cj.jdbc.Driver";
	public static final String URL = "jdbc:mysql://localhost:3306/InventorySubsystem";
	public static final String USERNAME = "root";
	public static final String PASSWORD = "root";
		
	//Private Constructer to load the MySQL Java Driver
	private JDBCMySQLConnection() {
		try {
			Class.forName(DRIVER_CLASS);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//Establish the MySQL Connection
	private Connection createConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (SQLException e) {
			System.out.println("ERROR: Unable to connect to database.");
			e.printStackTrace();
		}
		
		return connection;
	}
	
	public static Connection getConnection() {
		return instance.createConnection();
	}
	
	public static ResultSet getResultSet(String query) {
		ResultSet rs = null;
		Connection connection = null;
		Statement statement = null;

		try {
			connection = JDBCMySQLConnection.getConnection();
			statement = connection.createStatement();
			rs = statement.executeQuery(query);
			
			if (!rs.next()) {
				throw new SQLException("No One Found With Query: " + query);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		return rs;
	}
}
