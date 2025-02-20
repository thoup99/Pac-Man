package j2d.engine;

import j2d.components.Component;
import j2d.components.physics.RigidBody;
import j2d.components.sprite.Sprite;
import j2d.engine.input.keyboard.KeyHandler;
import j2d.engine.input.keyboard.KeySubscriber;
import j2d.engine.input.mouse.button.MouseButtonHandler;
import j2d.engine.input.mouse.button.MouseButtonSubscriber;
import j2d.engine.input.mouse.motion.MouseMotionHandler;
import j2d.engine.input.mouse.motion.MouseMotionSubscriber;
import j2d.engine.input.mouse.wheel.MouseWheelHandler;
import j2d.engine.input.mouse.wheel.MouseWheelSubscriber;

import java.util.ArrayList;
import java.util.List;

public abstract class GameObject {
    public List<Component> components = new ArrayList<Component>();

    public GameObject() {
        Engine.registerGameObject(this);
    }

    /**
     *  Removes all references to the GameObject making it
     *  eligible for garbage collection
     */
    protected void delete() {
        Engine.unregisterGameObject(this);

        if (this instanceof KeySubscriber) {
            KeySubscriber ks = (KeySubscriber) this;
            KeyHandler.unsubscribe(ks);
        }
        if (this instanceof MouseButtonSubscriber) {
            MouseButtonSubscriber mbs = (MouseButtonSubscriber) this;
            MouseButtonHandler.unsubscribe(mbs);
        }
        if (this instanceof MouseMotionSubscriber) {
            MouseMotionSubscriber mms = (MouseMotionSubscriber) this;
            MouseMotionHandler.unsubscribe(mms);
        }
        if (this instanceof MouseWheelSubscriber) {
            MouseWheelSubscriber mws = (MouseWheelSubscriber) this;
            MouseWheelHandler.unsubscribe(mws);
        }

        for (Component c : components) {
            if (c instanceof Sprite) {
                Sprite s = (Sprite) c;
                s.removeFromRenderer();
            }
            else if (c instanceof RigidBody) {
                //TODO Remove Physics body from required places
            }
        }

    }

    public abstract void update(double delta);
    public abstract void physics_update(double delta);
}
