package DealershipInventorySubsystem;
public class SalesRecord {
    private String saleDate;
    private double salePrice;
    private double profit;
    private int salesPersonID;
    private int customerID;
    private String vehicleID;

    // constructor for sales record with sale details
    public SalesRecord(String saleDate, double salePrice, double profit, int salesPersonID, int customerID, String vehicleID) {
        this.saleDate = saleDate;
        this.salePrice = salePrice;
        this.profit = profit;
        this.salesPersonID = salesPersonID;
        this.customerID = customerID;
        this.vehicleID = vehicleID;
    }

    // display sales record
    public String displayRecord() {
        return "sale date: " + saleDate + ", sale price: $" + salePrice + ", profit: $" + profit;
    }

    // getters and setters can be added
}
