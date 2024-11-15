package DealershipInventorySubsystem;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// represents a sales agent in dealership
public class SalesAgent {
	private int agentId;
	private String firstName;
    private String lastName; // name of sales agent
    private String email; // contact email of agent
    private String phoneNumber; // contact phone number

    // initializes sales agent with name, email, and phone number
    public SalesAgent(String firstName, String lastName, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
    
    public SalesAgent(int id) {
    	String query = "SELECT * FROM SalesAgent WHERE AgentID=" + id;
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
			
			this.agentId = rs.getInt("AgentID");
			this.firstName = rs.getString("firstName");
			this.lastName = rs.getString("lastName");
			this.email = rs.getString("email");
			this.phoneNumber = rs.getString("phoneNumber");
			
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

    
    //Getters / Setters
    public int getId() {
    	return agentId;
    }
    
    public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
