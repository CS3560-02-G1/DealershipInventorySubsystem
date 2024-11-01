package DealershipInventorySubsystem;
public abstract class Transaction {
    private String date;
    private double tax;
    private double totalAmount;
    private String paymentMethod;

    // constructor for transaction with date, tax, total amount, and payment method
    public Transaction(String date, double tax, double totalAmount, String paymentMethod) {
        this.date = date;
        this.tax = tax;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
    }

    // getters and setters can be added
}
