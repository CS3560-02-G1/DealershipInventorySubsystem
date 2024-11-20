package DealershipInventorySubsystem;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.util.List;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/*
 *  Grid layout:
 * 
 *    0 1
 *   +-+-+
 * 0 |V|T|
 *   +-+-+
 * 1 |V|B|
 *   +-+-+
 * 
 *  V - Vehicle List (Left, top, bottom padding)
 *  T - Title (Left, top, right padding)
 *  B - Buttons (Left, top, right, bottom padding)
 * 
 * Inventory car layout:
 * <Year> <Make> <Model> - <Color>
 * <Condition>, $<Price>
 */

public class GUIManager {
    public enum Permissions {
        VIEW_DETAILS,
        ADD_VEHICLE,
        MANAGE_VEHICLE,
        REMOVE_VEHICLE,
        MARK_AS_SOLD,
        SCHEDULE_MAINTENANCE
    }
    
    public enum Actors {
        ALL,
        CUSTOMER,
        SALES,
        MAINTENANCE
    }
    
    public static JFrame primaryFrame;

    public static int hoveredIndex = -1;
    public static Integer selectedIndex = null;
    public static Actors permissionLevel;

    public static void main(String[] args) {
        if (args.length == 0 || args[0].equals("all") || args[0].equals("0")) {
            permissionLevel = Actors.ALL;
        } else if (args[0].equals("customer") || args[0].equals("1")) {
            permissionLevel = Actors.CUSTOMER;
        } else if (args[0].equals("sales") || args[0].equals("2")) {
            permissionLevel = Actors.SALES;
        } else if (args[0].equals("maintenance") || args[0].equals("3")) {
            permissionLevel = Actors.MAINTENANCE;
        }

        primaryFrame = new JFrame("Dealership Inventory View");
        primaryFrame.setResizable(false);
        primaryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        VehicleBUS vehicleBUS = new VehicleBUS();
        // Vehicle vehicles[] = {
        //     new Vehicle("4Y1SL65848Z411439", "Transit 150", 2021, "Placeholder Status", "Mint Condition", "Ford", "White", 31000.0),       
        //     new Vehicle("4Y1SL65848Z411439", "Transit 250", 2021, "Placeholder Status", "Mint Condition", "Ford", "White", 31000.0)        
        // };
        new InventoryView(vehicleBUS, primaryFrame);
        
        primaryFrame.setVisible(true);
    }

    public static GridBagConstraints getGridBagConst(int gridx, int gridy, int gridwidth, int gridheight, boolean rightPadding, boolean bottomPadding, int padding) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = gridx;
        c.gridy = gridy;
        c.gridwidth = gridwidth;
        c.gridheight = gridheight;
        c.fill = GridBagConstraints.BOTH;
        c.insets = new Insets(padding, padding, bottomPadding ? padding : 0, rightPadding ? padding : 0);

        return c;
    }

    public static boolean getPermissions(Permissions permission) {
        /* Permission matrix structure:
         * 
         *               VIEW_DETAILS ADD_VEHICLE MANAGE_VEHICLE REMOVE_VEHICLE MARK_AS_SOLD SCHEDULE_MAINTENANCE
         *  CUSTOMER          __          __            __             __            __              __
         *  SALES             __          __            __             __            __              __
         *  MAINTENANCE       __          __            __             __            __              __
         */
        int[][] PERMISSION_MATRIX = {
            {1, 1, 1, 1, 1, 1},
            {1, 0, 0, 0, 0, 0},
            {1, 1, 1, 1, 1, 0},
            {1, 0, 0, 0, 0, 1},
        };
        return PERMISSION_MATRIX[permissionLevel.ordinal()][permission.ordinal()] == 1;
    }
    
}

class InventoryView {
    private int padding;
    private Integer vehicleIndexMap[];
    public List<Vehicle> vehicles;
    public VehicleBUS vehicleBUS;
    private String[] vehicleStrings;
    private JFrame frame;
    String currentSearch;
    final String SEARCH_PROMPT = " Search...";
    DefaultListModel<Object> vehicleListModel;

    public InventoryView(VehicleBUS vehicleBUS, JFrame frame) {
        this.frame = frame;
        currentSearch = SEARCH_PROMPT;

        padding = 10;
        this.vehicleBUS = vehicleBUS;
        this.vehicles = vehicleBUS.getAllUnsoldVehicles();

        ShowInventoryMenu();
    }
    
