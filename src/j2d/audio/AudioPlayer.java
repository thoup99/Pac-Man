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
    public enum SFX {eat_dot_0, eat_dot_1, start, credit, death_0, death_1, eat_fruit, eat_ghost, extended,
        eyes, eyes_firstloop, fright, fright_firstloop, intermission, siren0, siren1, siren0_firstloop,
        siren1_firstloop, siren2, siren2_firstloop, siren3, siren3_firstloop, siren4, siren4_firstloop
    }
    private static Map<SFX, URL> audioURLs = new HashMap<SFX, URL>();
    private static URL musicURL;
    private static Clip frightClip;

    /**
     * Private Constructor for AudioPlayer
     * This is to prevent creating objects of this class
     */
    private AudioPlayer() {}


    /**
     * Loads URLs for all j2d.audio files that will be used
     * during tetris
     */
    public static void loadAudioURLS() {
        ClassLoader classLoader = AudioPlayer.class.getClassLoader();
        musicURL = classLoader.getResource("music/korobeiniki_gb.wav");

        audioURLs.put(SFX.eat_dot_0, classLoader.getResource("sfx/eat_dot_0.wav"));
        audioURLs.put(SFX.eat_dot_1, classLoader.getResource("sfx/eat_dot_1.wav"));
        audioURLs.put(SFX.start, classLoader.getResource("sfx/start.wav"));
        audioURLs.put(SFX.credit, classLoader.getResource("sfx/credit.wav"));
        audioURLs.put(SFX.death_0, classLoader.getResource("sfx/death_0.wav"));
        audioURLs.put(SFX.death_1, classLoader.getResource("sfx/death_1.wav"));
        audioURLs.put(SFX.eat_fruit, classLoader.getResource("sfx/eat_fruit.wav"));
        audioURLs.put(SFX.eat_ghost, classLoader.getResource("sfx/eat_ghost.wav"));
        audioURLs.put(SFX.extended, classLoader.getResource("sfx/extended.wav"));
        audioURLs.put(SFX.eyes, classLoader.getResource("sfx/eyes.wav"));
        audioURLs.put(SFX.eyes_firstloop, classLoader.getResource("sfx/eyes_firstloop.wav"));
        audioURLs.put(SFX.fright, classLoader.getResource("sfx/fright.wav"));
        audioURLs.put(SFX.fright_firstloop, classLoader.getResource("sfx/fright_firstloop.wav"));
        audioURLs.put(SFX.intermission, classLoader.getResource("sfx/intermission.wav"));
        audioURLs.put(SFX.siren0, classLoader.getResource("sfx/siren0.wav"));
        audioURLs.put(SFX.siren0_firstloop, classLoader.getResource("sfx/siren0_firstloop.wav"));
        audioURLs.put(SFX.siren1, classLoader.getResource("sfx/siren1.wav"));
        audioURLs.put(SFX.siren1_firstloop, classLoader.getResource("sfx/siren1_firstloop.wav"));
        audioURLs.put(SFX.siren2, classLoader.getResource("sfx/siren2.wav"));
        audioURLs.put(SFX.siren2_firstloop, classLoader.getResource("sfx/siren2_firstloop.wav"));
        audioURLs.put(SFX.siren3, classLoader.getResource("sfx/siren3.wav"));
        audioURLs.put(SFX.siren3_firstloop, classLoader.getResource("sfx/siren3_firstloop.wav"));
        audioURLs.put(SFX.siren4, classLoader.getResource("sfx/siren4.wav"));
        audioURLs.put(SFX.siren4_firstloop, classLoader.getResource("sfx/siren4_firstloop.wav"));

        frightClip = getClipFromURL(audioURLs.get(SFX.fright));
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

    public static void loopFrightClip() {
        frightClip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public static void stopFrightClip() {frightClip.stop();
    }

}