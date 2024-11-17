package DealershipInventorySubsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CommissionDAO {
	public Commission insertCommission(Commission commission) {
		Connection connection = null;
		String query = "INSERT INTO Comission(comissionRate, paymentDate, TransactionID, AgentID) VALUES (?, ?, ?, ?)";
		try {
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			statement.setDouble(1, commission.getCommissionRate());
			statement.setString(2, commission.getPaymentDate());
			statement.setInt(3, commission.getTransaction().getId());
			statement.setDouble(4, commission.getSalesAgent().getId());


			statement.executeUpdate();
			return commission;
			
		} catch(SQLException e) {
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
		
		return null; //Returns null if it fails
	}
	
	public Commission getCommissionById(int transactionId, int agentId) {
		Connection connection = null;
		String query = "SELECT * FROM Comission WHERE TransactionID = ? AND AgentID = ?";
		try {
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setInt(1, transactionId);
			statement.setInt(2, agentId);
			
			ResultSet rs = statement.executeQuery();
			
			if (!rs.next()) {
				return null;
			}
			
			double commissionRate = rs.getDouble("comissionRate");
			String paymentDate = rs.getString("paymentDate");
			
			TransactionDAO transactionDAO = new TransactionDAO();
			Transaction transaction = transactionDAO.getTransactionById(transactionId);
			
			SalesAgentDAO salesAgentDAO = new SalesAgentDAO();
			SalesAgent agent = salesAgentDAO.getSalesAgentById(agentId);
			
			return new Commission(transaction, agent, commissionRate, paymentDate);
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
		
		return null;
	}
}
