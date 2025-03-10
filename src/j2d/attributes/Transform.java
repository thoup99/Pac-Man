package j2d.attributes;

import j2d.attributes.position.Position2D;

//TODO IMPLEMENT
public class Transform {
    final Position2D position;
    final Rotation rotation;

    public Transform(Position2D position) {
        this(position, new Rotation());
    }

    public Transform(Rotation rotation) {
        this(new Position2D(), rotation);
    }

    public Transform(Position2D position, Rotation rotation) {
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
