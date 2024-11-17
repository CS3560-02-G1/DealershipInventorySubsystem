package DealershipInventorySubsystem;

// represents a sales agent in dealership
public class SalesAgent {
	private int agentId;
	private String firstName;
    private String lastName; // name of sales agent
    private String email; // contact email of agent
    private String phoneNumber; // contact phone number

    // initializes sales agent with name, email, and phone number
    public SalesAgent(String firstName, String lastName, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
    
    public SalesAgent(int id, String firstName, String lastName, String email, String phoneNumber) {
    	this.agentId = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    
    //Getters / Setters
    public int getId() {
    	return agentId;
    }
    
    public void setId(int id) {
    	agentId = id;
    }
    
    public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
