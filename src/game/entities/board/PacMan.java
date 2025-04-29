package game.entities.board;

import game.PacManController;
import game.board.nodes.Node;
import game.board.pellets.Pellet;
import game.entities.board.ghost.Ghost;
import j2d.attributes.position.OffsetPosition2D;
import j2d.attributes.position.Position2D;
import j2d.audio.AudioPlayer;
import j2d.components.physics.collider.CircleCollider;
import j2d.components.sprite.AnimatedSprite;
import j2d.components.sprite.AnimationFrame;
import j2d.components.sprite.SpriteAnimation;
import j2d.components.sprite.SpriteSheet;
import j2d.engine.gameobject.GameObject;
import j2d.engine.input.keyboard.KeyHandler;
import j2d.engine.input.keyboard.KeySubscriber;

import game.Constants.Direction;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class PacMan extends BoardEntity implements KeySubscriber {
    CircleCollider collider;
    Direction facingDirection = Direction.LEFT;
    PacManController pacManController;
    boolean isPaused = false;
    boolean isAlive = true;
    boolean DeathNoise = false;
    OffsetPosition2D spriteDrawPosition;
    enum PacManAnimations {MOVE_UP, MOVE_DOWN, MOVE_LEFT, MOVE_RIGHT, DIE}
    AnimatedSprite<PacManAnimations> animatedSprite;

    public PacMan( PacManController pacManController, Node startNode) {
        super(startNode);
        this.pacManController = pacManController;

        spriteDrawPosition = new OffsetPosition2D(position, -15, -15);
        BufferedImage pacManSheet = SpriteSheet.loadImage("./images/pacman_sheet.png");
        animatedSprite = new AnimatedSprite<PacManAnimations>(this, spriteDrawPosition, pacManSheet, 4, 14);
        animatedSprite.setLayer(2);
        animatedSprite.setPadding(2, 2);
        animatedSprite.setSpacing(2, 2);
        loadAnimations();
        animatedSprite.playAnimation(PacManAnimations.MOVE_LEFT);

        collider = new CircleCollider(this, position, 9);

        currentDirection = Direction.LEFT;
        setMovementSpeed(100);

        int[] keys = {KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D};
        KeyHandler.subscribe(this, keys);

        ready();
    }

    @Override
    public void update(double delta) {
        if (isPaused) {
            return;
        }

        moveInCurrentDirection(delta);
        Direction newDirection = getDirection();
        Direction directionAtStart = currentDirection;

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

        if (currentDirection != Direction.STOP) {
            facingDirection = currentDirection;
        }

        if (directionAtStart != currentDirection) {
            determineAnimation();
        }
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

    @Override
    public void onCollision(GameObject other) {
        if (other instanceof Ghost) {
            Ghost ghost = (Ghost) other;
            if (ghost.getCurrentMode() == Ghost.GhostMode.FRIGHT) {
                pacManController.onGhostEaten();
            } else if (ghost.isGhostHostile()) {
                if (isAlive) {
                    isAlive = false;
                    onPacManDeath();
                }
            }
        } else if (other instanceof Pellet) {
            ((Pellet) other).eaten();
            pacManController.checkBoardClear();
        }
    }

    public void determineAnimation() {
        if (currentDirection == Direction.LEFT) {
            animatedSprite.playAnimation(PacManAnimations.MOVE_LEFT);
        } else if (currentDirection == Direction.RIGHT) {
            animatedSprite.playAnimation(PacManAnimations.MOVE_RIGHT);
        } else if (currentDirection == Direction.UP) {
            animatedSprite.playAnimation(PacManAnimations.MOVE_UP);
        } else if (currentDirection == Direction.DOWN) {
            animatedSprite.playAnimation(PacManAnimations.MOVE_DOWN);
        }
        animatedSprite.resumeAnimation();

        if (currentDirection == Direction.STOP) {
            animatedSprite.pauseAnimation();
        }
    }

    private void onPacManDeath() {
        pacManController.onPacManDeath();
        animatedSprite.playAnimation(PacManAnimations.DIE);
        if(!DeathNoise) {
            AudioPlayer.playSFX(AudioPlayer.SFX.death_0);
            DeathNoise = true;
        }else{
            AudioPlayer.playSFX(AudioPlayer.SFX.death_1);
            DeathNoise = false;
        }

    }

    private void onDeathComplete() {
        pacManController.onDeathAnimationCompleted();
        AudioPlayer.stopFrightClip();
    }

    public void pause() {
        isPaused = true;
        animatedSprite.pauseAnimation();
    }

    public void unpause() {
        isPaused = false;
        animatedSprite.resumeAnimation();
    }

    public void allowAnimations() {
        animatedSprite.resumeAnimation();
    }

    public Position2D getPosition() {
        return position;
    }

    private void loadAnimations() {
        //Make Animations
        int chompTime = 60;
        SpriteAnimation moveRight = new SpriteAnimation(true, Arrays.asList(
                new AnimationFrame(2, chompTime),
                new AnimationFrame(0, chompTime),
                new AnimationFrame(1, chompTime),
                new AnimationFrame(0, chompTime)
            )
        );

        SpriteAnimation moveLeft = new SpriteAnimation(true, Arrays.asList(
                new AnimationFrame(2, chompTime),
                new AnimationFrame(14, chompTime),
                new AnimationFrame(15, chompTime),
                new AnimationFrame(14, chompTime)
            )
        );

        SpriteAnimation moveUp = new SpriteAnimation(true, Arrays.asList(
                new AnimationFrame(2, chompTime),
                new AnimationFrame(28, chompTime),
                new AnimationFrame(29, chompTime),
                new AnimationFrame(28, chompTime)
            )
        );

        SpriteAnimation MoveDown = new SpriteAnimation(true, Arrays.asList(
                new AnimationFrame(2, chompTime),
                new AnimationFrame(42, chompTime),
                new AnimationFrame(43, chompTime),
                new AnimationFrame(42, chompTime)
            )
        );

        int deathFrameTime = 180;
        SpriteAnimation die = new SpriteAnimation(false, this::onDeathComplete, Arrays.asList(
                new AnimationFrame(2, deathFrameTime),
                new AnimationFrame(3, deathFrameTime),
                new AnimationFrame(4, deathFrameTime),
                new AnimationFrame(5, deathFrameTime),
                new AnimationFrame(6, deathFrameTime),
                new AnimationFrame(7, deathFrameTime),
                new AnimationFrame(8, deathFrameTime),
                new AnimationFrame(9, deathFrameTime),
                new AnimationFrame(10, deathFrameTime),
                new AnimationFrame(11, deathFrameTime),
                new AnimationFrame(12, deathFrameTime),
                new AnimationFrame(13, deathFrameTime)
            )
        );

        //Add animations to animatedSprite
        animatedSprite.addAnimation(PacManAnimations.MOVE_LEFT, moveLeft);
        animatedSprite.addAnimation(PacManAnimations.MOVE_RIGHT, moveRight);
        animatedSprite.addAnimation(PacManAnimations.MOVE_UP, moveUp);
        animatedSprite.addAnimation(PacManAnimations.MOVE_DOWN, MoveDown);
        animatedSprite.addAnimation(PacManAnimations.DIE, die);
    }

    public void resetPosition() {
        currentNode = startNode;
        targetNode = startNode;
        setPosition();
        currentDirection = Direction.LEFT;
        isAlive = true;
        determineAnimation();
    }

    public Direction getCurrentDirection() {
        return currentDirection;
    }

    public Direction getFacingDirection() {
        return facingDirection;
    }

    @Override
    public void keyPressed(int key) {

    }

    @Override
    public void keyReleased(int key) {

    }
}
