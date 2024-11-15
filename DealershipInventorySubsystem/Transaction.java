package DealershipInventorySubsystem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// represents a general transaction in the dealership
public abstract class Transaction {
	protected int transactionId;
	protected String vin;
	protected int customerId;
    protected String date; // date of transaction
    protected double tax; // tax applied to transaction
    protected String paymentMethod; // payment method used

    // initializes transaction with date, tax, total amount, and payment method
    public Transaction(String date, double tax, String paymentMethod) {
        this.date = date;
        this.tax = tax;
        this.paymentMethod = paymentMethod;
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
			this.vin = rs.getString("VIN");
			this.customerId = rs.getInt("CustomerID");
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
    public int getId() {
    	return transactionId;
    }
    
    public String getVIN() {
    	return vin;
    }
    
    public int getCustomerId() {
    	return customerId;
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