    public InventoryView(VehicleBUS vehicleBUS) {
        this(vehicleBUS, new JFrame("Dealership Inventory View"));
        
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public static String[] getVehicleStrings(List<Vehicle> vehicles) {
        String vehicleStrings[] = new String[vehicles.size()];
        for (int i = 0; i < vehicles.size(); i++) {
            vehicleStrings[i] = String.format(
                """
                <html>
                    <b>%d %s %s -</b> %s<br/>
                    %s, <u>$%,.2f</u>
                    </span>
                </html>
                """,
                vehicles.get(i).getYear(),
                vehicles.get(i).getMake(),
                vehicles.get(i).getModel(),
                vehicles.get(i).getColor(),
                vehicles.get(i).getCondition(),
                vehicles.get(i).getPrice()
            );

        }
        return vehicleStrings;
    }

    public Object[] getVehicleList() {
        vehicleStrings = getVehicleStrings(vehicles);
        String filterString = currentSearch.equals(SEARCH_PROMPT) ? null : currentSearch;
        vehicleIndexMap = new Integer[vehicles.size()];

        String stringsToInclude[];
        int numIncludedStrings;

        if (filterString == null) {
            stringsToInclude = vehicleStrings;
            numIncludedStrings = stringsToInclude.length;
            for (int i = 0; i < vehicleIndexMap.length; i++) {
                vehicleIndexMap[i] = i;
            }
        } else {
            stringsToInclude = new String[vehicleStrings.length]; 

            numIncludedStrings = 0;
            for (int i = 0; i < vehicles.size(); i++) {
                String vehicleDetails = String.format(
                    "%d %s %s - %s %s, $%,.2f %s %s",
                    vehicles.get(i).getYear(),
                    vehicles.get(i).getMake(),
                    vehicles.get(i).getModel(),
                    vehicles.get(i).getColor(),
                    vehicles.get(i).getCondition(),
                    vehicles.get(i).getPrice(),
                    vehicles.get(i).getVin(),
                    vehicles.get(i).getStatus()
                );
                if (vehicleDetails.contains(filterString)) {
                    vehicleIndexMap[i] = numIncludedStrings;
                    stringsToInclude[numIncludedStrings++] = vehicleStrings[i];
                } else {
                    vehicleIndexMap[i] = null;
                }
            }
        }
        Object vehicleList[] = new Object[numIncludedStrings * 2];
        for (int i = 0; i < numIncludedStrings; i++) {
            int vehicle_i = 2 * i;
            int spacer_i = 2 * i - 1;
            if (spacer_i >= 0) {
                JSeparator separator = new JSeparator(JSeparator.HORIZONTAL);
                separator.setForeground(new Color(175, 175, 175));
                vehicleList[spacer_i] = separator;
            }
            vehicleList[vehicle_i] = stringsToInclude[i];
        }
        if (numIncludedStrings * 2 - 1 >= 0) vehicleList[numIncludedStrings * 2 - 1] = null;

        for (int i = 0; i < vehicleIndexMap.length; i++) {
            if (vehicleIndexMap[i] != null) vehicleIndexMap[i] *= 2;
        }

        return vehicleList;
    }

    public void updateVehicleList() {
        Object data[] = getVehicleList();
        
        vehicleListModel.removeAllElements();
        vehicleListModel.addAll(Arrays.asList(data));

        frame.repaint();
    }
    
    public void ShowInventoryMenu() {
        frame.getContentPane().removeAll();

        frame.setLayout(new GridBagLayout());

        //region Create left panel, including list and search bar

        // Create list panel, with BoxLayout (simple column layout), and add it to the primary frame
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.PAGE_AXIS));
        frame.add(listPanel, GUIManager.getGridBagConst(0, 0, 1, 2, false, true, padding));

        // Create the search box with new (bigger) font
        Font searchFont = new Font("Arial", Font.PLAIN, 16);
        JTextField searchBox = new JTextField(" Search...");
        searchBox.setFont(searchFont);
        searchBox.setPreferredSize(new Dimension(400, 40));

        // Add search box to list panel
        listPanel.add(searchBox);

        // Create list of vehicles

        currentSearch = searchBox.getText();
        Object[] data = getVehicleList();
        vehicleListModel = new DefaultListModel<>();
        vehicleListModel.addAll(Arrays.asList(data));

        JList<Object> list = new JList<>(vehicleListModel);
        list.setCellRenderer(new ComboBoxRenderer());

