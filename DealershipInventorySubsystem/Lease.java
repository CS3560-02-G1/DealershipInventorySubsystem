package DealershipInventorySubsystem;

// represents a lease transaction
public class Lease extends Transaction {
    private int leasePeriod; // length of lease period in months
    private int monthlyFee; // monthly payment for lease

    // initializes lease 
    public Lease(String date, double tax, String paymentMethod, Vehicle vehicle, Customer customer, int leasePeriod, int monthlyFee) {
        super(date, tax, paymentMethod, vehicle, customer);
        this.leasePeriod = leasePeriod;
        this.monthlyFee = monthlyFee;
    }
    
    public Lease(int id, String date, double tax, String paymentMethod, Vehicle vehicle, Customer customer, int leasePeriod, int monthlyFee) {
        super(id, date, tax, paymentMethod, vehicle, customer);
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

    //Getters / Setters
	public int getLeasePeriod() {
		return leasePeriod;
	}

	public void setLeasePeriod(int leasePeriod) {
		this.leasePeriod = leasePeriod;
	}

	public int getMonthlyFee() {
		return monthlyFee;
	}

	public void setMonthlyFee(int monthlyFee) {
		this.monthlyFee = monthlyFee;
	}
    
    
}
