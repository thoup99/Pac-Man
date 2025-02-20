package j2d.engine.render;

import java.awt.*;

/**
 * Renderable.java
 * An Interface to give a Class the ability to be Rendered
 * every frame.
 *
 * @author Tyler Houp
 */
public interface Renderable {
    void addToRenderer();
    void removeFromRenderer();
    void render(Graphics2D g2);
}
