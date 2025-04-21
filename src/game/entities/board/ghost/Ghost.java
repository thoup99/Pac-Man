package game.entities.board.ghost;

import game.board.nodes.Node;
import game.board.nodes.NodeManager;
import game.entities.board.BoardEntity;
import game.entities.board.PacMan;
import j2d.attributes.Vector2D;
import j2d.attributes.position.OffsetPosition2D;
import j2d.attributes.position.Position2D;
import j2d.components.physics.collider.CircleCollider;

import game.Constants.Direction;
import j2d.components.sprite.AnimatedSprite;
import j2d.components.sprite.AnimationFrame;
import j2d.components.sprite.SpriteAnimation;
import j2d.components.sprite.SpriteSheet;
import j2d.engine.gameobject.GameObject;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class Ghost extends BoardEntity {
    public enum GhostMode {CHASE, SCATTER, FRIGHT, RETURN_SPAWN, AWAITING_START, LEAVING_START}
    static Random random = new Random();
    static final int NORMAL_SPEED = 90;
    static final int LEAVE_SPAWN_SPEED = 50;
    static final int FRIGHT_SPEED = 40;
    static final int SPAWN_SPEED = 150;

    NodeManager nodeManager;

    PacMan pacman;
    Position2D pacmanPosition;
    Position2D scatterPosition;
    Position2D goalPosition = new Position2D();
    Position2D homePosition;

    GhostMode currentMode = GhostMode.AWAITING_START;

    CircleCollider collider;

    OffsetPosition2D spriteDrawPosition;
    enum GhostAnimations {MOVE_UP, MOVE_DOWN, MOVE_LEFT, MOVE_RIGHT, FLEE}
    AnimatedSprite<GhostAnimations> animatedSprite;

    boolean isPaused = false;

    public Ghost(Node startNode, NodeManager nodeManager, PacMan pacman) {
        super(startNode);
        this.nodeManager = nodeManager;
        this.pacman = pacman;
        homePosition = startNode.getPosition();
        pacmanPosition = pacman.getPosition();
        isPaused = false;


        spriteDrawPosition = new OffsetPosition2D(position, -7, -7);
        BufferedImage ghostSheet = SpriteSheet.loadImage("./images/ghost_sheet.png");
        animatedSprite = new AnimatedSprite<GhostAnimations>(this, spriteDrawPosition, ghostSheet, 4, 10);
        animatedSprite.setLayer(3);
        animatedSprite.setPadding(1, 1);
        animatedSprite.setSpacing(1, 1);
        loadAnimations();
        animatedSprite.playAnimation(GhostAnimations.MOVE_LEFT);

        collider = new CircleCollider(this, position, 6);
        setMovementSpeed(NORMAL_SPEED);
    }

    @Override
    public void update(double delta) {
        if (isPaused) {
            return;
        }

        moveInCurrentDirection(delta);
        Direction directionAtStart = currentDirection;

        if (didOvershootTargetNode()) {
            currentNode = targetNode;
            checkSpawnReturn();
            checkHasLeftSpawn();

            if (currentMode == GhostMode.CHASE){
                calculateNewGoalPosition();
            }

            handleNodeTransition();

            if (directionAtStart != currentDirection) {
                determineAnimation();
            }
        }
    }

    @Override
    public void onCollision(GameObject other) {
        if (other instanceof PacMan) {
            if (currentMode == GhostMode.FRIGHT) {
                startSpawn();
                determineAnimation();
            }
        }
    }

    protected void startScatter() {
        currentMode = GhostMode.SCATTER;
        reverseDirection();
        setGoalPosition(scatterPosition);
        determineAnimation();
    }

    protected void startChase() {
        currentMode = GhostMode.CHASE;
        reverseDirection();
        setMovementSpeed(NORMAL_SPEED);
        calculateNewGoalPosition();
        determineAnimation();
    }

    protected void startFright() {
        reverseDirection();
        setMovementSpeed(FRIGHT_SPEED);
        currentMode = GhostMode.FRIGHT;
        determineAnimation();
    }

    protected void startSpawn() {
        currentMode = GhostMode.RETURN_SPAWN;
        reverseDirection();
        setMovementSpeed(SPAWN_SPEED);
        setGoalPosition(homePosition);
        determineAnimation();
    }

    protected void startLeavingSpawn() {
        currentMode = GhostMode.LEAVING_START;
        reverseDirection();
        setMovementSpeed(LEAVE_SPAWN_SPEED);
        setGoalPosition(nodeManager.getBlinkyStartNode().getPosition());
        determineAnimation();
    }

    protected abstract void startRound();
    protected abstract void calculateNewGoalPosition();

    protected void setGoalPosition(Position2D newGoal) {
        goalPosition = newGoal;
    }

    protected void checkSpawnReturn() {
        if (currentMode == GhostMode.RETURN_SPAWN && currentNode.getPosition().equals(homePosition)) {
            startLeavingSpawn();
        }
    }

    protected void checkHasLeftSpawn() {
        if (currentMode == GhostMode.LEAVING_START && currentNode.getPosition().equals(nodeManager.getBlinkyStartNode().getPosition())) {
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
        if (currentNode == nodeManager.getBlinkyStartNode() && currentMode != GhostMode.RETURN_SPAWN) {
            directions.remove(Direction.DOWN);
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

    protected Vector2D getPacManDirectionVector() {
        Direction pacManDirection = pacman.getFacingDirection();
        Vector2D directionVector = new Vector2D();
        if (pacManDirection == Direction.UP) {
            directionVector = new Vector2D(-1, -1);
        } else if (pacManDirection == Direction.DOWN) {
            directionVector = new Vector2D(0, 1);
        } else if (pacManDirection == Direction.LEFT) {
            directionVector = new Vector2D(-1, 0);
        } else if (pacManDirection == Direction.RIGHT) {
            directionVector = new Vector2D(1, 0);
        }
        return directionVector;
    }

    private void determineAnimation() {
        if (currentMode == GhostMode.RETURN_SPAWN) {
            animatedSprite.pauseAnimation();

            if (currentDirection == Direction.LEFT) {
                animatedSprite.setSprite(animatedSprite.getSpriteNum(1, 8));
            } else if (currentDirection == Direction.RIGHT) {
                animatedSprite.setSprite(animatedSprite.getSpriteNum(0, 8));
            } else if (currentDirection == Direction.UP) {
                animatedSprite.setSprite(animatedSprite.getSpriteNum(2, 8));
            } else if (currentDirection == Direction.DOWN) {
                animatedSprite.setSprite(animatedSprite.getSpriteNum(3, 8));
            }

            return;
        }

        if (currentDirection == Direction.LEFT) {
            animatedSprite.playAnimation(GhostAnimations.MOVE_LEFT);
        } else if (currentDirection == Direction.RIGHT) {
            animatedSprite.playAnimation(GhostAnimations.MOVE_RIGHT);
        } else if (currentDirection == Direction.UP) {
            animatedSprite.playAnimation(GhostAnimations.MOVE_UP);
        } else if (currentDirection == Direction.DOWN) {
            animatedSprite.playAnimation(GhostAnimations.MOVE_DOWN);
        }
        animatedSprite.resumeAnimation();
    }

    private void loadAnimations() {
        int spriteTime = 80;

        int ghostCol;
        if (this instanceof Blinky) {
            ghostCol = 0;
        } else if (this instanceof Pinky) {
            ghostCol = 2;
        } else if (this instanceof Inky) {
            ghostCol = 4;
        } else {
            ghostCol = 6;
        }

        SpriteAnimation move_right = new SpriteAnimation(true, Arrays.asList(
                new AnimationFrame(animatedSprite.getSpriteNum(0, ghostCol), spriteTime),
                new AnimationFrame(animatedSprite.getSpriteNum(0, ghostCol + 1), spriteTime)
            )
        );

        SpriteAnimation move_left = new SpriteAnimation(true, Arrays.asList(
                new AnimationFrame(animatedSprite.getSpriteNum(1, ghostCol), spriteTime),
                new AnimationFrame(animatedSprite.getSpriteNum(1, ghostCol + 1), spriteTime)
            )
        );

        SpriteAnimation move_up = new SpriteAnimation(true, Arrays.asList(
                new AnimationFrame(animatedSprite.getSpriteNum(2, ghostCol), spriteTime),
                new AnimationFrame(animatedSprite.getSpriteNum(2, ghostCol + 1), spriteTime)
            )
        );

        SpriteAnimation move_down = new SpriteAnimation(true, Arrays.asList(
                new AnimationFrame(animatedSprite.getSpriteNum(3, ghostCol), spriteTime),
                new AnimationFrame(animatedSprite.getSpriteNum(3, ghostCol + 1), spriteTime)
            )
        );

        SpriteAnimation flee = new SpriteAnimation(true, Arrays.asList(
                new AnimationFrame(animatedSprite.getSpriteNum(0, ghostCol), spriteTime),
                new AnimationFrame(animatedSprite.getSpriteNum(0, ghostCol + 1), spriteTime)
            )
        );

        animatedSprite.addAnimation(GhostAnimations.MOVE_RIGHT, move_right);
        animatedSprite.addAnimation(GhostAnimations.MOVE_LEFT, move_left);
        animatedSprite.addAnimation(GhostAnimations.MOVE_UP, move_up);
        animatedSprite.addAnimation(GhostAnimations.MOVE_DOWN, move_down);
    }

    public Position2D getPosition() {
        return position;
    }

    public GhostMode getCurrentMode() {
        return currentMode;
    }

    public boolean isGhostHostile() {
        return (currentMode == GhostMode.SCATTER || currentMode == GhostMode.CHASE);
    }

    protected void pause() {
        isPaused = true;
        animatedSprite.pauseAnimation();
    }

    protected void resume() {
        isPaused = false;
        determineAnimation();
    }

    protected void resetPosition() {
        currentNode = startNode;
        targetNode = startNode;
        setPosition();
    }
}
