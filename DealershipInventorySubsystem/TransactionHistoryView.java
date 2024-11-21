package DealershipInventorySubsystem;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class TransactionHistoryView extends JFrame {
	private JTable transactionTable;
	private DefaultTableModel tableModel;
	private TransactionBUS transactionBUS;
	private VehicleBUS vehicleBUS;
	
	public TransactionHistoryView() {
		this.transactionBUS = new TransactionBUS();
		this.vehicleBUS = new VehicleBUS();
		setTitle("Transaction History");
		setSize(800, 400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        String[] columnNames = {"Transaction ID", "Date", "Type", "Vehicle VIN", "Customer ID", "Tax", "Payment Method", "Down Payment", "Lease Period", "Monthly Fee"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Prevent editing
            }
        };
        
        transactionTable = new JTable(tableModel);
        add(new JScrollPane(transactionTable), BorderLayout.CENTER);
        
        transactionTable.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		if (e.getClickCount() == 2) {
        			int selectedRow = transactionTable.getSelectedRow();
        			int selectedColumn = transactionTable.getSelectedColumn();
        			if (selectedRow != -1 && selectedColumn == 3) {
        				String vin = (String) tableModel.getValueAt(selectedRow, selectedColumn);
        				Vehicle vehicle = vehicleBUS.getVehicleByVIN(vin);
        				new VehicleInfoView(vehicle);
        			}
        		}
        	}
		});

        // Fetch and populate transaction data
        loadTransactionData();
        
        setVisible(true);
	}
	
	private void loadTransactionData() {
		List<Transaction> transactions = transactionBUS.getAllTransactions();
		
		for (Transaction transaction : transactions) {
			int id = transaction.getId();
			String date = transaction.getDate();
			String vin = transaction.getVehicle().getVin();
			int customerId = transaction.getCustomer().getId();
			double tax = transaction.getTax();
			String paymentMethod = transaction.getPaymentMethod();
			
			String type = transaction instanceof Sale ? "Sale" : "Lease";
			if (type.equals("Sale")) {
				Sale sale = (Sale) transaction;
				int downPayment = sale.getDownPayment();
				Object[] rowData = new Object[] {id, date, type, vin, customerId, tax, paymentMethod, downPayment, null, null};
				tableModel.addRow(rowData);
			} else {
				Lease lease = (Lease) transaction;
				int leasePeriod = lease.getLeasePeriod();
				int monthlyFee = lease.getMonthlyFee();
				Object[] rowData = new Object[] {id, date, type, vin, customerId, tax, paymentMethod, null, leasePeriod, monthlyFee};
				tableModel.addRow(rowData);
			}
		}
	}
}
