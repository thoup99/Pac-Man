package j2d.components.graphics.text;

import j2d.attributes.position.OffsetPosition2D;
import j2d.attributes.position.Position2D;
import j2d.engine.GameObject;

import java.awt.*;

public class CenteredText extends Text{
    OffsetPosition2D offsetPosition;
    private boolean needsRecahced = false;


    public CenteredText(GameObject parentGameObject, Position2D position, String text) {
        this(parentGameObject, position, text, 0);
    }

    public CenteredText(GameObject parentGameObject, Position2D position, String text, int layer) {
        super(parentGameObject, position, text, layer);
        offsetPosition = new OffsetPosition2D(position, 0, 0);
        needsRecahced = true;
    }

    @Override
    public void setText(String text) {
        super.setText(text);
        needsRecahced = true;
    }

    @Override
    public void setFont(Font font) {
        super.setFont(font);
        needsRecahced = true;
    }

    @Override
    public void setFontSize(int fontSize) {
        super.setFontSize(fontSize);
        needsRecahced = true;
    }

    private void calculateOffset(Graphics2D g2) {
        FontMetrics metrics = g2.getFontMetrics(font);
        double new_x_offset = -((double) metrics.stringWidth(text) / 2);
        double new_y_offset = ((double) metrics.getHeight() / 2);
        offsetPosition.setXOffset(new_x_offset);
        offsetPosition.setYOffset(new_y_offset);
    }

    @Override
    public void render(Graphics2D g2) {
        if (needsRecahced) {
            calculateOffset(g2);
            needsRecahced = false;
        }
        Graphics2D g2Copy = (Graphics2D) g2.create();
        g2Copy.setColor(textColor);
        g2Copy.drawString(text, offsetPosition.getIntX(), offsetPosition.getIntY());
        g2Copy.dispose();
    }
}
