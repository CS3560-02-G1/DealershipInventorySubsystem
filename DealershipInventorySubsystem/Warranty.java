package DealershipInventorySubsystem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// represents a warranty for a vehicle
public class Warranty {
	private int warrantyId;
	private String vin;
    private String type; // type of warranty 
    private int duration; // duration of warranty in months
    private String policy; // policy details
    private double price; // cost of warranty
    private double coverageLimit; // coverage limit in dollars

    // initializes warranty with type, duration, policy, price, and coverage limit
    public Warranty(String type, int duration, String policy, double price, double coverageLimit) {
        this.type = type;
        this.duration = duration;
        this.policy = policy;
        this.price = price;
        this.coverageLimit = coverageLimit;
    }
    
    public Warranty(int id) {
    	String query = "SELECT * FROM Warranty WHERE WarrantyID=" + id;   	
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
			
			this.warrantyId = rs.getInt("WarrantyID");
			this.vin = rs.getString("VIN");
			this.type = rs.getString("type");
			this.duration = rs.getInt("duration");
			this.policy = rs.getString("policy");
			this.price = rs.getDouble("price");
			this.coverageLimit = rs.getDouble("coverageLimit");
			
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
    	return this.warrantyId;
    }
    
    public String getVIN() {
    	return this.vin;
    }

    // checks if warranty is active 
    public boolean isActive(int monthsSincePurchase) {
        return monthsSincePurchase < duration;
    }

    // returns summary of warranty details
    public String getWarrantySummary() {
        return "type: " + type + ", duration: " + duration + " months, coverage limit: $" + coverageLimit;
    }

    // getters for warranty attributes
    public String getType() {
        return type;
    }

    public int getDuration() {
        return duration;
    }

    public String getPolicy() {
        return policy;
    }

    public double getPrice() {
        return price;
    }

    public double getCoverageLimit() {
        return coverageLimit;
    }

    // setters for warranty attributes
    public void setType(String newType) {
        this.type = newType;
    }

    public void setDuration(int newDuration) {
        this.duration = newDuration;
    }

    public void setPolicy(String newPolicy) {
        this.policy = newPolicy;
    }

    public void setPrice(double newPrice) {
        this.price = newPrice;
    }

    public void setCoverageLimit(double newLimit) {
        this.coverageLimit = newLimit;
    }
}
