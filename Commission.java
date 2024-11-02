package DealershipInventorySubsystem;

// represents a commission structure for sales agents
public class Commission {
    private double commissionRate; // percentage of commission earned
    private String paymentDate; // date of commission payment

    // initializes a commission with rate and payment date
    public Commission(double commissionRate, String paymentDate) {
        this.commissionRate = commissionRate;
        this.paymentDate = paymentDate;
    }

    // calculates commission based on sale amount
    public double calculateCommission(double saleAmount) {
        return saleAmount * commissionRate;
    }

    // updates commission rate
    public void updateCommissionRate(double newRate) {
        this.commissionRate = newRate;
    }

    // returns payment date
    public String getPaymentDate() {
        return paymentDate;
    }
}
