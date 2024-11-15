package DealershipInventorySubsystem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// represents a service record for a vehicle
public class ServiceRecord {
	private int recordId;
	private String vin;
	private int maintenanceId;
    private String date; // date of service
    private double price; // cost of service
    private String status; // status of service 

    // initializes service record with date, price, and status
    public ServiceRecord(String date, double price, String status) {
        this.date = date;
        this.price = price;
        this.status = status;
    }
    
    public ServiceRecord(int id) {
    	String query = "SELECT * FROM ServiceRecord WHERE RecordID=" + id;    	
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
			
			this.recordId = rs.getInt("RecordID");
			this.vin = rs.getString("VIN");
			this.maintenanceId = rs.getInt("MaintenanceID");
			this.date = rs.getString("date");
			this.price = rs.getDouble("price");
			this.status = rs.getString("status");
			
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
    	return this.recordId;
    }
    
    public String getVIN() {
    	return this.vin;
    }
    
    public int getMaintenanceId() {
    	return this.maintenanceId;
    }

    // updates status of service record
    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }

    // checks if service is complete
    public boolean isServiceComplete() {
        return status.equalsIgnoreCase("complete");
    }

    // returns summary of service details
    public String getServiceDetails() {
        return "date: " + date + ", status: " + status + ", price: $" + price;
    }
}
