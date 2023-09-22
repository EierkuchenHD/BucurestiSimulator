import javax.swing.*;
import java.awt.*;

public class PlayPanel extends JPanel {
    private JLabel welcomeLabel;
    private JLabel timeLeftLabel;
    private JLabel robberiesLabel;

    public PlayPanel() {
        setLayout(new BorderLayout());

        // Create a panel for the top left corner with FlowLayout
        JPanel topLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        timeLeftLabel = new JLabel("Time Left: 0");
        timeLeftLabel.setFont(new Font("Consolas", Font.BOLD, 24));
        topLeftPanel.add(timeLeftLabel);

        // Create a panel for the top right corner with FlowLayout
        JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        robberiesLabel = new JLabel("Robberies: 0");
        robberiesLabel.setFont(new Font("Consolas", Font.BOLD, 24));
        topRightPanel.add(robberiesLabel);

        // Add the top left and top right panels to the main panel
        add(topLeftPanel, BorderLayout.WEST);
        add(topRightPanel, BorderLayout.EAST);

        // Center label
        welcomeLabel = new JLabel("Get ready...");
        welcomeLabel.setFont(new Font("Consolas", Font.BOLD, 36));
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        add(welcomeLabel, BorderLayout.CENTER);
    }

    // Method to set the welcome text
    public void setWelcomeText(String text) {
        welcomeLabel.setText(text);
    }

    // Method to update the "Time Left" counter
    public void updateTimeLeft(int timeLeft) {
        timeLeftLabel.setText("Time Left: " + timeLeft);
    }

    // Method to update the "Robberies" counter
    public void updateRobberies(int robberies) {
        robberiesLabel.setText("Robberies: " + robberies);
    }
}
