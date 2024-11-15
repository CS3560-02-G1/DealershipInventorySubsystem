package DealershipInventorySubsystem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// represents a vehicle in dealership inventory
public class Vehicle {
    private String vin; // vehicle identification number
    private int inventoryId;
    private int deliveryId;
    private String model; // model of vehicle
    private int year; // manufacturing year
    private String status; // available or sold
    private String condition; // new or used
    private String make; // brand of vehicle
    private String color; // color of vehicle
    private double price; // price of vehicle

    // initializes vehicle with vin, model, year, status, condition, make, color, and price
    public Vehicle(String vin, String model, int year, String status, String condition, String make, String color, double price) {
        this.vin = vin;
        this.model = model;
        this.year = year;
        this.status = status;
        this.condition = condition;
        this.make = make;
        this.color = color;
        this.price = price;
        //System.out.print("creating vehicle object\n");
    }
    
    public Vehicle(String vin) {
    	String query = "SELECT * FROM Vehicle WHERE VIN=" + vin;
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
			
			this.vin = rs.getString("vin");
			this.inventoryId = rs.getInt("inventoryId");
			this.deliveryId = rs.getInt("deliveryId");
			this.model = rs.getString("model");
			this.year = rs.getInt("year");
			this.status = rs.getString("status");
			this.condition = rs.getString("condition");
			this.make = rs.getString("make");
			this.color = rs.getString("color");
			this.price = rs.getDouble("price");
			
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

    // calculates depreciation 
    public double calculateDepreciation() {
        return 0.0; // placeholder
    }

    // checks if vehicle is new
    public boolean isNew() {
        return condition.equalsIgnoreCase("new");
    }
    
    public int getInventoryId() {
    	return this.inventoryId;
    }
    
    public int getDeliveryId() {
    	return this.deliveryId;
    }

    // returns age of vehicle based on current year
    public int getVehicleAge(int currentYear) {
        return currentYear - year;
    }

    // returns summary of vehicle details
    public String getVehicleSummary() {
        return year + " " + make + " " + model + " - " + color + ", $" + price;
    }

    // updates status of vehicle
    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }

    // update listed price of vehicle
    public void updatePrice(double newPrice) {
        this.price = newPrice;
    }

    public void updateVin(String newVin) {
        this.vin = newVin;
    }

    public void updateModel(String newModel) {
        this.model = newModel;
    }

    public void updateYear(int newYear) {
        this.year = newYear;
    }

    public void updateCondition(String newCondition) {
        this.condition = newCondition;
    }

    public void updateMake(String newMake) {
        this.make = newMake;
    }

    public void updateColor(String newColor) {
        this.color = newColor;
    }

    // getters for vehicle attributes
    public String getVin() {
        return vin;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public String getStatus() {
        return status;
    }

    public String getCondition() {
        return condition;
    }

    public String getMake() {
        return make;
    }

    public String getColor() {
        return color;
    }

    public double getPrice() {
        return price;
    }
}
