package j2d.components.graphics.shapes;

import j2d.attributes.position.Position2D;
import j2d.engine.GameObject;

import java.awt.*;

public class FillSquare extends Square {
    public FillSquare(GameObject parentGameObject) {
        super(parentGameObject);
    }

    public FillSquare(GameObject parentGameObject, int layer) {
        super(parentGameObject, layer);
    }

    public FillSquare(GameObject parentGameObject, int layer, Position2D topLeft, Position2D bottomRight) {
        super(parentGameObject, layer, topLeft, bottomRight);
    }

    @Override
    public void render(Graphics2D g2) {
        Graphics2D g2Copy = (Graphics2D) g2.create() ;
        applyG2Settings(g2Copy);

        g2Copy.fillRect(topLeft.getIntX(), topLeft.getIntY(), (bottomRight.getIntX() - topLeft.getIntX()), (bottomRight.getIntY() - topLeft.getIntY()));
    }
}
