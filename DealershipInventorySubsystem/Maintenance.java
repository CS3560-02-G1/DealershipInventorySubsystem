package DealershipInventorySubsystem;

// represents a maintenance record
public class Maintenance {
	private int maintenanceId;
    private String type; // type of maintenance 
    private String details; // specific details of maintenance

    // initializes maintenance with type and details
    public Maintenance(String type, String details) {
        this.type = type;
        this.details = details;
    }
    
    public Maintenance(int id, String type, String details) {
    	this.maintenanceId = id;
        this.type = type;
        this.details = details;
    }
    
    //Getters / Setters
    public void setId(int id) {
    	maintenanceId = id;
    }
    
    public int getId() {
    	return this.maintenanceId;
    }

    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	// checks if maintenance is marked as urgent
    public boolean isUrgent() {
        return type.equalsIgnoreCase("urgent");
    }

    // returns summary of maintenance record
    public String getMaintenanceSummary() {
        return type + ": " + details;
    }
}
