package DealershipInventorySubsystem;

public class VehicleBUS {
	private VehicleDAO vehicleDAO;
	private WarrantyDAO warrantyDAO;
	
	public VehicleBUS() {
		vehicleDAO = new VehicleDAO();
		warrantyDAO = new WarrantyDAO();
	}
	
	public Vehicle getVehicleByVIN(String vin) {
		return vehicleDAO.getVehicleById(vin);
	}
	
	public void addVehicle(String vin, String model, int year, String status, String condition, String make, String color, double price) {
		Vehicle vehicle = new Vehicle(vin, model, year, status, condition, make, color, price);
		vehicleDAO.insertVehicle(vehicle);
	}
	
	public void addVehicle(Vehicle vehicle) {
		vehicleDAO.insertVehicle(vehicle);
	}
	
	public void removeVehicle(String vin) {
		vehicleDAO.removeVehicle(vin);
	}
	
	public int addWarranty(String vin, String type, int duration, String policy, double price, double coverageLimit) {
		Vehicle vehicle = vehicleDAO.getVehicleById(vin);
		Warranty warranty = new Warranty(type, duration, policy, price, coverageLimit, vehicle);
		int newId = warrantyDAO.insertWarranty(warranty);
		return newId;
	}
	
	public Warranty getWarrantyById(int id) {
		return warrantyDAO.getWarrantyById(id);
	}
}
