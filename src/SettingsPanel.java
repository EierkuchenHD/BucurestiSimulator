import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;

public class SettingsPanel extends JPanel {

    private static final int SLIDER_MIN = 0;
    private static final int SLIDER_MAX = 100;
    private static final int DEFAULT_VOLUME = 50;
    private static final int SLIDER_WIDTH = 450;
    private static final int SLIDER_HEIGHT = 70;
    private static final Font LABEL_FONT = new Font("Consolas", Font.BOLD, 24);

    private JSlider bgmVolumeSlider;
    private JSlider sfxVolumeSlider;
    private JButton backButton;
    private JButton restoreDefaultsButton;
    private Preferences preferences;

    public SettingsPanel() {
        setLayout(new GridBagLayout());
        preferences = Preferences.userNodeForPackage(SettingsPanel.class);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;

        JLabel bgmVolumeLabel = createLabel("Background Music Volume:");
        bgmVolumeSlider = createSlider(preferences.getInt("bgmVolume", DEFAULT_VOLUME));
        bgmVolumeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int volume = bgmVolumeSlider.getValue();
                preferences.putInt("bgmVolume", volume);
                BGM.setVolume(volume);
            }
        });

        JLabel sfxVolumeLabel = createLabel("Sound Effects Volume:");
        sfxVolumeSlider = createSlider(preferences.getInt("sfxVolume", DEFAULT_VOLUME));
        sfxVolumeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int volume = sfxVolumeSlider.getValue();
                preferences.putInt("sfxVolume", volume);
                SFX.setVolume(volume);
            }
        });

        BGM.setVolume(preferences.getInt("bgmVolume", DEFAULT_VOLUME));

        backButton = createButton("Back", e -> navigateToMenu());
        restoreDefaultsButton = createButton("Restore Default Settings", e -> resetSliders());

        add(bgmVolumeLabel, gbc);
        gbc.gridy++;
        add(bgmVolumeSlider, gbc);
        gbc.gridy++;
        add(sfxVolumeLabel, gbc);
        gbc.gridy++;
        add(sfxVolumeSlider, gbc);
        gbc.gridy++;
        add(backButton, gbc);
        gbc.gridy++;
        add(restoreDefaultsButton, gbc);
    }

    private JSlider createSlider(int value) {
        JSlider slider = new JSlider(JSlider.HORIZONTAL, SLIDER_MIN, SLIDER_MAX, value);
        slider.setMajorTickSpacing(10);
        slider.setMinorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        slider.setFont(LABEL_FONT);
        slider.setPreferredSize(new Dimension(SLIDER_WIDTH, SLIDER_HEIGHT));
        return slider;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(LABEL_FONT);
        return label;
    }

    private JButton createButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setFont(LABEL_FONT);
        button.addActionListener(actionListener);
        return button;
    }

    private void navigateToMenu() {
        SFX.playButtonClickSound();
        CardLayout cardLayout = (CardLayout) getParent().getLayout();
        cardLayout.show(getParent(), "menu");
    }

    private void resetSliders() {
        bgmVolumeSlider.setValue(DEFAULT_VOLUME);
        sfxVolumeSlider.setValue(DEFAULT_VOLUME);
        SFX.playButtonClickSound();
    }

    public void loadSettings() {
        bgmVolumeSlider.setValue(preferences.getInt("bgmVolume", DEFAULT_VOLUME));
        sfxVolumeSlider.setValue(preferences.getInt("sfxVolume", DEFAULT_VOLUME));
    }
}
