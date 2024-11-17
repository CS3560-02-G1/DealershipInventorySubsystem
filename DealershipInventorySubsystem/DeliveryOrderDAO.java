package DealershipInventorySubsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DeliveryOrderDAO {
	public DeliveryOrder insertDeliveryOrder(DeliveryOrder order) {
		Connection connection = null;
		String query = "INSERT INTO DeliveryOrder(sourceLocation, status, arrivalDate) VALUES (?, ?, ?)";
		try {
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setString(1, order.getSourceLocation());
			statement.setString(2, order.getStatus());
			statement.setString(3, order.getArrivalDate());

			int affectedRows = statement.executeUpdate();
			
			if (affectedRows == 0) {
				throw new SQLException("Insert Failed, No Rows Affected");
			}
			
			try (ResultSet rs = statement.getGeneratedKeys()) {
				if (rs.next()) {
					order.setId(rs.getInt(1));
					return order;
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
	
	public DeliveryOrder getOrderById(int id) {
		Connection connection = null;
		String query = "SELECT * FROM DeliveryOrder WHERE DeliveryID = ?";
		try {
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setInt(1, id);
			
			ResultSet rs = statement.executeQuery();
			
			if (!rs.next()) {
				return null;
			}
			
			String sourceLocation = rs.getString("sourceLocation");
			String status = rs.getString("status");
			String arrivalDate = rs.getString("arrivalDate");
			
			return new DeliveryOrder(id, sourceLocation, status, arrivalDate);
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
