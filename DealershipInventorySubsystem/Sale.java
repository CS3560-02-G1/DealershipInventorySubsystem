package DealershipInventorySubsystem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// represents a sale transaction
public class Sale extends Transaction {
    private int downPayment; // down payment amount for sale

    // initializes sale with transaction details and down payment
    public Sale(String date, double tax, String paymentMethod, Vehicle vehicle, Customer customer, int downPayment) {
        super(date, tax, paymentMethod, vehicle, customer);
        this.downPayment = downPayment;
    }
    
    public Sale(int id) {
    	super(id);
    	String query = "SELECT * FROM Sale WHERE TransactionID=" + id;
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
			
			this.downPayment = rs.getInt("downPayment");
			
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

	public int getDownPayment() {
		return downPayment;
	}

	public void setDownPayment(int downPayment) {
		this.downPayment = downPayment;
	}

    
}
