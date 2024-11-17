package DealershipInventorySubsystem;

// represents a customer in the dealership system
public class Customer {
	private int customerId;
    private String firstName;
    private String lastName;
    private String phoneNumber; // customer's contact phone number
    private String address; // customer's home address
    private String email; // customer's email address

    // initializes a customer with name, phone, address, and email
    public Customer(String firstName, String lastName, String phoneNumber, String address, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
    }
    
    public Customer(int id, String firstName, String lastName, String phoneNumber, String address, String email) {
    	customerId = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.email = email;
    }
    
    public void setId(int id) {
    	this.customerId = id;
    }
    
    public int getId() {
    	return customerId;
    }
    
    public String getFullName() {
    	return firstName + " " + lastName;
    }
    
    public String getFirstName() {
    	return firstName;
    }

    public void setFirstName(String newName) {
        this.firstName = newName;
    }
    
    public String getLastName() {
    	return lastName;
    }
    
    public void setLastName(String newName) {
        this.lastName = newName;
    }

    public void setPhoneNumber(String newPhone) {
        this.phoneNumber = newPhone;
    }
    
    public String getPhone() {
    	return phoneNumber;
    }

    public void setAddress(String newAddress) {
        this.address = newAddress;
    }
    
    public String getAddress() {
    	return address;
    }

    public void setEmail(String newEmail) {
        this.email = newEmail;
    }
    
    public String getEmail() {
    	return email;
    }
}
