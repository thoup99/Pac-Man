package j2d.attributes;

import j2d.attributes.position.Position2D;

public class Vector2D {
    private double x, y;
    private double magnitude;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
        magnitude = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public void normalize() {
        this.x /= magnitude;
        this.y /= magnitude;
        magnitude = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public Vector2D add(Vector2D v) {
        return new Vector2D(x + v.x, y + v.y);
    }

    public Vector2D sub(Vector2D v) {
        return new Vector2D(x - v.x, y - v.y);
    }

    public double dot(Vector2D v) {
        return x * v.x + y * v.y;
    }

    public Vector2D cross(Vector2D v) {
        return new Vector2D(y * v.y - x * v.x,
                x * v.y - y);
    }

    public Vector2D getNormal(Position2D position1, Position2D position2) {
        return new Vector2D(position1.getX() - position2.getX(), position1.getY() - position2.getY());
    }

}
