package DealershipInventorySubsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeliveryOrderDAO {
	public void insertDeliveryOrder(DeliveryOrder order) {
		Connection connection = null;
		String query = "INSERT IGNORE INTO DeliveryOrder(sourceLocation, status, arrivalDate) VALUES (?, ?, ?)";
		try {
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setString(1, order.getSourceLocation());
			statement.setString(2, order.getStatus());
			statement.setString(3, order.getArrivalDate());

			statement.executeUpdate();
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
	}
	
	public DeliveryOrder getOrderById(int id) {
		Connection connection = null;
		String query = "SELECT * FROM DeliveryOrder WHERE DeliveryID = ?";
		try {
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setInt(1, id);
			
			ResultSet rs = statement.executeQuery();
			
			if (!rs.next()) {
				throw new SQLException("No One Found With Query: " + query);
			}
			
			String sourceLocation = rs.getString("sourceLocation");
			String status = rs.getString("status");
			String arrivalDate = rs.getString("arrivalDate");
			
			return new DeliveryOrder(sourceLocation, status, arrivalDate);
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
