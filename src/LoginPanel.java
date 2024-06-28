import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

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
        try {
            URL url = new URL("http://localhost/webServicejson/login.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);

            // Construct POST data
            String postData = "action=validateUser&username=" + username + "&password=" + password;

            try (OutputStream os = conn.getOutputStream()) {
                os.write(postData.getBytes());
                os.flush();
            }

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Parse JSON response
                String jsonResponse = response.toString();
                System.out.println("JSON Response: " + jsonResponse); // Debugging

                // Check if JSON response indicates successful login
                return jsonResponse.contains("\"status\":\"success\"");
            } else {
                System.out.println("HTTP Error: " + responseCode); // Debugging
            }
        } catch (IOException e) {
            e.printStackTrace(); // Log or display any Java IO exceptions
        }

        return false; // Default to login failure
    }
}
