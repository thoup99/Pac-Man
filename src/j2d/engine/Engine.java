package j2d.engine;


import j2d.engine.debug.Debug;
import j2d.engine.gameobject.GameObjectDeletion;
import j2d.engine.input.keyboard.KeyHandler;
import j2d.engine.input.mouse.button.MouseButtonHandler;
import j2d.engine.input.mouse.motion.MouseMotionHandler;
import j2d.engine.input.mouse.wheel.MouseWheelHandler;
import j2d.engine.updates.MasterTimer;
import j2d.engine.updates.gametick.GameTick;
import j2d.engine.updates.physics.CollisionServer;
import j2d.engine.updates.physics.PhysicsServer;
import j2d.engine.window.Window;

public class Engine implements Runnable {
    private static Engine engine;
    private static Thread engineThread;
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
        long frameEndTime = 0;
        double elapsedSecondsFrame = 0;
        boolean repaintWindow = false;

        double physicsAccumulatedTime = 0;
        boolean doPhysicsTick = false;

        long nanoTimeBetweenLoops = 0;

        double delta = 0;

        while (engineThread.isAlive()) {
            long currentTime = System.nanoTime();
            delta += (currentTime - previousTime) / drawInterval;
            nanoTimeBetweenLoops = currentTime - previousTime;
            physicsAccumulatedTime += (currentTime - previousTime) / 1_000_000_000.0;
            previousTime = currentTime;

            //Tick Timers
            MasterTimer.tickAll(nanoTimeBetweenLoops);

            //Physics
            if (physicsAccumulatedTime > PhysicsServer.timeStep) {
                PhysicsServer.doPhysicsUpdates(physicsAccumulatedTime);
                PhysicsServer.currentStepRate = physicsAccumulatedTime;

                physicsAccumulatedTime -= PhysicsServer.timeStep;

                CollisionServer.checkCollisions();
                doPhysicsTick = true;
            }

            //Update/FPS
            if (delta >= 1) {
                frameEndTime = System.nanoTime();
                elapsedSecondsFrame = (frameEndTime - frameStartTime) / 1_000_000_000.0;

                GameTick.doUpdates(elapsedSecondsFrame);

                while (delta >= 1) {
                    delta -= 1;
                }

                repaintWindow = true;
            }

            //Physics Tick
            if (doPhysicsTick) {
                //Physics tick should resolve rigid body overlaps
                PhysicsServer.tick();
                doPhysicsTick = false;
            }

            //Render
            if (repaintWindow) {
                if (Window.isCreated) {
                    window.repaintPanel();
                }
                currentFPS = (int) ((1.0 / elapsedSecondsFrame) + 0.5); //0.5 added to account for rounding
                if (isPrintingFPS) {
                    System.out.println("Current FPS: " + currentFPS);
                }

                frameStartTime = frameEndTime;
                repaintWindow = false;
            }

            //After loop remove GameObjects that were queued for deletion
            GameObjectDeletion.deleteAll();

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
