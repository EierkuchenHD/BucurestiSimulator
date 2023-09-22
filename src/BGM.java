import javax.sound.sampled.*;
import javax.swing.JOptionPane;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class BGM {

    private static Clip clip;
    private static float volume = 0.5f; // Default volume
    private static Map<String, Clip> clipMap = new HashMap<>();
    private static boolean isMuted = false; // New variable to track mute state

    private BGM() {
        JOptionPane.showMessageDialog(
                null,
                "Error: An instance of the BGM class was created, which should not occur.\nPlease use the static methods for music operations.",
                "Error",
                JOptionPane.ERROR_MESSAGE);
    }

    public static void playMainMenuBackgroundMusic() {
        BGM.play("music/speaker_city.wav");
    }

    public static void playPlayPanelMusic() {
        BGM.play("music/casa_dan_deal.wav");
    }

    public static void play(String filename) {
        try {
            if (clipMap.containsKey(filename)) {
                clip = clipMap.get(filename);
                clip.setFramePosition(0);
            } else {
                AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filename));
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clipMap.put(filename, clip);
            }

            clip = clipMap.get(filename);
            clip.setFramePosition(0);

            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);

            clip.start();
        } catch (IOException | UnsupportedAudioFileException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public static void stop() {
        if (clip != null) {
            clip.stop();
            clip.close(); // Release the resources
            clip = null; // Set the clip to null
        }
    }

    public static void mute() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            isMuted = true;
        }
    }

    public static void unmute() {
        if (clip != null && !clip.isRunning() && isMuted) {
            clip.start();
            isMuted = false;
        }
    }

    public static boolean isMuted() {
        return isMuted;
    }

    public static void setVolume(int percentage) {
        if (percentage >= 0 && percentage <= 100) {
            float volume = percentage / 100.0f; // Convert percentage to a float value between 0 and 1
            BGM.volume = volume;
            if (clip != null && clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
                gainControl.setValue(dB);
            }
        }
    }
}
