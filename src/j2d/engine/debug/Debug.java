package j2d.engine.debug;

import j2d.engine.render.Renderer;

public class Debug {
    static boolean drawFPS = false;
    static boolean drawPhysicsTick = false;

    static final FPSDisplay fpsDisplay = new FPSDisplay();
    static final PhysicsTickDisplay physicsTickDisplay = new PhysicsTickDisplay();

    static boolean printMouseButtons = false;
    static boolean printMousePosition = false;
    static final MousePrinter mousePrinter = new MousePrinter();

    public static boolean drawCollisionShapes = false;

    public static void initialize() {
        Renderer.createDebugLayer();
        FPSDisplay.hideText();
        PhysicsTickDisplay.hideText();
    }

    public static void setDrawFPS(boolean drawFPS) {
        Debug.drawFPS = drawFPS;
        if (Debug.drawFPS) {
            FPSDisplay.showText();
        } else {
            FPSDisplay.hideText();
        }
    }

    public static void setDrawPhysicsTick(boolean drawPhysicsTick) {
        Debug.drawPhysicsTick = drawPhysicsTick;
        if (Debug.drawPhysicsTick) {
            PhysicsTickDisplay.showText();
        } else {
            PhysicsTickDisplay.hideText();
        }
    }

    public static void setPrintMouseButtons(boolean printMouseButtons) {
        Debug.printMouseButtons = printMouseButtons;
    }

    public static void setPrintMousePosition(boolean printMousePosition) {
        Debug.printMousePosition = printMousePosition;
    }
}
