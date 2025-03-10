package j2d.attributes.position;


import java.awt.*;

/**
 * Position2D.java
 * A wrapper class using two ints to represent x and y positions
 * can be used to have objects share a common position.
 *
 * @author Tyler Houp
 */
public class Position2D {
    protected double x;
    protected double y;

    /**
     * Constructor for Position2D
     * @param x X position
     * @param y Y position
     */
    public Position2D(double x, double y) {
        setPosition(x, y);
    }

    /**
     * Constructor for Position2D
     * Sets position at (0,0)
     */
    public Position2D() {
        setPosition(0, 0);
    }

    public Position2D(Point p) {
        if (p != null) {
            setPosition(p.x, p.y);
        } else {
            setPosition(-1, -1);
        }
    }

    /**
     * Sets the x and y j2d.components of the Position2D
     * @param x X position
     * @param y Y position
     */
    public void setPosition(double x , double y) {
        this.x = x;
        this.y = y;
    }

    public void setPosition(Position2D newPosition) {
        setPosition(newPosition.x, newPosition.y);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getIntX() {
        return (int) (x + 0.5); // + 0.5 for rounding
    }

    public int getIntY() {
        return (int) (y + 0.5);
    }

    public Position2D copy() {
        return new Position2D(x, y);
    }

    public String toString() {
        return String.format("(%.2f, %.2f)", x, y);
    }

}
