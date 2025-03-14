package j2d.engine.debug;

import j2d.attributes.position.Position2D;
import j2d.components.graphics.text.Text;
import j2d.engine.gameobject.GameObject;
import j2d.engine.render.Renderer;
import j2d.engine.updates.physics.PhysicsServer;

import java.awt.*;

public class PhysicsTickDisplay extends GameObject {
    private static Text pdtText;

    PhysicsTickDisplay() {
        pdtText = new Text(this, new Position2D(5, 30), "Physics Tick: ", Renderer.DEBUG_LAYER);
        pdtText.setColor(Color.red);

        ready();
    }

    static void hideText() {
        pdtText.removeFromRenderer();
    }

    static void showText() {
        pdtText.addToRenderer();
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void physicsUpdate(double delta) {
        if (Debug.drawPhysicsTick) {
            int temp = (int) (PhysicsServer.currentStepRate * 1000.0);
            double trimmedStepRate = ((double) temp) / 1000.0;
            pdtText.setText("Physics Tick: " + trimmedStepRate);
        }
    }
}
