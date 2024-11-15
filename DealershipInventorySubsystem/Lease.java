package DealershipInventorySubsystem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// represents a lease transaction
public class Lease extends Transaction {
    private int leasePeriod; // length of lease period in months
    private int monthlyFee; // monthly payment for lease

    // initializes lease 
    public Lease(String date, double tax, String paymentMethod, int leasePeriod, int monthlyFee) {
        super(date, tax, paymentMethod);
        this.leasePeriod = leasePeriod;
        this.monthlyFee = monthlyFee;
    }
    
    public Lease(int id) {
    	super(id);
    	String query = "SELECT * FROM Lease WHERE TransactionID=" + id;
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
			
			this.leasePeriod = rs.getInt("leasePeriod");
			this.monthlyFee = rs.getInt("monthlyFee");
			
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

    // calculates total cost of lease
    public double calculateTotalLeaseCost() {
        return leasePeriod * monthlyFee;
    }

    // checks if lease is still active
    public boolean isLeaseActive() {
        return leasePeriod > 0;
    }

    // returns remaining lease period in months
    public int getRemainingLeasePeriod() {
        return leasePeriod;
    }

    //Getters / Setters
	public int getLeasePeriod() {
		return leasePeriod;
	}

	public void setLeasePeriod(int leasePeriod) {
		this.leasePeriod = leasePeriod;
	}

	public int getMonthlyFee() {
		return monthlyFee;
	}

	public void setMonthlyFee(int monthlyFee) {
		this.monthlyFee = monthlyFee;
	}
    
    
}
