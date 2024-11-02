package DealershipInventorySubsystem;

// represents a delivery order for vehicles
public class DeliveryOrder {
    private String sourceLocation; // origin location of vehicle
    private String status; // current status of delivery
    private String arrivalDate; // expected date of arrival

    // initializes a delivery order with source location, status, and arrival date
    public DeliveryOrder(String sourceLocation, String status, String arrivalDate) {
        this.sourceLocation = sourceLocation;
        this.status = status;
        this.arrivalDate = arrivalDate;
    }

    // updates status of delivery order
    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }

    // estimates delivery time based on source location
    public int estimateDeliveryTime() {
        return sourceLocation.equalsIgnoreCase("local") ? 1 : 5;
    }

    // returns arrival details
    public String getArrivalDetails() {
        return "expected arrival on: " + arrivalDate;
    }
}
