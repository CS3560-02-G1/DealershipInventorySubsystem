package DealershipInventorySubsystem;

// represents a customer in the dealership system
public class Customer {
    private String name; // name of customer
    private String phoneNumber; // customer's contact phone number
    private String address; // customer's home address
    private String email; // customer's email address

    // initializes a customer with name, phone, address, and email
    public Customer(String name, String phoneNumber, String address, String email) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
    }

    // updates customer's contact information
    public void updateContactInfo(String newPhone, String newEmail) {
        this.phoneNumber = newPhone;
        this.email = newEmail;
    }

    // returns customer's contact information 
    public String getContactInfo() {
        return name + " | " + phoneNumber + " | " + email;
    }

    // checks if email is valid
    public boolean isEmailValid() {
        return email.contains("@") && email.contains(".");
    }
}
