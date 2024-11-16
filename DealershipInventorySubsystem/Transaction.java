package DealershipInventorySubsystem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// represents a general transaction in the dealership
public abstract class Transaction {
	protected int transactionId;
    protected String date; // date of transaction
    protected double tax; // tax applied to transaction
    protected String paymentMethod; // payment method used
    protected Vehicle vehicle;
    protected Customer customer;

    // initializes transaction with date, tax, total amount, and payment method
    public Transaction(String date, double tax, String paymentMethod, Vehicle vehicle, Customer customer) {
        this.date = date;
        this.tax = tax;
        this.paymentMethod = paymentMethod;
        this.vehicle = vehicle;
        this.customer = customer;
    }
    

	public Transaction(int id) {
    	String query = "SELECT * FROM Transaction WHERE TransactionID=" + id;
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
			
			this.transactionId = rs.getInt("TransactionID");
			this.date = rs.getString("date");
			this.tax = rs.getInt("tax");
			this.paymentMethod = rs.getString("paymentMethod");
			
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

    // checks if transaction is taxable
    public boolean isTaxable() {
        return tax > 0;
    }
    
    //Getters / Setters
    public void setId(int id) {
    	transactionId = id;
    }
    
    public int getId() {
    	return transactionId;
    }
    
    public void setVehicle(Vehicle vehicle) {
    	this.vehicle = vehicle;
    }
    
    public Vehicle getVehicle() {
    	return vehicle;
    }
    
    public void setCustomer(Customer customer) {
    	this.customer = customer;
    }
    
    public Customer getCustomer() {
    	return customer;
    }
    
    public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
}
