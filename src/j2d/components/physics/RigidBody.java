package j2d.components.physics;

import j2d.attributes.Transform;
import j2d.components.Component;
import j2d.engine.GameObject;
import j2d.engine.updates.physics.PhysicsServer;

public class RigidBody extends Component {
    final Transform transform;

    public RigidBody(GameObject parentGameObject, Transform transform) {
        super(parentGameObject);
        this.transform = transform;

        PhysicsServer.registerRigidBody(this);
    }

    @Override
    public void delete() {
        super.delete();
        PhysicsServer.unregisterRigidBody(this);
    }
}
