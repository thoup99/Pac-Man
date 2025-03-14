package j2d.components.graphics.shapes;

import j2d.attributes.position.OffsetPosition2D;
import j2d.attributes.position.Position2D;
import j2d.engine.gameobject.GameObject;
import j2d.engine.render.Renderer;

import java.awt.*;

public class Square extends Shape {
    Position2D topLeft;
    OffsetPosition2D bottomRight;

    public Square(GameObject parentGameObject) {
        this(parentGameObject, Renderer.getTopLayer());
    }

    public Square(GameObject parentGameObject, int layer) {
        this(parentGameObject, layer, new Position2D(0, 0), new Position2D(10, 10));
    }

    public Square(GameObject parentGameObject, int layer, Position2D topLeft, Position2D bottomRight) {
        super(parentGameObject, layer);
        this.topLeft = topLeft;
        this.bottomRight = new OffsetPosition2D(topLeft, (bottomRight.getIntX() - topLeft.getIntX()), (bottomRight.getIntY() - topLeft.getIntY()));
    }

    public Position2D getTopLeft() {
        return topLeft;
    }

    public Position2D getBottomRight() {
        return bottomRight;
    }

    public void setTopLeft(Position2D topLeft) {
        this.topLeft = topLeft;
        this.bottomRight.setBaseKeepPosition(topLeft);
        validatePositions();
    }

    public void setBottomRight(Position2D bottomRight) {
        this.bottomRight = new OffsetPosition2D(topLeft, (bottomRight.getIntX() - topLeft.getIntX()), (bottomRight.getIntY() - topLeft.getIntY()));
        validatePositions();
    }

    //TODO if the bottomRight becomes the bottomLeft change topLeft and bottomRight accordingly & similar
    private void validatePositions() {

    }

    @Override
    public void render(Graphics2D g2) {
        Graphics2D g2Copy = (Graphics2D) g2.create() ;
        applyG2Settings(g2Copy);

        g2Copy.drawRect(topLeft.getIntX(), topLeft.getIntY(), (bottomRight.getIntX() - topLeft.getIntX()), (bottomRight.getIntY() - topLeft.getIntY()));
    }
}
