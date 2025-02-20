package j2d.attributes.position;

public class OffsetPosition2D extends Position2D {
    double x_offset, y_offset;
    Position2D basePosition;
    double x_off_pos, y_off_pos;

    public OffsetPosition2D(Position2D basePosition, double x_offset, double y_offset) {
        super();
        this.basePosition = basePosition;
        setXOffset(x_offset);
        setYOffset(y_offset);
    }

    public void setXOffset(double x_offset) {
        this.x_offset = x_offset;
        x_off_pos = basePosition.getX() + x_offset;
    }

    public void setYOffset(double y_offset) {
        this.y_offset = y_offset;
        y_off_pos = basePosition.getY() + y_offset;
    }



    public void setBasePosition(Position2D pos) {
        this.basePosition = pos;
        recalculateOffsetPosition();
    }

    public void setBaseKeepPosition(Position2D pos) {
        x_offset = x_off_pos - pos.getX();
        y_offset = y_off_pos - pos.getY();

        this.basePosition = pos;
        recalculateOffsetPosition();
    }

    private void recalculateOffsetPosition() {
        x_off_pos = basePosition.getX() + x_offset;
        y_off_pos = basePosition.getY() + y_offset;
    }

    public double getXOffset() {
        return x_offset;
    }

    public double getYOffset() {
        return y_offset;
    }

    @Override
    public double getX() {
        return x_off_pos;
    }

    @Override
    public double getY() {
        return y_off_pos;
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
    public String toString() {
        return "(" + getX() + ", " + getY() + ")";
    }
}
