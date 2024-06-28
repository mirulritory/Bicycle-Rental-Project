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
        loginFormPanel.setBackground(new Color(128, 128, 255));
        loginFormPanel.setLayout(null);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        usernameLabel.setBounds(98, 58, 111, 66);
        JTextField usernameField = new JTextField();
        usernameField.setBounds(209, 79, 225, 30);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        passwordLabel.setBounds(98, 110, 111, 95);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(209, 141, 225, 37);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(209, 197, 225, 37);

        JLabel label = new JLabel();
        label.setBounds(0, 203, 225, 95);

        loginFormPanel.add(usernameLabel);
        loginFormPanel.add(usernameField);
        loginFormPanel.add(passwordLabel);
        loginFormPanel.add(passwordField);
        loginFormPanel.add(label);
        loginFormPanel.add(loginButton);

        add(loginFormPanel, BorderLayout.CENTER);

        JLabel lblNewLabel = new JLabel("BICYCLE RENTAL SYSTEM");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel.setBounds(209, 18, 209, 30);
        loginFormPanel.add(lblNewLabel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                if (validateUser(username, password)) {
                    BicycleRentalSystem frame = (BicycleRentalSystem) SwingUtilities.getWindowAncestor(mainPanel);
                    frame.setLoggedInUser(username);
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password.");
                }
            }

            private boolean validateUser(String username, String password) {
                try {
                    URL url = new URL("http://10.200.116.32/Bicycle-Rental-Project/login.php");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.setRequestMethod("POST");
                    conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    conn.setDoOutput(true);

                    // Construct POST data
                    String postData = "username=" + URLEncoder.encode(username, "UTF-8") + "&password=" + URLEncoder.encode(password, "UTF-8");

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
                        if (jsonResponse.contains("\"status\":\"success\"")) {
                            CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
                            if (jsonResponse.contains("\"role\":\"user\"")) {
                                cardLayout.show(mainPanel, "Home");
                            } else if (jsonResponse.contains("\"role\":\"staff\"")) {
                                cardLayout.show(mainPanel, "Staff");
                            }
                            return true;
                        }
                    } else {
                        System.out.println("HTTP Error: " + responseCode); // Debugging
                    }
                } catch (IOException e) {
                    e.printStackTrace(); // Log or display any Java IO exceptions
                }

                return false; // Default to login failure
            }
        });
    }
}
