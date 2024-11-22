package DealershipInventorySubsystem;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class MaintenanceForm extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable maintenanceTable;
    private DefaultTableModel tableModel;
    private VehicleBUS vehicleBUS;
    private Vehicle vehicle;
    private Maintenance maintenance;

    public MaintenanceForm(Vehicle vehicle) {
    	this.vehicle = vehicle;
    	this.vehicleBUS = new VehicleBUS();
    	this.maintenance = null;
        setTitle("Maintenance Records");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this form
        setLayout(new BorderLayout());

        // Table setup
        tableModel = new DefaultTableModel(new String[] {"ID", "Type", "Details"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Prevent all cells from being edited
            }
        };
        
        maintenanceTable = new JTable(tableModel);
        
        maintenanceTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Single row selection
        add(new JScrollPane(maintenanceTable), BorderLayout.CENTER);

        // Add table selection listener
        maintenanceTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) { // Prevent multiple events
                int selectedRow = maintenanceTable.getSelectedRow();
                if (selectedRow != -1) {
                    int id = (int) tableModel.getValueAt(selectedRow, 0); // Get ID
                    String type = (String) tableModel.getValueAt(selectedRow, 1); // Get Type
                    String details = (String) tableModel.getValueAt(selectedRow, 2); // Get Description
                    
                    maintenance = new Maintenance(id, type, details);
                    MaintenanceInputDialog dialog = new MaintenanceInputDialog(this, vehicleBUS, this.vehicle, this.maintenance);
                    dialog.setVisible(true);
                }
            }
        });
        
        // Fetch and populate maintenance records
        loadMaintenanceData();

        setVisible(true);
    }

    private void loadMaintenanceData() {
        List<Maintenance> maintenanceList = vehicleBUS.getAllMaintenances();
        
        for (Maintenance maintenance : maintenanceList) {
            tableModel.addRow(new Object[]{
                maintenance.getId(),
                maintenance.getType(),
                maintenance.getDetails()
            });
        }
    }
    
    public class MaintenanceInputDialog extends JDialog {
        private JTextField dateField;
        private JTextField priceField;
        private JComboBox<String> statusComboBox;
        private JButton saveButton, cancelButton;
        private VehicleBUS vehicleBUS;

        public MaintenanceInputDialog(JFrame parent, VehicleBUS vehicleBUS, Vehicle vehicle, Maintenance maintenance) {
            super(parent, "Add Service Record", true);
            this.vehicleBUS = vehicleBUS;
            
            JPanel mainPanel = new JPanel(new GridLayout(5, 2, 10, 10));
            mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Add 10px padding on all sides

            setLayout(new GridLayout(5, 2, 10, 10));
            setSize(400, 300);
            setLocationRelativeTo(parent);

            // Date field
            add(new JLabel("Date (YYYY-MM-DD):"));
            dateField = new JTextField("");
            add(dateField);

            // Price field
            add(new JLabel("Price:"));
            priceField = new JTextField("");
            add(priceField);

            // Status combo box
            add(new JLabel("Status:"));
            statusComboBox = new JComboBox<>(new String[]{"Pending", "In Progress", "Completed"});
            add(statusComboBox);

            // Save and cancel buttons
            saveButton = new JButton("Save");
            cancelButton = new JButton("Cancel");

            saveButton.addActionListener(e -> onSave());
            cancelButton.addActionListener(e -> dispose());

            add(saveButton);
            add(cancelButton);
        }

        private void onSave() {
            try {
                String date = dateField.getText().trim();
                double price = Double.parseDouble(priceField.getText().trim());
                String status = (String) statusComboBox.getSelectedItem();

                ServiceRecord serviceRecord = new ServiceRecord(date, price, status, vehicle, maintenance);

                vehicleBUS.addServiceRecord(serviceRecord);

                JOptionPane.showMessageDialog(this, "Maintenance saved successfully!");
                dispose();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
