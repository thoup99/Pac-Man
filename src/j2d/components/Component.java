package j2d.components;

import j2d.engine.GameObject;

public abstract class Component {

    public Component(GameObject parentGameObject) {
        parentGameObject.components.add(this);
    }
}
