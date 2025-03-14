package j2d.components.physics.collider;

import j2d.attributes.position.Position2D;
import j2d.engine.gameobject.GameObject;

public class CircleCollider extends Collider {
    private final Position2D centerPosition;
    private final double radius;

    public CircleCollider(GameObject parentGameObject, Position2D position, double radius) {
        super(parentGameObject);
        this.centerPosition = position;
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public Position2D getPosition() {
        return centerPosition;
    }
}
