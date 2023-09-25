import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayPanel extends JPanel {
    private JLabel timeLeftLabel;
    private JLabel robberiesLabel;
    private JLabel countdownLabel;
    private int countdownSeconds = 10; // Initial countdown time in seconds
    private Timer countdownTimer;

    public PlayPanel() {
        setLayout(new BorderLayout());

        // Create a panel for the top center
        JPanel topCenterPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcCenter = new GridBagConstraints();
        gbcCenter.insets = new Insets(5, 10, 5, 10); // Padding

        countdownLabel = new JLabel("Get ready in " + countdownSeconds + " seconds");
        countdownLabel.setFont(new Font("Consolas", Font.BOLD, 24));
        topCenterPanel.add(countdownLabel, gbcCenter);

        // Add the top center panel to the main panel
        add(topCenterPanel, BorderLayout.CENTER);

        // Create a panel for the top left corner
        JPanel topLeftPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcLeft = new GridBagConstraints();
        gbcLeft.insets = new Insets(5, 10, 5, 10); // Padding
        timeLeftLabel = new JLabel("Time Left: 0");
        timeLeftLabel.setFont(new Font("Consolas", Font.BOLD, 24));
        topLeftPanel.add(timeLeftLabel, gbcLeft);

        // Create a panel for the top right corner
        JPanel topRightPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcRight = new GridBagConstraints();
        gbcRight.insets = new Insets(5, 10, 5, 10); // Padding
        robberiesLabel = new JLabel("Robberies: 0");
        robberiesLabel.setFont(new Font("Consolas", Font.BOLD, 24));
        topRightPanel.add(robberiesLabel, gbcRight);

        // Add the top left and top right panels to the main panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(topLeftPanel, BorderLayout.WEST);
        topPanel.add(topRightPanel, BorderLayout.EAST);
        add(topPanel, BorderLayout.NORTH);

        // Create a timer to update the countdown
        countdownTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                countdownSeconds--;

                if (countdownSeconds > 0) {
                    countdownLabel.setText("Get ready in " + countdownSeconds + " seconds");
                } else if (countdownSeconds == 0) {
                    countdownLabel.setText("GO!!!");

                    // Start a timer to spawn Basic Dariuses after the countdown
                    EntityCreation.createEntityPanel(PlayPanel.this);
                } else {
                    // Timer reached a negative value (countdown finished)
                    ((Timer) e.getSource()).stop(); // Stop the timer
                    countdownLabel.setText(""); // Clear the label
                }
            }
        });

        countdownTimer.start(); // Start the countdown timer
    }
}
