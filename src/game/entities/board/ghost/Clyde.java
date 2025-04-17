package game.entities.board.ghost;

import game.board.nodes.Node;
import game.board.nodes.NodeManager;
import game.entities.board.PacMan;
import j2d.attributes.Vector2D;
import j2d.attributes.position.Position2D;
import j2d.components.Timer;

import java.awt.*;

import static game.Constants.*;
import static game.Constants.BOARD_TOTAL_ROWS;

public class Clyde extends Ghost{
    double eightTilesSquared = Math.pow(8 * TILE_SIZE, 2);
    Timer awaitLeaveTimer;

    public Clyde(NodeManager nodeManager, PacMan pacman) {
        super(6, nodeManager.getClydeStartNode(), nodeManager, pacman);
        scatterPosition = new Position2D(BOARD_START_POSITION.getX(),
                BOARD_START_POSITION.getY() + (TILE_SIZE * BOARD_TOTAL_ROWS));
        calculateNewGoalPosition();

        awaitLeaveTimer = new Timer(this, 10000, this::timeToLeave);
        awaitLeaveTimer.setOneShot(true);

        ready();
    }

    @Override
    protected void startRound() {
        currentMode = GhostMode.AWAITING_START;
        setGoalPosition(homePosition);
        awaitLeaveTimer.restart();
        awaitLeaveTimer.start();
    }

    private void timeToLeave() {
        startLeavingSpawn();
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
