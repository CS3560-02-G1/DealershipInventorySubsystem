package DealershipInventorySubsystem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// represents a commission structure for sales agents
public class Commission {
	private int transactionId;
	private int agentId;
    private double commissionRate; // percentage of commission earned
    private String paymentDate; // date of commission payment

    // initializes a commission with rate and payment date
    public Commission(double commissionRate, String paymentDate) {
        this.commissionRate = commissionRate;
        this.paymentDate = paymentDate;
    }
    
    public Commission(int transactionId, int agentId) {
    	String query = "SELECT * FROM Comission WHERE TransactionID=" + transactionId + " AND AgentID=" + agentId;
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
			this.agentId = rs.getInt("AgentID");
			this.commissionRate = rs.getDouble("comissionRate");
			this.paymentDate = rs.getString("paymentDate");
			
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

    // calculates commission based on sale amount
    public double calculateCommission(double saleAmount) {
        return saleAmount * commissionRate;
    }

    // updates commission rate
    public void updateCommissionRate(double newRate) {
        this.commissionRate = newRate;
    }

    // updates the day that the sales agent gets paid / was paid
    public void updatePaymentDate(String newDate) {
        this.paymentDate = newDate;
    }

    // Getters / Setters
    
    public int getTransactionId() {
		return transactionId;
	}

	public int getAgentId() {
		return agentId;
	}
	
    public String getPaymentDate() {
        return paymentDate;
    }
    
    public void setPaymentDate(String paymentDate) {
		this.paymentDate = paymentDate;
	}

	public double getCommissionRate() {
		return commissionRate;
	}

	public void setCommissionRate(double commissionRate) {
		this.commissionRate = commissionRate;
	}
    
}
