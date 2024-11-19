package DealershipInventorySubsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class MaintenanceDAO {
	
	//Insert maintenace object into database, returns the id created
	public Maintenance insertMaintenance(Maintenance maintenance) {
		Connection connection = null;
		String query = "INSERT INTO Maintenance(type, details) VALUES (?, ?)";
		try {
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			statement.setString(1, maintenance.getType());
			statement.setString(2, maintenance.getDetails());

			int affectedRows = statement.executeUpdate();
			
			if (affectedRows == 0) {
				throw new SQLException("Insert Failed, No Rows Affected");
			}
			
			try (ResultSet rs = statement.getGeneratedKeys()) {
				if (rs.next()) {
					maintenance.setId(rs.getInt(1));
					return maintenance;
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
		
		return null; //Returns null upon failure
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
				return null;
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
	
	public List<Maintenance> getAllMaintenances() {
		List<Maintenance> maintenances = new ArrayList<>();
		Connection connection = null;
		String query = "SELECT * FROM Maintenance";
		try {
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			
			ResultSet rs = statement.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt("MaintenanceID");
				String type = rs.getString("type");
				String details = rs.getString("details");
				
				maintenances.add(new Maintenance(id, type, details));
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
		
		return maintenances;
	}
	
	public Maintenance updateMaintenance(Maintenance newMaintenance) {
		Connection connection = null;
		String query = "UPDATE Maintenance SET type = ?, details = ? WHERE MaintenanceID = ?";
		try {
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setString(1, newMaintenance.getType());
			statement.setString(2, newMaintenance.getDetails());
			statement.setInt(3, newMaintenance.getId());
			
			statement.executeUpdate();
			return newMaintenance;
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
	public boolean removeMaintenance(Maintenance maintenance) {
		Connection connection = null;
		String query = "DELETE FROM Maintenance WHERE MaintenanceID = ?";
		try {
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setInt(1, maintenance.getId());
			
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
