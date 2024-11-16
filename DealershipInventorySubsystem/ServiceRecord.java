package DealershipInventorySubsystem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// represents a service record for a vehicle
public class ServiceRecord {
	private int recordId;
    private String date; // date of service
    private double price; // cost of service
    private String status; // status of service 
    private Vehicle vehicle;
    private Maintenance maintenance;

    // initializes service record with date, price, and status
    public ServiceRecord(String date, double price, String status, Vehicle vehicle, Maintenance maintenance) {
        this.date = date;
        this.price = price;
        this.status = status;
        this.vehicle = vehicle;
        this.maintenance = maintenance;
    }
    
    public ServiceRecord(int id, String date, double price, String status, Vehicle vehicle, Maintenance maintenance) {
    	this.recordId = id;
        this.date = date;
        this.price = price;
        this.status = status;
        this.vehicle = vehicle;
        this.maintenance = maintenance;
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
    
 // checks if service is complete
    public boolean isServiceComplete() {
        return status.equalsIgnoreCase("complete");
    }

    // returns summary of service details
    public String getServiceDetails() {
        return "date: " + date + ", status: " + status + ", price: $" + price;
    }
    
    //Getters / Setters

    public int getRecordId() {
		return recordId;
	}

	public void setRecordId(int recordId) {
		this.recordId = recordId;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Vehicle getVehicle() {
		return vehicle;
	}

	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}

	public Maintenance getMaintenance() {
		return maintenance;
	}

	public void setMaintenance(Maintenance maintenance) {
		this.maintenance = maintenance;
	}

	
}
