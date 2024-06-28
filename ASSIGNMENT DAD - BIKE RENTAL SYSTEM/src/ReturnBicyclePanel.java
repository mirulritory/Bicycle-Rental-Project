import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class ReturnBicyclePanel extends JPanel {
    private JList<String> rentedBicycleList;
    private DefaultListModel<String> rentedBicycleListModel;
    private JPanel mainPanel;
    private String loggedInUsername;
    private JLabel usernameLabel;

    public ReturnBicyclePanel(BicycleRentalSystem frame, JPanel mainPanel) {
        this.mainPanel = mainPanel;
        setLayout(new BorderLayout());

        usernameLabel = new JLabel("Logged-in User: ");
        add(usernameLabel, BorderLayout.NORTH);

        JLabel titleLabel = new JLabel("Return a Bicycle", JLabel.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        rentedBicycleListModel = new DefaultListModel<>();
        rentedBicycleList = new JList<>(rentedBicycleListModel);
        rentedBicycleList.setBackground(new Color(128, 128, 255));
        add(new JScrollPane(rentedBicycleList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(0, 64, 128));
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
                String selectedBicycle = rentedBicycleList.getSelectedValue();
                if (selectedBicycle != null) {
                    if (returnBicycle(selectedBicycle)) {
                        JOptionPane.showMessageDialog(null, "Successfully returned: " + selectedBicycle);
                        loadRentedBicycles();
                    } else {
                        JOptionPane.showMessageDialog(null, "Failed to return bicycle.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a bicycle to return.");
                }
            }
        });

        buttonPanel.add(backButton);
        buttonPanel.add(returnButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setLoggedInUsername(String username) {
        this.loggedInUsername = username;
        usernameLabel.setText("Logged-in User: " + username);
    }

    public void loadRentedBicycles() {
        if (loggedInUsername != null) {
            List<String> rentedBicycles = fetchRentedBicycles(loggedInUsername);
            rentedBicycleListModel.clear();
            for (String bicycle : rentedBicycles) {
                rentedBicycleListModel.addElement(bicycle);
            }
        } else {
            JOptionPane.showMessageDialog(null, "No logged-in user found.");
        }
    }

    private List<String> fetchRentedBicycles(String username) {
        List<String> bicycles = new ArrayList<>();
        try {
            URL url = new URL("http://10.200.116.32/Bicycle-Rental-Project/fetchRentedBicycle.php?username=" + URLEncoder.encode(username, "UTF-8"));
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    bicycles.add(inputLine);
                }
                in.close();
            } else {
                System.out.println("HTTP Error: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bicycles;
    }

    private boolean returnBicycle(String bicycleID) {
        try {
            if (loggedInUsername != null) {
                URL url = new URL("http://10.200.116.32/Bicycle-Rental-Project/returnBicycle.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setDoOutput(true);

                String postData = "bicycleID=" + URLEncoder.encode(bicycleID, "UTF-8") + "&username=" + URLEncoder.encode(loggedInUsername, "UTF-8");

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

                    // Print server response for debugging
                    System.out.println("Server response: " + response.toString());

                    // Parse JSON response
                    JSONObject jsonResponse = new JSONObject(response.toString());
                    if (jsonResponse.has("status") && jsonResponse.getString("status").equals("success")) {
                        return true;
                    } else {
                        System.out.println("Return bicycle failed: " + response);
                    }
                } else {
                    System.out.println("HTTP Error: " + responseCode);
                }
            } else {
                JOptionPane.showMessageDialog(null, "No logged-in user found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
