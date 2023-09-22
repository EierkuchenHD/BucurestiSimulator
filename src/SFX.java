import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import java.io.File;

public class SFX {
    private static Clip clip;

    private SFX() {
        // Private constructor to prevent instantiation
    }

    // Play the sound effect from a file path
    public static void play(String filePath) {
        try {
            File soundFile = new File(filePath);

            // Create an audio input stream
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);

            // Get a clip to play the sound
            clip = AudioSystem.getClip();

            // Open the audio input stream with the clip
            clip.open(audioInputStream);

            // Add a listener to handle sound events (e.g., when the sound finishes playing)
            clip.addLineListener(new LineListener() {
                @Override
                public void update(LineEvent event) {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                    }
                }
            });

            // Play the sound effect
            clip.start();
        } catch (Exception e) {
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
        if (clip != null) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);
        }
    }

    // Check if a sound effect is currently playing
    public static boolean isPlaying() {
        return clip != null && clip.isRunning();
    }
}
