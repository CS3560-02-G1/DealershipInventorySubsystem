package DealershipInventorySubsystem;

public class Tester {
	public static void main(String[] args) {
		VehicleBUS vehicleBUS = new VehicleBUS();
		TransactionBUS transactionBUS = new TransactionBUS();
		
		String vin = "4Y1SL65848Z411439";
		Vehicle newVehicle = new Vehicle(vin, "Transit 150", 2021, "Placeholder Status", "Mint Condition", "Ford", "White", 31000.0);
		
		vehicleBUS.addVehicle(newVehicle);
		newVehicle = vehicleBUS.getVehicleByVIN(vin);
		
		System.out.println(newVehicle.getColor() + "  " + newVehicle.getCondition());
		
		Warranty warranty = vehicleBUS.addWarranty(vin, "Extended", 36, "don't get hit", 5000.50, 20000.10);
		
		System.out.println(warranty.getVehicle().getVin());
		
		Customer customer = transactionBUS.addCustomer("bob", "smith", "777 777-7777", "123 oak lanes", "bsmith@gmail.com");
		
		Sale sale = transactionBUS.addSale(vin, customer.getId(), "10/20/2024", 9.5, "loan", 10000);
		
		SalesAgent agent = transactionBUS.addSalesAgent("joe", "moe", "jmoe@gmai.com", "123 456-7890");
		
		Commission commission = transactionBUS.addCommission(sale.getId(), agent.getId(), 10, "10/21/2024");
		
		
		sale = transactionBUS.getSaleById(sale.getId());
		agent = transactionBUS.getSalesAgentById(agent.getId());
		commission = transactionBUS.getCommissionById(sale.getId(), agent.getId());
		
		System.out.println(customer.getFirstName());
		customer.setFirstName("pope");
		transactionBUS.upateCustomer(customer);
		System.out.println(transactionBUS.getCustomerById(customer.getId()).getFirstName());
		
		System.out.println(agent.getFirstName());
		agent.setFirstName("jerome");
		transactionBUS.updateSalesAgent(agent);
		System.out.println(transactionBUS.getSalesAgentById(agent.getId()).getFirstName());
		
		System.out.println(commission.getCommissionRate());
		commission.setCommissionRate(15);
		transactionBUS.updateCommission(commission);
		System.out.println(transactionBUS.getCommissionById(sale.getId(), agent.getId()).getCommissionRate());
		
		System.out.println(sale.getDate());
		sale.setDate("10/30/2024");
		transactionBUS.updateSale(sale);
		System.out.println(transactionBUS.getSaleById(sale.getId()).getDate());
		
		
		
		vehicleBUS.removeVehicle(vin);
		
		
		
	}
}
