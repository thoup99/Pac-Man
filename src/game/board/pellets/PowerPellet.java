package game.board.pellets;

import game.PacMan;
import j2d.attributes.position.Position2D;
import j2d.components.graphics.shapes.FillCircle;
import j2d.components.physics.collider.CircleCollider;
import j2d.engine.GameObject;

import java.awt.*;

public class PowerPellet extends GameObject {
    Position2D position;
    FillCircle fillCircle;
    CircleCollider circleCollider;

    public PowerPellet(Position2D position) {
        this.position = position;
        fillCircle = new FillCircle(this, 1, position, 8);
        fillCircle.setColor(Color.YELLOW);

        circleCollider = new CircleCollider(this, position, 8);

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
