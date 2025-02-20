package j2d.engine;


import j2d.engine.debug.Debug;
import j2d.engine.input.keyboard.KeyHandler;
import j2d.engine.input.mouse.button.MouseButtonHandler;
import j2d.engine.input.mouse.motion.MouseMotionHandler;
import j2d.engine.input.mouse.wheel.MouseWheelHandler;
import j2d.engine.window.Window;

import java.util.ArrayList;
import java.util.List;

public class Engine implements Runnable {
    private static Engine engine;
    private static Thread engineThread;
    private static List<GameObject> gameObjects = new ArrayList<>();
    private static Window window;
    private static int targetFPS;
    private static double drawInterval;
    private static boolean isPrintingFPS = false;
    public static int currentFPS = 0;

    /**
     * Initializes all components of the engine
     */
    public static void initialize() {
        KeyHandler.initialize();
        MouseButtonHandler.initialize();
        MouseMotionHandler.initialize();
        MouseWheelHandler.initialize();

        //audio init
        //physics init?

        Debug.initialize();

        setTargetFPS(30);

        engine = new Engine();
        engineThread = new Thread(engine);
        engineThread.start();
    }

    private Engine() {}

    @Override
    public void run() {
        //    Order
        //Physics Update
        //Update
        //Physics Tick
        //Render


        long previousTime = System.nanoTime();
        long frameStartTime = System.nanoTime();
        long frameEndTime;
        double delta = 0;

        while (engineThread.isAlive()) {
            long currentTime = System.nanoTime();
            delta += (currentTime - previousTime) / drawInterval;
            previousTime = currentTime;

            if (delta >= 1) {
                frameEndTime = System.nanoTime();
                double elapsedSeconds = (frameEndTime - frameStartTime) / 1_000_000_000.0;

                doPhysicsUpdates(elapsedSeconds);
                doUpdates(elapsedSeconds);

                while (delta >= 1) {
                    delta -= 1;
                }

                if (Window.isCreated) {
                    window.repaintPanel();
                }
                currentFPS = (int) ((1.0 / elapsedSeconds) + 0.5); //0.5 added to account for rounding
                if (isPrintingFPS) {
                    System.out.println("Current FPS: " + currentFPS);
                }

                frameStartTime = frameEndTime;
            }
        }

    }

    public static void registerGameObject(GameObject gameObject) {
        if (!gameObjects.contains(gameObject)) {
            gameObjects.add(gameObject);
        }
    }

    public static void unregisterGameObject(GameObject gameObject) {
        gameObjects.remove(gameObject);
    }

    private void doPhysicsUpdates(double delta) {
        for (GameObject gameObject : gameObjects) {
            gameObject.physics_update(delta);
        }
    }

    private void doUpdates(double delta) {
        for (GameObject gameObject : gameObjects) {
            gameObject.update(delta);
        }
    }

    public static void setTargetFPS(int targetFPS) {
        Engine.targetFPS = targetFPS;
        calculateDrawInterval();
    }

    public static void calculateDrawInterval() {
        drawInterval = 1000000000 / targetFPS;
    }

    public static void registerWindow(Window window) {
        Engine.window = window;
    }

    public static void printFPSCounter(boolean isPrintingFPS) {
        Engine.isPrintingFPS = isPrintingFPS;
    }
}
