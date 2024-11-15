package DealershipInventorySubsystem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
    
    public Customer(int id) {
    	String query = "SELECT * FROM Customer WHERE CustomerID=" + id;
		ResultSet rs = null;
		Connection connection = null;
		Statement statement = null;
		
		try {
			connection = JDBCMySQLConnection.getConnection();
			statement = connection.createStatement();
			rs = statement.executeQuery(query);
			
			if (!rs.next()) {
				throw new SQLException("No One Found With Query: " + query);
			}
			
			this.customerId = rs.getInt("CustomerID");
			this.firstName = rs.getString("firstName");
			this.lastName = rs.getString("lastName");
			this.phoneNumber = rs.getString("phoneNumber");
			this.address = rs.getString("address");
			this.email = rs.getString("email");
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
    }
    
    public int getId() {
    	return customerId;
    }
    
    public String getFullName() {
    	return firstName + " " + lastName;
    }

    public void setFirstName(String newName) {
        this.firstName = newName;
    }
    
    public void setLastName(String newName) {
        this.lastName = newName;
    }

    // update customer's phone number
    public void setPhoneNumber(String newPhone) {
        this.phoneNumber = newPhone;
    }
    
    public String getPhone() {
    	return phoneNumber;
    }

    // update customer's address
    public void setAddress(String newAddress) {
        this.address = newAddress;
    }
    
    public String getAddress() {
    	return address;
    }

    // update customer's email
    public void setEmail(String newEmail) {
        this.email = newEmail;
    }
    
    public String getEmail() {
    	return email;
    }
}
