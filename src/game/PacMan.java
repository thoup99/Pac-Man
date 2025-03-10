package game;

import j2d.attributes.position.Position2D;
import j2d.components.graphics.shapes.Circle;
import j2d.components.graphics.shapes.FillCircle;
import j2d.engine.GameObject;
import j2d.engine.input.keyboard.KeyHandler;
import j2d.engine.input.keyboard.KeySubscriber;

import java.awt.*;
import java.awt.event.KeyEvent;

public class PacMan extends GameObject implements KeySubscriber {
    final Position2D position;
    final Circle pacCircle;
    int movementSpeed = 100;

    public PacMan() {
        position = new Position2D(300, 300);
        pacCircle = new FillCircle(this,3, position, 18 );
        pacCircle.setColor(Color.ORANGE);

        int[] keys = {KeyEvent.VK_W, KeyEvent.VK_A, KeyEvent.VK_S, KeyEvent.VK_D};
        KeyHandler.subscribe(this, keys);
    }

    //MAKE SURE TO FIX OFFSETPOSITION

    @Override
    public void update(double delta) {
        if (KeyHandler.isKeyPressed(KeyEvent.VK_W)) {
            position.setPosition(position.getX(), position.getY() - (movementSpeed * delta));
        }
        else if (KeyHandler.isKeyPressed(KeyEvent.VK_S)) {
            position.setPosition(position.getX(), position.getY() + (movementSpeed * delta));
        }
        if (KeyHandler.isKeyPressed(KeyEvent.VK_A)) {
            position.setPosition(position.getX() - (movementSpeed * delta), position.getY());
        }
        else if (KeyHandler.isKeyPressed(KeyEvent.VK_D)) {
            position.setPosition(position.getX() + (movementSpeed * delta), position.getY());
        }
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
