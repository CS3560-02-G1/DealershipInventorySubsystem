package DealershipInventorySubsystem;

// represents a warranty for a vehicle
public class Warranty {
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

    // updates warranty details with new values
    public void updateWarrantyDetails(String newType, int newDuration, String newPolicy, double newPrice, double newCoverageLimit) {
        this.type = newType;
        this.duration = newDuration;
        this.policy = newPolicy;
        this.price = newPrice;
        this.coverageLimit = newCoverageLimit;
    }
}
