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

    public void setName(String newName) {
        this.name = newName;
    }

    // update customer's phone number
    public void setPhoneNumber(String newPhone) {
        this.phoneNumber = newPhone;
    }

    // update customer's address
    public void setAddress(String newAddress) {
        this.address = newAddress;
    }

    // update customer's email
    public void setEmail(String newEmail) {
        this.email = newEmail;
    }

    // returns customer's contact information 
    public String getContactInfo() {
        return name + " | " + phoneNumber + " | " + email;
    }

    // checks if email is valid
    // may need to use regex in the future or just verify email when it is typed into the system
    public boolean isEmailValid() {
        return email.contains("@") && email.contains(".");
    }
}
