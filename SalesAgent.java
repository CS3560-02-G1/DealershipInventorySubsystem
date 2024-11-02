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

    // updates contact information for sales agent
    public void updateContactInfo(String newEmail, String newPhone) {
        this.email = newEmail;
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
