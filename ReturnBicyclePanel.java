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

public class ReturnBicyclePanel extends JPanel {

    private JList<String> rentedBicycleList;
    private DefaultListModel<String> rentedBicycleListModel;
    private JPanel mainPanel;
    private String loggedInUsername;
    private JLabel usernameLabel; // Highlighted: Added a JLabel to display the logged-in username

    public ReturnBicyclePanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
        setLayout(new BorderLayout());

        usernameLabel = new JLabel("Logged-in User: "); // Highlighted: Added initialization of the JLabel
        add(usernameLabel, BorderLayout.NORTH);

        JLabel titleLabel = new JLabel("Return a Bicycle", JLabel.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        rentedBicycleListModel = new DefaultListModel<>();
        rentedBicycleList = new JList<>(rentedBicycleListModel);
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
                fetchLoggedInUsername(); // Fetch the logged-in username on button click
                if (loggedInUsername != null) {
                    String selectedBicycle = rentedBicycleList.getSelectedValue();
                    if (selectedBicycle != null) {
                        if (returnBicycle(selectedBicycle)) {
                            JOptionPane.showMessageDialog(null, "Returning: " + selectedBicycle);
                            loadRentedBicycles(); // Reload rented bicycles for the user
                            CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
                            cardLayout.show(mainPanel, "Home");
                        } else {
                            JOptionPane.showMessageDialog(null, "Failed to return bicycle.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Please select a bicycle to return.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No logged-in user found.");
                }
            }
        });

        buttonPanel.add(backButton);
        buttonPanel.add(returnButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void addNotify() {
        super.addNotify();
        fetchLoggedInUsername(); // Fetch logged-in username on panel initialization
        if (loggedInUsername != null) {
            usernameLabel.setText("Logged-in User: " + loggedInUsername); // Highlighted: Set the text of the usernameLabel
            loadRentedBicycles();
        }
    }

    private void fetchLoggedInUsername() {
        BicycleRentalSystem frame = (BicycleRentalSystem) SwingUtilities.getWindowAncestor(mainPanel);
        if (frame != null) {
            loggedInUsername = frame.getLoggedInUser();
            System.out.println("fetchLoggedInUsername: loggedInUsername = " + loggedInUsername); // Debugging log
            usernameLabel.setText("Logged-in User: " + loggedInUsername); // Highlighted: Set the text of the usernameLabel
        }
    }

    private void loadRentedBicycles() {
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
            URL url = new URL("http://localhost/webServicejson/fetchRentedBicycles.php?username=" + URLEncoder.encode(username, "UTF-8"));
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
                URL url = new URL("http://localhost/webServicejson/returnBicycle.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setDoOutput(true);

                String postData = "bicycleID=" + URLEncoder.encode(bicycleID, "UTF-8");

                try (OutputStream os = conn.getOutputStream()) {
                    os.write(postData.getBytes());
                    os.flush();
                }

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String response = in.readLine();
                    in.close();

                    return response.contains("\"status\":\"success\"");
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
