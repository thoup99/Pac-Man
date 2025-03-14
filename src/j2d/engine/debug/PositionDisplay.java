package j2d.engine.debug;

import j2d.attributes.position.OffsetPosition2D;
import j2d.attributes.position.Position2D;
import j2d.components.graphics.shapes.Circle;
import j2d.components.graphics.shapes.FillCircle;
import j2d.components.graphics.text.CenteredText;
import j2d.components.graphics.text.Text;
import j2d.engine.gameobject.GameObject;
import j2d.engine.render.Renderer;

import java.awt.*;

public class PositionDisplay extends GameObject {
    private final Position2D position;
    Circle pointCircle;
    Text pointText;

    public PositionDisplay(Position2D position) {
        this.position = position;
        Position2D textOffset = new OffsetPosition2D(position, 0, -20);

        pointCircle = new FillCircle(this, position, 8);
        pointText = new CenteredText(this, textOffset, "(0, 0");

        pointCircle.setColor(Color.RED);

        pointCircle.setLayer(Renderer.DEBUG_LAYER);
        pointText.setLayer(Renderer.DEBUG_LAYER);

        ready();
    }

    @Override
    public void update(double delta) {
        pointText.setText(position.toString());
    }

    @Override
    public void physicsUpdate(double delta) {

    }
}
