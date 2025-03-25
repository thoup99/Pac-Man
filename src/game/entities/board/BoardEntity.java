package game.entities.board;

import game.Constants.Direction;
import game.board.nodes.Node;
import j2d.attributes.Vector2D;
import j2d.attributes.position.Position2D;
import j2d.engine.gameobject.GameObject;

import java.util.HashMap;
import java.util.Map;

public abstract class BoardEntity extends GameObject {
    protected final Position2D position = new Position2D();
    protected Direction currentDirection = Direction.STOP;
    protected Map<Direction, Vector2D> directionMap;
    protected int movementSpeed = 60;

    protected Node startNode;
    protected Node currentNode;
    protected Node targetNode;

    public BoardEntity(Node startNode) {
        this.startNode = startNode;
        currentNode = startNode;
        targetNode = startNode;
        setPosition();
        loadDirectionMap();
    }

    protected void setPosition() {
        position.setPosition(currentNode.getPosition());
    }

    protected void setMovementSpeed(int speed) {
        movementSpeed = speed;
    }

    private void loadDirectionMap() {
        directionMap = new HashMap<Direction, Vector2D>();
        directionMap.put(Direction.STOP, new Vector2D(0, 0));
        directionMap.put(Direction.UP, new Vector2D(0, -1));
        directionMap.put(Direction.DOWN, new Vector2D(0, 1));
        directionMap.put(Direction.LEFT, new Vector2D(-1, 0));
        directionMap.put(Direction.RIGHT, new Vector2D(1, 0));
    }

    protected boolean didOvershootTargetNode() {
        if (targetNode != null) {
            double nodeToTarget = targetNode.getPosition().distance(currentNode.getPosition()).getMagnitudeSquared();
            double nodeToPacMan = position.distance(currentNode.getPosition()).getMagnitudeSquared();
            return nodeToPacMan >= nodeToTarget;
        }
        return false;
    }

    protected boolean isValidDirection(Direction direction) {
        if (direction != Direction.STOP) {
            return currentNode.getNeighbors().get(direction) != null;
        }
        return false;
    }

    protected Node getNewTargetNode(Direction direction) {
        if (isValidDirection(direction)) {
            return currentNode.getNeighbors().get(direction);
        }
        return currentNode;
    }

    protected void reverseDirection() {
        currentDirection = Direction.getOpposite(currentDirection);
        Node temp = currentNode;
        currentNode = targetNode;
        targetNode = temp;
    }

    protected boolean isOppositeDirection(Direction direction) {
        if (direction != Direction.STOP) {
            return currentDirection.getValue() == -direction.getValue();
        }
        return false;
    }

    protected void moveInCurrentDirection(double delta) {
        Vector2D movementVector = directionMap.get(currentDirection);
        position.addX((movementSpeed * delta) * movementVector.getX());
        position.addY((movementSpeed * delta) * movementVector.getY());
    }

    @Override
    public abstract void update(double delta);

    @Override
    public void physicsUpdate(double delta) {

    }
}
