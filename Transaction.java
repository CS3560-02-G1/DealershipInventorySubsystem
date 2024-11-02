package DealershipInventorySubsystem;

// represents a general transaction in the dealership
public abstract class Transaction {
    protected String date; // date of transaction
    protected double tax; // tax applied to transaction
    protected double totalAmount; // total amount of transaction before tax
    protected String paymentMethod; // payment method used

    // initializes transaction with date, tax, total amount, and payment method
    public Transaction(String date, double tax, double totalAmount, String paymentMethod) {
        this.date = date;
        this.tax = tax;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
    }

    // calculates total cost after tax
    public double calculateTotal() {
        return totalAmount + tax;
    }

    // checks if transaction is taxable
    public boolean isTaxable() {
        return tax > 0;
    }

    // returns transaction details as a summary
    public String getTransactionDetails() {
        return "date: " + date + ", total: $" + totalAmount + ", payment: " + paymentMethod;
    }
}
