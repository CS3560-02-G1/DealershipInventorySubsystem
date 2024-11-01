package DealershipInventorySubsystem;
public class DealershipStats {
    private int monthlySales;
    private int yearlySales;
    private String popularModels;
    private String highDemandVehicles;
    private double grossSales;
    private boolean lowStockAlerts;

    // constructor for dealership stats with sales and alert info
    public DealershipStats(int monthlySales, int yearlySales, String popularModels, String highDemandVehicles, double grossSales, boolean lowStockAlerts) {
        this.monthlySales = monthlySales;
        this.yearlySales = yearlySales;
        this.popularModels = popularModels;
        this.highDemandVehicles = highDemandVehicles;
        this.grossSales = grossSales;
        this.lowStockAlerts = lowStockAlerts;
    }

    // check if low stock alert is on
    public boolean isLowStockAlert() {
        return lowStockAlerts;
    }

    // getters and setters can be added
}
