package DealershipInventorySubsystem;

// represents a sale transaction
public class Sale extends Transaction {
    private int downPayment; // down payment amount for sale

    // initializes sale with transaction details and down payment
    public Sale(String date, double tax, String paymentMethod, Vehicle vehicle, Customer customer, int downPayment) {
        super(date, tax, paymentMethod, vehicle, customer);
        this.downPayment = downPayment;
    }
    
    public Sale(int id, String date, double tax, String paymentMethod, Vehicle vehicle, Customer customer, int downPayment) {
        super(id, date, tax, paymentMethod, vehicle, customer);
        this.downPayment = downPayment;
    }

	public int getDownPayment() {
		return downPayment;
	}

	public void setDownPayment(int downPayment) {
		this.downPayment = downPayment;
	}

    
}
