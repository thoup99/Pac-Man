package game.entities.ui.text;

import j2d.attributes.position.Position2D;
import j2d.components.sprite.SpriteSheet;
import j2d.engine.gameobject.GameObject;

import java.awt.image.BufferedImage;

public class CharEntity extends GameObject {
    private static BufferedImage font_sheet = null;
    private final SpriteSheet spriteSheet;

    /**
     * Constructor for CharEntity
     * Loads SpriteSheet
     * @param position Position2D to link to
     */
    public CharEntity(Position2D position) {
        this(position, '0');
    }

    /**
     * Constructor for CharEntity
     * Loads SpriteSheet
     * @param position Position2D to link to
     * @param character character to set sprite to
     */
    public CharEntity(Position2D position, char character) {
        loadSheetImage();
        spriteSheet = new SpriteSheet(this, position, font_sheet, 6, 16);
        spriteSheet.setPadding(1, 1);
        spriteSheet.setSpacing(1, 1);
        spriteSheet.setLayer(4);
        setCharacter(character);
    }

    public void loadSheetImage() {
        if (font_sheet == null) {
            font_sheet = SpriteSheet.loadImage("./images/font_sheet.png");
        }
    }


    /**
     * Determines the sprite number of the character and sets
     * the sprite accordingly
     * @param character Character to be set to
     */
    public void setCharacter(char character) {
        int number = Character.getNumericValue(character);
        spriteSheet.setSprite(number);
    }

    /**
     * Shows Entity
     */
    public void show() {
        spriteSheet.setVisible(true);
    }

    /**
     * Hides Entity
     */
    public void hide() {
        spriteSheet.setVisible(false);
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void physicsUpdate(double delta) {

    }
}
