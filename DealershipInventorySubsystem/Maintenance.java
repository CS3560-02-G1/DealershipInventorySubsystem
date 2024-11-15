package DealershipInventorySubsystem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// represents a maintenance record
public class Maintenance {
	private int maintenanceId;
    private String type; // type of maintenance 
    private String details; // specific details of maintenance

    // initializes maintenance with type and details
    public Maintenance(String type, String details) {
        this.type = type;
        this.details = details;
    }
    
    public Maintenance(int id) {
    	String query = "SELECT * FROM Inventory WHERE InventoryID=" + id;
		ResultSet rs = null;
		Connection connection = null;
		Statement statement = null;
		
		try {
			connection = JDBCMySQLConnection.getConnection();
			statement = connection.createStatement();
			rs = statement.executeQuery(query);
			
			if (!rs.next()) {
				throw new SQLException("No One Found With Query: " + query);
			}
			
			this.maintenanceId = rs.getInt("MaintenanceID");
			this.type = rs.getString("type");
			this.details = rs.getString("details");
			
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
    
    public int getId() {
    	return this.maintenanceId;
    }

    // updates details of maintenance record
    public void updateDetails(String newDetails) {
        this.details = newDetails;
    }

    // checks if maintenance is marked as urgent
    public boolean isUrgent() {
        return type.equalsIgnoreCase("urgent");
    }

    // returns summary of maintenance record
    public String getMaintenanceSummary() {
        return type + ": " + details;
    }
}
