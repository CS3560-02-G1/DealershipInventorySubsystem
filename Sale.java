package DealershipInventorySubsystem;

// represents a sale transaction
public class Sale extends Transaction {
    private double downPayment; // down payment amount for sale

    // initializes sale with transaction details and down payment
    public Sale(String date, double tax, double totalAmount, String paymentMethod, double downPayment) {
        super(date, tax, totalAmount, paymentMethod);
        this.downPayment = downPayment;
    }

    // calculates final sale price after down payment
    public double calculateFinalPrice() {
        return totalAmount - downPayment;
    }

    // checks if sale is fully paid
    public boolean isFullyPaid() {
        return downPayment >= totalAmount;
    }

    // returns summary of sale
    public String getSaleSummary() {
        return "total: $" + totalAmount + ", down payment: $" + downPayment;
    }
}
