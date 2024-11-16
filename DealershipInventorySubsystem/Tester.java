package DealershipInventorySubsystem;

public class Tester {
	public static void main(String[] args) {
		VehicleBUS vehicleBUS= new VehicleBUS();
		String vin = "4Y1SL65848Z411439";
		Vehicle newVehicle = new Vehicle(vin, "Transit 150", 2021, "Placeholder Status", "Mint Condition", "Ford", "White", 31000.0);
		
		vehicleBUS.addVehicle(newVehicle);
		newVehicle = vehicleBUS.getVehicleByVIN(vin);
		
		System.out.println(newVehicle.getColor() + "  " + newVehicle.getCondition());
		
		int warrantyId = vehicleBUS.addWarranty(vin, "Extended", 36, "don't get hit", 5000.50, 20000.10);
		
		Warranty warranty = vehicleBUS.getWarrantyById(warrantyId);
		System.out.println(warranty.getVehicle().getVin());
		
		vehicleBUS.removeVehicle("4Y1SL65848Z411439");
	}
}
