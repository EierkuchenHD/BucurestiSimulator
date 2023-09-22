import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;

public class SettingsPanel extends JPanel {

    private JSlider bgmVolumeSlider;
    private JSlider sfxVolumeSlider;
    private JButton backButton; // Added "Back" button
    private JButton restoreDefaultsButton; // Added "Restore Default Settings" button
    private Preferences preferences;

    public SettingsPanel() {
        setLayout(new GridBagLayout());

        preferences = Preferences.userNodeForPackage(SettingsPanel.class);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST; // Align labels to the left

        Font labelFont = new Font("Consolas", Font.BOLD, 24);

        JLabel bgmVolumeLabel = createLabel("Background Music Volume:");
        bgmVolumeLabel.setFont(labelFont);
        bgmVolumeSlider = createSlider(0, 100, preferences.getInt("bgmVolume", 50));
        bgmVolumeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int volume = bgmVolumeSlider.getValue();
                preferences.putInt("bgmVolume", volume);
                BGM.setVolume(volume); // Update BGM volume immediately based on slider position
            }
        });

        JLabel sfxVolumeLabel = createLabel("Sound Effects Volume:");
        sfxVolumeLabel.setFont(labelFont);
        sfxVolumeSlider = createSlider(0, 100, preferences.getInt("sfxVolume", 50));
        sfxVolumeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int volume = sfxVolumeSlider.getValue();
                preferences.putInt("sfxVolume", volume);
                SFX.setVolume(volume); // Update SFX volume immediately based on slider position
            }
        });

        // Added "Back" button
        backButton = createButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Play button click sound
                SFX.playButtonClickSound();

                // Handle navigation to the previous panel
                CardLayout cardLayout = (CardLayout) getParent().getLayout();
                cardLayout.show(getParent(), "menu"); // Assuming "menu" is the name of the previous panel
            }
        });

        // Added "Restore Default Settings" button
        restoreDefaultsButton = createButton("Restore Default Settings");
        restoreDefaultsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Reset sliders to default value (50)
                bgmVolumeSlider.setValue(50);
                sfxVolumeSlider.setValue(50);

                // Play button click sound
                SFX.playButtonClickSound();
            }
        });

        add(bgmVolumeLabel, gbc);
        gbc.gridy++;
        add(bgmVolumeSlider, gbc);
        gbc.gridy++;
        add(sfxVolumeLabel, gbc);
        gbc.gridy++;
        add(sfxVolumeSlider, gbc);
        gbc.gridy++;
        add(backButton, gbc); // "Back" button
        gbc.gridy++;
        add(restoreDefaultsButton, gbc); // "Restore Default Settings" button
    }

    private JSlider createSlider(int min, int max, int value) {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, min, max, value);
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setFont(new Font("Consolas", Font.BOLD, 24));
        slider.setPreferredSize(new Dimension(450, 70));
        return slider;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Consolas", Font.BOLD, 24));
        return label;
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Consolas", Font.BOLD, 24));
        return button;
    }

    public void loadSettings() {
        bgmVolumeSlider.setValue(preferences.getInt("bgmVolume", 50));
        sfxVolumeSlider.setValue(preferences.getInt("sfxVolume", 50));
    }
}
