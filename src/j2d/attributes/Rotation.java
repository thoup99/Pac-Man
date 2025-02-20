package j2d.attributes;

//TODO IMPLEMENT
public class Rotation {
    double angleDegrees;

    public Rotation() {
        this(0);
    }

    public Rotation(double angleDegrees) {
        this.angleDegrees = angleDegrees;
    }

    public double getAngleDegrees() {
        return angleDegrees;
    }

    public void setAngleDegrees(double angleDegrees) {
        this.angleDegrees = angleDegrees;
    }

    public double getAngleRadians() {
        return Math.toRadians(angleDegrees);
    }

    public void setAngleRadians(double angleRadians) {
        this.angleDegrees = Math.toDegrees(angleRadians);
    }
}
