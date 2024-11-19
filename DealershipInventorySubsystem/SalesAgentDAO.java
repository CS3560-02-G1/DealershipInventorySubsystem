package DealershipInventorySubsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class SalesAgentDAO {
	public SalesAgent insertSalesAgent(SalesAgent agent) {
		Connection connection = null;
		String query = "INSERT INTO SalesAgent(firstName, lastName, email, phoneNumber) VALUES (?, ?, ?, ?)";
		try {
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			statement.setString(1, agent.getFirstName());
			statement.setString(2, agent.getLastName());
			statement.setString(3, agent.getEmail());
			statement.setString(4, agent.getPhoneNumber());

			int affectedRows = statement.executeUpdate();
			
			if (affectedRows == 0) {
				throw new SQLException("Insert Failed, No Rows Affected");
			}
			
			try (ResultSet rs = statement.getGeneratedKeys()) {
				if (rs.next()) {
					agent.setId(rs.getInt(1));
					return agent;
				} else {
					throw new SQLException("Creation Failed, no ID obtained");
				}
			}
			
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
	
	public SalesAgent getSalesAgentById(int id) {
		Connection connection = null;
		String query = "SELECT * FROM SalesAgent WHERE AgentID = ?";
		try {
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setInt(1, id);
			
			ResultSet rs = statement.executeQuery();
			
			if (!rs.next()) {
				return null;
			}
			
			String firstName = rs.getString("firstName");
			String lastName = rs.getString("lastName");
			String email = rs.getString("email");
			String phoneNumber = rs.getString("phoneNumber");
			
			return new SalesAgent(id, firstName, lastName, email, phoneNumber);
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
	
	public List<SalesAgent> getAllSalesAgents() {
		List<SalesAgent> salesAgents = new ArrayList<>();
		Connection connection = null;
		String query = "SELECT * FROM SalesAgent";
		try {
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			
			ResultSet rs = statement.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt("AgentID");
				String firstName = rs.getString("firstName");
				String lastName = rs.getString("lastName");
				String email = rs.getString("email");
				String phoneNumber = rs.getString("phoneNumber");
				
				salesAgents.add(new SalesAgent(id, firstName, lastName, email, phoneNumber));
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
		
		return salesAgents;
	}
	
	public SalesAgent updateSalesAgent(SalesAgent newAgent) {
		Connection connection = null;
		String query = "UPDATE SalesAgent SET firstName = ?, lastName = ?, email = ?, phoneNumber = ? WHERE AgentID = ?";
		try {
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setString(1, newAgent.getFirstName());
			statement.setString(2, newAgent.getLastName());
			statement.setString(3, newAgent.getEmail());
			statement.setString(4, newAgent.getPhoneNumber());
			statement.setInt(5, newAgent.getId());
			
			statement.executeUpdate();
			return newAgent;
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
	public boolean removeSalesAgent(SalesAgent agent) {
		Connection connection = null;
		String query = "DELETE FROM SalesAgent WHERE AgentID = ?";
		try {
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setInt(1, agent.getId());
			
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
