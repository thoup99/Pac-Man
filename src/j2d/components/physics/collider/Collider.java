package j2d.components.physics.collider;

import j2d.components.Component;
import j2d.engine.GameObject;
import j2d.engine.updates.physics.CollisionServer;

public abstract class Collider extends Component {
    public Collider(GameObject parentGameObject) {
        super(parentGameObject);
    }

    @Override
    public void delete() {
        CollisionServer.unregisterCollider(this);
    }
}
