package j2d.engine.updates.gametick;

import j2d.engine.gameobject.GameObject;

import java.util.ArrayList;
import java.util.List;

public class GameTick {
    private static final List<GameObject> gameObjects = new ArrayList<>();

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

    public static void doUpdates(double delta) {
        synchronized (gameObjects) {
            for (GameObject gameObject : gameObjects) {
                gameObject.update(delta);
            }
        }
    }
}
