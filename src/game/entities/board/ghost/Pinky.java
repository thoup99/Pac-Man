package game.entities.board.ghost;

import game.board.nodes.NodeManager;
import game.entities.board.PacMan;
import j2d.attributes.Vector2D;
import j2d.attributes.position.Position2D;

import java.awt.*;

import static game.Constants.BOARD_START_POSITION;
import static game.Constants.TILE_SIZE;

public class Pinky extends Ghost {

    public Pinky(NodeManager nodeManager, PacMan pacman) {
        super(2, nodeManager.getPinkyStartNode(), nodeManager, pacman);
        scatterPosition = new Position2D(BOARD_START_POSITION);
        calculateNewGoalPosition();

        ready();
    }

    @Override
    protected void calculateNewGoalPosition() {
        Vector2D directionVector = getPacManDirectionVector().multiply(4 * TILE_SIZE);
        Position2D newGoalPosition = new Position2D(pacmanPosition);
        newGoalPosition.addVector2D(directionVector);

        setGoalPosition(newGoalPosition);
    }

    @Override
    protected void startRound() {
        startLeavingSpawn();
    }
}
