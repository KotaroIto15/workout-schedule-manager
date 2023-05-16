package ui;

import javax.swing.*;
import java.awt.*;

// Home panel of the application
public class WelcomeUI extends JPanel {

    // EFFECTS: constructs the home of the application with the background color of GREEN,
    //          and the welcome message.
    public WelcomeUI() {
        super();
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        JLabel welcomeMsg = new JLabel("Welcome to the workout planner!!");
        welcomeMsg.setVerticalAlignment(JLabel.CENTER);
        welcomeMsg.setHorizontalAlignment(JLabel.CENTER);
        welcomeMsg.setForeground(Color.BLUE);
        welcomeMsg.setFont(new Font("Arial", Font.PLAIN, 30));

        add(welcomeMsg, BorderLayout.CENTER);
    }
}
