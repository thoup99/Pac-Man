package game.board.pellets;

import game.entities.board.PacMan;
import j2d.attributes.position.OffsetPosition2D;
import j2d.attributes.position.Position2D;
import j2d.components.graphics.shapes.FillCircle;
import j2d.components.physics.collider.CircleCollider;
import j2d.components.sprite.Sprite;
import j2d.engine.gameobject.GameObject;

import java.awt.*;

public class Pellet extends GameObject {
    PelletManager pelletManager;
    Position2D position;
    //FillCircle fillCircle;
    CircleCollider circleCollider;

    Sprite sprite;

    public Pellet(PelletManager pelletManager, Position2D position) {
        this(pelletManager, position, 4);
    }

    Pellet(PelletManager pelletManager, Position2D position, int radius) {
        this.pelletManager = pelletManager;
        this.position = position;
        loadVisuals();
        //fillCircle = new FillCircle(this, 1, position, radius);
        //fillCircle.setColor(Color.GRAY);
        circleCollider = new CircleCollider(this, position, radius);

        ready();
    }

    public void eaten() {
        pelletManager.removePellet(this);
        queueDelete();
    }

    protected void loadVisuals() {
        OffsetPosition2D spritePosition = new OffsetPosition2D(position, -8, -8);
        sprite = new Sprite(this, spritePosition, pelletManager.getPelletImage(), 1);
    }

    @Override
    public void onCollision(GameObject other) {
        if (other instanceof PacMan) {
            queueDelete();
        }
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void physicsUpdate(double delta) {

    }
}
