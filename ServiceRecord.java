package DealershipInventorySubsystem;

// represents a service record for a vehicle
public class ServiceRecord {
    private String date; // date of service
    private double price; // cost of service
    private String status; // status of service 

    // initializes service record with date, price, and status
    public ServiceRecord(String date, double price, String status) {
        this.date = date;
        this.price = price;
        this.status = status;
    }

    // updates status of service record
    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }

    // checks if service is complete
    public boolean isServiceComplete() {
        return status.equalsIgnoreCase("complete");
    }

    // returns summary of service details
    public String getServiceDetails() {
        return "date: " + date + ", status: " + status + ", price: $" + price;
    }
}
