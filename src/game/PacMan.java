package game;

import game.board.nodes.Node;
import j2d.attributes.Vector2D;
import j2d.attributes.position.Position2D;
import j2d.components.graphics.shapes.Circle;
import j2d.components.graphics.shapes.FillCircle;
import j2d.engine.GameObject;
import j2d.engine.input.keyboard.KeyHandler;
import j2d.engine.input.keyboard.KeySubscriber;

import game.Constants.Direction;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class PacMan extends GameObject implements KeySubscriber {
    final Position2D position = new Position2D();
    final Circle pacCircle;
    Direction currentDirection;
    Map<Direction, Vector2D> directionMap;
    int movementSpeed = 60;

    Node currentNode;
    Node targetNode;

    public PacMan(Node startNode) {
        currentNode = startNode;
        targetNode = startNode;
        setPosition();

        pacCircle = new FillCircle(this,2, position, 8 );
        loadDirectionMap();
        pacCircle.setColor(Color.ORANGE);

        int[] keys = {KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D};
        KeyHandler.subscribe(this, keys);

        ready();
    }

    @Override
    public void update(double delta) {
        Direction newDirection = getDirection();

        if (didOvershootTargetNode()) {
            currentNode = targetNode;
            if (currentNode.getNeighbors().get(Direction.PORTAL) != null) {
                currentNode = currentNode.getNeighbors().get(Direction.PORTAL);
            }
            targetNode = getNewTargetNode(newDirection);
            if (targetNode != currentNode) {
                currentDirection = newDirection;
            } else {
                targetNode = getNewTargetNode(currentDirection);
            }

            if (targetNode == currentNode) {
                currentDirection = Direction.STOP;
            }
            setPosition();

        } else {
            if (isOppositeDirection(newDirection)) {
                reverseDirection();
            }
        }


        Vector2D movementVector = directionMap.get(currentDirection);
        position.addX((movementSpeed * delta) * movementVector.getX());
        position.addY((movementSpeed * delta) * movementVector.getY());

    }

    private void loadDirectionMap() {
        directionMap = new HashMap<Direction, Vector2D>();
        directionMap.put(Direction.STOP, new Vector2D(0, 0));
        directionMap.put(Direction.UP, new Vector2D(0, -1));
        directionMap.put(Direction.DOWN, new Vector2D(0, 1));
        directionMap.put(Direction.LEFT, new Vector2D(-1, 0));
        directionMap.put(Direction.RIGHT, new Vector2D(1, 0));
    }

    private Direction getDirection() {
        if (KeyHandler.isKeyPressed(KeyEvent.VK_W)) {
            return Direction.UP;
        }
        else if (KeyHandler.isKeyPressed(KeyEvent.VK_S)) {
            return Direction.DOWN;
        }
        else if (KeyHandler.isKeyPressed(KeyEvent.VK_A)) {
            return Direction.LEFT;
        }
        else if (KeyHandler.isKeyPressed(KeyEvent.VK_D)) {
            return Direction.RIGHT;
        }
        return Direction.STOP;
    }

    public void setPosition() {
        position.setPosition(currentNode.getPosition());
    }

    private boolean isValidDirection(Direction direction) {
        if (direction != Direction.STOP) {
            return currentNode.getNeighbors().get(direction) != null;
        }
        return false;
    }

    private Node getNewTargetNode(Direction direction) {
        if (isValidDirection(direction)) {
            return currentNode.getNeighbors().get(direction);
        }
        return currentNode;
    }

    private boolean didOvershootTargetNode() {
        if (targetNode != null) {
            double nodeToTarget = targetNode.getPosition().distance(currentNode.getPosition()).getMagnitudeSquared();
            double nodeToPacMan = position.distance(currentNode.getPosition()).getMagnitudeSquared();
            return nodeToPacMan >= nodeToTarget;
        }
        return false;
    }

    private void reverseDirection() {
        currentDirection = Direction.getOpposite(currentDirection);
        Node temp = currentNode;
        currentNode = targetNode;
        targetNode = temp;
    }

    private boolean isOppositeDirection(Direction direction) {
        if (direction != Direction.STOP) {
            return currentDirection.getValue() == -direction.getValue();
        }
        return false;
    }

    @Override
    public void physicsUpdate(double delta) {

    }

    @Override
    public void keyPressed(int key) {

    }

    @Override
    public void keyReleased(int key) {

    }
}
