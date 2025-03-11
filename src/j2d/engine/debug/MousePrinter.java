package j2d.engine.debug;

import j2d.engine.GameObject;
import j2d.engine.input.mouse.button.MouseButtonHandler;
import j2d.engine.window.Window;

import java.awt.event.MouseEvent;

public class MousePrinter extends GameObject {

    MousePrinter() {
        ready();
    }

    @Override
    public void update(double delta) {
        if (Debug.printMouseButtons){
            System.out.println("Left Click: " + MouseButtonHandler.isButtonPressed(MouseEvent.BUTTON1));
            System.out.println("Middle Click: " + MouseButtonHandler.isButtonPressed(MouseEvent.BUTTON2));
            System.out.println("Right Click: " + MouseButtonHandler.isButtonPressed(MouseEvent.BUTTON3));
        }
        if (Debug.printMousePosition && Window.isCreated) {
            System.out.println(Window.getMousePosition());
        }
    }

    @Override
    public void physicsUpdate(double delta) {

    }
}
