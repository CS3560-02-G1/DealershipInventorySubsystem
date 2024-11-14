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
import java.awt.event.WindowEvent;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.border.EmptyBorder;


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

public class UseCases {
    private static JFrame primaryFrame;

    public static int hoveredIndex = -1;
    public static Integer selectedIndex = null;

    public static void main(String[] args) {
        primaryFrame = new JFrame("Dealership Inventory View");
        primaryFrame.setResizable(false);
        primaryFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Vehicle vehicles[] = {
            new Vehicle("4Y1SL65848Z411439", "Transit 150", 2021, "Status", "Mint Condition", "Ford", "White", 31000.0),       
            new Vehicle("4Y1SL65848Z411439", "Transit 250", 2021, "Status", "Mint Condition", "Ford", "White", 31000.0)        
        };
        new InventoryView(vehicles, primaryFrame);
        
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
    
}

class InventoryView {
    private int padding;
    private Integer vehicleIndexMap[];
    private Vehicle[] vehicles;
    private String[] vehicleStrings;
    private JFrame frame;

    public InventoryView(Vehicle vehicles[], JFrame frame) {
        this.frame = frame;

        padding = 10;
        this.vehicles = vehicles;
        vehicleIndexMap = new Integer[vehicles.length];
        vehicleStrings = getVehicleStrings(vehicles);

        ShowInventoryMenu();
    }
    
    public InventoryView(Vehicle vehicles[]) {
        this(vehicles, new JFrame("Dealership Inventory View"));
        
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    public static String[] getVehicleStrings(Vehicle vehicles[]) {
        String vehicleStrings[] = new String[vehicles.length];
        for (int i = 0; i < vehicles.length; i++) {
            vehicleStrings[i] = String.format(
                """
                <html>
                    <b>%d %s %s -</b> %s<br/>
                    %s, <u>$%,.2f</u>
                    </span>
                </html>
                """,
                vehicles[i].getYear(),
                vehicles[i].getMake(),
                vehicles[i].getModel(),
                vehicles[i].getColor(),
                vehicles[i].getCondition(),
                vehicles[i].getPrice()
            );
        }
        return vehicleStrings;
    }

    public Object[] findVehicleList(String filterString) {
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
            for (int i = 0; i < vehicles.length; i++) {
                String vehicleDetails = String.format(
                    "%d %s %s - %s %s, $%,.2f %s %s",
                    vehicles[i].getYear(),
                    vehicles[i].getMake(),
                    vehicles[i].getModel(),
                    vehicles[i].getColor(),
                    vehicles[i].getCondition(),
                    vehicles[i].getPrice(),
                    vehicles[i].getVin(),
                    vehicles[i].getStatus()
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

    private void ShowInventoryMenu() {
        frame.getContentPane().removeAll();

        frame.setLayout(new GridBagLayout());

        //region Create left panel, including list and search bar

        // Create list panel, with BoxLayout (simple column layout), and add it to the primary frame
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.PAGE_AXIS));
        frame.add(listPanel, UseCases.getGridBagConst(0, 0, 1, 2, false, true, padding));

        // Create the search box with new (bigger) font
        Font searchFont = new Font("Arial", Font.PLAIN, 16);
        JTextField searchBox = new JTextField(" Search...");
        searchBox.setFont(searchFont);
        searchBox.setPreferredSize(new Dimension(400, 40));

        // Add search box to list panel
        listPanel.add(searchBox);

        
        // Create list of vehicles
        Object[] data = findVehicleList(searchBox.getText().equals(" Search...") ? null : searchBox.getText());
        DefaultListModel<Object> listModel = new DefaultListModel<>();
        listModel.addAll(Arrays.asList(data));

        JList<Object> list = new JList<>(listModel);
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
        frame.add(titlePanel, UseCases.getGridBagConst(1, 0, 1, 1, true, false, padding));

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
        frame.add(buttonsPanel, UseCases.getGridBagConst(1, 1, 1, 1, true, true, padding));

        JButton viewDetailsButton = new JButton("View Vehicle Details");
        JButton addVehicleButton = new JButton("Add Vehicle");
        JButton removeVehicleButton = new JButton("Remove Vehicle");
        JButton markAsSoldButton = new JButton("Mark as Sold");
        JButton scheduleMaintenanceButton = new JButton("Schedule for Maintenance");
        JButton manageVehicleButton = new JButton("Manage Vehicle");

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
                UseCases.hoveredIndex = -1; 
                list.repaint(); 
            }

            @Override
            public void mousePressed(MouseEvent e) {
                UseCases.selectedIndex = null;
                int indexClicked = list.locationToIndex(new Point(e.getX(), e.getY()));
                for (int i = 0; i < vehicleIndexMap.length; i++) {
                    if (vehicleIndexMap[i] != null && vehicleIndexMap[i] == indexClicked) {
                        UseCases.selectedIndex = i;
                    }
                }

                for (int i = 0; i < requireVehicleSelected.length; i++) 
                    requireVehicleSelected[i].setEnabled(UseCases.selectedIndex != null);
            }
        });

