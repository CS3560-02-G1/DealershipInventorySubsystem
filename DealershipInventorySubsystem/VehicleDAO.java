package DealershipInventorySubsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAO {
	
	public Vehicle insertVehicle(Vehicle vehicle) {
		Connection connection = null;
		String query = "INSERT INTO Vehicle(vin, model, make, year, color, status, currentCondition, price) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
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
			return vehicle;
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
	
	public Vehicle getVehicleById(String vin) {
		Connection connection = null;
		String query = "SELECT * FROM Vehicle WHERE VIN=?";
		try {
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setString(1, vin);
			
			ResultSet rs = statement.executeQuery();
			
			if (!rs.next()) {
				return null;
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
	
	public List<Vehicle> getAllUnsoldVehicles() {
		List<Vehicle> vehicles = new ArrayList<>();
		Connection connection = null;
		String query = "SELECT * FROM Vehicle WHERE status != 'sold'";
		try {
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			
			ResultSet rs = statement.executeQuery();
			
			while (rs.next()) {
				String vin = rs.getString("VIN");
				String model = rs.getString("model");
				int year = rs.getInt("year");
				String status = rs.getString("status");
				String condition = rs.getString("currentCondition");
				String make = rs.getString("make");
				String color = rs.getString("color");
				Double price = rs.getDouble("price");
				
				vehicles.add(new Vehicle(vin, model, year, status, condition, make, color, price));
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
		
		return vehicles;
	}
	
	public List<Vehicle> getAllSoldVehicles() {
		List<Vehicle> vehicles = new ArrayList<>();
		Connection connection = null;
		String query = "SELECT * FROM Vehicle WHERE status = 'sold'";
		try {
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			
			ResultSet rs = statement.executeQuery();
			
			while (rs.next()) {
				String vin = rs.getString("VIN");
				String model = rs.getString("model");
				int year = rs.getInt("year");
				String status = rs.getString("status");
				String condition = rs.getString("currentCondition");
				String make = rs.getString("make");
				String color = rs.getString("color");
				Double price = rs.getDouble("price");
				
				vehicles.add(new Vehicle(vin, model, year, status, condition, make, color, price));
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
		
		return vehicles;
	}
	
	//Updates a vehicle in the database with the new vehicle given. Assumes that the VIN of the vehicle is the same and already inserted.
	public Vehicle updateVehicle(Vehicle newVehicle) {
		Connection connection = null;
		String query = "UPDATE Vehicle SET model = ?, make = ?, year = ?, color = ?, status = ?, currentCondition = ?, price = ? WHERE VIN = ?";
		try {
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setString(1, newVehicle.getModel());
			statement.setString(2, newVehicle.getMake());
			statement.setInt(3, newVehicle.getYear());
			statement.setString(4, newVehicle.getColor());
			statement.setString(5, newVehicle.getStatus());
			statement.setString(6, newVehicle.getCondition());
			statement.setDouble(7, newVehicle.getPrice());
			statement.setString(8, newVehicle.getVin());
			
			statement.executeUpdate();
			return newVehicle;
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
	
	//Returns true on delete / and false on unsuccessful
	public boolean removeVehicle(String vin) {
		Connection connection = null;
		String query = "DELETE FROM Vehicle WHERE VIN=?";
		try {
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setString(1, vin);
			
			int rs = statement.executeUpdate();
			
			if (rs == 0) {
				return false;
			}
			
			return true;
			
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
		
		return false;
		
	}
}
