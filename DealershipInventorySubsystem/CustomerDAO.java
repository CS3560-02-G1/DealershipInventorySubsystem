package DealershipInventorySubsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CustomerDAO {
	public int insertCustomer(Customer customer) {
		Connection connection = null;
		String query = "INSERT IGNORE INTO Customer(firstName, lastName, phoneNumber, address, email) VALUES (?, ?, ?, ?, ?)";
		try {
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			statement.setString(1, customer.getFirstName());
			statement.setString(2, customer.getLastName());
			statement.setString(3, customer.getPhone());
			statement.setString(4, customer.getAddress());
			statement.setString(5, customer.getEmail());

			int newId = statement.executeUpdate();
			customer.setId(newId);
			return newId;
			
		} catch(SQLException e) {
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
		
		return -1;
	}
	
	public Customer getCustomerById(int id) {
		Connection connection = null;
		String query = "SELECT * FROM Customer WHERE CustomerID = ?";
		try {
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setInt(1, id);
			
			ResultSet rs = statement.executeQuery();
			
			if (!rs.next()) {
				throw new SQLException("No One Found With Query: " + query);
			}
			
			String firstName = rs.getString("firstName");
			String lastName = rs.getString("lastName");
			String phoneNumber = rs.getString("phoneNumber");
			String address = rs.getString("address");
			String email = rs.getString("email");
			
			return new Customer(firstName, lastName, phoneNumber, address, email);
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
		
		return null;
	}
}
