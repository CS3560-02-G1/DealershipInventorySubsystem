package DealershipInventorySubsystem;

// represents a maintenance record
public class Maintenance {
    private String type; // type of maintenance 
    private String details; // specific details of maintenance

    // initializes maintenance with type and details
    public Maintenance(String type, String details) {
        this.type = type;
        this.details = details;
    }

    // updates details of maintenance record
    public void updateDetails(String newDetails) {
        this.details = newDetails;
    }

    // checks if maintenance is marked as urgent
    public boolean isUrgent() {
        return type.equalsIgnoreCase("urgent");
    }

    // returns summary of maintenance record
    public String getMaintenanceSummary() {
        return type + ": " + details;
    }
}
