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
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    public static void setVolume(float volume) {
        if (volume >= 0.0f && volume <= 1.0f) {
            BGM.volume = volume;
            for (Clip clip : clipMap.values()) {
                if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                    FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
                    gainControl.setValue(dB);
                }
            }
        }
    }

    public static boolean isPlaying() {
        return clip != null && clip.isRunning();
    }
}
