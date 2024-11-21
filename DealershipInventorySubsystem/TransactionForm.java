package DealershipInventorySubsystem;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.List;

public class TransactionForm extends JFrame{
	private JTable customerTable, salesAgentTable;
    private DefaultTableModel customerTableModel, salesAgentTableModel;
    private JTextField transactionDateField, transactionPriceField, transactionMethodField;
    private TransactionBUS transactionBUS;
    private VehicleBUS vehicleBUS;
    private JButton saveButton;
    private Vehicle vehicle;
    
    private JComboBox<String> transactionTypeDropdown;
    
    private JPanel dynamicPanel;
    private JTextField downPaymentField;
    private JTextField leasePeriodField;
    private JTextField monthlyFeeField;
    
    private InventoryView parentView;

    public TransactionForm(Vehicle vehicle, InventoryView parent) {
    	this.vehicle = vehicle;
    	this.transactionBUS = new TransactionBUS();
    	this.vehicleBUS = new VehicleBUS();
    	this.parentView = parent;
        setTitle("Transaction Form");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        add(mainPanel, BorderLayout.CENTER);

        // Customer Table Setup
        JLabel customerTitle = new JLabel("Select the Customer");
        customerTitle.setFont(new Font("Arial", Font.BOLD, 16));
        customerTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Padding
        mainPanel.add(customerTitle);
        
        customerTableModel = new DefaultTableModel(new String[] {"ID", "FirstName", "LastName", "PhoneNumber", "Address", "Email"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Prevent all cells from being edited
            }
        };
        
        customerTable = new JTable(customerTableModel);
        customerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Single row selection
        mainPanel.add(new JScrollPane(customerTable));
        
        // Sale Agent Table Setup
        JLabel salesAgentTitle = new JLabel("Select Sales Agents");
        salesAgentTitle.setFont(new Font("Arial", Font.BOLD, 16));
        salesAgentTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0)); // Padding
        mainPanel.add(salesAgentTitle);
        
        salesAgentTableModel = new DefaultTableModel(new String[] {"Select", "ID", "FirstName", "LastName", "Email", "PhoneNumber", "CommissionRate", "paymentDate"}, 0) {
        	@Override
            public Class<?> getColumnClass(int column) {
        		if (column == 0) {
                    return Boolean.class; // Checkbox column
                } else if (column == 1) {
                    return Integer.class; // ID column
                } else if (column == 6){
                    return Double.class; // Commission column
                } else {
                	return String.class; //All other columns
                }
            }
        	
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0 || column >= 6; // Allow the checkbox, commissionRate and paymentDate to be edited
            }
        };
        
        salesAgentTable = new JTable(salesAgentTableModel);
        mainPanel.add(new JScrollPane(salesAgentTable));
        
        JPanel typePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel typeLabel = new JLabel("Transaction Type:");
        transactionTypeDropdown = new JComboBox<>(new String[]{"Sale", "Lease"});
        typePanel.add(typeLabel);
        typePanel.add(transactionTypeDropdown);
        mainPanel.add(typePanel);
        
        dynamicPanel = new JPanel();
        dynamicPanel.setLayout(new BoxLayout(dynamicPanel, BoxLayout.Y_AXIS));
        mainPanel.add(dynamicPanel);
        
        showSaleFields();
        
        transactionTypeDropdown.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selectedType = (String) e.getItem();
                dynamicPanel.removeAll();
                if (selectedType.equals("Sale")) {
                    showSaleFields();
                } else if (selectedType.equals("Lease")) {
                    showLeaseFields();
                }
                dynamicPanel.revalidate();
                dynamicPanel.repaint();
            }
        });
        
        //Setup Transaction Input Panel
        JPanel transactionDetailsPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        transactionDetailsPanel.setBorder(BorderFactory.createTitledBorder("Transaction Details"));
        transactionDetailsPanel.add(new JLabel("Transaction Date (YYYY-MM-DD):"));
        transactionDateField = new JTextField();
        transactionDetailsPanel.add(transactionDateField);
        transactionDetailsPanel.add(new JLabel("Tax:"));
        transactionPriceField = new JTextField();
        transactionDetailsPanel.add(transactionPriceField);
        transactionDetailsPanel.add(new JLabel("Payment Method:"));
        transactionMethodField = new JTextField();
        transactionDetailsPanel.add(transactionMethodField);
        
        saveButton = new JButton("Save Transaction");
        saveButton.addActionListener(e -> saveTransaction());
     
        
        add(mainPanel, BorderLayout.CENTER);
        add(transactionDetailsPanel, BorderLayout.NORTH);
        add(saveButton, BorderLayout.SOUTH);
        
        
        // Fetch and populate Customers and SalesAgents
        loadCustomerData();
        loadSalesAgentData();

        setVisible(true);
    }
    
    private void loadCustomerData() {
    	List<Customer> customers = transactionBUS.getAllCustomers();
    	for (Customer customer : customers) {
    		customerTableModel.addRow(new Object[] {
    				customer.getId(),
    				customer.getFirstName(),
    				customer.getLastName(),
    				customer.getPhone(),
    				customer.getAddress(),
    				customer.getEmail()
    		});
    	}
    }
    
    private void loadSalesAgentData() {
    	List<SalesAgent> agents = transactionBUS.getAllSalesAgents();
    	for (SalesAgent agent : agents) {
    		salesAgentTableModel.addRow(new Object[] {
    				false,
    				agent.getId(),
    				agent.getFirstName(),
    				agent.getLastName(),
    				agent.getEmail(),
    				agent.getPhoneNumber()
    		});
    	}
    }
    
    private void saveTransaction() {
    	// Get selected customer
    	int selectedCustomerRow = customerTable.getSelectedRow();
        if (selectedCustomerRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a customer.");
            return;
        }
        int customerId = (int) customerTableModel.getValueAt(selectedCustomerRow, 0);
        String firstName = (String) customerTableModel.getValueAt(selectedCustomerRow, 1);
    	String lastName = (String) customerTableModel.getValueAt(selectedCustomerRow, 2);
    	String phoneNumber = (String) customerTableModel.getValueAt(selectedCustomerRow, 3);
    	String address = (String) customerTableModel.getValueAt(selectedCustomerRow, 4);
    	String email = (String) customerTableModel.getValueAt(selectedCustomerRow, 5);
    	
    	Customer customer = new Customer(customerId, firstName, lastName, phoneNumber, address, email);
        
        String date = transactionDateField.getText().trim();
        double tax = Double.parseDouble(transactionPriceField.getText().trim());
        String method = transactionMethodField.getText().trim();
        
        if (date.isEmpty() || tax == 0 || method.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all transaction details.");
            return;
        }
        
        String transactionType = (String)transactionTypeDropdown.getSelectedItem();
        
        Transaction transaction = null;
        if (transactionType.equals("Sale")) {
        	int downPayment = Integer.parseInt(downPaymentField.getText().trim());
        	Sale sale = new Sale(date, tax, method, vehicle, customer, downPayment);
        	transaction = transactionBUS.addSale(sale);
        } else {
        	int leasePeriod = Integer.parseInt(leasePeriodField.getText().trim());
        	int monthlyFee = Integer.parseInt(monthlyFeeField.getText().trim());
        	Lease lease = new Lease(date, tax, method, vehicle, customer, leasePeriod, monthlyFee);
        	transaction = transactionBUS.addLease(lease);
        }
        
        // Get selected sales agents and create their commissions
        for (int i = 0; i < salesAgentTableModel.getRowCount(); i++) {
            boolean isSelected = (boolean) salesAgentTableModel.getValueAt(i, 0); //If checkbox is selected
            if (isSelected) {
            	int agentId = (int) salesAgentTableModel.getValueAt(i, 1);
            	firstName = (String) salesAgentTableModel.getValueAt(i, 2);
            	lastName = (String) salesAgentTableModel.getValueAt(i, 3);
            	email = (String) salesAgentTableModel.getValueAt(i, 4);
            	phoneNumber = (String) salesAgentTableModel.getValueAt(i, 5);
            	
            	double commissionRate = 0.0;
            	if (salesAgentTableModel.getValueAt(i, 6) != null) {
            		commissionRate = (double) salesAgentTableModel.getValueAt(i, 6);
            	}
            	
            	String paymentDate = date;
            	if (salesAgentTableModel.getValueAt(i, 7) != null) {
            		paymentDate = (String) salesAgentTableModel.getValueAt(i, 7);
            	}
                
                SalesAgent agent = new SalesAgent(agentId, firstName, lastName, email, phoneNumber);
                
                Commission commission = new Commission(transaction, agent, commissionRate, paymentDate);
                
                transactionBUS.addCommission(commission);
            }
        }
        
        vehicle.updateStatus("sold");
        vehicleBUS.updateVehicle(vehicle);
        
        
        JOptionPane.showMessageDialog(this, "Transaction saved successfully!");
        parentView.updateVehicleList();
        dispose();
    }
    
    private void showSaleFields() {
        JLabel downPaymentLabel = new JLabel("Down Payment:");
        downPaymentField = new JTextField(10);
        JPanel salePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        salePanel.add(downPaymentLabel);
        salePanel.add(downPaymentField);
        dynamicPanel.add(salePanel);
    }
    
    private void showLeaseFields() {
        JLabel leasePeriodLabel = new JLabel("Lease Period:");
        leasePeriodField = new JTextField(10);
        JLabel monthlyFeeLabel = new JLabel("Monthly Fee:");
        monthlyFeeField = new JTextField(10);

        JPanel leasePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leasePanel.add(leasePeriodLabel);
        leasePanel.add(leasePeriodField);
        leasePanel.add(monthlyFeeLabel);
        leasePanel.add(monthlyFeeField);

        dynamicPanel.add(leasePanel);
    }
    
    
}