        // Create a listScroller to contain list (allows for scrolling through list)
        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(new Dimension(400, 700));

        // Add listScroller to list Panel
        listPanel.add(listScroller);

        //endregion

        //region Create top right panel, containing title

        //Create title panel, and add it to the primary frame
        JPanel titlePanel = new JPanel();
        frame.add(titlePanel, GUIManager.getGridBagConst(1, 0, 1, 1, true, false, padding));

        // Create title text and center it in the title panel
        Font titleFont = new Font("Arial", Font.BOLD, 40);
        JLabel titleText = new JLabel("<html>Dealership<br/>Inventory", SwingConstants.CENTER);
        titleText.setFont(titleFont);
        titleText.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        titleText.setBorder(new EmptyBorder(20, 0, 20, 0));

        // Add title text to title panel
        titlePanel.add(titleText);

        //endregion

        //region Create bottom right panel, containing buttons

        // Create buttons panel, with BoxLayout (simple column layout), and add it to the primary frame
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.PAGE_AXIS));
        frame.add(buttonsPanel, GUIManager.getGridBagConst(1, 1, 1, 1, true, true, padding));

        JButton viewDetailsButton = new JButton("View Vehicle Details");
        viewDetailsButton.setVisible(GUIManager.getPermissions(GUIManager.Permissions.VIEW_DETAILS));
        JButton addVehicleButton = new JButton("Add Vehicle");
        addVehicleButton.setVisible(GUIManager.getPermissions(GUIManager.Permissions.ADD_VEHICLE));
        JButton removeVehicleButton = new JButton("Remove Vehicle");
        removeVehicleButton.setVisible(GUIManager.getPermissions(GUIManager.Permissions.REMOVE_VEHICLE));
        JButton markAsSoldButton = new JButton("Mark as Sold");
        markAsSoldButton.setVisible(GUIManager.getPermissions(GUIManager.Permissions.MARK_AS_SOLD));
        JButton scheduleMaintenanceButton = new JButton("Schedule for Maintenance");
        scheduleMaintenanceButton.setVisible(GUIManager.getPermissions(GUIManager.Permissions.SCHEDULE_MAINTENANCE));
        JButton manageVehicleButton = new JButton("Manage Vehicle");
        manageVehicleButton.setVisible(GUIManager.getPermissions(GUIManager.Permissions.MANAGE_VEHICLE));

        JButton[] buttons = {viewDetailsButton, addVehicleButton, manageVehicleButton, removeVehicleButton, markAsSoldButton, scheduleMaintenanceButton};
        JButton[] requireVehicleSelected = {viewDetailsButton, manageVehicleButton, removeVehicleButton, markAsSoldButton, scheduleMaintenanceButton};
        for (int i = 0; i < requireVehicleSelected.length; i++)
            requireVehicleSelected[i].setEnabled(false);
        
        Font buttonFont = new Font("Arial", Font.BOLD, 20);
        for (int i = 0; i < buttons.length; i++) {
            if (i != 0) buttonsPanel.add(Box.createRigidArea(new Dimension(0, padding)));

            buttons[i].setPreferredSize(new Dimension(300, 70));
            buttons[i].setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));

            buttons[i].setFont(buttonFont);

            buttonsPanel.add(buttons[i]);
        }
        //endregion

        //region Add listeners
        list.addMouseListener(new MouseAdapter() {
            @Override 
            public void mouseExited(MouseEvent e) { 
                GUIManager.hoveredIndex = -1; 
                list.repaint(); 
            }

            @Override
            public void mousePressed(MouseEvent e) {
                GUIManager.selectedIndex = null;
                int indexClicked = list.locationToIndex(new Point(e.getX(), e.getY()));
                for (int i = 0; i < vehicleIndexMap.length; i++) {
                    if (vehicleIndexMap[i] != null && vehicleIndexMap[i] == indexClicked) {
                        GUIManager.selectedIndex = i;
                    }
                }

                for (int i = 0; i < requireVehicleSelected.length; i++) 
                    requireVehicleSelected[i].setEnabled(GUIManager.selectedIndex != null);
            }
        });

        list.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int index = list.locationToIndex(new Point(e.getX(), e.getY()));
                if (index != GUIManager.hoveredIndex) {
                    GUIManager.hoveredIndex = index;
                    list.repaint();
                }
            }
        });

        searchBox.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                searchBox.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (searchBox.getText().equals("")) {
                    searchBox.setText(" Search...");
                }
            }
        });

        searchBox.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                currentSearch = searchBox.getText();
                updateVehicleList();
            }
        });
        
        viewDetailsButton.addActionListener(_ -> new VehicleInfoView(vehicles.get(GUIManager.selectedIndex)));

        addVehicleButton.addActionListener(_ -> new VehicleEditor(null, null, this));
        
        manageVehicleButton.addActionListener(_ -> new VehicleEditor(vehicles.get(GUIManager.selectedIndex), GUIManager.selectedIndex, this));
        
        removeVehicleButton.addActionListener(_ -> new ConfirmationBox(new ConfirmationBox.OnResults() {
            public void onConfirm() {
                if (GUIManager.selectedIndex != null) {
                    vehicleBUS.removeVehicle(vehicles.get(GUIManager.selectedIndex).getVin());
                    vehicles.remove((int)GUIManager.selectedIndex);
                    GUIManager.selectedIndex = null;

                    updateVehicleList();
                }
            }
        }));

        markAsSoldButton.addActionListener(_ -> new ConfirmationBox(new ConfirmationBox.OnResults() {
            public void onConfirm() {
                Vehicle selectedVehicle = vehicles.get(GUIManager.selectedIndex);
                selectedVehicle.updateStatus("sold");
                vehicleBUS.updateVehicle(selectedVehicle);
            }
        }));

        scheduleMaintenanceButton.addActionListener(_ -> new ConfirmationBox(new ConfirmationBox.OnResults() {
            public void onConfirm() {
                System.out.println("Scheduled for Maintenance!");
            }
        }, String.format(
            "<html><b>Schedule Maintenance For:</b><br/><span style='font-size:75%%'>%s %d %s %s<br/>VIN: %s</span>",
            vehicles.get(GUIManager.selectedIndex).getColor(),
            vehicles.get(GUIManager.selectedIndex).getYear(),
            vehicles.get(GUIManager.selectedIndex).getMake(),
            vehicles.get(GUIManager.selectedIndex).getModel(),
            vehicles.get(GUIManager.selectedIndex).getVin()
        )));

        //endregion

        // Shrink the frame to fit its contents
        frame.pack();

        // Update the frame
        frame.repaint();

    }
}

