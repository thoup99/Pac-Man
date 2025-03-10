package j2d.engine.updates.physics;

import j2d.components.physics.RigidBody;
import j2d.engine.GameObject;

import java.util.ArrayList;
import java.util.List;

public class PhysicsServer {
    private static final List<GameObject> gameObjects = new ArrayList<>();
    private static final List<RigidBody> rigidBodies = new ArrayList<RigidBody>();
    public static double timeStep = 0.02;
    public static double currentStepRate;

    //TODO impliment collisions. Can use GameObject Base Class

    public static void registerGameObject(GameObject gameObject) {
        if (!gameObjects.contains(gameObject)) {
            synchronized (gameObjects) {
                gameObjects.add(gameObject);
            }
        }
    }

    public static void unregisterGameObject(GameObject gameObject) {
        synchronized (gameObjects) {
            gameObjects.remove(gameObject);
        }
    }

    public static void registerRigidBody(RigidBody gameObject) {
        if (!rigidBodies.contains(gameObject)) {
            synchronized (rigidBodies) {
                rigidBodies.add(gameObject);
            }
        }
    }

    public static void unregisterRigidBody(RigidBody gameObject) {
        synchronized (rigidBodies) {
            rigidBodies.remove(gameObject);
        }
    }

    public static void doPhysicsUpdates(double delta) {
        synchronized (gameObjects) {
            for (GameObject gameObject : gameObjects) {
                gameObject.physicsUpdate(delta);
            }
        }
    }

    public static void tick() {

    }
}


