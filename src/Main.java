import game.Constants;
import game.PacManController;
import game.client.ConnectionText;
import game.client.PacManClient;
import j2d.engine.Engine;
import j2d.engine.debug.Debug;
import j2d.engine.render.Renderer;
import j2d.engine.window.Window;

import java.awt.*;

/**
 * Tetris.java
 * Class used to run PacMan.
 *
 * @author Tyler Houp
 */
public class Main {
    /**
     * Initializes KeyHandler and Audio Player then creates
     * JFrame for the game to be displayed on. Creates gamePanel
     * and sets settings on it. Finally, gamePanel starts the gameThread.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        Engine.initialize();
        Engine.setTargetFPS(60);

        Window window = new Window(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
        window.setRecommendedDefaults();
        window.setTitle("Pac-Man");
        window.setBackgroundColor(Color.BLACK);

        //Debug.setDrawFPS(true);

        window.setCloseOnEsc(true);

        Renderer.createLayer(0); // Background
        Renderer.createLayer(1); // Pellets
        Renderer.createLayer(2); // PacMan
        Renderer.createLayer(3); // Ghost
        Renderer.createLayer(4); // UI

        ConnectionText connectionText = new ConnectionText((float) Constants.SCREEN_WIDTH / 2, 200);
        PacManClient.init();
        PacManController pacManController = new PacManController();
        connectionText.queueDelete();
        Engine.ready();
    }
}
