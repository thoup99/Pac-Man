import game.GamePanel;
import j2d.keyboard.KeyHandler;

import javax.swing.*;

/**
 * Tetris.java
 * Class used to run PacMan.
 *
 * @author Tyler Houp
 */
public class PacMan {
    /**
     * Initializes KeyHandler and Audio Player then creates
     * JFrame for the game to be displayed on. Creates gamePanel
     * and sets settings on it. Finally, gamePanel starts the gameThread.
     *
     * @param args Command line arguments
     */
    public static void main(String[] args) {
        KeyHandler.initialize();

        JFrame frame = new JFrame("Tetris");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);
        frame.pack();

        frame.setLocationRelativeTo(null);
        frame.setLayout(null);
        frame.setResizable(false);
        frame.setVisible(true);

        gamePanel.startGameThread();
    }
}
