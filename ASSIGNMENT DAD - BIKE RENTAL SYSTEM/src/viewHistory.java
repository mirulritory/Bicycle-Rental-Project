import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class viewHistory extends JPanel {

    private JTable rentedBicycleTable;
    private DefaultTableModel tableModel;
    private JPanel mainPanel;

    public viewHistory(JPanel mainPanel) {
        this.mainPanel = mainPanel;
        setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("View Rental History", JLabel.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        // Define table columns
        String[] columnNames = {"Name", "Phone Number", "Bicycle ID"};
        tableModel = new DefaultTableModel(columnNames, 0);
        rentedBicycleTable = new JTable(tableModel);

        add(new JScrollPane(rentedBicycleTable), BorderLayout.CENTER);

        loadRentalHistory(); // Load rental history on panel initialization

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(0, 64, 128));
        JButton backButton = new JButton("Back");

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
                cardLayout.show(mainPanel, "Staff"); // Navigate back to Staff panel
            }
        });

        buttonPanel.add(backButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private List<String[]> fetchRentalHistory() {
        List<String[]> rentals = new ArrayList<>();
        try {
            URL url = new URL("http://localhost/Bicycle-Rental-Project/fetchRentalHistory.php");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                
                JSONArray jsonArray = new JSONArray(response.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String name = jsonObject.getString("Name");
                    String phoneNo = jsonObject.getString("phoneNo");
                    String bicycleID = jsonObject.getString("bicycleID");
                    rentals.add(new String[]{name, phoneNo, bicycleID});
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return rentals;
    }

    private void loadRentalHistory() {
        List<String[]> rentals = fetchRentalHistory();
        tableModel.setRowCount(0); // Clear the table
        for (String[] rental : rentals) {
            tableModel.addRow(rental);
        }
    }
}
