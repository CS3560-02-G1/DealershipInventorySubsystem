package DealershipInventorySubsystem;

// represents a delivery order for vehicles
public class DeliveryOrder {
	private int deliveryId;
    private String sourceLocation; // origin location of vehicle
    private String status; // current status of delivery
    private String arrivalDate; // expected date of arrival

    // initializes a delivery order with source location, status, and arrival date
    public DeliveryOrder(String sourceLocation, String status, String arrivalDate) {
        this.sourceLocation = sourceLocation;
        this.status = status;
        this.arrivalDate = arrivalDate;
    }
    
    public DeliveryOrder(int id, String sourceLocation, String status, String arrivalDate) {
    	deliveryId = id;
        this.sourceLocation = sourceLocation;
        this.status = status;
        this.arrivalDate = arrivalDate;
    }
    
    

    // estimates delivery time based on source location
    public int estimateDeliveryTime() {
        return sourceLocation.equalsIgnoreCase("local") ? 1 : 5;
    }
    
    //Getters / Setters
	public int getId() {
		return deliveryId;
	}

	public void setId(int deliveryId) {
		this.deliveryId = deliveryId;
	}

	public String getSourceLocation() {
		return sourceLocation;
	}

	public void setSourceLocation(String sourceLocation) {
		this.sourceLocation = sourceLocation;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getArrivalDate() {
		return arrivalDate;
	}

	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
    
    
}
