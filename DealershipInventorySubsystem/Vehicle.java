package DealershipInventorySubsystem;

// represents a vehicle in dealership inventory
public class Vehicle {
    private String vin; // vehicle identification number
    private DeliveryOrder order;
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

    // calculates depreciation 
    public double calculateDepreciation() {
        return 0.0; // placeholder
    }

    // checks if vehicle is new
    public boolean isNew() {
        return condition.equalsIgnoreCase("new");
    }
    
    public void setDeliveryOrder(DeliveryOrder order) {
    	this.order = order;
    }
    
    public DeliveryOrder getDeliveryOrder() {
    	return order;
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
