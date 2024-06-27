import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ReturnBicyclePanel extends JPanel {

    public ReturnBicyclePanel(JPanel mainPanel) {
        setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("Return a Bicycle", JLabel.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Dummy list of rented bicycles
        String[] rentedBicycles = {"Bicycle 1"};
        JList<String> rentedBicycleList = new JList<>(rentedBicycles);
        add(new JScrollPane(rentedBicycleList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton backButton = new JButton("Back");
        JButton returnButton = new JButton("Return");

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
                cardLayout.show(mainPanel, "Home");
            }
        });

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Code to handle returning the selected bicycle
                String selectedBicycle = rentedBicycleList.getSelectedValue();
                if (selectedBicycle != null) {
                    JOptionPane.showMessageDialog(null, "Returning: " + selectedBicycle);
                    CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
                    cardLayout.show(mainPanel, "Home");
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a bicycle to return.");
                }
            }
        });

        buttonPanel.add(backButton);
        buttonPanel.add(returnButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }
}
