package DealershipInventorySubsystem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// represents a delivery order for vehicles
public class DeliveryOrder {
	private int deliveryOrderId;
	private int inventoryId;
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
			
			this.deliveryOrderId = rs.getInt("DeliveryID");
			this.inventoryId = rs.getInt("InventoryID");
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
    
    public int getId() {
    	return this.deliveryOrderId;
    }
    
    public int getInvetoryId() {
    	return this.inventoryId;
    }

    // updates status of delivery order
    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }

    //Track the delivery
    public String getStatus() {
        return this.status;
    }

    // estimates delivery time based on source location
    public int estimateDeliveryTime() {
        return sourceLocation.equalsIgnoreCase("local") ? 1 : 5;
    }

    // updates the arrival date to the new expected, or the actual date it arrived
    public void setArrivalDate(String newArrivalDate) {
        this.arrivalDate = newArrivalDate;
    }

    // returns arrival details
    public String getArrivalDetails() {
        return "expected arrival on: " + arrivalDate;
    }
}
