package DealershipInventorySubsystem;
public class Salesperson {
    private String name;
    private int employeeID;
    private String salesHistory;

    // constructor for salesperson with name and id
    public Salesperson(String name, int employeeID, String salesHistory) {
        this.name = name;
        this.employeeID = employeeID;
        this.salesHistory = salesHistory;
    }

    // add a sale to history
    public void addSale(String sale) {
        this.salesHistory += ", " + sale;
    }

    // getters and setters can be added
}
