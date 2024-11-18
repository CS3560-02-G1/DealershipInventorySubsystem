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
	
	public Vehicle addVehicle(String vin, String model, int year, String status, String condition, String make, String color, double price) {
		Vehicle vehicle = new Vehicle(vin, model, year, status, condition, make, color, price);
		return vehicleDAO.insertVehicle(vehicle);
	}
	
	public Vehicle addVehicle(Vehicle vehicle) {
		return vehicleDAO.insertVehicle(vehicle);
	}
	
	public Vehicle updateVehicle(Vehicle newVehicle) {
		return vehicleDAO.updateVehicle(newVehicle);
	}
	
	//Returns true on success, false otherwise
	public boolean removeVehicle(String vin) {
		return vehicleDAO.removeVehicle(vin);
	}
	
	
	//CRUD Methods for Warranty
	public Warranty addWarranty(Warranty warranty) {
		return warrantyDAO.insertWarranty(warranty);
	}
	
	public Warranty addWarranty(String vin, String type, int duration, String policy, double price, double coverageLimit) {
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
	
	public Warranty updateWarranty(Warranty newWarranty) {
		return warrantyDAO.updateWarranty(newWarranty);
	}
	
	public boolean removeWarranty(Warranty warranty) {
		return warrantyDAO.removeWarranty(warranty);
	}
	
	//CRUD Methods for Maintenance
	public Maintenance addMaintenance(Maintenance maintenance) {
		return maintenanceDAO.insertMaintenance(maintenance);
	}
	
	public Maintenance addMaintenance(String type, String details) {
		return maintenanceDAO.insertMaintenance(new Maintenance(type, details));
	}
	
	public Maintenance getMaintenanceById(int id) {
		return maintenanceDAO.getMaintenanceById(id);
	}
	
	public Maintenance updateMaintenance(Maintenance newMaintenance) {
		return maintenanceDAO.updateMaintenance(newMaintenance);
	}
	
	public boolean removeMaintenance(Maintenance maintenance) {
		return maintenanceDAO.removeMaintenance(maintenance);
	}
	
	//CRUD Methods for Service Records
	public ServiceRecord addServiceRecord(ServiceRecord serviceRecord) {
		return serviceRecordDAO.insertServiceRecord(serviceRecord);
	}
	
	public ServiceRecord addServiceRecord(String date, double price, String status, String vin, int maintenanceId) {
		Vehicle vehicle = vehicleDAO.getVehicleById(vin);
		Maintenance maintenance = maintenanceDAO.getMaintenanceById(maintenanceId);
		ServiceRecord newRecord = new ServiceRecord(date, price, status, vehicle, maintenance);
		return serviceRecordDAO.insertServiceRecord(newRecord);
	}
	
	public ServiceRecord getServiceRecordById(int id) {
		return serviceRecordDAO.getServiceRecordById(id);
	}
	
	public ServiceRecord updateServiceRecord(ServiceRecord newRecord) {
		return serviceRecordDAO.updateServiceRecord(newRecord);
	}
	
	public boolean removeServiceRecord(ServiceRecord record) {
		return serviceRecordDAO.removeServiceRecord(record);
	}
	
}
