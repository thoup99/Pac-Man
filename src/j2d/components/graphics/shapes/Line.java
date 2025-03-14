package j2d.components.graphics.shapes;

import j2d.attributes.position.Position2D;
import j2d.engine.gameobject.GameObject;
import j2d.engine.render.Renderer;

import java.awt.*;

public class Line extends Shape {
    Position2D start, end;


    public Line(GameObject parentGameObject) {
        this(parentGameObject, Renderer.getTopLayer());
    }

    public Line(GameObject parentGameObject, int layer) {
        this(parentGameObject, layer, new Position2D(0, 0), new Position2D(0, 0));
    }

    public Line(GameObject parentGameObject, int layer, Position2D start, Position2D end) {
        super(parentGameObject, layer);
        this.start = start;
        this.end = end;
    }

    public Position2D getStart() {
        return start;
    }

    public Position2D getEnd() {
        return end;
    }

    public void setStart(Position2D start) {
        this.start = start;
    }

    public void setEnd(Position2D end) {
        this.end = end;
    }

    @Override
    public void render(Graphics2D g2) {
        Graphics2D g2Copy = (Graphics2D) g2.create() ;
        applyG2Settings(g2Copy);

        g2Copy.drawLine(start.getIntX(), start.getIntY(), end.getIntX(), end.getIntY());
    }
}
