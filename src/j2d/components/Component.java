package j2d.components;

import j2d.engine.GameObject;

public abstract class Component {
    protected GameObject parentObject;

    public Component(GameObject parentGameObject) {
        parentGameObject.components.add(this);
        this.parentObject = parentGameObject;
    }

    public void delete() {
        parentObject.components.remove(this);
    }

    public GameObject getParent() {
        return parentObject;
    }
}
