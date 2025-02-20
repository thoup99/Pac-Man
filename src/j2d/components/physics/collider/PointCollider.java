package j2d.components.physics.collider;

import j2d.attributes.position.Position2D;
import j2d.engine.GameObject;

public class PointCollider extends Collider {
    Position2D position;

    public PointCollider(GameObject gameObject, Position2D position) {
        super(gameObject);
        this.position = position;
    }
}
