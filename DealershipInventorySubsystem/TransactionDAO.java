package DealershipInventorySubsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class TransactionDAO {
	public int insertSale(Sale sale) {
		Connection connection = null;
		String query = "INSERT IGNORE INTO Sale(TransactionID, downPayment) VALUES (?, ?)";
		try {
			String vin = sale.getVehicle().getVin();
			int customerId = sale.getCustomer().getId(); 
			int transactionId = insertTransaction(vin, customerId, sale.getDate(), sale.getTax(), sale.getPaymentMethod());
			
			
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			statement.setInt(1, transactionId);
			statement.setInt(2, sale.getDownPayment());

			int newId = statement.executeUpdate();
			sale.setId(newId);
			return newId;
			
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
		
		return -1; //Returns -1 as id if it fails
	}
	
	public int insertLease(Lease lease) {
		Connection connection = null;
		String query = "INSERT IGNORE INTO Lease(TransactionID, leasePeriod, monthlyFee) VALUES (?, ?, ?)";
		try {
			String vin = lease.getVehicle().getVin();
			int customerId = lease.getCustomer().getId(); 
			int transactionId = insertTransaction(vin, customerId, lease.getDate(), lease.getTax(), lease.getPaymentMethod());
			
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			statement.setInt(1, transactionId);
			statement.setInt(2, lease.getLeasePeriod());
			statement.setInt(3, lease.getMonthlyFee());

			int newId = statement.executeUpdate();
			lease.setId(newId);
			return newId;
			
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
		
		return -1; //Returns -1 as id if it fails
	}
	
	//Private function to insert a transaction, since a transaction is an abtract class and can't be instantiated
	//But it is still needed to keep track of sales and leases
	private static int insertTransaction(String vin, int customerId, String date, double tax, String paymentMethod) {
		Connection connection = null;
		String query = "INSERT IGNORE INTO Transaction(VIN, customerID, date, tax, paymentMethod) VALUES(?, ?, ?, ?, ?)";
		
		try {
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			statement.setString(1, vin);
			statement.setInt(2, customerId);
			statement.setString(3, date);
			statement.setDouble(4, tax);
			statement.setString(5, paymentMethod);

			return statement.executeUpdate();
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
		
		return -1;
	}
}
