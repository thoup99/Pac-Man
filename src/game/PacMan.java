package game;

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
    final Position2D position;
    final Circle pacCircle;
    Direction direction;
    Map<Direction, Vector2D> directionMap;
    int movementSpeed = 100;

    public PacMan() {
        position = new Position2D(300, 300);
        pacCircle = new FillCircle(this,2, position, 18 );
        loadDirectionMap();
        pacCircle.setColor(Color.ORANGE);

        int[] keys = {KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D};
        KeyHandler.subscribe(this, keys);
    }

    @Override
    public void update(double delta) {
        direction = getDirection();
        Vector2D movementVector = directionMap.get(direction);

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
