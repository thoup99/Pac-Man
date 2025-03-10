package j2d.components.graphics.text;

import j2d.attributes.position.Position2D;
import j2d.components.Component;
import j2d.engine.GameObject;
import j2d.engine.render.Renderable;
import j2d.engine.render.Renderer;

import javax.swing.*;
import java.awt.*;


public class Text extends Component implements Renderable {
    protected static final Color defaultColor = Color.BLACK;
    protected Color textColor;
    public static final Font defaultFont = new JLabel().getFont();
    protected Font font;
    protected int style;
    protected int size;

    String text;
    Position2D position;
    private int layer;

    public Text(GameObject parentGameObject, Position2D position, String text) {
        this(parentGameObject, position, text, Renderer.getTopLayer());
    }

    public Text(GameObject parentGameObject, Position2D position, String text, int layer) {
        super(parentGameObject);
        this.text = text;
        this.position = position;
        this.style = Font.PLAIN;
        this.size = 16;
        this.font = defaultFont.deriveFont(style, size);

        this.layer = layer;
        addToRenderer();
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setLayer(int layer) {
        this.layer = layer;
        removeFromRenderer();
        addToRenderer();
    }

    public String getText() {
        return text;
    }

    public void setColor(Color textColor) {
        this.textColor = textColor;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public void setFontSize(int size) {
        this.size = size;
        font = font.deriveFont(style, size);
    }

    public void setFontStyle(int style) {
        this.style = style;
        font = font.deriveFont(style, size);
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
        Graphics2D g2Copy = (Graphics2D) g2.create();
        g2Copy.setColor(textColor);
        g2Copy.drawString(text, position.getIntX(), position.getIntY()); //Position is bottom left of Text
        g2Copy.dispose();
    }
}
