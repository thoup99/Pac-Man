package game;

import keyboard.KeySubscriber;
import keyboard.KeyHandler;
import render.Renderer;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * GamePanel.java
 * A class to create the panel the game will be run on
 * and start playing the game
 *
 * @author Tyler Houp
 */
public class GamePanel extends JPanel implements Runnable, KeySubscriber {
    Thread gameThread;


    //FPS
    private final int FPS = 60;

    /**
     * Constructor for GamePanel
     * Makes the panel the game will be drawn on and sets keylistener
     */
    public GamePanel() {
        this.setPreferredSize(new Dimension(600, 600));
        this.setBackground(Color.BLACK);
        this.setDoubleBuffered(true);

        this.addKeyListener(KeyHandler.handler);
        KeyHandler.subscribe(this, new int[]{KeyEvent.VK_ESCAPE}); // Closes game by pressing escape
        this.setFocusable(true); //Panel Can be focused for key input
    }

    /**
     * Starts running the game on its own thread
     */
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    /**
     * Runs through the game and its updating/drawing logic
     */
    @Override
    public void run() {

        double drawInterval = 1000000000 / FPS; //Get Nano seconds per frame draw
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;

        while (gameThread.isAlive()) {

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += currentTime - lastTime;
            lastTime = currentTime;

            if (delta >= 1) {
                //Draw
                repaint();

                delta--;
            }

            if (timer >= drawInterval) {
                //System.out.println("FPS: " + FPS);
            }

        }
    }

    /**
     * Paints all graphic components to the screen
     * @param g the <code>Graphics</code> object to protect
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, 600, 600);

        Renderer.renderAll(g2d);

        g2d.dispose();
    }

    /**
     * Responds to key presses
     * @param key KeyEvent to respond to
     */
    @Override
    public void keyPressed(int key) {
        if (key == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }
}
