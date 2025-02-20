package j2d.engine.input.mouse.motion;

import j2d.attributes.position.Position2D;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

public class MouseMotionHandler implements MouseMotionListener {
    private static List<MouseMotionSubscriber> motionSubscribers = new ArrayList<>();
    public static MouseMotionHandler movementHandler = new MouseMotionHandler();

    private MouseMotionHandler() {}

    public static void initialize() {

    }

    public static void subscribe(MouseMotionSubscriber subscriber) {
        if (!motionSubscribers.contains(subscriber)) {
            motionSubscribers.add(subscriber);
        }
    }

    public static void unsubscribe(MouseMotionSubscriber subscriber) {
        motionSubscribers.remove(subscriber);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        Position2D mousePosition = new Position2D(e.getX(), e.getY());
        for (MouseMotionSubscriber subscriber : motionSubscribers) {
            subscriber.mouseDragged(mousePosition);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Position2D mousePosition = new Position2D(e.getX(), e.getY());
        for (MouseMotionSubscriber subscriber : motionSubscribers) {
            subscriber.mouseMoved(mousePosition);
        }
    }
}
