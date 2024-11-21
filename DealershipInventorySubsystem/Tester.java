package DealershipInventorySubsystem;

import java.util.List;

public class Tester {
	public static void main(String[] args) {
		VehicleBUS vehicleBUS = new VehicleBUS();
		TransactionBUS transactionBUS = new TransactionBUS();
		
		removeVehicles(vehicleBUS, vehicleBUS.getAllSoldVehicles());
		removeVehicles(vehicleBUS, vehicleBUS.getAllUnsoldVehicles());
		
//		List<Maintenance> maintenances = vehicleBUS.getAllMaintenances();
//		for (Maintenance maintenance : maintenances) {
//			System.out.println(maintenance.getDetails());
//		}
//		System.out.println();
//		
//		List<Customer> customers = transactionBUS.getAllCustomers();
//		for (Customer customer : customers) {
//			System.out.println(customer.getFirstName() + " " + customer.getLastName());
//		}
//		System.out.println();
//		
//		List<SalesAgent> agents = transactionBUS.getAllSalesAgents();
//		for (SalesAgent agent : agents) {
//			System.out.println(agent.getFirstName() + " " + agent.getLastName());
//		}
//		System.out.println();
		
		String vin = "4Y1SL65848Z411439";
		Vehicle newVehicle = new Vehicle(vin, "Transit 150", 2021, "for sale", "Mint Condition", "Ford", "White", 31000.0);
		vehicleBUS.addVehicle(newVehicle);
		
		vin = "2C9NM25244A491439";
		Vehicle newVehicle2 = new Vehicle(vin, "Civic", 2020, "for sale", "Mint Condition", "Honda", "Black", 25000.0);
		vehicleBUS.addVehicle(newVehicle2);
		
//		List<Vehicle> unsoldVehicles = vehicleBUS.getAllUnsoldVehicles();
//		for (Vehicle vehicle : unsoldVehicles) {
//			System.out.println(vehicle.getVin() + "  " + vehicle.getMake() + "   " + vehicle.getModel());
//		}
//		System.out.println();
//		
//		transactionBUS.addSale(new Sale("11/18/2024", 9.5, "cash", newVehicle2, customers.get(0), 25000));
//		transactionBUS.addLease(new Lease("11/18/2024", 10.5, "cash", newVehicle, customers.get(1), 36, 1000));
//		
//		List<Transaction> transactions = transactionBUS.getAllTransactions();
//		for (Transaction transaction : transactions) {
//			System.out.println(transaction.getVehicle().getVin() + "   " + transaction.getCustomer().getFirstName() + "  " + transaction.getDate());
//		}
//		System.out.println();
//		
//		Commission commission = new Commission(transactions.get(0), agents.get(0), 5, "2022-12-15");
//		transactionBUS.addCommission(commission);
		
		
		
		//vehicleBUS.removeVehicle(newVehicle.getVin());
		//vehicleBUS.removeVehicle(newVehicle2.getVin());
	}
	
	private static void removeVehicles(VehicleBUS vehicleBUS, List<Vehicle> vehicles) {
		for (Vehicle vehicle : vehicles) {
			vehicleBUS.removeVehicle(vehicle.getVin());
		}
	}
	
	private static void addVehicles(VehicleBUS vehicleBUS) {
		String vin = "4Y1SL65848Z411439";
		Vehicle newVehicle = new Vehicle(vin, "Transit 150", 2021, "for sale", "Mint Condition", "Ford", "White", 31000.0);
		vehicleBUS.addVehicle(newVehicle);
		
		vin = "2C9NM25244A491439";
		Vehicle newVehicle2 = new Vehicle(vin, "Civic", 2020, "for sale", "Mint Condition", "Honda", "Black", 25000.0);
		vehicleBUS.addVehicle(newVehicle2);
	}
	
	private static void testAddUpdate(TransactionBUS transactionBUS, VehicleBUS vehicleBUS) {
		String vin = "4Y1SL65848Z411439";
		Vehicle newVehicle = new Vehicle(vin, "Transit 150", 2021, "on sale", "Mint Condition", "Ford", "White", 31000.0);
		
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
