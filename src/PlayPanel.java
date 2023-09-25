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
        initializeComponents();
        createLabels();
        setupCountdownTimer();
    }

    private void initializeComponents() {
        countdownLabel = new JLabel("Get ready in " + countdownSeconds + " seconds");
        countdownLabel.setFont(new Font("Consolas", Font.BOLD, 24));

        timeLeftLabel = new JLabel("Time Left: 0s");
        timeLeftLabel.setFont(new Font("Consolas", Font.BOLD, 24));

        robberiesLabel = new JLabel("Robberies: 0");
        robberiesLabel.setFont(new Font("Consolas", Font.BOLD, 24));
    }

    private void createLabels() {
        setLayout(new BorderLayout());

        JPanel topCenterPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcCenter = new GridBagConstraints();
        gbcCenter.insets = new Insets(5, 10, 5, 10); // Padding
        topCenterPanel.add(countdownLabel, gbcCenter);

        JPanel topLeftPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcLeft = new GridBagConstraints();
        gbcLeft.insets = new Insets(5, 10, 5, 10); // Padding
        topLeftPanel.add(timeLeftLabel, gbcLeft);

        JPanel topRightPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbcRight = new GridBagConstraints();
        gbcRight.insets = new Insets(5, 10, 5, 10); // Padding
        topRightPanel.add(robberiesLabel, gbcRight);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(topLeftPanel, BorderLayout.WEST);
        topPanel.add(topRightPanel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);
        add(topCenterPanel, BorderLayout.CENTER);
    }

    private void setupCountdownTimer() {
        countdownTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                countdownSeconds--;

                if (countdownSeconds > 0) {
                    countdownLabel.setText("Get ready in " + countdownSeconds + " seconds");
                } else if (countdownSeconds == 0) {
                    countdownLabel.setText("GO!!!");
                    spawnEntity();
                } else {
                    ((Timer) e.getSource()).stop();
                    countdownLabel.setText("");
                }
            }
        });
        countdownTimer.start();
    }

    private void spawnEntity() {
        EntityCreation.createEntityPanel(PlayPanel.this);
    }
}
