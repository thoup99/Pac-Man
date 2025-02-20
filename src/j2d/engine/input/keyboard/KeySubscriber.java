package j2d.engine.input.keyboard;

/**
 * KeySubscriber.java
 * An Interface to allow receiving callbacks from KeyHandler
 *
 * @author Tyler Houp
 */
public interface KeySubscriber {
    void keyPressed(int key);
    void keyReleased(int key);
}
