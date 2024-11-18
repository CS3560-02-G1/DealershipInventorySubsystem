package DealershipInventorySubsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TransactionDAO {
	public Sale insertSale(Sale sale) {
		Connection connection = null;
		String query = "INSERT INTO Sale(TransactionID, downPayment) VALUES (?, ?)";
		try {
			String vin = sale.getVehicle().getVin();
			int customerId = sale.getCustomer().getId(); 
			int transactionId = insertTransaction(vin, customerId, sale.getDate(), sale.getTax(), sale.getPaymentMethod());
			
			
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			statement.setInt(1, transactionId);
			statement.setInt(2, sale.getDownPayment());

			statement.executeUpdate();
			
			sale.setId(transactionId);
			
			return sale;
			
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
	
	public Lease insertLease(Lease lease) {
		Connection connection = null;
		String query = "INSERT INTO Lease(TransactionID, leasePeriod, monthlyFee) VALUES (?, ?, ?)";
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
			return lease;
			
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
	
	public Sale getSaleById(int id) {
		Connection connection = null;
		String query = "SELECT * FROM Sale INNER JOIN Transaction ON Sale.TransactionID = Transaction.TransactionID WHERE Sale.TransactionID = ?";
		try {
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setInt(1, id);
			
			ResultSet rs = statement.executeQuery();
			
			if (!rs.next()) {
				return null;
			}
			
			String date = rs.getString("date");
			double tax = rs.getDouble("tax");
			String paymentMethod = rs.getString("paymentMethod");
			
			VehicleDAO vehicleDAO = new VehicleDAO();
			Vehicle vehicle = vehicleDAO.getVehicleById(rs.getString("VIN"));
			
			CustomerDAO customerDAO = new CustomerDAO();
			Customer customer = customerDAO.getCustomerById(rs.getInt("CustomerID"));
			
			int downPayment = rs.getInt("downPayment");
			
			return new Sale(id, date, tax, paymentMethod, vehicle, customer, downPayment);
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
	
	public Lease getLeaseById(int id) {
		Connection connection = null;
		String query = "SELECT * FROM Lease INNER JOIN Transaction ON Lease.TransactionID = Transaction.TransactionID WHERE Lease.TransactionID = ?";
		try {
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setInt(1, id);
			
			ResultSet rs = statement.executeQuery();
			
			if (!rs.next()) {
				return null;
			}
			
			String date = rs.getString("date");
			double tax = rs.getDouble("tax");
			String paymentMethod = rs.getString("paymentMethod");
			
			VehicleDAO vehicleDAO = new VehicleDAO();
			Vehicle vehicle = vehicleDAO.getVehicleById(rs.getString("VIN"));
			
			CustomerDAO customerDAO = new CustomerDAO();
			Customer customer = customerDAO.getCustomerById(rs.getInt("CustomerID"));
			
			int leasePeriod = rs.getInt("leasePeriod");
			int monthlyFee = rs.getInt("monthlyFee");
			
			return new Lease(id, date, tax, paymentMethod, vehicle, customer, leasePeriod, monthlyFee);
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
	
	public Sale updateSale(Sale sale) {
		Connection connection = null;
		String query = "UPDATE Sale SET downPayment = ? WHERE TransactionID = ?";
		try {
			updateTransaction(sale);
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setInt(1, sale.getDownPayment());
			statement.setInt(2, sale.getId());
			
			statement.executeUpdate();
			return sale;
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
	
	public Lease updateLease(Lease lease) {
		Connection connection = null;
		String query = "UPDATE Lease SET leasePeriod = ?, monthlyFee = ? WHERE TransactionID = ?";
		try {
			updateTransaction(lease);
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setInt(1, lease.getLeasePeriod());
			statement.setInt(2, lease.getMonthlyFee());
			statement.setInt(3, lease.getId());
			
			statement.executeUpdate();
			return lease;
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
	
	public Transaction getTransactionById(int id) {
		Sale sale = getSaleById(id);
		if (sale != null) {
			return sale;
		}
		
		Lease lease = getLeaseById(id);
		return lease;	//Will return null if lease not found
	}
	
	//Private function to insert a transaction, since a transaction is an abtract class and can't be instantiated
	//But it is still needed to keep track of sales and leases
	private static int insertTransaction(String vin, int customerId, String date, double tax, String paymentMethod) {
		Connection connection = null;
		String query = "INSERT INTO Transaction(VIN, customerID, date, tax, paymentMethod) VALUES(?, ?, ?, ?, ?)";
		
		try {
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			
			statement.setString(1, vin);
			statement.setInt(2, customerId);
			statement.setString(3, date);
			statement.setDouble(4, tax);
			statement.setString(5, paymentMethod);

			int affectedRows = statement.executeUpdate();

			if (affectedRows == 0) {
				throw new SQLException("Insert Failed, No Rows Affected");
			}
			
			try (ResultSet rs = statement.getGeneratedKeys()) {
				if (rs.next()) {
					return rs.getInt(1);
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
		
		return -1;
	}
	
	private Transaction updateTransaction(Transaction newTransaction) {
		Connection connection = null;
		String query = "UPDATE Transaction SET VIN = ?, CustomerID = ?, date = ?, tax = ?, paymentMethod = ? WHERE TransactionID = ?";
		try {
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setString(1, newTransaction.getVehicle().getVin());
			statement.setInt(2, newTransaction.getCustomer().getId());
			statement.setString(3, newTransaction.getDate());
			statement.setDouble(4, newTransaction.getTax());
			statement.setString(5, newTransaction.getPaymentMethod());
			statement.setInt(6, newTransaction.getId());
			
			statement.executeUpdate();
			return newTransaction;
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
	
	//Should delete the sale/lease as well, since it is on delete cascade
	public boolean removeTransaction(Transaction transaction) {
		Connection connection = null;
		String query = "DELETE FROM Transaction WHERE TransactionID = ?";
		try {
			connection = JDBCMySQLConnection.getConnection();
			PreparedStatement statement = connection.prepareStatement(query);
			
			statement.setInt(1, transaction.getId());
			
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
