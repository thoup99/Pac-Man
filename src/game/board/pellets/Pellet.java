package game.board.pellets;

import j2d.attributes.position.Position2D;
import j2d.components.graphics.shapes.FillCircle;
import j2d.engine.GameObject;

import java.awt.*;

public class Pellet extends GameObject {
    Position2D position;
    FillCircle fillCircle;

    public Pellet(Position2D position) {
        this.position = position;
        fillCircle = new FillCircle(this, 1, position, 4);
        fillCircle.setColor(Color.GRAY);
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void physicsUpdate(double delta) {

    }
}
