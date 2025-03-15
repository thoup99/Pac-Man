package game.board.pellets;

import game.PacManController;
import game.entities.board.PacMan;
import game.entities.board.ghost.GhostManager;
import j2d.attributes.position.Position2D;
import j2d.engine.gameobject.GameObject;

import java.awt.*;

public class PowerPellet extends Pellet {
    private final PacManController pacManController;

    public PowerPellet(Position2D position, PacManController pacManController) {
        super(position, 8);
        this.pacManController = pacManController;
        fillCircle.setColor(Color.YELLOW);

        ready();
    }

    @Override
    public void onCollision(GameObject other) {
        if (other instanceof PacMan) {
            pacManController.powerPelletEaten();
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
