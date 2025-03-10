package j2d.attributes;

import j2d.attributes.position.Position2D;

public class Vector2D {
    private static double thresh = 0.000001;
    private double x, y;

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getMagnitudeSquared() {
        return Math.pow(x, 2) + Math.pow(y, 2);
    }

    public double getMagnitude() {
        return Math.sqrt(getMagnitudeSquared());
    }

    public Vector2D copy() {
        return new Vector2D(x, y);
    }

    public Vector2D add(Vector2D v) {
        return new Vector2D(x + v.x, y + v.y);
    }

    public Vector2D subtract(Vector2D v) {
        return new Vector2D(x - v.x, y - v.y);
    }

    public Vector2D multiply(double scalar) {
        return new Vector2D(x * scalar, y * scalar);
    }

    public Vector2D divide(double scalar) {
        return new Vector2D(x / scalar, y / scalar);
    }

    public double dot(Vector2D v) {
        return x * v.x + y * v.y;
    }

    public Vector2D cross(Vector2D v) {
        return new Vector2D(y * v.y - x * v.x,
                x * v.y - y);
    }

    public boolean isEqual(Vector2D other) {
        return x == other.x && y == other.y;
    }

    public boolean isEqualApprox(Vector2D other) {
        return Math.abs(x - other.x) < thresh && Math.abs(y - other.y) < thresh;
    }

    public boolean isOpposite(Vector2D other) {
        return x == -other.x && y == -other.y;
    }

    public boolean isOppositeApprox(Vector2D other) {
        return isEqualApprox(new Vector2D(-other.x, -other.y));
    }

    public Vector2D flip() {
        return new Vector2D(-x, -y);
    }

    public Vector2D normalize() {
        double magnitude = getMagnitude();
        double newX = x / magnitude;
        double newY = y / magnitude;
        return new Vector2D(newX, newY);
    }

    public static Vector2D getNormal(Position2D position1, Position2D position2) {
        return new Vector2D(position1.getX() - position2.getX(), position1.getY() - position2.getY());
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getIntX() {
        return (int) x;
    }

    public double getIntY() {
        return (int) y;
    }

    public String toString() {
        return String.format("<%.2f, %.2f>", x, y);
    }
}
