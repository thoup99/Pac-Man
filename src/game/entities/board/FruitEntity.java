package game.entities.board;

import game.PacManController;
import j2d.attributes.position.OffsetPosition2D;
import j2d.attributes.position.Position2D;
import j2d.components.Timer;
import j2d.components.graphics.shapes.FillCircle;
import j2d.components.physics.collider.CircleCollider;
import j2d.components.sprite.SpriteSheet;
import j2d.engine.gameobject.GameObject;

import java.awt.*;

public class FruitEntity extends GameObject {
    PacManController pacManController;
    Position2D position;
    OffsetPosition2D spritePosition;

    final int maxLevel = 8;
    final int[] scores = {100, 300, 500, 700, 100, 2000, 3000, 5000};
    int currentScore = scores[0];

    boolean active = false;
    boolean eaten = false;

    SpriteSheet sprite;
    CircleCollider collider;
    Timer activeTimer;

    public FruitEntity(PacManController pacManController, Position2D position) {
        this.pacManController = pacManController;
        this.position = position;

        spritePosition = new OffsetPosition2D(position, -16, -16);
        sprite = new SpriteSheet(this, spritePosition, SpriteSheet.loadImage("./images/fruit_sheet.png"), 1, 8);
        sprite.setPadding(2, 2);
        sprite.setSpacing(2, 2);
        sprite.setLayer(1);

        collider = new CircleCollider(this, position, 10);

        activeTimer = new Timer(this, 9500, this::deactivate);
        activeTimer.setOneShot(true);

        setLevel(1);
        deactivate();

        ready();
    }

    public void setLevel(int level) {
        eaten = false;
        active = false;

        if (level < maxLevel) {
            sprite.setSprite(level - 1);
            currentScore = scores[level - 1];
        } else {
            sprite.setSprite(7);
            currentScore = scores[7];
        }
    }

    public void activate() {
        if (!eaten) {
            sprite.setVisible(true);
            active = true;
            activeTimer.start();
        }
    }

    public void deactivate() {
        active = false;
        sprite.setVisible(false);
    }

    @Override
    public void onCollision(GameObject other) {
        if (other instanceof PacMan) {
            if (active) {
                eaten = true;
                pacManController.addScore(currentScore);
                deactivate();
            }
        }
    }

    public void pauseTimer() {
        activeTimer.pause();
    }

    public void resumeTimer() {
        if (active) {
            activeTimer.resume();
        }
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void physicsUpdate(double delta) {

    }
}
