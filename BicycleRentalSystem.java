import java.awt.CardLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class BicycleRentalSystem extends JFrame {

    private String loggedInUser;

    public BicycleRentalSystem() {
        setTitle("Bicycle Rental System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        mainPanel.add(new LoginPanel(mainPanel), "Login");
        mainPanel.add(new HomePanel(mainPanel), "Home");
        mainPanel.add(new RentBicyclePanel(mainPanel), "RentBicycle");
        mainPanel.add(new ReturnBicyclePanel(mainPanel), "ReturnBicycle");

        add(mainPanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BicycleRentalSystem());
    }

    public void setLoggedInUser(String username) {
        this.loggedInUser = username;
    }

    public String getLoggedInUser() {
        return loggedInUser;
    }
}
