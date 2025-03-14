package j2d.components.sprite;


import j2d.components.Component;
import j2d.engine.gameobject.GameObject;
import j2d.engine.render.Renderable;
import j2d.engine.render.Renderer;
import j2d.attributes.position.Position2D;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Sprite.java
 * A class to draw images on the screen and move them around.
 *
 * @author Tyler Houp
 */
public class Sprite extends Component implements Renderable {
    protected Position2D position;
    protected boolean visible = true;
    protected BufferedImage image;
    private int layer = 0;

    /**
     * Constructor for Sprite Class
     * @param pos Position2D Sprite will be linked to
     */
    public Sprite(GameObject parent, Position2D pos) {
        super(parent);
        position = pos;
    }

    public Sprite(GameObject parent, Position2D pos, int layer) {
        super(parent);
        position = pos;
        this.layer = layer;
    }

    /**
     * Constructor for Sprite Class
     * @param pos Position2D Sprite will be linked to
     * @param path Resource path to PNG for sprite
     */
    public Sprite(GameObject parent, Position2D pos, String path) {
        super(parent);
        position = pos;
        loadSpriteFromPath(path);
    }

    public Sprite(GameObject parent, Position2D pos, String path, int layer) {
        super(parent);
        position = pos;
        loadSpriteFromPath(path);
        this.layer = layer;
    }

    /**
     * Takes the path to png and loads it as a sprite
     * @param path Resource path to PNG
     */
    public void loadSpriteFromPath(String path) {
        System.out.println("Loading Sprite Texture at path:  " + path);
        try {
            image = ImageIO.read(getClass().getResourceAsStream(path));
            addToRenderer();

        } catch (IOException e) {
            System.out.println(e.getMessage() + "Error Loading Image");
        }
    }

    /**
     * Sets visibility of sprite
     * @param newVisible Visibility of sprite
     */
    public void setVisible(boolean newVisible) {
        visible = newVisible;
    }

    public void setLayer(int newLayer) {
        removeFromRenderer();
        layer = newLayer;
        addToRenderer();
    }

    @Override
    public void delete() {
        super.delete();
        removeFromRenderer();
    }

    /**
     * Draws Sprite to the screen
     * @param g2 Graphics2D object used to draw images
     */
    @Override
    public void render(Graphics2D g2) {
        if (visible) {
            g2.drawImage(image, position.getIntX(), position.getIntY(), image.getWidth(), image.getHeight(), null);
        }
    }

    /**
     * Implementation from Renderable
     * Adds object to Renderer
     */
    @Override
    public void addToRenderer() {
        Renderer.add(this, layer);
    }

    /**
     * Implementation from Renderable
     * Removes object from Renderer
     */
    @Override
    public void removeFromRenderer() {
        Renderer.remove(this, layer);
    }
}
