package DealershipInventorySubsystem;
public class Sale extends Transaction {
    private double downPayment;

    // constructor for sale transaction with down payment
    public Sale(String date, double tax, double totalAmount, String paymentMethod, double downPayment) {
        super(date, tax, totalAmount, paymentMethod);
        this.downPayment = downPayment;
    }

    // getters and setters can be added
}
