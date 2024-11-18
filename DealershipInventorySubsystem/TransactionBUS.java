package DealershipInventorySubsystem;

public class TransactionBUS {
	private TransactionDAO transactionDAO;
	private CustomerDAO customerDAO;
	private VehicleDAO vehicleDAO;
	private SalesAgentDAO salesAgentDAO;
	private CommissionDAO commissionDAO;
	
	public TransactionBUS() {
		transactionDAO = new TransactionDAO();
		customerDAO = new CustomerDAO();
		vehicleDAO = new VehicleDAO();
		salesAgentDAO = new SalesAgentDAO();
		commissionDAO = new CommissionDAO();
	}
	
	//CRUD Methods for customers
	public Customer addCustomer(Customer customer) {
		return customerDAO.insertCustomer(customer);
	}
	
	public Customer addCustomer(String firstName, String lastName, String phone, String address, String email) {
		return customerDAO.insertCustomer(new Customer(firstName, lastName, phone, address, email));
	}
	
	public Customer getCustomerById(int id) {
		return customerDAO.getCustomerById(id);
	}
	
	public Customer upateCustomer(Customer newCustomer) {
		return customerDAO.updateCustomer(newCustomer);
	}
	
	public boolean removeCustomer(Customer customer) {
		return customerDAO.removeCustomer(customer);
	}
	
	//CRUD Methods for transactions
	public Sale addSale(Sale sale) {
		return transactionDAO.insertSale(sale);
	}
	
	public Sale addSale(String vin, int customerId, String date, double tax, String paymentMethod, int downPayment) {
		Vehicle vehicle = vehicleDAO.getVehicleById(vin);
		Customer customer = customerDAO.getCustomerById(customerId);
		return transactionDAO.insertSale(new Sale(date, tax, paymentMethod, vehicle, customer, downPayment));
	}
	
	public Lease addLease(Lease lease) {
		return transactionDAO.insertLease(lease);
	}
	
	public Lease addLease(String vin, int customerId, String date, double tax, String paymentMethod, int leasePeriod, int monthlyFee) {
		Vehicle vehicle = vehicleDAO.getVehicleById(vin);
		Customer customer = customerDAO.getCustomerById(customerId);
		return transactionDAO.insertLease(new Lease(date, tax, paymentMethod, vehicle, customer, leasePeriod, monthlyFee));
	}
	
	public Sale getSaleById(int id) {
		return transactionDAO.getSaleById(id);
	}
	
	public Lease getLeaseById(int id) {
		return transactionDAO.getLeaseById(id);
	}
	
	public Sale updateSale(Sale newSale) {
		return transactionDAO.updateSale(newSale);
	}
	
	public Lease updateLease(Lease newLease) {
		return transactionDAO.updateLease(newLease);
	}
	
	public boolean removeTransaction(Transaction transaction) {
		return transactionDAO.removeTransaction(transaction);
	}
	
	//CRUD Methods for Sales Agent
	public SalesAgent addSalesAgent(SalesAgent agent) {
		return salesAgentDAO.insertSalesAgent(agent);
	}
	
	public SalesAgent addSalesAgent(String firstName, String lastName, String email, String phone) {
		return salesAgentDAO.insertSalesAgent(new SalesAgent(firstName, lastName, email, phone));
	}
	
	public SalesAgent getSalesAgentById(int id) {
		return salesAgentDAO.getSalesAgentById(id);
	}
	
	public SalesAgent updateSalesAgent(SalesAgent newAgent) {
		return salesAgentDAO.updaSalesAgent(newAgent);
	}
	
	public boolean removeSalesAgent(SalesAgent agent) {
		return salesAgentDAO.removeSalesAgent(agent);
	}
	
	//CRUD Methods for Commissions
	public Commission addCommission(Commission commission) {
		return commissionDAO.insertCommission(commission);
	}
	
	public Commission addCommission(int transactionId, int agentId, double commissionRate, String paymentDate) {
		Transaction transaction = transactionDAO.getTransactionById(transactionId);
		SalesAgent agent = salesAgentDAO.getSalesAgentById(agentId);
		return commissionDAO.insertCommission(new Commission(transaction, agent, commissionRate, paymentDate));
	}
	
	public Commission getCommissionById(int transactionId, int agentId) {
		return commissionDAO.getCommissionById(transactionId, agentId);
	}
	
	public Commission updateCommission(Commission newCommission) {
		return commissionDAO.updateCommission(newCommission);
	}
	
	public boolean removeCommision(Commission commission) {
		return commissionDAO.removeCommission(commission);
	}
}
