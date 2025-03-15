package game.entities.board.ghost;

import game.Constants.Direction;
import game.board.nodes.Node;
import game.entities.board.PacMan;
import game.entities.board.ghost.GhostManager.GhostMode;
import j2d.attributes.Vector2D;
import j2d.attributes.position.Position2D;
import j2d.engine.gameobject.GameObject;

import static game.Constants.*;

public class Blinky extends Ghost {
    Position2D pacmanPosition;
    Position2D scatterPosition;
    Position2D homePosition;


    public Blinky(Node startNode, PacMan pacman) {
        super(startNode);
        homePosition = startNode.getPosition();
        pacmanPosition = pacman.getPosition();
        scatterPosition = new Position2D(BOARD_START_POSITION.getX() + (TILE_SIZE * BOARD_TOTAL_COLUMNS), BOARD_START_POSITION.getY());
        setGoalPosition(pacmanPosition);

        ready();
    }

    @Override
    public void update(double delta) {
        if (didOvershootTargetNode()) {
            currentNode = targetNode;
            if (currentMode == GhostMode.SPAWN && currentNode.getPosition().equals(homePosition)) {
                startChase();
            }
            Direction newDirection = getClosestDirection(getValidDirections());
            if (currentMode == GhostMode.FRIGHT) {
                newDirection = pickRandomDirection(getValidDirections());
            }
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

    @Override
    public void onCollision(GameObject other) {
        if (other instanceof PacMan) {
            if (currentMode == GhostMode.FRIGHT) {
                startSpawn();
            }
        }
    }

    @Override
    protected void startScatter() {
        currentMode = GhostMode.SCATTER;
        setGoalPosition(scatterPosition);
    }

    @Override
    protected void startChase() {
        currentMode = GhostMode.CHASE;
        setMovementSpeed(90);
        setGoalPosition(pacmanPosition);
    }

    @Override
    protected void startFright() {
        reverseDirection();
        setMovementSpeed(40);
        currentMode = GhostMode.FRIGHT;
    }

    @Override
    protected void startSpawn() {
        currentMode = GhostMode.SPAWN;
        reverseDirection();
        setMovementSpeed(150);
        setGoalPosition(homePosition);
    }
}
