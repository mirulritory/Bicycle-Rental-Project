import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePanel extends JPanel {
    private BicycleRentalSystem frame;

    public HomePanel(BicycleRentalSystem frame, JPanel mainPanel) {
        this.frame = frame;
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Welcome to the Bicycle Rental System", JLabel.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(128, 128, 255));
        JButton rentButton = new JButton("Rent a Bicycle");
        rentButton.setBackground(new Color(255, 128, 128));
        rentButton.setBounds(142, 116, 137, 36);
        JButton returnButton = new JButton("Return a Bicycle");
        returnButton.setBackground(new Color(255, 128, 128));
        returnButton.setBounds(313, 116, 137, 36);

        rentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
                cardLayout.show(mainPanel, "RentBicycle");
            }
        });

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = frame.getLoggedInUser();
                ReturnBicyclePanel returnBicyclePanel = (ReturnBicyclePanel) mainPanel.getComponent(3);
                returnBicyclePanel.setLoggedInUsername(username);
                returnBicyclePanel.loadRentedBicycles();

                CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
                cardLayout.show(mainPanel, "ReturnBicycle");
            }
        });
        buttonPanel.setLayout(null);

        buttonPanel.add(rentButton);
        
        JButton logoutButton = new JButton("Log Out");
        logoutButton.setBackground(new Color(255, 128, 128));
        logoutButton.setBounds(234, 244, 114, 21);
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Show the main panel on logout
                CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
                cardLayout.show(mainPanel, "Login");
            }
        });
        buttonPanel.add(logoutButton);
        buttonPanel.add(returnButton);

        add(buttonPanel, BorderLayout.CENTER);
        
        JLabel lblNewLabel = new JLabel("MAIN MENU");
        lblNewLabel.setForeground(new Color(0, 64, 128));
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 24));
        lblNewLabel.setBounds(234, 59, 140, 36);
        buttonPanel.add(lblNewLabel);
    }
}
