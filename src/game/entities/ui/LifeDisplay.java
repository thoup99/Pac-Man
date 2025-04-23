package game.entities.ui;

import game.Constants;
import j2d.attributes.position.Position2D;
import j2d.components.sprite.Sprite;
import j2d.engine.gameobject.GameObject;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class LifeDisplay extends GameObject {
    ArrayList<Sprite> sprites = new ArrayList<>();
    int startX = Constants.TILE_SIZE * 2;
    int startY = Constants.TILE_SIZE * 34;

    public LifeDisplay() {

        BufferedImage lifeIcon = Sprite.loadImage("./images/life_icon.png");
        for (int x = 0; x < Constants.MAX_LIVES; x++) {
            Sprite newSprite = new Sprite(this, new Position2D(startX + (Constants.TILE_SIZE * x), startY), lifeIcon, 4);
            newSprite.setVisible(false);
            sprites.add(newSprite);
        }
    }

    public void setLives(int lives) {
        int hideIndex = 0;
        for (int x = 0; x < lives - 1; x++) {
            sprites.get(x).setVisible(true);
            hideIndex = x + 1;
        }
        for (int y = hideIndex; y < Constants.MAX_LIVES; y++) {
            sprites.get(y).setVisible(false);
        }
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void physicsUpdate(double delta) {

    }

}
