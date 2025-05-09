package game.entities.ui;

import game.Constants;
import j2d.attributes.position.Position2D;
import j2d.components.sprite.SpriteSheet;
import j2d.engine.gameobject.GameObject;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class FruitDisplay extends GameObject {
    final int FRUIT_NUMBER = 8;
    final int FRUIT_TO_DISPLAY = 7;
    ArrayList<SpriteSheet> fruitSprites = new ArrayList<>();

    int startX = Constants.TILE_SIZE * 26;
    int startY = Constants.TILE_SIZE * 34;
    int shownFruits = 0;

    public FruitDisplay() {
        BufferedImage fruitImage = SpriteSheet.loadImage("./images/fruit_sheet.png");
        for (int i = 0; i < FRUIT_TO_DISPLAY; i++) {
            SpriteSheet newFruit = new SpriteSheet(this, new Position2D(startX - (2 * i * Constants.TILE_SIZE), startY), fruitImage, 1, FRUIT_NUMBER);
            newFruit.setSpacing(2, 2);
            newFruit.setPadding(2, 2);
            newFruit.setLayer(4);
            newFruit.setSprite(7);
            newFruit.setVisible(false);

            fruitSprites.add(newFruit);
        }
    }

    public void addFruit(int fruitLevel) {
        if (fruitLevel > FRUIT_NUMBER) {
            fruitLevel = FRUIT_NUMBER;
        }

        shownFruits++;
        if (shownFruits == FRUIT_TO_DISPLAY) {
            shownFruits = FRUIT_TO_DISPLAY;
        }

        int lastSpriteNumber = fruitSprites.get(0).getSpriteNumber();
        fruitSprites.get(0).setSprite(fruitLevel - 1);
        fruitSprites.get(0).setVisible(true);

        for (int i = 1; i < shownFruits; i++) {
            SpriteSheet fruitSprite = fruitSprites.get(i);
            int tempLastSpriteNumber = fruitSprite.getSpriteNumber();
            fruitSprite.setSprite(lastSpriteNumber);
            lastSpriteNumber = tempLastSpriteNumber;
            System.out.println("Setting " + i + " to visible");
            fruitSprite.setVisible(true);
        }
    }

    public void reset() {
        for (SpriteSheet sprite: fruitSprites) {
            sprite.setVisible(false);
        }
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void physicsUpdate(double delta) {

    }
}
