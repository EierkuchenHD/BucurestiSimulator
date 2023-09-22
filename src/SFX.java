import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SFX {
    private static Clip clip;
    private static Map<String, Clip> clipMap = new HashMap<>();
    private static float volume = 0.5f; // Default volume

    private SFX() {
        // Display a more formal error message for creating an instance of SFX
        JOptionPane.showMessageDialog(
                null,
                "Error: An instance of the SFX class was created, which should not occur.\nPlease use the static methods for audio operations.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void playButtonClickSound() {
        play("sounds/preview.wav");
    }

    // Play the sound effect from a file path
    private static void play(String filename) {
        try {
            if (clipMap.containsKey(filename)) {
                Clip clip = clipMap.get(filename);
                clip.setFramePosition(0);
            } else {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filename));
                Clip clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clipMap.put(filename, clip);
            }

            Clip clip = clipMap.get(filename);
            clip.setFramePosition(0);

            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);

            clip.start();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    // Stop the currently playing sound effect
    public static void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    // Set the volume of the currently playing sound effect (0.0f to 1.0f)
    public static void setVolume(float volume) {
        if (volume >= 0.0f && volume <= 1.0f) {
            SFX.volume = volume;
            for (Clip clip : clipMap.values()) {
                if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                    FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
                    gainControl.setValue(dB);
                }
            }
        }
    }

    // Check if a sound effect is currently playing
    public static boolean isPlaying() {
        return clip != null && clip.isRunning();
    }
}
