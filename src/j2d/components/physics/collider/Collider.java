package j2d.components.physics.collider;

import j2d.engine.GameObject;

public abstract class Collider {
    private GameObject gameObject;

    public Collider(GameObject gameObject) {
        this.gameObject = gameObject;
    }
}
