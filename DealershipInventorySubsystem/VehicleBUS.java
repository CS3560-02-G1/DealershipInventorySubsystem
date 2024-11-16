package DealershipInventorySubsystem;

import java.util.List;

public class VehicleBUS {
	private VehicleDAO vehicleDAO;
	private WarrantyDAO warrantyDAO;
	private MaintenanceDAO maintenanceDAO;
	private ServiceRecordDAO serviceRecordDAO;
	
	public VehicleBUS() {
		vehicleDAO = new VehicleDAO();
		warrantyDAO = new WarrantyDAO();
		maintenanceDAO = new MaintenanceDAO();
		serviceRecordDAO = new ServiceRecordDAO();
	}
	
	//CRUD Methods for Vehicles
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
	
	//NEED TO ADD UPDATE
	
	public void removeVehicle(String vin) {
		vehicleDAO.removeVehicle(vin);
	}
	
	
	//CRUD Methods for Warranty
	public int addWarranty(String vin, String type, int duration, String policy, double price, double coverageLimit) {
		Vehicle vehicle = vehicleDAO.getVehicleById(vin);
		Warranty warranty = new Warranty(type, duration, policy, price, coverageLimit, vehicle);
		return warrantyDAO.insertWarranty(warranty); //Returns newly created id
	}
	
	public Warranty getWarrantyById(int id) {
		return warrantyDAO.getWarrantyById(id);
	}
	
	public List<Warranty> getAllWarranties(String vin) {
		return warrantyDAO.getAllWarranties(vin);
	}
	
	//NEED TO ADD UPDATE AND DELETE METHODS
	
	//CRUD Methods for Maintenance
	public int addMaintenance(String type, String details) {
		return maintenanceDAO.insertMaintenance(new Maintenance(type, details));
	}
	
	public Maintenance getMaintenanceById(int id) {
		return maintenanceDAO.getMaintenanceById(id);
	}
	
	//NEED TO ADD UPDATE AND DELETE METHODS
	
	//CRUD Methods for Service Records
	public int addServiceRecord(String date, double price, String status, String vin, int maintenanceId) {
		Vehicle vehicle = vehicleDAO.getVehicleById(vin);
		Maintenance maintenance = maintenanceDAO.getMaintenanceById(maintenanceId);
		ServiceRecord newRecord = new ServiceRecord(date, price, status, vehicle, maintenance);
		return serviceRecordDAO.insertServiceRecord(newRecord);
	}
	
	public ServiceRecord getServiceRecordById(int id) {
		return serviceRecordDAO.getServiceRecordById(id);
	}
	
}
