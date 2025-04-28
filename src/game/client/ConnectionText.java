package game.client;

import j2d.attributes.position.Position2D;
import j2d.components.graphics.text.CenteredText;
import j2d.components.graphics.text.Text;
import j2d.engine.gameobject.GameObject;

import java.awt.*;

public class ConnectionText extends GameObject {
    CenteredText text;

    public ConnectionText(float x, float y) {
        text = new CenteredText(this, new Position2D(x, y), "Connecting To Server...", 5);
        text.setColor(Color.WHITE);
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void physicsUpdate(double delta) {

    }
}
