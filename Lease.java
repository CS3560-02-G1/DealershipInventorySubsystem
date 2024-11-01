package DealershipInventorySubsystem;
public class Lease extends Transaction {
    private int leasePeriod;
    private double monthlyFee;

    // constructor for lease transaction with lease period and monthly fee
    public Lease(String date, double tax, double totalAmount, String paymentMethod, int leasePeriod, double monthlyFee) {
        super(date, tax, totalAmount, paymentMethod);
        this.leasePeriod = leasePeriod;
        this.monthlyFee = monthlyFee;
    }

    // getters and setters can be added 
}
