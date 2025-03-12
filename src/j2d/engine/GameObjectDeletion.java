package j2d.engine;

import java.util.ArrayList;
import java.util.List;

public class GameObjectDeletion {
    private static final List<GameObject> objects = new ArrayList<>();

    public static void queueGameObject(GameObject object) {
        if (!objects.contains(object)) {
            objects.add(object);
        }
    }

    public static void deleteAll() {
        for (GameObject gameObject : objects) {
            gameObject.delete();
        }
        objects.clear();
    }
}
