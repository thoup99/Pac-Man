package j2d.components.graphics.shapes;

import j2d.components.Component;
import j2d.engine.GameObject;
import j2d.engine.render.Renderable;
import j2d.engine.render.Renderer;

import java.awt.*;

public abstract class Shape extends Component implements Renderable {
    int layer;
    protected Color color;
    protected BasicStroke stroke;

    public Shape(GameObject parentGameObject) {
        this(parentGameObject, Renderer.getTopLayer());
    }

    public Shape(GameObject parentGameObject, int layer) {
        super(parentGameObject);
        this.layer = layer;
        addToRenderer();
    }

    public void setLayer(int layer) {
        this.layer = layer;
        removeFromRenderer();
        addToRenderer();
    }

    public void setStrokeWidth(int width) {
        stroke = new BasicStroke(width);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    protected void applyG2Settings(Graphics2D g2) {
        if (stroke != null) {
            g2.setStroke(stroke);
        }
        if (color != null) {
            g2.setColor(color);
        }
    }

    @Override
    public void delete() {
        super.delete();
        removeFromRenderer();
    }

    @Override
    public void addToRenderer() {
        Renderer.add(this, layer);
    }

    @Override
    public void removeFromRenderer() {
        Renderer.remove(this, layer);
    }

    @Override
    public void render(Graphics2D g2) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}