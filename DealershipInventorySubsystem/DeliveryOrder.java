package DealershipInventorySubsystem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// represents a delivery order for vehicles
public class DeliveryOrder {
	private int deliveryId;
    private String sourceLocation; // origin location of vehicle
    private String status; // current status of delivery
    private String arrivalDate; // expected date of arrival

    // initializes a delivery order with source location, status, and arrival date
    public DeliveryOrder(String sourceLocation, String status, String arrivalDate) {
        this.sourceLocation = sourceLocation;
        this.status = status;
        this.arrivalDate = arrivalDate;
    }
    
    public DeliveryOrder(int id) {
    	String query = "SELECT * FROM DeliveryOrder WHERE DeliveryID=" + id;
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
			
			this.deliveryId = rs.getInt("DeliveryID");
			this.sourceLocation = rs.getString("sourceLocation");
			this.status = rs.getString("status");
			this.arrivalDate = rs.getString("arrivalDate");
			
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

    // estimates delivery time based on source location
    public int estimateDeliveryTime() {
        return sourceLocation.equalsIgnoreCase("local") ? 1 : 5;
    }
    
    //Getters / Setters
	public int getDeliveryId() {
		return deliveryId;
	}

	public void setDeliveryId(int deliveryId) {
		this.deliveryId = deliveryId;
	}

	public String getSourceLocation() {
		return sourceLocation;
	}

	public void setSourceLocation(String sourceLocation) {
		this.sourceLocation = sourceLocation;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
    
    
}
