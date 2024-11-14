package DealershipInventorySubsystem;
import java.sql.*;

public class Tester {
	public static void main(String[] args) {
		int inventoryID = 1;
		
		Tester tester = new Tester();
		Inventory inventory = tester.getInventory(inventoryID);
		System.out.println(inventory.getType());
	}
	
	public Inventory getInventory(int inventoryID) {
		ResultSet rs = null;
		Connection connection = null;
		Statement statement = null;
		
		Inventory inventory = null;
		String query = "SELECT * FROM Inventory WHERE InventoryID=" + inventoryID;
		try {
			connection = JDBCMySQLConnection.getConnection();
			statement = connection.createStatement();
			rs = statement.executeQuery(query);
			
			if (rs.next()) {
				String type = rs.getString("type");
				int vehicleCount = rs.getInt("vehicleCount");
				inventory = new Inventory(type, vehicleCount);
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
		
		return inventory;
	}
}
