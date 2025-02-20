package j2d.engine.input.mouse.button;

import j2d.attributes.position.Position2D;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MouseButtonHandler implements MouseListener {
    private static Map<Integer, Boolean> buttonMap = new HashMap<Integer, Boolean>();
    private static Map<Integer, ArrayList<MouseButtonSubscriber>> buttonSubscribers = new HashMap<Integer, ArrayList<MouseButtonSubscriber>>();
    public static MouseButtonHandler mouseHandler = new MouseButtonHandler();

    private MouseButtonHandler() {}

    public static void initialize() {
        addButton(MouseEvent.BUTTON1);
        addButton(MouseEvent.BUTTON2);
        addButton(MouseEvent.BUTTON3);
    }

    private static void addButton(int awtMouseEventButton) {
        buttonMap.put(awtMouseEventButton, false);
        buttonSubscribers.put(awtMouseEventButton, new ArrayList<MouseButtonSubscriber>());
    }

    public static void subscribe(MouseButtonSubscriber subscriber, int[] subscribedButtons) {
        for (int button : subscribedButtons) {
            if (!buttonSubscribers.get(button).contains(subscriber)) {
                buttonSubscribers.get(button).add(subscriber);
            }
        }
    }

    public static void unsubscribe(MouseButtonSubscriber subscriber) {
        for (int button: buttonSubscribers.keySet()) {
            buttonSubscribers.get(button).remove(subscriber);
        }
    }

    public static boolean isButtonPressed(int awtMouseEventButton) {
        return buttonMap.get(awtMouseEventButton);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int code = e.getButton();
        if (buttonMap.containsKey(code)) {

            Position2D mousePosition = new Position2D(e.getX(), e.getY());
            for (MouseButtonSubscriber subscriber : buttonSubscribers.get(code)) {
                subscriber.mouseClicked(code, mousePosition);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int code = e.getButton();
        if (buttonMap.containsKey(code)) {
            buttonMap.put(code, true);

            Position2D mousePosition = new Position2D(e.getX(), e.getY());
            for (MouseButtonSubscriber subscriber : buttonSubscribers.get(code)) {
                subscriber.mousePressed(code, mousePosition);
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        int code = e.getButton();
        if (buttonMap.containsKey(code)) {
            buttonMap.put(code, false);

            Position2D mousePosition = new Position2D(e.getX(), e.getY());
            for (MouseButtonSubscriber subscriber : buttonSubscribers.get(code)) {
                subscriber.mouseReleased(code, mousePosition);
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
