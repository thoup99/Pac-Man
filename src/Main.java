import game.Constants;
import game.JoyStick;
import game.PacMan;
import game.PacManController;
import j2d.attributes.position.Position2D;
import j2d.engine.Engine;
import j2d.engine.debug.Debug;
import j2d.engine.render.Renderer;
import j2d.engine.window.Window;

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

        Debug.setDrawFPS(true);

        window.setCloseOnEsc(true);

        Renderer.createLayer(0); // Background
        Renderer.createLayer(1); // Pellets / Ghost
        Renderer.createLayer(2); // PacMan
        Renderer.createLayer(3); // UI

        PacManController pacManController = new PacManController();
        PacMan pacMan = new PacMan();
    }
}
