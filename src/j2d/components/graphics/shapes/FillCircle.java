package j2d.components.graphics.shapes;

import j2d.attributes.position.Position2D;
import j2d.engine.gameobject.GameObject;

import java.awt.*;

public class FillCircle extends Circle{
    public FillCircle(GameObject parentGameObject) {
        super(parentGameObject);
    }

    public FillCircle(GameObject parentGameObject, Position2D position, int radius) {
        super(parentGameObject, position, radius);
    }

    public FillCircle(GameObject parentGameObject, int layer, Position2D position, int radius) {
        super(parentGameObject, layer, position, radius);
    }

    @Override
    public void render(Graphics2D g2) {
        Graphics2D g2Copy = (Graphics2D) g2.create() ;
        applyG2Settings(g2Copy);

        int diameter = radius * 2;
        g2Copy.fillOval(topLeftPosition.getIntX(), topLeftPosition.getIntY(), diameter, diameter);
    }
}
