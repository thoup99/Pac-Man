package j2d.engine.debug;

import j2d.attributes.position.Position2D;
import j2d.components.graphics.text.Text;
import j2d.engine.Engine;
import j2d.engine.gameobject.GameObject;
import j2d.engine.render.Renderer;

import java.awt.*;

public class FPSDisplay extends GameObject {
    private static Text fpsText;

    FPSDisplay() {
        fpsText = new Text(this, new Position2D(5, 15), "FPS: ", Renderer.DEBUG_LAYER);
        fpsText.setColor(Color.red);

        ready();
    }

    static void hideText() {
        fpsText.removeFromRenderer();
    }

    static void showText() {
        fpsText.addToRenderer();
    }

    @Override
    public void update(double delta) {
        if (Debug.drawFPS) {
            fpsText.setText("FPS: " + Engine.currentFPS);
        }
    }

    @Override
    public void physicsUpdate(double delta) {

    }
}
