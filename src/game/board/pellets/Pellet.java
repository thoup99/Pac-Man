package game.board.pellets;

import game.entities.board.PacMan;
import j2d.attributes.position.Position2D;
import j2d.components.graphics.shapes.FillCircle;
import j2d.components.physics.collider.CircleCollider;
import j2d.engine.gameobject.GameObject;

import java.awt.*;

public class Pellet extends GameObject {
    Position2D position;
    FillCircle fillCircle;
    CircleCollider circleCollider;

    public Pellet(Position2D position) {
        this(position, 4);
    }

    Pellet(Position2D position, int radius) {
        this.position = position;
        fillCircle = new FillCircle(this, 1, position, radius);
        fillCircle.setColor(Color.GRAY);
        circleCollider = new CircleCollider(this, position, radius);

        ready();
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
