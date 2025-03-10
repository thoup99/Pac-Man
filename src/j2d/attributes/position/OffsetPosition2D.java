package j2d.attributes.position;

public class OffsetPosition2D extends Position2D {
    double x_offset, y_offset;
    Position2D basePosition;

    public OffsetPosition2D(Position2D basePosition, double x_offset, double y_offset) {
        super();
        this.basePosition = basePosition;
        setXOffset(x_offset);
        setYOffset(y_offset);
    }

    public void setXOffset(double x_offset) {
        this.x_offset = x_offset;
    }

    public void setYOffset(double y_offset) {
        this.y_offset = y_offset;
    }

    public void setBasePosition(Position2D pos) {
        this.basePosition = pos;
    }

    public void setBaseKeepPosition(Position2D pos) {
        //TODO Reimplement
    }

    public double getXOffset() {
        return x_offset;
    }

    public double getYOffset() {
        return y_offset;
    }

    @Override
    public double getX() {
        return basePosition.getX() + x_offset;
    }

    @Override
    public double getY() {
        return basePosition.getY() + y_offset;
    }

    @Override
    public int getIntX() {
        return (int) getX();
    }

    @Override
    public int getIntY() {
        return (int) getY();
    }

    @Override
    public Position2D copy() {
        return new OffsetPosition2D(basePosition, x_offset, y_offset);
    }

    @Override
    public String toString() {
        return "(" + getX() + ", " + getY() + ")";
    }
}
