import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SFX {
    private static final float DEFAULT_VOLUME = 0.5f;
    private static Map<String, Clip> clipMap = new HashMap<>();
    private static ExecutorService soundThreadPool = Executors.newFixedThreadPool(5);
    private static float volume = DEFAULT_VOLUME;

    private SFX() {
        throw new AssertionError("SFX class should not be instantiated.");
    }

    public static void playButtonClickSound() {
        play("sounds/preview.wav");
    }

    public static void playCoinSound() {
        play("sounds/coinsound.wav");
    }

    public static void playSecretSound() {
        play("sounds/secret.wav");
    }

    private static void play(String filename) {
        soundThreadPool.submit(() -> {
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
        });
    }

    public static void stop() {
        soundThreadPool.shutdownNow();
        soundThreadPool = Executors.newFixedThreadPool(5);
    }

    public static void setVolume(int percentage) {
        if (percentage >= 0 && percentage <= 100) {
            float volume = percentage / 100.0f; // Convert percentage to a float value between 0 and 1
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

    public static boolean isPlaying() {
        return !soundThreadPool.isShutdown();
    }
}
