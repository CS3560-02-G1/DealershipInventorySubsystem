package DealershipInventorySubsystem;

// represents a general transaction in the dealership
public abstract class Transaction {
	protected int transactionId;
    protected String date; // date of transaction
    protected double tax; // tax applied to transaction
    protected String paymentMethod; // payment method used
    protected Vehicle vehicle;
    protected Customer customer;

    // initializes transaction with date, tax, total amount, and payment method
    public Transaction(String date, double tax, String paymentMethod, Vehicle vehicle, Customer customer) {
        this.date = date;
        this.tax = tax;
        this.paymentMethod = paymentMethod;
        this.vehicle = vehicle;
        this.customer = customer;
    }
    
    public Transaction(int id, String date, double tax, String paymentMethod, Vehicle vehicle, Customer customer) {
    	this.transactionId = id;
        this.date = date;
        this.tax = tax;
        this.paymentMethod = paymentMethod;
        this.vehicle = vehicle;
        this.customer = customer;
    }

    // checks if transaction is taxable
    public boolean isTaxable() {
        return tax > 0;
    }
    
    //Getters / Setters
    public void setId(int id) {
    	transactionId = id;
    }
    
    public int getId() {
    	return transactionId;
    }
    
    public void setVehicle(Vehicle vehicle) {
    	this.vehicle = vehicle;
    }
    
    public Vehicle getVehicle() {
    	return vehicle;
    }
    
    public void setCustomer(Customer customer) {
    	this.customer = customer;
    }
    
    public Customer getCustomer() {
    	return customer;
    }
    
    public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
}
