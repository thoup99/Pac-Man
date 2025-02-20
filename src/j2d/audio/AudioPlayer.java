package j2d.audio;


import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * AudioPlayer.java
 * A class used to play sfx from any Class.
 * Also allows playing of the music track.
 *
 * @author Tyler Houp
 */
public class AudioPlayer {
    public enum SFX {MOVE, ROTATE, BUMP, LINECLEAR, TETRISCLEAR, LEVELUP, GAMEOVER}
    private static Map<SFX, URL> audioURLs = new HashMap<SFX, URL>();
    private static URL musicURL;

    /**
     * Private Constructor for AudioPlayer
     * This is to prevent creating objects of this class
     */
    private AudioPlayer() {}

    /**
     * Must be called before AudioPlayer will function
     */
    public static void initialize() {
        loadAudioURLS();
    }

    /**
     * Loads URLs for all j2d.audio files that will be used
     * during tetris
     */
    public static void loadAudioURLS() {
        ClassLoader classLoader = AudioPlayer.class.getClassLoader();
        musicURL = classLoader.getResource("music/korobeiniki_gb.wav");

        audioURLs.put(SFX.MOVE, classLoader.getResource("sfx/move.wav"));
        audioURLs.put(SFX.ROTATE, classLoader.getResource("sfx/rotate.wav"));
        audioURLs.put(SFX.BUMP, classLoader.getResource("sfx/bump.wav"));
        audioURLs.put(SFX.LINECLEAR, classLoader.getResource("sfx/lineclear.wav"));
        audioURLs.put(SFX.TETRISCLEAR, classLoader.getResource("sfx/tetrisclear.wav"));
        audioURLs.put(SFX.LEVELUP, classLoader.getResource("sfx/levelup.wav"));
        audioURLs.put(SFX.GAMEOVER, classLoader.getResource("sfx/gameover.wav"));
    }

    /**
     *
     * @param url URL of j2d.audio file to get a Clip of
     * @return Returns a Clip of the passed in j2d.audio file URL
     */
    private static Clip getClipFromURL(URL url) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(url));
            return clip;
        }
        catch (UnsupportedAudioFileException e) {
            System.out.println("Audio File Type Not Supported");
        }
        catch (IOException e) {
            System.out.println("Error Loading Music File");
        }
        catch (LineUnavailableException e) {
            System.out.println("Music Line Unavailable");
        }
        return null;
    }

    /**
     * Plays the sound effect associated with the SFX
     * that's passed in
     * @param sfx SFX file to play
     */
    public static void playSFX(SFX sfx) {
        Clip clip = getClipFromURL(audioURLs.get(sfx));
        clip.start();
    }

    /**
     * Plays Korobeiniki on loop
     */
    public static void playMusic() {
        Clip musicClip = getClipFromURL(musicURL);
        musicClip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}