class VehicleInfoView {
    JFrame frame;
    Vehicle vehicle;
    int padding;

    public VehicleInfoView(Vehicle vehicle, JFrame frame) {
        this.frame = frame;

        padding = 10;
        this.vehicle = vehicle;

        ShowVehicleView();
    }
    
    public VehicleInfoView(Vehicle vehicle) {
        this(vehicle, new JFrame("Vehicle Info View"));
        
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    private void ShowVehicleView() {
        frame.setLayout(new GridBagLayout());
        
        JLabel title = new JLabel(String.format("%d %s %s", vehicle.getYear(), vehicle.getMake(), vehicle.getModel()), SwingConstants.CENTER);
        Font titleFont = new Font("Arial", Font.BOLD, 30);
        title.setFont(titleFont);

        frame.add(title, GUIManager.getGridBagConst(0, 0, 2, 1, true, false, padding));

        JSeparator titleSeparator = new JSeparator(JSeparator.HORIZONTAL);
        titleSeparator.setForeground(Color.BLACK);
        frame.add(titleSeparator, GUIManager.getGridBagConst(0, 1, 2, 1, true, false, padding));

        JLabel makeLabel = new JLabel("<html><b>Make:</b> " + vehicle.getMake());
        JLabel modelLabel = new JLabel("<html><b>Model:</b> " + vehicle.getModel());
        JLabel yearLabel = new JLabel("<html><b>Year:</b> " + vehicle.getYear());
        JLabel colorLabel = new JLabel("<html><b>Color:</b> " + vehicle.getColor());
        JLabel priceLabel = new JLabel("<html><b>Price:</b> " + String.format("$%,.2f", vehicle.getPrice()));
        JLabel conditionLabel = new JLabel("<html><b>Condition:</b> " + vehicle.getCondition());
        JLabel vinLabel = new JLabel("<html><b>Vin:</b> " + vehicle.getVin());
        JLabel statusLabel = new JLabel("<html><b>Status:</b> " + vehicle.getStatus());
    
        JLabel detailLabels[] = {makeLabel, modelLabel, yearLabel, colorLabel, priceLabel, conditionLabel, vinLabel, statusLabel};

        Font detailFont = new Font("Arial", Font.PLAIN, 20);
        for (int i = 0; i < detailLabels.length; i++) {
            detailLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
            detailLabels[i].setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
            detailLabels[i].setFont(detailFont);
    
            frame.add(detailLabels[i], GUIManager.getGridBagConst(0, i + 2, 2, 1, true, false, padding));
        }

        JSeparator exitSeparator = new JSeparator(JSeparator.HORIZONTAL);
        exitSeparator.setForeground(Color.BLACK);
        frame.add(exitSeparator, GUIManager.getGridBagConst(0, 11, 2, 1, true, false, padding));

        JButton exitButton = new JButton("Exit");
        exitButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        exitButton.setPreferredSize(new Dimension(0, 50));
        Font exitFont = new Font("Arial", Font.BOLD, 20);
        exitButton.setFont(exitFont);
        frame.add(exitButton, GUIManager.getGridBagConst(0, 12, 2, 1, true, true, padding));

        exitButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
            }
        });

        frame.pack();
        frame.repaint();
    }
}

