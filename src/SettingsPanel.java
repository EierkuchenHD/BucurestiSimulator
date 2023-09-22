import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.util.prefs.Preferences;

public class SettingsPanel extends JPanel {

    private JSlider bgmVolumeSlider;
    private JSlider sfxVolumeSlider;
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

        add(bgmVolumeLabel, gbc);
        gbc.gridy++;
        add(bgmVolumeSlider, gbc);
        gbc.gridy++;
        add(sfxVolumeLabel, gbc);
        gbc.gridy++;
        add(sfxVolumeSlider, gbc);
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

    public void loadSettings() {
        bgmVolumeSlider.setValue(preferences.getInt("bgmVolume", 50));
        sfxVolumeSlider.setValue(preferences.getInt("sfxVolume", 50));
    }
}
