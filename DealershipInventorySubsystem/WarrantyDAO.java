package DealershipInventorySubsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class WarrantyDAO {
	//Inserts the warranty into the database and returns the id. Requires the warranty to have a vehicle linked.
	public Warranty insertWarranty(Warranty warranty) {
		Connection connection = null;
		String query = "INSERT INTO Warranty(type, duration, policy, price, coverageLimit, VIN) VALUES (?, ?, ?, ?, ?, ?)";
		try {
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			statement.setString(1, warranty.getType());
			statement.setInt(2, warranty.getDuration());
			statement.setString(3, warranty.getPolicy());
			statement.setDouble(4, warranty.getPrice());
			statement.setDouble(5, warranty.getCoverageLimit());
			statement.setString(6, warranty.getVehicle().getVin());

			int affectedRows = statement.executeUpdate();
			
			if (affectedRows == 0) {
				throw new SQLException("Insert Failed, No Rows Affected");
			}
			
			try (ResultSet rs = statement.getGeneratedKeys()) {
				if (rs.next()) {
					warranty.setId(rs.getInt(1));
					return warranty;
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
		
		return null; //Returns null if it fails
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
				return null;
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
	
	public List<Warranty> getAllWarranties(String vin) {
		List<Warranty> warranties = new ArrayList<>();
		
		Connection connection = null;
		String query = "SELECT * FROM Warranty WHERE VIN= ?";
		try {
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setString(1, vin);
			
			ResultSet rs = statement.executeQuery();
			
			while (rs.next()) {
				int warrantyId = rs.getInt("WarrantyID");
				String type = rs.getString("type");
				int duration = rs.getInt("duration");
				String policy = rs.getString("policy");
				double price = rs.getDouble("price");
				double coverageLimit = rs.getDouble("coverageLimit");
				VehicleDAO vehicleDAO = new VehicleDAO();
				Vehicle vehicle = vehicleDAO.getVehicleById(rs.getString("VIN"));
				
				warranties.add(new Warranty(warrantyId, type, duration, policy, price, coverageLimit, vehicle));
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
		
		return warranties;
	}
}
