package game.entities.board;

import game.Constants;
import game.board.nodes.Node;
import j2d.attributes.Vector2D;
import j2d.attributes.position.Position2D;
import j2d.components.graphics.shapes.Circle;
import j2d.components.graphics.shapes.FillCircle;
import j2d.components.physics.collider.CircleCollider;
import j2d.engine.input.keyboard.KeyHandler;
import j2d.engine.input.keyboard.KeySubscriber;

import game.Constants.Direction;

import java.awt.*;
import java.awt.event.KeyEvent;

public class PacMan extends BoardEntity implements KeySubscriber {
    final Circle pacCircle;
    CircleCollider collider;

    public PacMan(Node startNode) {
        super(startNode);
        pacCircle = new FillCircle(this,2, position, 12 );
        pacCircle.setColor(Color.ORANGE);

        collider = new CircleCollider(this, position, 6);

        setMovementSpeed(100);

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

    public Position2D getPosition() {
        return position;
    }

    @Override
    public void keyPressed(int key) {

    }

    @Override
    public void keyReleased(int key) {

    }
}
