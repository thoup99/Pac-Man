package j2d.components.sprite;

import j2d.attributes.position.Position2D;
import j2d.engine.gameobject.GameObject;

import java.awt.image.BufferedImage;

public class AnimatedSprite extends SpriteSheet {

    /**
     * Constructor for SpriteSheet Class
     *
     * @param parent
     * @param pos         Position2D SpriteSheet will be linked to
     * @param loadedSheet BufferedImage that is the sprite sheet image
     * @param numRows     Number of rows in the sprite sheet
     * @param numCols     Number of columns in the sprite sheet
     */
    public AnimatedSprite(GameObject parent, Position2D pos, BufferedImage loadedSheet, int numRows, int numCols) {
        super(parent, pos, loadedSheet, numRows, numCols);
    }
}
