package j2d.components.physics;

import j2d.components.Component;
import j2d.engine.GameObject;

public class RigidBody extends Component {
    Transform transform;

    public RigidBody(GameObject parentGameObject, Transform transform) {
        super(parentGameObject);
        this.transform = transform;
    }
}
