package DealershipInventorySubsystem;
import java.sql.*;

public class Tester {
	public static void main(String[] args) {
		Inventory inventory = new Inventory(1);
		Inventory inventory2 = new Inventory(2);
		System.out.println(inventory2.getId());
		System.out.println(inventory.getId() + "  " + inventory.getType() + "   " + inventory.getVehicleCount());
	}
}