        list.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                int index = list.locationToIndex(new Point(e.getX(), e.getY()));
                if (index != UseCases.hoveredIndex) {
                    UseCases.hoveredIndex = index;
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
                listModel.removeAllElements();
                Object data[] = findVehicleList(searchBox.getText().equals(" Search...") ? null : searchBox.getText());

                listModel.addAll(Arrays.asList(data));
                frame.repaint();
            }
        });
        
        viewDetailsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (viewDetailsButton.isEnabled()) {
                    new VehicleInfoView(vehicles[UseCases.selectedIndex]);
                }
            }
        });
        
        removeVehicleButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (removeVehicleButton.isEnabled()) {
                    new ConfirmationBox(new OnResults() {
                        @Override
                        public void onConfirm() {
                            System.out.println("Removed!");
                        }
                    });
                }
            }
        });

        markAsSoldButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (markAsSoldButton.isEnabled()) {
                    new ConfirmationBox(new OnResults() {
                        @Override
                        public void onConfirm() {
                            System.out.println("Marked as sold!");
                        }
                    });
                }
            }
        });

        scheduleMaintenanceButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (scheduleMaintenanceButton.isEnabled()) {
                    new ConfirmationBox(new OnResults() {
                        @Override
                        public void onConfirm() {
                            System.out.println("Scheduled for maintenance!");
                        }
                    });
                }
            }
        });
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
        
        JLabel title = new JLabel(String.format("%d %s %s", vehicle.getYear(), vehicle.getMake(), vehicle.getModel()));
        Font titleFont = new Font("Arial", Font.BOLD, 30);
        title.setFont(titleFont);

        frame.add(title, UseCases.getGridBagConst(0, 0, 2, 1, true, false, padding));

        JSeparator titleSeparator = new JSeparator(JSeparator.HORIZONTAL);
        titleSeparator.setForeground(Color.BLACK);
        frame.add(titleSeparator, UseCases.getGridBagConst(0, 1, 2, 1, true, false, padding));

        JLabel makeLabel = new JLabel("<html><b>Make:</b> " + vehicle.getMake());
        JLabel modelLabel = new JLabel("<html><b>Model:</b> " + vehicle.getModel());
        JLabel year = new JLabel("<html><b>Year:</b> " + vehicle.getYear());
        JLabel color = new JLabel("<html><b>Color:</b> " + vehicle.getColor());
        JLabel price = new JLabel("<html><b>Price:</b> " + String.format("$%,.2f", vehicle.getPrice()));
        JLabel condition = new JLabel("<html><b>Condition:</b> " + vehicle.getCondition());
        JLabel vin = new JLabel("<html><b>Vin:</b> " + vehicle.getVin());
        JLabel status = new JLabel("<html><b>Status:</b> " + vehicle.getStatus());
    
        JLabel detailLabels[] = {makeLabel, modelLabel, year, color, price, condition, vin, status};

        Font detailFont = new Font("Arial", Font.PLAIN, 20);
        for (int i = 0; i < detailLabels.length; i++) {
            detailLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
            detailLabels[i].setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
            detailLabels[i].setFont(detailFont);
    
            frame.add(detailLabels[i], UseCases.getGridBagConst(0, i + 2, 2, 1, true, false, padding));
        }

        JSeparator exitSeparator = new JSeparator(JSeparator.HORIZONTAL);
        exitSeparator.setForeground(Color.BLACK);
        frame.add(exitSeparator, UseCases.getGridBagConst(0, 11, 2, 1, true, false, padding));

        JButton exitButton = new JButton("Exit");
        exitButton.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        exitButton.setPreferredSize(new Dimension(0, 50));
        Font exitFont = new Font("Arial", Font.BOLD, 20);
        exitButton.setFont(exitFont);
        frame.add(exitButton, UseCases.getGridBagConst(0, 12, 2, 1, true, true, padding));

        exitButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
            }
        });

        frame.pack();
        frame.repaint();
    }
}

class ConfirmationBox {
    JFrame frame;
    int padding;
    OnResults onResultsFunctions;

    ConfirmationBox(OnResults onResultsFunctions) {
        this.onResultsFunctions = onResultsFunctions;
        frame = new JFrame("Confirm Selection");

        padding = 10;

        ShowConfirmationBox();
        
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setVisible(true);
    }

    void ShowConfirmationBox() {
        frame.setLayout(new GridBagLayout());

        JLabel question = new JLabel("Are you sure?", SwingConstants.CENTER);
        question.setFont(new Font("Arial", Font.BOLD, 25));
        question.setMaximumSize(new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE));
        frame.add(question, UseCases.getGridBagConst(0, 0, 2, 1, true, false, padding));

        Font buttonFont = new Font("Arial", Font.BOLD, 15);

        JButton denyButton = new JButton("Deny");
        denyButton.setPreferredSize(new Dimension(150, 40));
        denyButton.setFont(buttonFont);
        frame.add(denyButton, UseCases.getGridBagConst(0, 1, 1, 1, false, true, padding));
        denyButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                onResultsFunctions.onDeny();
            }
        });

        JButton confirmButton = new JButton("Confirm");
        confirmButton.setPreferredSize(new Dimension(150, 40));
        confirmButton.setFont(buttonFont);
        frame.add(confirmButton, UseCases.getGridBagConst(1, 1, 1, 1, true, true, padding));
        confirmButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
                onResultsFunctions.onConfirm();
            }
        });

        frame.pack();
    }
}

interface OnResults {
    default void onConfirm() {};
    default void onDeny() {};
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
        } else if (index == UseCases.hoveredIndex) {
            setBackground(new Color(230, 230, 230));
        } else {
            setBackground(new Color(255, 255, 255));
        }
                
        return this;
    }   
 }