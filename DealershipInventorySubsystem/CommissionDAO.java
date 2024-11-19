package DealershipInventorySubsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CommissionDAO {
	TransactionDAO transactionDAO;
	SalesAgentDAO salesAgentDAO;
	
	public CommissionDAO() {
		transactionDAO = new TransactionDAO();
		salesAgentDAO = new SalesAgentDAO();
	}
	
	public Commission insertCommission(Commission commission) {
		Connection connection = null;
		String query = "INSERT INTO Commission(commissionRate, paymentDate, TransactionID, AgentID) VALUES (?, ?, ?, ?)";
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
		String query = "SELECT * FROM Commission WHERE TransactionID = ? AND AgentID = ?";
		try {
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setInt(1, transactionId);
			statement.setInt(2, agentId);
			
			ResultSet rs = statement.executeQuery();
			
			if (!rs.next()) {
				return null;
			}
			
			double commissionRate = rs.getDouble("commissionRate");
			String paymentDate = rs.getString("paymentDate");
			
			Transaction transaction = transactionDAO.getTransactionById(transactionId);
			
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
	
	public List<Commission> getAllCommissionsForTransaction(Transaction transaction) {
		List<Commission> commissions = new ArrayList<>();
		Connection connection = null;
		String query = "SELECT * FROM Commission WHERE TransactionID = ?";
		try {
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setInt(1, transaction.getId());
			
			ResultSet rs = statement.executeQuery();
			
			while (rs.next()) {
				double commissionRate = rs.getDouble("commissionRate");
				String paymentDate = rs.getString("paymentDate");
				
				SalesAgent agent = salesAgentDAO.getSalesAgentById(rs.getInt("AgentID"));
				
				commissions.add(new Commission(transaction, agent, commissionRate, paymentDate));
			}
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
		
		return commissions;
	}
	
	public Commission updateCommission(Commission newCommission) {
		Connection connection = null;
		String query = "UPDATE Commission SET commissionRate = ?, paymentDate = ? WHERE TransactionID = ? AND AgentID = ?";
		try {
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setDouble(1, newCommission.getCommissionRate());
			statement.setString(2, newCommission.getPaymentDate());
			statement.setInt(3, newCommission.getTransaction().getId());
			statement.setInt(4, newCommission.getSalesAgent().getId());
			
			statement.executeUpdate();
			return newCommission;
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
		
		return null;
	}
	
	//Returns true on delete / and false on unsuccessful
	public boolean removeCommission(Commission commission) {
		Connection connection = null;
		String query = "DELETE FROM Commission WHERE TransactionID = ? AND AgentID = ?";
		try {
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setInt(1, commission.getTransaction().getId());
			statement.setInt(2, commission.getSalesAgent().getId());
			
			int rs = statement.executeUpdate();
			
			if (rs == 0) {
				return false;
			}
			
			return true;
			
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
		
		return false;
		
	}
}
