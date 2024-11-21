package DealershipInventorySubsystem;

import java.awt.*;
import javax.swing.*;

public class WarrantyForm extends JFrame {
	
	private JComboBox<String> typeComboBox;
    private JTextField durationField, priceField, coverageLimitField;
    private JTextArea policyArea;
    private Vehicle vehicle;
    private VehicleBUS vehicleBUS;
	
	public WarrantyForm(Vehicle vehicle) {
		this.vehicle = vehicle;
		this.vehicleBUS = new VehicleBUS();
		setTitle("Warranty Form");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        
        JPanel formPanel = new JPanel(new GridLayout(5, 2, 10, 10)); // 5 rows, 2 columns, with padding
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Warranty Type
        formPanel.add(new JLabel("Warranty Type:"));
        typeComboBox = new JComboBox<>(new String[]{"Basic", "Extended", "Premium"});
        formPanel.add(typeComboBox);

        // Duration
        formPanel.add(new JLabel("Duration (months):"));
        durationField = new JTextField();
        formPanel.add(durationField);

        // Policy
        formPanel.add(new JLabel("Policy Details:"));
        policyArea = new JTextArea(3, 20);
        JScrollPane scrollPane = new JScrollPane(policyArea);
        formPanel.add(scrollPane);

        // Price
        formPanel.add(new JLabel("Price:"));
        priceField = new JTextField();
        formPanel.add(priceField);

        // Coverage Limit
        formPanel.add(new JLabel("Coverage Limit:"));
        coverageLimitField = new JTextField();
        formPanel.add(coverageLimitField);

        // Add formPanel to the center
        add(formPanel, BorderLayout.CENTER);

        // Buttons Panel
        JPanel buttonsPanel = new JPanel();
        JButton submitButton = new JButton("Submit");
        JButton cancelButton = new JButton("Cancel");
        
        submitButton.addActionListener(e -> saveWarranty());
        cancelButton.addActionListener(e -> dispose());
        
        buttonsPanel.add(submitButton);
        buttonsPanel.add(cancelButton);
        
        add(buttonsPanel, BorderLayout.SOUTH);
        
        setVisible(true);
	}
	
	private void saveWarranty() {
		String type = (String) typeComboBox.getSelectedItem();
        String duration = durationField.getText();
        String policy = policyArea.getText();
        String price = priceField.getText();
        String coverageLimit = coverageLimitField.getText();
        
        if (type == null || duration.isEmpty() || policy.isEmpty() || price.isEmpty() || coverageLimit.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields must be filled out.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
        	int durationInt = Integer.parseInt(duration);
            double priceDouble = Double.parseDouble(price);
            double coverageDouble = Double.parseDouble(coverageLimit);
            
            Warranty warranty = new Warranty(type, durationInt, policy, priceDouble, coverageDouble, vehicle);
            vehicleBUS.addWarranty(warranty);
            
            JOptionPane.showMessageDialog(this, "Warranty details saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (NumberFormatException e) {
        	JOptionPane.showMessageDialog(this, "Please enter valid numeric values for duration, price, and coverage limit.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        
	}
}
