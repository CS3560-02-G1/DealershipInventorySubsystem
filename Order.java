package DealershipInventorySubsystem;
public class Order {
    private String orderDate;
    private String deliveryDate;
    private String status;
    private String supplier;

    // constructor for order with order details
    public Order(String orderDate, String deliveryDate, String status, String supplier) {
        this.orderDate = orderDate;
        this.deliveryDate = deliveryDate;
        this.status = status;
        this.supplier = supplier;
    }

    // update order status
    public void updateStatus(String newStatus) {
        this.status = newStatus;
    }

    // getters and setters can be added 
}
