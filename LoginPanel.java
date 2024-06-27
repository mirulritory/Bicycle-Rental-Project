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
import java.net.URLEncoder;

public class LoginPanel extends JPanel {

    private JPanel mainPanel;

    public LoginPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
        setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("Login", JLabel.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        JPanel loginFormPanel = new JPanel();
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(110, 22, 111, 84);
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(116, 95, 105, 73);
        JTextField usernameField = new JTextField();
        usernameField.setBounds(215, 54, 148, 20);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(215, 121, 148, 20);
        loginFormPanel.setLayout(null);

        loginFormPanel.add(usernameLabel);
        loginFormPanel.add(usernameField);
        loginFormPanel.add(passwordLabel);
        loginFormPanel.add(passwordField);

        add(loginFormPanel, BorderLayout.CENTER);
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(197, 179, 72, 23);
        loginFormPanel.add(loginButton);
        
                loginButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String username = usernameField.getText();
                        String password = new String(passwordField.getPassword());
        
                        if (validateUser(username, password)) {
                            ((BicycleRentalSystem) SwingUtilities.getWindowAncestor(mainPanel)).setLoggedInUser(username);
                            CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
                            cardLayout.show(mainPanel, "Home");
                        } else {
                            JOptionPane.showMessageDialog(null, "Invalid username or password.");
                        }
                    }
        
                    private boolean validateUser(String username, String password) {
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
        
                });

        JPanel buttonPanel = new JPanel();
        add(buttonPanel, BorderLayout.SOUTH);
    }
}
