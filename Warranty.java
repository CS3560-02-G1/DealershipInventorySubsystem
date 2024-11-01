package DealershipInventorySubsystem;
public class Warranty {
    private String type;
    private int duration;
    private String policy;
    private double price;
    private double coverageLimit;

    // constructor for warranty with type, duration, policy, price, and coverage limit
    public Warranty(String type, int duration, String policy, double price, double coverageLimit) {
        this.type = type;
        this.duration = duration;
        this.policy = policy;
        this.price = price;
        this.coverageLimit = coverageLimit;
    }

    // getters and setters can be added if needed
}
