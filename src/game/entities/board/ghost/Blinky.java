package game.entities.board.ghost;

import game.Constants.Direction;
import game.board.nodes.Node;
import game.entities.board.PacMan;
import j2d.attributes.Vector2D;
import j2d.attributes.position.Position2D;

import java.util.List;

public class Blinky extends Ghost {
    Position2D pacmanPosition;

    public Blinky(Node startNode, PacMan pacman) {
        super(startNode);
        pacmanPosition = pacman.getPosition();
    }

    @Override
    public void update(double delta) {
        if (didOvershootTargetNode()) {
            currentNode = targetNode;
            Direction newDirection = getClosestDirection(currentNode, getValidDirections());
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
        if (movementVector == null) {
            System.out.println("Movement Vector is null! " + currentDirection );
        }
        position.addX((movementSpeed * delta) * movementVector.getX());
        position.addY((movementSpeed * delta) * movementVector.getY());
    }

    private Direction getClosestDirection(Node startNode, List<Direction> directions) {
        double shortestDistance = Double.MAX_VALUE;
        Direction closestDirection = null;

        for (Direction direction : directions) {
            Position2D endPosition = startNode.getNeighbors().get(direction).getPosition();
            double distanceToEnd = endPosition.distance(pacmanPosition).getMagnitudeSquared();
            if (distanceToEnd < shortestDistance) {
                shortestDistance = distanceToEnd;
                closestDirection = direction;
            } else if (distanceToEnd == shortestDistance) {
                boolean keepLastClosest = random.nextBoolean();
                if (!keepLastClosest) {
                    closestDirection = direction;
                }
            }
        }

        return closestDirection;

    }

}
