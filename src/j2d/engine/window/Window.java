package j2d.engine.window;

import j2d.attributes.position.Position2D;
import j2d.engine.Engine;
import j2d.engine.input.keyboard.KeyHandler;
import j2d.engine.input.keyboard.KeySubscriber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class Window implements KeySubscriber {
    public static boolean isCreated = false;
    int width, height;
    private boolean closeOnEsc = false;

    static JFrame frame;
    static WindowPanel panel;

    public Window() {
        this(600, 600);
    }

    public Window(int width, int height) {
        if (isCreated) {
            //Ensures only one window can exist at a time
            throw new IllegalStateException("Window is already created");
        }

        this.width = width;
        this.height = height;

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        panel = new WindowPanel(width, height);
        frame.add(panel);
        frame.pack();

        frame.setLayout(null);
        //setSize(width, height);

        Engine.registerWindow(this);
        isCreated = true;
        setVisible(true);

        int[] subscribedKeys = {KeyEvent.VK_ESCAPE};
        KeyHandler.subscribe(this, subscribedKeys);
    }

    public void repaintPanel() {
        panel.repaint();
    }

    public void setTitle(String title) {
        frame.setTitle(title);
    }

    /**
     * TODO - Doees not seems to resize panel after it has been created. Needs fixed before being made public again.
     * @param width
     * @param height
     */
    private void setSize(int width, int height) {
        this.width = width;
        this.height = height;
        frame.setSize(width, height);
        panel.setPreferredSize(new Dimension(width, height));
    }

    public void centerWindow() {
        frame.setLocationRelativeTo(null);
    }

    public void setResizable(boolean resizable) {
        frame.setResizable(resizable);
    }

    public void setVisible(boolean isVisible) {
        frame.setVisible(isVisible);
    }

    public void setRecommendedDefaults() {
        centerWindow();
        setResizable(false);
        setVisible(true);
    }

    public void setCloseOnEsc(boolean closeOnEsc) {
        this.closeOnEsc = closeOnEsc;
    }

    public static Position2D getMousePosition() {
        return new Position2D(panel.getMousePosition());
    }

    public void setBackgroundColor(Color newColor) {
        panel.setBackground(newColor);
    }

    @Override
    public void keyPressed(int key) {
        if (key == KeyEvent.VK_ESCAPE && closeOnEsc) {
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(int key) {

    }
}
