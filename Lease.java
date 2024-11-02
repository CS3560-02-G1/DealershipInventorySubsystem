package DealershipInventorySubsystem;

// represents a lease transaction
public class Lease extends Transaction {
    private int leasePeriod; // length of lease period in months
    private double monthlyFee; // monthly payment for lease

    // initializes lease 
    public Lease(String date, double tax, double totalAmount, String paymentMethod, int leasePeriod, double monthlyFee) {
        super(date, tax, totalAmount, paymentMethod);
        this.leasePeriod = leasePeriod;
        this.monthlyFee = monthlyFee;
    }

    // calculates total cost of lease
    public double calculateTotalLeaseCost() {
        return leasePeriod * monthlyFee;
    }

    // checks if lease is still active
    public boolean isLeaseActive() {
        return leasePeriod > 0;
    }

    // returns remaining lease period in months
    public int getRemainingLeasePeriod() {
        return leasePeriod;
    }
}
