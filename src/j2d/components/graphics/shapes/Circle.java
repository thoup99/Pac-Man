package j2d.components.graphics.shapes;

import j2d.attributes.position.OffsetPosition2D;
import j2d.attributes.position.Position2D;
import j2d.engine.GameObject;
import j2d.engine.render.Renderer;

import java.awt.*;

public class Circle extends Shape {
    int radius;
    OffsetPosition2D position = new OffsetPosition2D(new Position2D(0 ,0) , 100, 100);

    public Circle(GameObject parentGameObject) {
        this(parentGameObject, Renderer.getTopLayer(), new Position2D(0, 0), 10);
    }

    public Circle(GameObject parentGameObject, Position2D position, int radius) {
        this(parentGameObject, Renderer.getTopLayer(), new Position2D(0, 0), radius);
    }

    public Circle(GameObject parentGameObject, int layer, Position2D position, int radius) {
        super(parentGameObject, layer);
        this.radius = radius;
        this.position = new OffsetPosition2D(position, -radius, -radius );
    }

    @Override
    public void render(Graphics2D g2) {
        Graphics2D g2Copy = (Graphics2D) g2.create() ;
        applyG2Settings(g2Copy);

        int diameter = radius * 2;
        g2Copy.drawOval(position.getIntX(), position.getIntY(), diameter, diameter);
    }
}
