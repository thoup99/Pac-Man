package game.board.pellets;

import game.entities.board.PacMan;
import j2d.attributes.position.Position2D;
import j2d.engine.gameobject.GameObject;

import java.awt.*;

public class PowerPellet extends Pellet {
    public PowerPellet(Position2D position) {
        super(position, 8);
        fillCircle.setColor(Color.YELLOW);

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