class ConfirmationBox {
    public interface OnResults {
        default void onConfirm() {};
        default void onDeny() {};
    }

    JFrame frame;
    int padding;
    OnResults onResultsFunctions;
    String prompt;

    ConfirmationBox(OnResults onResultsFunctions, String prompt) {
        this.onResultsFunctions = onResultsFunctions;
        frame = new JFrame("Confirm Selection");
        this.prompt = prompt;

        padding = 10;

        ShowConfirmationBox();
        
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    ConfirmationBox(OnResults onResultsFunctions) {
        this(onResultsFunctions, "<html><b>Are you sure?</b>");
    }

    void ShowConfirmationBox() {
        frame.setLayout(new GridBagLayout());

        JLabel question = new JLabel(prompt, SwingConstants.CENTER);
        question.setHorizontalAlignment(SwingConstants.CENTER);
        question.setFont(new Font("Arial", Font.PLAIN, 25));
        question.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        frame.add(question, GUIManager.getGridBagConst(0, 0, 2, 1, true, false, padding));

        Font buttonFont = new Font("Arial", Font.BOLD, 15);

        JButton denyButton = new JButton("Deny");

        frame.pack();

        int buttonWidth = Math.max(150, (question.getWidth() - padding) / 2);

        denyButton.setPreferredSize(new Dimension(buttonWidth, 40));
        denyButton.setFont(buttonFont);
        frame.add(denyButton, GUIManager.getGridBagConst(0, 1, 1, 1, false, true, padding));
        denyButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                onResultsFunctions.onDeny();
            }
        });

        JButton confirmButton = new JButton("Confirm");
        confirmButton.setPreferredSize(new Dimension(buttonWidth, 40));
        confirmButton.setFont(buttonFont);
        frame.add(confirmButton, GUIManager.getGridBagConst(1, 1, 1, 1, true, true, padding));
        confirmButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                onResultsFunctions.onConfirm();
            }
        });

        frame.pack();
    }
}

class VehicleEditor {
    private Vehicle vehicle;
    private JFrame frame;
    private int padding;
    private String saveText;
    private String titleText;
    private Integer vehicleIndex;
    private InventoryView parentInventoryView;
    String originalVin;
    boolean vinChanged;

    public VehicleEditor(Vehicle vehicle, Integer vehicleIndex, InventoryView parentInventoryView, JFrame frame) {
        this.frame = frame;
        this.vehicleIndex = vehicleIndex;
        if (vehicleIndex == null) {
            titleText = "New Vehicle";
            saveText = "Add Vehicle";
        } else {
            titleText = "Edit Vehicle";
            saveText = "Save Vehicle";
        }

        this.parentInventoryView = parentInventoryView;

        padding = 10;
        if (vehicle == null) {
            this.vehicle = new Vehicle(null, null, 0, "For Sale", null, null, null, 0.0);
        } else {
            this.vehicle = vehicle;
        }

        originalVin = this.vehicle.getVin();
        vinChanged = false;

        ShowVehicleEditor();
    }
    
