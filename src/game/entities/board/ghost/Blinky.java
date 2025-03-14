package game.entities.board.ghost;

import game.Constants.Direction;
import game.board.nodes.Node;
import game.entities.board.PacMan;
import j2d.attributes.Vector2D;
import j2d.attributes.position.Position2D;
import j2d.components.Timer;

import static game.Constants.*;

public class Blinky extends Ghost {
    Position2D pacmanPosition;
    Position2D scatterPosition;

    Timer scatterTimer;
    boolean scatter = false;

    public Blinky(Node startNode, PacMan pacman) {
        super(startNode);
        pacmanPosition = pacman.getPosition();
        scatterPosition = new Position2D(BOARD_START_POSITION.getX() + (TILE_SIZE * BOARD_TOTAL_COLUMNS), BOARD_START_POSITION.getY());
        setGoalPosition(pacmanPosition);
        scatterTimer = new Timer(this, 7000, this::toggleScatter);
        scatterTimer.start();

        ready();
    }

    @Override
    public void update(double delta) {
        if (didOvershootTargetNode()) {
            currentNode = targetNode;
            Direction newDirection = getClosestDirection(getValidDirections());
            if (currentNode.getNeighbors().get(Direction.PORTAL) != null) {
                currentNode = currentNode.getNeighbors().get(Direction.PORTAL);
            }
            targetNode = getNewTargetNode(newDirection);
            if (targetNode != currentNode) {
                currentDirection = newDirection;
            } else {
                targetNode = getNewTargetNode(currentDirection);
            }
            setPosition();
        }

        Vector2D movementVector = directionMap.get(currentDirection);
        position.addX((movementSpeed * delta) * movementVector.getX());
        position.addY((movementSpeed * delta) * movementVector.getY());
    }

    void toggleScatter() {
        scatter = !scatter;
        if (scatter) {
            setGoalPosition(scatterPosition);
        }
        else {
            setGoalPosition(pacmanPosition);
        }
    }
}
