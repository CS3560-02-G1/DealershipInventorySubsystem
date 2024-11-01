package DealershipInventorySubsystem;
public class Inventory {
    private int totalVehicles;
    private int availableVehicles;
    private boolean lowStockAlert;

    // constructor for inventory with vehicle counts
    public Inventory(int totalVehicles, int availableVehicles, boolean lowStockAlert) {
        this.totalVehicles = totalVehicles;
        this.availableVehicles = availableVehicles;
        this.lowStockAlert = lowStockAlert;
    }

    // check if inventory is low
    public boolean checkLowStock() {
        return availableVehicles < 5;
    }

    // getters and setters can be added 
}
