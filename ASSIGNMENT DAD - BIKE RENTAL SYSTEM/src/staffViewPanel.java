import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class staffViewPanel extends JPanel {

    private JPanel mainPanel;

    public staffViewPanel(JPanel mainPanel) {
        this.mainPanel = mainPanel;
        setLayout(new BorderLayout());

        JPanel contentPane = new JPanel();
        contentPane.setBackground(new Color(128, 128, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(null);

        JButton btnNewButton = new JButton("View History");
        btnNewButton.setBounds(230, 114, 127, 21);
        contentPane.add(btnNewButton);

        btnNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
                cardLayout.show(mainPanel, "ViewHistory"); // Navigate to viewHistory panel
            }
        });

        add(contentPane, BorderLayout.CENTER);

        JButton btnNewButton_1 = new JButton("Log Out");
        btnNewButton_1.setBounds(230, 161, 127, 21);
        btnNewButton_1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) mainPanel.getLayout();
                cardLayout.show(mainPanel, "Login"); // Navigate to login panel on logout
            }
        });
        contentPane.add(btnNewButton_1);
        
        JLabel lblNewLabel = new JLabel("Staff Menu");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
        lblNewLabel.setBounds(234, 41, 123, 33);
        contentPane.add(lblNewLabel);
    }
}
