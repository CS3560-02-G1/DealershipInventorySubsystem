package DealershipInventorySubsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.w3c.dom.UserDataHandler;

public class CustomerDAO {
	public Customer insertCustomer(Customer customer) {
		Connection connection = null;
		String query = "INSERT INTO Customer(firstName, lastName, phoneNumber, address, email) VALUES (?, ?, ?, ?, ?)";
		try {
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			statement.setString(1, customer.getFirstName());
			statement.setString(2, customer.getLastName());
			statement.setString(3, customer.getPhone());
			statement.setString(4, customer.getAddress());
			statement.setString(5, customer.getEmail());

			int affectedRows = statement.executeUpdate();
			
			if (affectedRows == 0) {
				throw new SQLException("Insert Failed, No Rows Affected");
			}
			
			try (ResultSet rs = statement.getGeneratedKeys()) {
				if (rs.next()) {
					customer.setId(rs.getInt(1));
					return customer;
				} else {
					throw new SQLException("Creation Failed, no ID obtained");
				}
			}
			
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
		
		return null;
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
				return null;
			}
			
			String firstName = rs.getString("firstName");
			String lastName = rs.getString("lastName");
			String phoneNumber = rs.getString("phoneNumber");
			String address = rs.getString("address");
			String email = rs.getString("email");
			
			return new Customer(id, firstName, lastName, phoneNumber, address, email);
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
