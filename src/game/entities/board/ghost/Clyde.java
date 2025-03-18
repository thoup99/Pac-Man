package game.entities.board.ghost;

import game.board.nodes.Node;
import game.entities.board.PacMan;
import j2d.attributes.Vector2D;
import j2d.attributes.position.Position2D;

import java.awt.*;

import static game.Constants.*;
import static game.Constants.BOARD_TOTAL_ROWS;

public class Clyde extends Ghost{
    double eightTilesSquared = Math.pow(8 * TILE_SIZE, 2);

    public Clyde(Node startNode, PacMan pacman) {
        super(startNode, pacman);
        scatterPosition = new Position2D(BOARD_START_POSITION.getX(),
                BOARD_START_POSITION.getY() + (TILE_SIZE * BOARD_TOTAL_ROWS));
        calculateNewGoalPosition();

        ghostCircle.setColor(Color.ORANGE);

        ready();
    }

    @Override
    protected void calculateNewGoalPosition() {
        double toPacmanSquared = position.distance(pacmanPosition).getMagnitudeSquared();
        if (toPacmanSquared < eightTilesSquared) {
            setGoalPosition(scatterPosition);
        } else {
            setGoalPosition(pacmanPosition);
        }
    }
}
