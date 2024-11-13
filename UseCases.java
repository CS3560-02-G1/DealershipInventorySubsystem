package DealershipInventorySubsystem;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;

import org.w3c.dom.events.MouseEvent;

/*
 *  Grid layout:
 * 
 *    0 1 2 3 4
 *   +-+-+-+-+-+
 * 0 | | | | | |
 *   +-+-+-+-+-+
 * 1 | |V| |T| |
 *   +-+-+-+-+-+
 * 2 | |V| | | |
 *   +-+-+-+-+-+
 * 3 | |V| |B| |
 *   +-+-+-+-+-+
 * 4 | | | | | |
 *   +-+-+-+-+-+
 * 
 *  Space - Padding
 *  V - Vehicle List
 *  B - Buttons
 * 
 * Inventory car layout:
 * <Year> <Make> <Model> - <Color>
 * <Condition>, $<Price>
 */

public class UseCases {
    private static JFrame primaryFrame;

    private static final Vehicle VEHICLES[] = {
        new Vehicle("4Y1SL65848Z411439", "Transit 150", 2021, "Status", "Mint Condition", "Ford", "White", 31000.0),       
        new Vehicle("4Y1SL65848Z411439", "Transit 250", 2021, "Status", "Mint Condition", "Ford", "White", 31000.0)        
    };

    public static String[] getVehicleStrings(Vehicle vehicles[]) {
        String vehicleStrings[] = new String[vehicles.length];
        for (int i = 0; i < vehicles.length; i++) {
            vehicleStrings[i] = String.format(
                "<html><span style=\"font-size: 200%%;\">%d %s %s - %s<br/>%s, $%,.2f</span>",
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

    public static void main(String[] args) {
        primaryFrame = new JFrame("Dealership Inventory View");
        primaryFrame.setResizable(false);
        primaryFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        ShowInventoryMenu(primaryFrame);

        primaryFrame.setVisible(true);
    }

    private static GridBagConstraints getGridBagConst(int gridx, int gridy, int gridwidth, int gridheight) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = gridx;
        c.gridy = gridy;
        c.gridwidth = gridwidth;
        c.gridheight = gridheight;
        c.fill = GridBagConstraints.BOTH;

        return c;
    }

    private static Component getPaddingComponent() {
        return Box.createRigidArea(new Dimension(10, 10));
    }

    private static void ShowInventoryMenu(JFrame frame) {
        // frame.removeAll();

        frame.setLayout(new GridBagLayout());

        // GridBagConstraints c = new GridBagConstraints();
        frame.add(getPaddingComponent(), getGridBagConst(0, 0, 5, 1));
        frame.add(getPaddingComponent(), getGridBagConst(0, 1, 1, 3));
        frame.add(getPaddingComponent(), getGridBagConst(2, 1, 1, 3));
        frame.add(getPaddingComponent(), getGridBagConst(4, 1, 1, 3));
        frame.add(getPaddingComponent(), getGridBagConst(0, 4, 5, 1));
        frame.add(getPaddingComponent(), getGridBagConst(3, 2, 1, 1));

        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.PAGE_AXIS));


        Font myFont = new Font("Arial", Font.PLAIN, 16);
        JTextField searchBox = new JTextField(" Search...");
        searchBox.setFont(myFont);
        searchBox.setPreferredSize(new Dimension(400, 40));

        listPanel.add(searchBox);

        String[] data = getVehicleStrings(VEHICLES);
        JList<String> list = new JList<>(data);

        list.setBorder(new EtchedBorder());

        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setPreferredSize(new Dimension(400, 700));

        listPanel.add(listScroller);

        // listScroller.setBorder(paddedBorder);

        frame.add(listPanel, getGridBagConst(1, 1, 1, 3));

        JPanel imagePanel = new JPanel();
        imagePanel.setLayout(new BoxLayout(imagePanel, BoxLayout.PAGE_AXIS));
        imagePanel.setBackground(new Color(255, 0, 0));
        frame.add(imagePanel, getGridBagConst(3, 1, 1, 1));

        JLabel titleText = new JLabel("<html><span style=\"font-size: 250%;\">Dealership<br/>Inventory</span>", SwingConstants.CENTER);
        JPanel titleTextWrapper = new JPanel();
        titleTextWrapper.add(titleText);
        titleTextWrapper.setBorder(new EmptyBorder(20, 0, 20, 0));
        imagePanel.add(titleTextWrapper);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.PAGE_AXIS));

        frame.add(buttonsPanel, getGridBagConst(3, 3, 1, 1));

        String[] buttonLabels = {"Add Vehicle", "Remove Vehicle", "Mark as Sold", "Schedule for<br/>Maintenance", "Manage Vehicle"};

        for (int i = 0; i < buttonLabels.length; i++) {
            if (i != 0) buttonsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
            JButton button = new JButton("<html><span style=\"font-size: 150%\">" + buttonLabels[i]);
            // button.setBackground(new Color(255, 255, 255));
            // button.setMargin(new Insets(10, 50, 10, 50));
            button.setPreferredSize(new Dimension(300, 70));
            button.setMaximumSize(new Dimension(Integer.MAX_VALUE, 70));
            // button.setBorder(BorderFactory.createCompoundBorder(
            //     paddedBorder, new EmptyBorder(10, 10, 10, 10)
            // ));
            if (i > 3) button.setEnabled(false);

            buttonsPanel.add(button);
        }

        // c.gridx = 3;
        // c.gridy = 0;
        // c.gridwidth = 1;
        // c.gridheight = 7;

        // frame.add(Box.createRigidArea(new Dimension(10, 0)), c);

        frame.pack();
    }

    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("HelloWorldSwing");
        frame.setLayout(new FlowLayout());
        frame.setSize(new Dimension(500, 500));
        frame.setMinimumSize(new Dimension(500, 500));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel label = new JLabel("Hello World");
        label.setHorizontalAlignment(0);
        frame.getContentPane().add(label);

        JButton button = new JButton("Test");
        frame.add(button);
        JButton button2 = new JButton("Test 2");
        frame.add(button2);

        //Display the window.
        // frame.pack();
        frame.setVisible(true);
    }

    // public static ... checkInventory() {

    // }
}