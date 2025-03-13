package game.entities.board.ghost;

import game.board.nodes.Node;
import game.entities.board.BoardEntity;
import j2d.attributes.Vector2D;
import j2d.components.graphics.shapes.Circle;
import j2d.components.graphics.shapes.FillCircle;
import j2d.components.physics.collider.CircleCollider;

import game.Constants.Direction;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Ghost extends BoardEntity {
    Random random;
    final Circle ghostCircle;
    CircleCollider collider;

    public Ghost(Node startNode) {
        super(startNode);
        random = new Random();
        ghostCircle = new FillCircle(this,2, position, 12 );
        ghostCircle.setColor(Color.RED);

        collider = new CircleCollider(this, position, 6);
        setMovementSpeed(90);

        ready();
    }

    @Override
    public void update(double delta) {
        if (didOvershootTargetNode()) {
            Direction newDirection = pickRandomDirection(getValidDirections());
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
            setPosition();
        }

        Vector2D movementVector = directionMap.get(currentDirection);
        position.addX((movementSpeed * delta) * movementVector.getX());
        position.addY((movementSpeed * delta) * movementVector.getY());
    }

    protected List<Direction> getValidDirections() {
        List<Direction> directions = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            if (Direction.getOpposite(direction) != currentDirection && isValidDirection(direction)) {
                directions.add(direction);
            }
        }
        if (directions.isEmpty()) {
            directions.add(Direction.getOpposite(currentDirection));
        }
        return directions;
    }

    protected Direction pickRandomDirection(List<Direction> validDirections) {
        int index = random.nextInt(validDirections.size());
        return validDirections.get(index);
    }
}
