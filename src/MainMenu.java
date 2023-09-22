import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu extends JFrame {

    private CardLayout cardLayout;
    private JPanel cardPanel;
    private SettingsPanel settingsPanel;
    private boolean isFullscreen = false; // Track the fullscreen state

    public MainMenu() {
        try {
            // Set the system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Set custom icon
        setCustomIcon();

        setTitle("București Simulator");
        setSize(1280, 720);

        // Set a minimum resolution lock
        setMinimumSize(new Dimension(854, 480));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        settingsPanel = new SettingsPanel();

        initButtons();
        initKeyBindings();
        initCardPanel();

        BGM.playMainMenuBackgroundMusic();

        setVisible(true);
    }

    private void setCustomIcon() {
        ImageIcon icon = new ImageIcon("images/fiend.png");
        Image iconImage = icon.getImage();
        setIconImage(iconImage);
    }

    private void initButtons() {
        // Create buttons with specific sizes
        JButton playButton = createButton("Play", 180, 70); // Adjusted size
        JButton settingsButton = createButton("Settings", 180, 70); // Adjusted size
        JButton creditsButton = createButton("Credits", 180, 70); // Adjusted size
        JButton quitButton = createButton("Quit", 180, 70); // Adjusted size

        playButton.addActionListener(e -> handleButtonClick("play"));
        settingsButton.addActionListener(e -> handleButtonClick("settings"));
        creditsButton.addActionListener(e -> handleButtonClick("credits"));
        quitButton.addActionListener(e -> handleButtonClick("quit"));

        // Create a panel for buttons with GridBagLayout
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Padding

        // Add the "BUCURESTI SIMULATOR" text above the buttons
        JLabel titleLabel = createTitleLabel("BUCURESTI SIMULATOR");
        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(titleLabel, gbc);

        gbc.gridy = 1;
        buttonPanel.add(playButton, gbc);

        gbc.gridy = 2;
        buttonPanel.add(settingsButton, gbc);

        gbc.gridy = 3;
        buttonPanel.add(creditsButton, gbc);

        gbc.gridy = 4;
        buttonPanel.add(quitButton, gbc);

        cardPanel.add(buttonPanel, "menu");
    }

    private JButton createButton(String text, int width, int height) {
        JButton button = new JButton(text);
        button.setFont(new Font("Consolas", Font.BOLD, 24));
        button.setPreferredSize(new Dimension(width, height));
        return button;
    }

    private JLabel createTitleLabel(String text) {
        JLabel titleLabel = new JLabel(text);
        titleLabel.setFont(new Font("Consolas", Font.BOLD, 36));
        titleLabel.setForeground(Color.RED); // Set text color to red
        return titleLabel;
    }

    private void initKeyBindings() {
        Action showMainMenuAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "menu");
            }
        };

        Action muteBGMAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Toggle BGM mute state
                if (BGM.isMuted()) {
                    BGM.unmute();
                } else {
                    BGM.mute();
                }
            }
        };

        Action toggleFullscreenAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Toggle fullscreen mode
                setFullscreen(!isFullscreen);
            }
        };

        Action playSecretSoundAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SFX.playSecretSound();
            }
        };

        // Add key bindings for M, F11, and P
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_M, 0), "muteBGM");
        getRootPane().getActionMap().put("muteBGM", muteBGMAction);

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_F11, 0), "toggleFullscreen");
        getRootPane().getActionMap().put("toggleFullscreen", toggleFullscreenAction);

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_P, 0), "playSecretSound");
        getRootPane().getActionMap().put("playSecretSound", playSecretSoundAction);

        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "showMainMenu");
        getRootPane().getActionMap().put("showMainMenu", showMainMenuAction);
    }

    private void setFullscreen(boolean fullscreen) {
        if (fullscreen) {
            if (!isFullscreen) {
                dispose(); // Dispose of the current frame
                setUndecorated(true); // Make it borderless
                setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the window
                setVisible(true);
                isFullscreen = true;
            }
        } else {
            if (isFullscreen) {
                dispose(); // Dispose of the current frame
                setUndecorated(false); // Make it decorated (with borders)
                setExtendedState(JFrame.NORMAL); // Restore normal state
                pack(); // Pack the frame to preferred size
                setLocationRelativeTo(null); // Center the frame
                setVisible(true);
                isFullscreen = false;
            }
        }
    }

    private void initCardPanel() {
        JPanel creditsPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon dariusImage = new ImageIcon("images/darius.png");
                Image image = dariusImage.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
            }
        };

        cardPanel.add(creditsPanel, "credits");

        cardPanel.add(settingsPanel, "settings");

        add(cardPanel);
    }

    // Handle button click actions
    private void handleButtonClick(String action) {
        SFX.playButtonClickSound(); // Play button click sound
        switch (action) {
            case "play":
                showPlayPanel();
                break;
            case "settings":
                showSettingsPanel();
                break;
            case "credits":
                cardLayout.show(cardPanel, "credits");
                break;
            case "quit":
                System.exit(0);
                break;
        }
    }

    // Method to show the play panel
    private void showPlayPanel() {
        PlayPanel playPanel = new PlayPanel(); // Create an instance of PlayPanel
        cardPanel.add(playPanel, "playpanel"); // Add it to the card panel
        cardLayout.show(cardPanel, "playpanel"); // Show the play panel
    }

    // Method to show the settings panel
    private void showSettingsPanel() {
        cardLayout.show(cardPanel, "settings");
    }
}
