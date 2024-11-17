package DealershipInventorySubsystem;

// represents a commission structure for sales agents
public class Commission {
	//Composite Primary Key
	private Transaction transaction;
    private SalesAgent salesAgent;
    
    private double commissionRate; // percentage of commission earned
    private String paymentDate; // date of commission payment

    // initializes a commission with rate and payment date
    public Commission(Transaction transaction, SalesAgent salesAgent, double commissionRate, String paymentDate) {
    	this.transaction = transaction;
        this.salesAgent = salesAgent;
        this.commissionRate = commissionRate;
        this.paymentDate = paymentDate;
    }


    // calculates commission based on sale amount
    public double calculateCommission(double saleAmount) {
        return saleAmount * commissionRate;
    }
    
    // Getters / Setters
	public double getCommissionRate() {
		return commissionRate;
	}

	public void setCommissionRate(double commissionRate) {
		this.commissionRate = commissionRate;
	}

	public String getPaymentDate() {
		return paymentDate;
	}

	public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public Transaction getTransaction() {
		return transaction;
	}

	public void setTransaction(Transaction transaction) {
		this.transaction = transaction;
	}

	public SalesAgent getSalesAgent() {
		return salesAgent;
	}

	public void setSalesAgent(SalesAgent salesAgent) {
		this.salesAgent = salesAgent;
	}

    
    
    
    
}
