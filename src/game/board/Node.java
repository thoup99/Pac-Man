package game.board;

import j2d.attributes.position.Position2D;
import j2d.engine.GameObject;

public class Node extends GameObject {
    Position2D position;
    Node up;
    Node down;
    Node left;
    Node right;


    @Override
    public void update(double delta) {

    }

    @Override
    public void physicsUpdate(double delta) {

    }
}
