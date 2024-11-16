package DealershipInventorySubsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MaintenanceDAO {
	
	//Insert maintenace object into database, returns the id created
	public int insertMaintenance(Maintenance maintenance) {
		Connection connection = null;
		String query = "INSERT IGNORE INTO Maintenance(type, details) VALUES (?, ?)";
		try {
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			statement.setString(1, maintenance.getType());
			statement.setString(2, maintenance.getDetails());

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
		
		return -1; //Returns -1 upon failure
	}
	
	public Maintenance getMaintenanceById(int id) {
		Connection connection = null;
		String query = "SELECT * FROM Maintenance WHERE MaintenanceID = ?";
		try {
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setInt(1, id);
			
			ResultSet rs = statement.executeQuery();
			
			if (!rs.next()) {
				throw new SQLException("No One Found With Query: " + query);
			}
			
			String type = rs.getString("type");
			String details = rs.getString("details");
			
			return new Maintenance(id, type, details);
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