    public VehicleEditor(Vehicle vehicle, Integer vehicleIndex, InventoryView parentInventoryView) {
        this(vehicle, vehicleIndex, parentInventoryView, new JFrame("Vehicle Editor"));
        
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    private void ShowVehicleEditor() {
        frame.setLayout(new GridBagLayout());

        JLabel title = new JLabel(titleText, SwingConstants.CENTER);
        Font titleFont = new Font("Arial", Font.BOLD, 30);
        title.setFont(titleFont);

        frame.add(title, GUIManager.getGridBagConst(0, 0, 6, 1, true, false, padding));

        JSeparator titleSeparator = new JSeparator(JSeparator.HORIZONTAL);
        titleSeparator.setForeground(Color.BLACK);
        frame.add(titleSeparator, GUIManager.getGridBagConst(0, 1, 6, 1, true, false, padding));

        JLabel makeLabel = new JLabel("<html><b>Make:</b> ");
        JLabel modelLabel = new JLabel("<html><b>Model:</b> ");
        JLabel yearLabel = new JLabel("<html><b>Year:</b> ");
        JLabel colorLabel = new JLabel("<html><b>Color:</b> ");
        JLabel priceLabel = new JLabel("<html><b>Price:</b> ");
        JLabel conditionLabel = new JLabel("<html><b>Condition:</b> ");
        JLabel vinLabel = new JLabel("<html><b>Vin:</b> ");

        JTextField makeTextField = new JTextField(vehicle.getMake() == null ? "" : vehicle.getMake());
        JTextField modelTextField = new JTextField(vehicle.getModel() == null ? "" : vehicle.getModel());
        JSpinner yearSpinner = new JSpinner(new SpinnerNumberModel(vehicle.getYear(), 0, 9999, 1));
        yearSpinner.setEditor(new JSpinner.NumberEditor(yearSpinner, "#"));
        JTextField colorTextField = new JTextField(vehicle.getColor() == null ? "" : vehicle.getColor());
        JSpinner priceSpinner = new JSpinner(new SpinnerNumberModel(vehicle.getPrice(), 0.00, 9999999.99, 500.00));
        JTextField conditionTextField = new JTextField(vehicle.getCondition() == null ? "" : vehicle.getCondition());
        JTextField vinTextField = new JTextField(vehicle.getVin() == null ? "" : vehicle.getVin());


        JLabel labels[] = {makeLabel, modelLabel, yearLabel, colorLabel, priceLabel, conditionLabel, vinLabel};
        Component fields[] = {makeTextField, modelTextField, yearSpinner, colorTextField, priceSpinner, conditionTextField, vinTextField};
        Font labelFont = new Font("Arial", Font.PLAIN, 20);
        Font fieldFont = new Font("Arial", Font.PLAIN, 18);
        for (int i = 0; i < labels.length; i++) {
            labels[i].setHorizontalAlignment(SwingConstants.RIGHT);
            labels[i].setFont(labelFont);
            frame.add(labels[i], GUIManager.getGridBagConst(0, 2 + i, 1, 1, false, false, padding));

            fields[i].setFont(fieldFont);
            if (i == 4) {
                JLabel dollarLabel = new JLabel("$");
                dollarLabel.setFont(fieldFont);
                frame.add(dollarLabel, GUIManager.getGridBagConst(1, 2 + i, 1, 1, false, false, padding));
        
                frame.add(fields[i], GUIManager.getGridBagConst(2, 2 + i, 4, 1, true, false, padding));
            } else {
                frame.add(fields[i], GUIManager.getGridBagConst(1, 2 + i, 5, 1, true, false, padding));
            }
        }

        JSeparator buttonSeparator = new JSeparator(JSeparator.HORIZONTAL);
        buttonSeparator.setForeground(Color.BLACK);
        frame.add(buttonSeparator, GUIManager.getGridBagConst(0, 9, 6, 1, true, false, padding));

        Font buttonFont = new Font("Arial", Font.BOLD, 15);

        JButton denyButton = new JButton("Cancel");
        denyButton.setPreferredSize(new Dimension(200, 40));
        denyButton.setFont(buttonFont);
        frame.add(denyButton, GUIManager.getGridBagConst(0, 10, 3, 1, false, true, padding));
        denyButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
            }
        });

        JButton confirmButton = new JButton(saveText);
        confirmButton.setPreferredSize(new Dimension(200, 40));
        confirmButton.setFont(buttonFont);
        frame.add(confirmButton, GUIManager.getGridBagConst(3, 10, 3, 1, true, true, padding));
        confirmButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (confirmButton.isEnabled()) {
                    if (vehicleIndex == null) {
                        addVehicle(vehicle);
                    } else {
                        changeVehicle(vehicle, vehicleIndex);
                    }
                    frame.dispose();
                }
            }
        });

        JTextField fieldsToCheck[] = {makeTextField, modelTextField, colorTextField, conditionTextField, vinTextField};
        JSpinner spinnersToCheck[] = {yearSpinner, priceSpinner};

        setButtonEnabled(fieldsToCheck, spinnersToCheck, confirmButton);

        makeTextField.addKeyListener(new KeyAdapter() { public void keyReleased(KeyEvent e) {
                vehicle.updateMake(makeTextField.getText());
                setButtonEnabled(fieldsToCheck, spinnersToCheck, confirmButton);
        }});

        modelTextField.addKeyListener(new KeyAdapter() { public void keyReleased(KeyEvent e) {
                vehicle.updateModel(modelTextField.getText());
                setButtonEnabled(fieldsToCheck, spinnersToCheck, confirmButton);
        }});

        yearSpinner.addChangeListener(new ChangeListener() { public void stateChanged(ChangeEvent e) {
                vehicle.updateYear((int)yearSpinner.getValue());
                setButtonEnabled(fieldsToCheck, spinnersToCheck, confirmButton);
        }});

        colorTextField.addKeyListener(new KeyAdapter() { public void keyReleased(KeyEvent e) {
                vehicle.updateColor(colorTextField.getText());
                setButtonEnabled(fieldsToCheck, spinnersToCheck, confirmButton);
        }});

        priceSpinner.addChangeListener(new ChangeListener() { public void stateChanged(ChangeEvent e) {
                vehicle.updatePrice((double)priceSpinner.getValue());
                setButtonEnabled(fieldsToCheck, spinnersToCheck, confirmButton);
        }});

        conditionTextField.addKeyListener(new KeyAdapter() { public void keyReleased(KeyEvent e) {
                vehicle.updateCondition(conditionTextField.getText());
                setButtonEnabled(fieldsToCheck, spinnersToCheck, confirmButton);
        }});

        vinTextField.addKeyListener(new KeyAdapter() { public void keyReleased(KeyEvent e) {
                vehicle.updateVin(vinTextField.getText());
                vinChanged = true;
                setButtonEnabled(fieldsToCheck, spinnersToCheck, confirmButton);
        }});

        frame.pack();
    }

    public void addVehicle(Vehicle vehicle) {
        parentInventoryView.vehicles.add(vehicle);
        
        parentInventoryView.vehicleBUS.addVehicle(vehicle);

        parentInventoryView.updateVehicleList();
    }

    public void changeVehicle(Vehicle newVehicle, int vehicleIndex) {
        parentInventoryView.vehicles.set(vehicleIndex, newVehicle);

        if (!vinChanged) {
            parentInventoryView.vehicleBUS.updateVehicle(vehicle);
        } else {
            parentInventoryView.vehicleBUS.removeVehicle(originalVin);
            parentInventoryView.vehicleBUS.addVehicle(newVehicle);
        }

        parentInventoryView.updateVehicleList();
    }

    private void setButtonEnabled(JTextField textFields[], JSpinner spinners[], JButton button) {
        for (int i = 0; i < textFields.length; i++) {
            if (textFields[i].getText().equals("")) {
                button.setEnabled(false);
                return;
            }
        }
        for (int i = 0; i < spinners.length; i++) {
            if (spinners[i].getValue() instanceof Integer && spinners[i].getValue().equals(0) ||
                spinners[i].getValue() instanceof Double && spinners[i].getValue().equals(0.0)) {
                button.setEnabled(false);
                return;
            }
        }
        button.setEnabled(true);
    }
}

class ComboBoxRenderer extends JLabel implements ListCellRenderer<Object> {
    public Component getListCellRendererComponent(JList<? extends Object> list,
                                                  Object value, int index, boolean isSelected, boolean cellHasFocus) {
        setOpaque(true);

        if (value == null) {
            setText("");
            return this;
        }

        if(value instanceof JSeparator) {

            return (Component)value;
        } else {
            setText(value.toString());
        }
        
        Font listFont = new Font("Arial", Font.PLAIN, 25);
        setFont(listFont);
        
        if (isSelected) {
            setBackground(new Color(200, 200, 200));
        } else if (index == GUIManager.hoveredIndex) {
            setBackground(new Color(230, 230, 230));
        } else {
            setBackground(new Color(255, 255, 255));
        }
                
        return this;
    }   
 }