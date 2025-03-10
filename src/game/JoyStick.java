package game;

import j2d.attributes.position.Position2D;
import j2d.components.graphics.shapes.Circle;
import j2d.components.graphics.shapes.Line;
import j2d.engine.GameObject;
import j2d.engine.input.mouse.motion.MouseMotionHandler;
import j2d.engine.input.mouse.motion.MouseMotionSubscriber;

import java.awt.*;

public class JoyStick extends GameObject implements MouseMotionSubscriber {
    Position2D centerPosition;
    Circle outerCircle;
    Circle knobCircle;
    Line shaft;

    public JoyStick(Position2D centerPosition) {
        this.centerPosition = centerPosition;
        outerCircle = new Circle(this, 0, centerPosition, 20);
        knobCircle = new Circle(this, 1, centerPosition, 15);
        shaft = new Line(this, 0, centerPosition, knobCircle.getPosition());

        outerCircle.setStrokeWidth(5);
        knobCircle.setStrokeWidth(8);
        shaft.setStrokeWidth(5);

        outerCircle.setColor(Color.YELLOW);
        knobCircle.setColor(Color.RED);
        shaft.setColor(Color.BLACK);

        MouseMotionHandler.subscribe(this);
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void physicsUpdate(double delta) {

    }

    @Override
    public void mouseMoved(Position2D position) {
        System.out.println("Mouse moved at " + position);
        knobCircle.getCenterPosition().setPosition(position.getIntX(), position.getIntY());
        System.out.println(knobCircle.getCenterPosition());
    }

    @Override
    public void mouseDragged(Position2D position) {

    }
}
