package DealershipInventorySubsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class WarrantyDAO {
	//Inserts the warranty into the database and returns the id. Requires the warranty to have a vehicle linked.
	public int insertWarranty(Warranty warranty) {
		Connection connection = null;
		String query = "INSERT IGNORE INTO Warranty(type, duration, policy, price, coverageLimit, VIN) VALUES (?, ?, ?, ?, ?, ?)";
		try {
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			statement.setString(1, warranty.getType());
			statement.setInt(2, warranty.getDuration());
			statement.setString(3, warranty.getPolicy());
			statement.setDouble(4, warranty.getPrice());
			statement.setDouble(5, warranty.getCoverageLimit());
			statement.setString(6, warranty.getVehicle().getVin());

			int newId = statement.executeUpdate();
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
		
		return -1; //Returns -1 as id if it fails
	}
	
	public Warranty getWarrantyById(int id) {
		Connection connection = null;
		String query = "SELECT * FROM Warranty WHERE WarrantyID = ?";
		try {
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setInt(1, id);
			
			ResultSet rs = statement.executeQuery();
			
			if (!rs.next()) {
				throw new SQLException("No One Found With Query: " + query);
			}
			
			String type = rs.getString("type");
			int duration = rs.getInt("duration");
			String policy = rs.getString("policy");
			double price = rs.getDouble("price");
			double coverageLimit = rs.getDouble("coverageLimit");
			VehicleDAO vehicleDAO = new VehicleDAO();
			Vehicle vehicle = vehicleDAO.getVehicleById(rs.getString("VIN"));
			
			return new Warranty(id, type, duration, policy, price, coverageLimit, vehicle);
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
