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

    // updates the day that the sales agent gets paid / was paid
    public void updatePaymentDate(String newDate) {
        this.paymentDate = newDate;
    }

    // returns payment date
    public String getPaymentDate() {
        return paymentDate;
    }
}
