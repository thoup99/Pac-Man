package j2d.engine.input.mouse.wheel;

import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.ArrayList;
import java.util.List;

public class MouseWheelHandler implements MouseWheelListener {
    private static List<MouseWheelSubscriber> wheelSubscribers = new ArrayList<>();
    public static MouseWheelHandler wheelHandler = new MouseWheelHandler();

    private MouseWheelHandler() {}

    public static void initialize() {

    }

    public static void subscribe(MouseWheelSubscriber subscriber) {
        if (!wheelSubscribers.contains(subscriber)) {
            wheelSubscribers.add(subscriber);
        }
    }

    public static void unsubscribe(MouseWheelSubscriber subscriber) {
        wheelSubscribers.remove(subscriber);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        int direction = e.getWheelRotation();
        int amount = e.getScrollAmount();
        for (MouseWheelSubscriber subscriber : wheelSubscribers) {
            subscriber.mouseWheelMoved(direction, amount);
        }
    }
}
