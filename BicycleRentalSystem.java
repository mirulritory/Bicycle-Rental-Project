import javax.swing.*;
import java.awt.CardLayout;

public class BicycleRentalSystem extends JFrame {

    private String loggedInUser;

    public BicycleRentalSystem() {
        setTitle("Bicycle Rental System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        CardLayout cardLayout = new CardLayout();
        JPanel mainPanel = new JPanel(cardLayout);

        LoginPanel loginPanel = new LoginPanel(mainPanel);
        HomePanel homePanel = new HomePanel(this, mainPanel);
        RentBicyclePanel rentBicyclePanel = new RentBicyclePanel(mainPanel);
        ReturnBicyclePanel returnBicyclePanel = new ReturnBicyclePanel(this, mainPanel);

        mainPanel.add(loginPanel, "Login");
        mainPanel.add(homePanel, "Home");
        mainPanel.add(rentBicyclePanel, "RentBicycle");
        mainPanel.add(returnBicyclePanel, "ReturnBicycle");

        // Initially show the login panel
        cardLayout.show(mainPanel, "Login");

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
