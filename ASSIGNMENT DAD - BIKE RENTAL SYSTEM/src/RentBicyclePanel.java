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

public class RentBicyclePanel extends JPanel {

    private JList<String> bicycleList;
    private DefaultListModel<String> bicycleListModel;
    private JPanel mainPanel;
    private String loggedInUsername;

    public RentBicyclePanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Available Bicycles", JLabel.CENTER);
        titleLabel.setBackground(new Color(0, 64, 128));
        add(titleLabel, BorderLayout.NORTH);

        bicycleListModel = new DefaultListModel<>();
        bicycleList = new JList<>(bicycleListModel);
        bicycleList.setBackground(new Color(128, 128, 255));
        add(new JScrollPane(bicycleList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(0, 64, 128));
        JButton backButton = new JButton("Back");
        JButton rentButton = new JButton("Rent");

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
                cardLayout.show(mainPanel, "Home");
            }
        });

        rentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fetchLoggedInUsername(); // Fetch the logged-in username on button click
                if (loggedInUsername != null) {
                    String selectedBicycle = bicycleList.getSelectedValue();
                    if (selectedBicycle != null) {
                        if (rentBicycle(selectedBicycle, loggedInUsername)) {
                            JOptionPane.showMessageDialog(null, "Rented: " + selectedBicycle);
                            loadAvailableBicycles(); // Reload available bicycles
                            CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
                            cardLayout.show(mainPanel, "Home");
                        } else {
                            JOptionPane.showMessageDialog(null, "Failed to rent bicycle.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Please select a bicycle to rent.");
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "No logged-in user found.");
                }
            }
        });

        buttonPanel.add(backButton);
        buttonPanel.add(rentButton);

        add(buttonPanel, BorderLayout.SOUTH);

        loadAvailableBicycles(); // Load available bicycles on panel initialization
    }

    @Override
    public void addNotify() {
        super.addNotify();
        fetchLoggedInUsername(); // Fetch logged-in username on panel initialization
    }

    private void fetchLoggedInUsername() {
        BicycleRentalSystem frame = (BicycleRentalSystem) SwingUtilities.getWindowAncestor(mainPanel);
        if (frame != null) {
            loggedInUsername = frame.getLoggedInUser();
            System.out.println("fetchLoggedInUsername: loggedInUsername = " + loggedInUsername); // Debugging log
        }
    }

    private List<String> fetchAvailableBicycles() {
        List<String> bicycles = new ArrayList<>();
        try {
            URL url = new URL("http://10.200.116.32/Bicycle-Rental-Project/fetchbicycle.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    bicycles.add(inputLine); // Assuming each line is a bicycleID, adjust as needed
                }
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bicycles;
    }

    private void loadAvailableBicycles() {
        List<String> bicycles = fetchAvailableBicycles();
        bicycleListModel.clear();
        for (String bicycle : bicycles) {
            bicycleListModel.addElement(bicycle);
        }
    }

    private boolean rentBicycle(String bicycleID, String username) {
        try {
            URL url = new URL("http://10.200.116.32/Bicycle-Rental-Project/rentBicycle.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            conn.setDoOutput(true);

            // Construct POST data
            String postData = "bicycleID=" + URLEncoder.encode(bicycleID, "UTF-8") + "&username=" + URLEncoder.encode(username, "UTF-8");

            try (OutputStream os = conn.getOutputStream()) {
                os.write(postData.getBytes());
                os.flush();
            }

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String response = in.readLine();
                in.close();

                // Log response
                System.out.println("Server response: " + response);

                return response.contains("\"status\":\"success\"");
            } else {
                System.out.println("HTTP Error: " + responseCode); // Debugging
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
