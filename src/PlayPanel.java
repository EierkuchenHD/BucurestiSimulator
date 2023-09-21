import javax.swing.*;
import java.awt.*;

public class PlayPanel extends JPanel {
    private JLabel welcomeLabel;

    public PlayPanel() {
        setLayout(new BorderLayout());

        welcomeLabel = new JLabel("Welcome to the game!");
        welcomeLabel.setFont(new Font("Consolas", Font.BOLD, 36)); // Set font and size
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER); // Center-align the text
        add(welcomeLabel, BorderLayout.CENTER);
    }

    // Method to set the welcome text
    public void setWelcomeText(String text) {
        welcomeLabel.setText(text);
    }
}
