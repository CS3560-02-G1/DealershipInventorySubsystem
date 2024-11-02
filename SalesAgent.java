package DealershipInventorySubsystem;

// represents a sales agent in dealership
public class SalesAgent {
    private String name; // name of sales agent
    private String email; // contact email of agent
    private String phoneNumber; // contact phone number

    // initializes sales agent with name, email, and phone number
    public SalesAgent(String name, String email, String phoneNumber) {
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // update the email of a sales agent
    public void setEmail(String newEmail) {
        this.email = newEmail;
    }

    // update the phone number of a sales agent
    public void setPhoneNumber(String newPhone) {
        this.phoneNumber = newPhone;
    }

    // checks if phone number is valid
    public boolean isPhoneNumberValid() {
        return phoneNumber.length() == 10;
    }

    // returns summary of sales agent information
    public String getSalesAgentSummary() {
        return name + " | " + email + " | " + phoneNumber;
    }
}
