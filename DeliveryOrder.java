package DealershipInventorySubsystem;
public class DeliveryOrder {
    private String sourceLocation;
    private String status;
    private String arrivalDate;

    // constructor for delivery order with source location, status, and arrival date
    public DeliveryOrder(String sourceLocation, String status, String arrivalDate) {
        this.sourceLocation = sourceLocation;
        this.status = status;
        this.arrivalDate = arrivalDate;
    }

    // getters and setters can be added
}
