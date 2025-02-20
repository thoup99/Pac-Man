package j2d.components.physics;

import j2d.attributes.position.Position2D;
import j2d.attributes.Rotation;
import j2d.components.Component;
import j2d.engine.GameObject;

//TODO IMPLEMENT
public class Transform extends Component {
    Position2D position;
    Rotation rotation;

    public Transform(GameObject gameObject) {
        this(gameObject, new Position2D(), new Rotation());
    }

    public Transform(GameObject gameObject, Position2D position) {
        this(gameObject, position, new Rotation());
    }

    public Transform(GameObject gameObject, Rotation rotation) {
        this(gameObject, new Position2D(), rotation);
    }

    public Transform(GameObject gameObject, Position2D position, Rotation rotation) {
        super(gameObject);
        this.position = position;
        this.rotation = rotation;
    }

    public Position2D getPosition() {
        return position;
    }

    public Rotation getRotation() {
        return rotation;
    }
}
