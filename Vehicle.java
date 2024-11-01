package DealershipInventorySubsystem;
public class Vehicle {
    private String make;
    private String model;
    private String vin;
    private int year;
    private double price;
    private String status; // available, sold, in transit, etc.
    private String color;
    private int mileage;
    private String transmissionType;

    // constructor to initialize vehicle attributes
    public Vehicle(String make, String model, String vin, int year, double price, String status, String color, int mileage, String transmissionType) {
        this.make = make;
        this.model = model;
        this.vin = vin;
        this.year = year;
        this.price = price;
        this.status = status;
        this.color = color;
        this.mileage = mileage;
        this.transmissionType = transmissionType;
    }

    // update vehicle status
    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }

    // getters and setters can be added 
}
