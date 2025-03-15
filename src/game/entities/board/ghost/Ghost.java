package game.entities.board.ghost;

import game.board.nodes.Node;
import game.entities.board.BoardEntity;
import game.entities.board.PacMan;
import game.entities.board.ghost.GhostManager.GhostMode;
import j2d.attributes.Vector2D;
import j2d.attributes.position.Position2D;
import j2d.components.graphics.shapes.Circle;
import j2d.components.graphics.shapes.FillCircle;
import j2d.components.physics.collider.CircleCollider;

import game.Constants.Direction;
import j2d.engine.gameobject.GameObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Ghost extends BoardEntity {
    static Random random = new Random();
    static final int NORMAL_SPEED = 90;
    static final int FRIGHT_SPEED = 40;
    static final int SPAWN_SPEED = 150;

    Position2D pacmanPosition;
    Position2D scatterPosition;
    Position2D goalPosition = new Position2D();
    Position2D homePosition;

    GhostMode currentMode = GhostMode.SCATTER;

    CircleCollider collider;
    Circle ghostCircle;

    public Ghost(Node startNode, PacMan pacman) {
        super(startNode);
        homePosition = startNode.getPosition();
        pacmanPosition = pacman.getPosition();

        ghostCircle = new FillCircle(this,2, position, 12 );

        collider = new CircleCollider(this, position, 6);
        setMovementSpeed(NORMAL_SPEED);
    }

    @Override
    public void update(double delta) {
        if (didOvershootTargetNode()) {
            currentNode = targetNode;
            checkSpawnReturn();

            if (currentMode == GhostManager.GhostMode.CHASE){
                calculateNewGoalPosition();
            }

            handleNodeTransition();
        }

        moveInCurrentDirection(delta);
    }

    @Override
    public void onCollision(GameObject other) {
        if (other instanceof PacMan) {
            if (currentMode == GhostMode.FRIGHT) {
                startSpawn();
            } else {
                //Kill PacMan
            }
        }
    }

    protected void startScatter() {
        currentMode = GhostMode.SCATTER;
        reverseDirection();
        setGoalPosition(scatterPosition);
    }

    protected void startChase() {
        currentMode = GhostMode.CHASE;
        reverseDirection();
        setMovementSpeed(NORMAL_SPEED);
        calculateNewGoalPosition();
    }

    protected void startFright() {
        reverseDirection();
        setMovementSpeed(FRIGHT_SPEED);
        currentMode = GhostMode.FRIGHT;
    }

    protected void startSpawn() {
        currentMode = GhostMode.SPAWN;
        reverseDirection();
        setMovementSpeed(SPAWN_SPEED);
        setGoalPosition(homePosition);
    }

    protected abstract void calculateNewGoalPosition();

    protected void setGoalPosition(Position2D newGoal) {
        goalPosition = newGoal;
    }

    protected void checkSpawnReturn() {
        if (currentMode == GhostMode.SPAWN && currentNode.getPosition().equals(homePosition)) {
            startChase();
        }
    }

    protected void handleNodeTransition() {
        Direction newDirection = getNewDirection();

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

    protected Direction getNewDirection() {
        Direction newDirection = getClosestDirection(getValidDirections());
        if (currentMode == GhostMode.FRIGHT) {
            newDirection = pickRandomDirection(getValidDirections());
        }
        return newDirection;
    }

    protected List<Direction> getValidDirections() {
        List<Direction> directions = new ArrayList<>();
        for (Direction direction : Direction.values()) {
            if (Direction.getOpposite(direction) != currentDirection && isValidDirection(direction)) {
                directions.add(direction);
            }
        }
        directions.remove(Direction.PORTAL);
        if (directions.isEmpty()) {
            directions.add(Direction.getOpposite(currentDirection));
        }
        return directions;
    }

    protected Direction pickRandomDirection(List<Direction> validDirections) {
        int index = random.nextInt(validDirections.size());
        return validDirections.get(index);
    }

    protected Direction getClosestDirection(List<Direction> directions) {
        double shortestDistance = Double.MAX_VALUE;
        Direction closestDirection = null;

        for (Direction direction : directions) {
            Position2D endPosition = currentNode.getNeighbors().get(direction).getPosition();
            double distanceToEnd = endPosition.distance(goalPosition).getMagnitudeSquared();
            if (distanceToEnd < shortestDistance) {
                shortestDistance = distanceToEnd;
                closestDirection = direction;
            } else if (distanceToEnd == shortestDistance) {
                if (direction.getValue() > closestDirection.getValue()) {
                    closestDirection = direction;
                }
            }
        }

        return closestDirection;

    }
}
