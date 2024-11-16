package DealershipInventorySubsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ServiceRecordDAO {
	public int insertServiceRecord(ServiceRecord record) {
		Connection connection = null;
		String query = "INSERT IGNORE INTO ServiceRecord(date, price, status, VIN, MaintenanceID) VALUES (?, ?, ?, ?, ?)";
		try {
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			statement.setString(1, record.getDate());
			statement.setDouble(2, record.getPrice());
			statement.setString(3, record.getStatus());
			statement.setString(4, record.getVehicle().getVin());
			statement.setInt(5, record.getMaintenance().getId());

			return statement.executeUpdate();
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
	
	public ServiceRecord getServiceRecordById(int id) {
		Connection connection = null;
		String query = "SELECT * FROM ServiceRecord WHERE RecordID = ?";
		try {
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setInt(1, id);
			
			ResultSet rs = statement.executeQuery();
			
			if (!rs.next()) {
				throw new SQLException("No One Found With Query: " + query);
			}
			
			int recordId = rs.getInt("RecordID");
			String date = rs.getString("date");
			Double price = rs.getDouble("price");
			String status = rs.getString("status");
			
			VehicleDAO vehicleDAO = new VehicleDAO();
			Vehicle vehicle = vehicleDAO.getVehicleById(rs.getString("VIN"));
			
			MaintenanceDAO maintenanceDAO = new MaintenanceDAO();
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
}
