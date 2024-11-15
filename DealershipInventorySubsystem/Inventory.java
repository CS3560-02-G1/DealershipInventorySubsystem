package DealershipInventorySubsystem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// represents vehicle inventory for dealership
public class Inventory {
	private int inventoryId;
    private String type; // type of inventory 
    private int vehicleCount; // number of vehicles in inventory

    // initializes inventory with type and vehicle count
    public Inventory(String type, int vehicleCount) {
        this.type = type;
        this.vehicleCount = vehicleCount;
    }
    
    //Constructor that reads the database to get values
    public Inventory(int id) {
    	ResultSet rs = null;
		Connection connection = null;
		Statement statement = null;
		String query = "SELECT * FROM Inventory WHERE InventoryID=" + id;
		
		try {
			connection = JDBCMySQLConnection.getConnection();
			statement = connection.createStatement();
			rs = statement.executeQuery(query);
			
			if (!rs.next()) {
				throw new SQLException("No One Found With Query: " + query);
			}
			
			this.inventoryId = rs.getInt("InventoryID");
			this.type = rs.getString("type");
			this.vehicleCount = rs.getInt("vehicleCount");
			
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
    	return this.inventoryId;
    }
    
    public void setType(String type) {
    	this.type = type;
    }
    
    public String getType() {
    	return this.type;
    }

    // updates count of vehicles in inventory
    public void updateVehicleCount(int newCount) {
        this.vehicleCount = newCount;
    }

    // incrementally updates the vehicle count
    public void addVehicle() {
        this.vehicleCount++;
    }

    // decrements the vehicle count for whenever a vehicle is removed from the inventory
    public void removeVehicle() {
        this.vehicleCount--;
    }

    // getter method for vehicle count
    public int getVehicleCount() {
        return this.vehicleCount;
    }

    // checks if inventory is low
    public boolean isLowStock() {
        return vehicleCount < 5;
    }

    // checks if inventory is empty
    public boolean isEmpty() {
        return vehicleCount == 0;
    }
}
