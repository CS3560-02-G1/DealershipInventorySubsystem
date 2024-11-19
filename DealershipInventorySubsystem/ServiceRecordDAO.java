package DealershipInventorySubsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ServiceRecordDAO {
	VehicleDAO vehicleDAO;
	MaintenanceDAO maintenanceDAO;
	
	public ServiceRecordDAO() {
		vehicleDAO = new VehicleDAO();
		maintenanceDAO = new MaintenanceDAO();
	}
	
	public ServiceRecord insertServiceRecord(ServiceRecord record) {
		Connection connection = null;
		String query = "INSERT INTO ServiceRecord(date, price, status, VIN, MaintenanceID) VALUES (?, ?, ?, ?, ?)";
		try {
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			statement.setString(1, record.getDate());
			statement.setDouble(2, record.getPrice());
			statement.setString(3, record.getStatus());
			statement.setString(4, record.getVehicle().getVin());
			statement.setInt(5, record.getMaintenance().getId());

			int affectedRows = statement.executeUpdate();
			
			if (affectedRows == 0) {
				throw new SQLException("Insert Failed, No Rows Affected");
			}
			
			try (ResultSet rs = statement.getGeneratedKeys()) {
				if (rs.next()) {
					record.setId(rs.getInt(1));
					return record;
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
	
	public ServiceRecord getServiceRecordById(int id) {
		Connection connection = null;
		String query = "SELECT * FROM ServiceRecord WHERE RecordID = ?";
		try {
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setInt(1, id);
			
			ResultSet rs = statement.executeQuery();
			
			if (!rs.next()) {
				return null;
			}
			
			int recordId = rs.getInt("RecordID");
			String date = rs.getString("date");
			Double price = rs.getDouble("price");
			String status = rs.getString("status");

			Vehicle vehicle = vehicleDAO.getVehicleById(rs.getString("VIN"));
			
			Maintenance maintenance = maintenanceDAO.getMaintenanceById(rs.getInt("MaintenanceID"));
			
			return new ServiceRecord(recordId, date, price, status, vehicle, maintenance);
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
	
	public List<ServiceRecord> getAllServiceRecordsForVehicle(Vehicle vehicle) {
		List<ServiceRecord> serviceRecords = new ArrayList<>();
		Connection connection = null;
		String query = "SELECT * FROM ServiceRecord WHERE VIN = ?";
		try {
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, vehicle.getVin());
			
			ResultSet rs = statement.executeQuery();
			
			while (rs.next()) {
				int recordId = rs.getInt("RecordID");
				String date = rs.getString("date");
				Double price = rs.getDouble("price");
				String status = rs.getString("status");
				
				Maintenance maintenance = maintenanceDAO.getMaintenanceById(rs.getInt("MaintenanceID"));
				
				serviceRecords.add(new ServiceRecord(recordId, date, price, status, vehicle, maintenance));
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
		
		return serviceRecords;
	}
	
	public ServiceRecord updateServiceRecord(ServiceRecord newRecord) {
		Connection connection = null;
		String query = "UPDATE ServiceRecord SET VIN = ?, MaintenanceID = ?, date = ?, price = ?, status = ? WHERE RecordID = ?";
		try {
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setString(1, newRecord.getVehicle().getVin());
			statement.setInt(2, newRecord.getMaintenance().getId());
			statement.setString(3, newRecord.getDate());
			statement.setDouble(4, newRecord.getPrice());
			statement.setString(5, newRecord.getStatus());
			statement.setInt(6, newRecord.getId());
			
			statement.executeUpdate();
			return newRecord;
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
	public boolean removeServiceRecord(ServiceRecord record) {
		Connection connection = null;
		String query = "DELETE FROM ServiceRecord WHERE RecordID = ?";
		try {
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setInt(1, record.getId());
			
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
