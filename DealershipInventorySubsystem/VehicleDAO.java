package DealershipInventorySubsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VehicleDAO {
	
	public void insertVehicle(Vehicle vehicle) {
		Connection connection = null;
		String query = "INSERT IGNORE INTO Vehicle(vin, model, make, year, color, status, currentCondition, price) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setString(1, vehicle.getVin());
			statement.setString(2, vehicle.getModel());
			statement.setString(3, vehicle.getMake());
			statement.setInt(4, vehicle.getYear());
			statement.setString(5, vehicle.getColor());
			statement.setString(6, vehicle.getStatus());
			statement.setString(7, vehicle.getCondition());
			statement.setDouble(8, vehicle.getPrice());
			
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
	
	public Vehicle getVehicleById(String vin) {
		Connection connection = null;
		String query = "SELECT * FROM Vehicle WHERE VIN=?";
		try {
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setString(1, vin);
			
			ResultSet rs = statement.executeQuery();
			
			if (!rs.next()) {
				throw new SQLException("No One Found With Query: " + query);
			}
			
			String model = rs.getString("model");
			int year = rs.getInt("year");
			String status = rs.getString("status");
			String condition = rs.getString("currentCondition");
			String make = rs.getString("make");
			String color = rs.getString("color");
			Double price = rs.getDouble("price");
			
			return new Vehicle(vin, model, year, status, condition, make, color, price);
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
	
	public void removeVehicle(String vin) {
		Connection connection = null;
		String query = "DELETE FROM Vehicle WHERE VIN=?";
		try {
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setString(1, vin);
			
			int rs = statement.executeUpdate();
			
			if (rs == 0) {
				throw new SQLException("Delete Failed");
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
		
	}
}
