package game.entities.board.ghost;

import game.board.nodes.Node;
import game.board.nodes.NodeManager;
import game.entities.board.PacMan;
import j2d.attributes.Vector2D;
import j2d.attributes.position.Position2D;
import j2d.components.Timer;

import java.awt.*;

import static game.Constants.*;

public class Inky extends Ghost {
    Blinky blinky;
    Timer awaitLeaveTimer;

    public Inky(NodeManager nodeManager, PacMan pacman, Blinky blinky) {
        super(nodeManager.getInkyStartNode(), nodeManager, pacman);
        this.blinky = blinky;
        scatterPosition = new Position2D(BOARD_START_POSITION.getX() + (TILE_SIZE * BOARD_TOTAL_COLUMNS),
                BOARD_START_POSITION.getY() + (TILE_SIZE * BOARD_TOTAL_ROWS));
        calculateNewGoalPosition();

        awaitLeaveTimer = new Timer(this, 4000, this::timeToLeave);
        awaitLeaveTimer.setOneShot(true);

        ghostCircle.setColor(Color.BLUE);

        ready();
    }

    @Override
    protected void startRound() {
        currentMode = GhostManager.GhostMode.AWAITING_START;
        setGoalPosition(homePosition);
        awaitLeaveTimer.restart();
        awaitLeaveTimer.start();
    }

    private void timeToLeave() {
        startLeavingSpawn();
    }

    @Override
    protected void calculateNewGoalPosition() {
        Vector2D directionVector = getPacManDirectionVector().multiply(2 * TILE_SIZE);
        Position2D intermediatePosition = new Position2D(pacmanPosition);
        intermediatePosition.addVector2D(directionVector);

        Vector2D interToBlinky = intermediatePosition.distance(blinky.getPosition());

        Position2D newGoalPosition = new Position2D(intermediatePosition);
        newGoalPosition.addVector2D(interToBlinky.flip());

        setGoalPosition(newGoalPosition);
    }
}
