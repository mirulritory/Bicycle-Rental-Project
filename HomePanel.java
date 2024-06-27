import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePanel extends JPanel {

    public HomePanel(JPanel mainPanel) {
        setLayout(new BorderLayout());
        JLabel titleLabel = new JLabel("Welcome to the Bicycle Rental System", JLabel.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        JButton rentButton = new JButton("Rent a Bicycle");
        JButton returnButton = new JButton("Return a Bicycle");

        rentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
                cardLayout.show(mainPanel, "RentBicycle");
            }
        });

        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
                cardLayout.show(mainPanel, "ReturnBicycle");
            }
        });

        buttonPanel.add(rentButton);
        buttonPanel.add(returnButton);

        add(buttonPanel, BorderLayout.CENTER);
    }
}
