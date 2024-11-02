package DealershipInventorySubsystem;

// represents vehicle inventory for dealership
public class Inventory {
    private String type; // type of inventory 
    private int vehicleCount; // number of vehicles in inventory

    // initializes inventory with type and vehicle count
    public Inventory(String type, int vehicleCount) {
        this.type = type;
        this.vehicleCount = vehicleCount;
    }

    // updates count of vehicles in inventory
    public void updateVehicleCount(int newCount) {
        this.vehicleCount = newCount;
    }

    // incrementally updates the vehicle count
    public void addVehicle() {
        this.vehicleCount++;
    }

    // decrements the vehicle count for whenever a vehicle is removed from the inventory
    public void removeVehicle() {
        this.vehicleCount--;
    }

    // getter method for vehicle count
    public int getVehicleCount() {
        return this.vehicleCount;
    }

    // checks if inventory is low
    public boolean isLowStock() {
        return vehicleCount < 5;
    }

    // checks if inventory is empty
    public boolean isEmpty() {
        return vehicleCount == 0;
    }
}
