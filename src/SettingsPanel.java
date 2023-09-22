import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SettingsPanel extends JPanel {

    private JSlider bgmVolumeSlider;
    private JSlider sfxVolumeSlider;
    private JComboBox<String> resolutionComboBox;
    private Preferences preferences;

    public SettingsPanel() {
        setLayout(new GridBagLayout());

        preferences = Preferences.userNodeForPackage(SettingsPanel.class);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;

        Font labelFont = new Font("Consolas", Font.BOLD, 24);

        JLabel bgmVolumeLabel = createLabel("Background Music Volume:");
        bgmVolumeLabel.setFont(labelFont);
        bgmVolumeSlider = createSlider(0, 100, preferences.getInt("bgmVolume", 50));
        bgmVolumeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int volume = bgmVolumeSlider.getValue();
                preferences.putInt("bgmVolume", volume);
                float normalizedVolume = (float) volume / 100.0f;
                BGM.setVolume(normalizedVolume); // Update BGM volume immediately
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
                float normalizedVolume = (float) volume / 100.0f;
                SFX.setVolume(normalizedVolume); // Adjust SFX volume
            }
        });

        JLabel resolutionLabel = createLabel("Screen Resolution:");
        resolutionComboBox = createResolutionComboBox();
        resolutionComboBox.setSelectedItem(preferences.get("resolution", "1280x720"));
        resolutionComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedResolution = (String) resolutionComboBox.getSelectedItem();
                if (isValidResolution(selectedResolution)) {
                    preferences.put("resolution", selectedResolution);
                    setScreenResolution(selectedResolution);
                } else {
                    // Handle invalid resolution input
                    JOptionPane.showMessageDialog(
                            SettingsPanel.this,
                            "Invalid resolution format. Please use 'WIDTHxHEIGHT' format.",
                            "Invalid Input",
                            JOptionPane.ERROR_MESSAGE);
                }
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
        add(resolutionLabel, gbc);
        gbc.gridy++;
        add(resolutionComboBox, gbc);
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

    private JComboBox<String> createResolutionComboBox() {
        String[] resolutions = { "640x480", "1280x720", "1920x1080" };
        JComboBox<String> comboBox = new JComboBox<>(resolutions);
        comboBox.setFont(new Font("Consolas", Font.BOLD, 24));
        comboBox.setPreferredSize(new Dimension(180, 70));
        return comboBox;
    }

    public void loadSettings() {
        bgmVolumeSlider.setValue(preferences.getInt("bgmVolume", 50));
        sfxVolumeSlider.setValue(preferences.getInt("sfxVolume", 50));
        resolutionComboBox.setSelectedItem(preferences.get("resolution", "1280x720"));
    }

    private boolean isValidResolution(String resolution) {
        Pattern pattern = Pattern.compile("\\d{3,4}x\\d{3,4}");
        Matcher matcher = pattern.matcher(resolution);
        return matcher.matches();
    }

    private void setScreenResolution(String resolution) {
        int width = Integer.parseInt(resolution.split("x")[0]);
        int height = Integer.parseInt(resolution.split("x")[1]);
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        frame.setSize(width, height);
    }
}
