package DealershipInventorySubsystem;
public class Customer {
    private String name;
    private String phoneNumber;
    private String emailAddress;
    private String purchaseHistory;

    // constructor for customer with contact info and purchase history
    public Customer(String name, String phoneNumber, String emailAddress, String purchaseHistory) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.purchaseHistory = purchaseHistory;
    }

    // add a purchase to history
    public void addPurchase(String purchase) {
        this.purchaseHistory += ", " + purchase;
    }

    // getters and setters can be added
}
