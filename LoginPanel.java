import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {
    public LoginPanel(JPanel mainPanel) {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Login", JLabel.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        JPanel loginPanel = new JPanel();
        loginPanel.setBackground(new Color(128, 128, 255));
        JLabel userLabel = new JLabel("Username: ");
        userLabel.setBackground(new Color(0, 64, 128));
        userLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        userLabel.setBounds(98, 58, 111, 66);
        JTextField userField = new JTextField();
        userField.setBounds(209, 79, 225, 30);
        JLabel passLabel = new JLabel("Password: ");
        passLabel.setBackground(new Color(0, 64, 128));
        passLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        passLabel.setBounds(98, 110, 111, 95);
        JPasswordField passField = new JPasswordField();
        passField.setBounds(209, 141, 225, 37);
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(209, 197, 225, 37);
        loginPanel.setLayout(null);

        loginPanel.add(userLabel);
        loginPanel.add(userField);
        loginPanel.add(passLabel);
        loginPanel.add(passField);
        JLabel label = new JLabel();
        label.setBounds(0, 203, 225, 95);
        loginPanel.add(label);
        loginPanel.add(loginButton);

        add(loginPanel, BorderLayout.CENTER);
        
        JLabel lblNewLabel = new JLabel("BICYCLE RENTAL SYSTEM");
        lblNewLabel.setBackground(new Color(0, 64, 128));
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel.setBounds(209, 18, 209, 30);
        loginPanel.add(lblNewLabel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passField.getPassword());

                if (validateLogin(username, password)) {
                    BicycleRentalSystem frame = (BicycleRentalSystem) SwingUtilities.getWindowAncestor(LoginPanel.this);
                    frame.setLoggedInUser(username);
                    CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
                    cardLayout.show(mainPanel, "Home");
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password.");
                }
            }
        });
    }

    private boolean validateLogin(String username, String password) {
        // Add your login validation logic here
        // For now, it returns true for simplicity
        return true;
    }
}
