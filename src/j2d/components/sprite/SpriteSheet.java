package j2d.components.sprite;

import j2d.attributes.position.Position2D;
import j2d.engine.gameobject.GameObject;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

/**
 * SpriteSheet.java
 * A class to load and j2d.engine.render sprite sheets
 *
 * @author Tyler Houp
 */
public class SpriteSheet extends Sprite {
    private int individualSpriteHeight;
    private int individualSpriteWidth;
    private int xPadding, yPadding = 0;
    private int xSpacing, ySpacing = 0;

    private final int numRows;
    private final int numCols;

    private int spriteNumber = 0;

    BufferedImage spriteImage;

    /**
     * Constructor for SpriteSheet Class
     * @param pos Position2D SpriteSheet will be linked to
     * @param loadedSheet BufferedImage that is the sprite sheet image
     * @param numRows Number of rows in the sprite sheet
     * @param numCols Number of columns in the sprite sheet
     */
    public SpriteSheet(GameObject parent, Position2D pos, BufferedImage loadedSheet, int numRows, int numCols) {
        super(parent, pos);
        this.image = loadedSheet;

        this.numRows = numRows;
        this.numCols = numCols;

        recalculateSpriteDimensions();

        setSprite(0,0);
        addToRenderer();
    }

    public void setPadding(int xPadding, int yPadding) {
        this.xPadding = xPadding;
        this.yPadding = yPadding;
        recalculateSpriteDimensions();
    }

    public void setSpacing(int xSpacing, int ySpacing) {
        this.xSpacing = xSpacing;
        this.ySpacing = ySpacing;
        recalculateSpriteDimensions();
    }

    private void recalculateSpriteDimensions() {
        this.individualSpriteWidth = (image.getWidth() - (2 * xPadding) - ((numCols - 1) * xSpacing)) / numCols;
        this.individualSpriteHeight = (image.getHeight() - (2 * yPadding) - ((numRows - 1) * ySpacing)) / numRows;
    }

    /**
     * Sets Sprite based off its number. Starts at zero.
     * @param spriteNum Number of the sprite to set.
     */
    public void setSprite(int spriteNum) {
        int col = (spriteNum % numCols);
        int row = (spriteNum / numCols);
        setSprite(row, col);
    }

    public int getSpriteNum(int row, int col) {
        return row * numCols + col;
    }

    /**
     * Sets sprite based off row and column. Both start at zero.
     * @param row Row of desired sprite.
     * @param col Column of desired sprite.
     */
    public void setSprite(int row, int col) {
        int x = xPadding + ( (individualSpriteWidth + xSpacing) * col );
        int y = yPadding + ( (individualSpriteHeight + ySpacing) * row );
        spriteNumber = getSpriteNum(row, col);
        spriteImage = image.getSubimage(x, y, individualSpriteWidth, individualSpriteHeight);
    }

    public int getSpriteNumber() {
        return spriteNumber;
    }

    /**
     * Implementation of Renderable
     * Rendering uses spriteSheet variables to draw the desired sprite.
     * @param g2 Graphics2D object used to draw images
     */
    @Override
    public void render(Graphics2D g2) {
        if (visible) {
            g2.drawImage(spriteImage, position.getIntX(), position.getIntY(), individualSpriteWidth, individualSpriteHeight, null);
        }
    }
}
