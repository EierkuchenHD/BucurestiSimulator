import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class MainMenu extends JFrame {

    private CardLayout cardLayout;
    private JPanel cardPanel;
    private SettingsPanel settingsPanel;
    private boolean isFullscreen = false;

    // Constants for button dimensions
    private static final int BUTTON_WIDTH = 180;
    private static final int BUTTON_HEIGHT = 70;

    public MainMenu() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error setting look and feel: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
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
        File iconFile = new File("images/fiend.png");
        if (iconFile.exists()) {
            ImageIcon icon = new ImageIcon(iconFile.getAbsolutePath());
            Image iconImage = icon.getImage();
            setIconImage(iconImage);
        } else {
            JOptionPane.showMessageDialog(null, "Icon file not found.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initButtons() {
        // Create buttons with specific sizes
        JButton playButton = createButton("Play"); // Adjusted size
        JButton settingsButton = createButton("Settings"); // Adjusted size
        JButton creditsButton = createButton("Credits"); // Adjusted size
        JButton quitButton = createButton("Quit"); // Adjusted size

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

    // Use constants in createButton method
    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Consolas", Font.BOLD, 24));
        button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
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
                    JOptionPane.showMessageDialog(null, "Background music unmuted.", "Sound",
                            JOptionPane.INFORMATION_MESSAGE);
                } else {
                    BGM.mute();
                    JOptionPane.showMessageDialog(null, "Background music muted.", "Sound",
                            JOptionPane.INFORMATION_MESSAGE);
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

        // Add key binding for Escape key while in PlayPanel
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "backToMainMenu");
        getRootPane().getActionMap().put("backToMainMenu", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "menu"); // Show the main menu
                BGM.stop(); // Stop the BGM
                BGM.playMainMenuBackgroundMusic(); // Play main menu background music
            }
        });
    }

    private void setFullscreen(boolean fullscreen) {
        if (fullscreen) {
            if (!isFullscreen) {
                dispose(); // Dispose of the current frame
                setUndecorated(true); // Make it borderless
                setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximize the window
                setVisible(true);
                isFullscreen = true;
                JOptionPane.showMessageDialog(null, "Switched to Fullscreen Mode.", "Fullscreen",
                        JOptionPane.INFORMATION_MESSAGE);
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
                JOptionPane.showMessageDialog(null, "Switched to Windowed Mode.", "Fullscreen",
                        JOptionPane.INFORMATION_MESSAGE);
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
        BGM.stop(); // Stop the current music
        PlayPanel playPanel = new PlayPanel(); // Create an instance of PlayPanel
        cardPanel.add(playPanel, "playpanel"); // Add it to the card panel
        cardLayout.show(cardPanel, "playpanel"); // Show the play panel
        BGM.playPlayPanelMusic(); // Play play panel music
    }

    // Method to show the settings panel
    private void showSettingsPanel() {
        BGM.stop(); // Stop the current music
        cardLayout.show(cardPanel, "settings");
    }
}
