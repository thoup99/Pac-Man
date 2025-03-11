package j2d.engine;

import j2d.components.Component;
import j2d.components.graphics.shapes.Shape;
import j2d.components.graphics.text.Text;
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
import j2d.engine.updates.gametick.GameTick;
import j2d.engine.updates.physics.PhysicsServer;

import java.util.ArrayList;
import java.util.List;

public abstract class GameObject {
    public List<Component> components = new ArrayList<Component>();

    public GameObject() {

    }

    protected void ready() {
        GameTick.registerGameObject(this);
        PhysicsServer.registerGameObject(this);
    }

    /**
     *  Removes all references to the GameObject making it
     *  eligible for garbage collection
     */
    protected void delete() {
        GameTick.unregisterGameObject(this);
        PhysicsServer.unregisterGameObject(this);

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
                RigidBody rigidBody = (RigidBody) c;
                PhysicsServer.unregisterRigidBody(rigidBody);
            } else if (c instanceof Shape) {
                Shape shape = (Shape) c;
                shape.removeFromRenderer();
            } else if (c instanceof Text) {
                Text text = (Text) c;
                text.removeFromRenderer();
            }

            c.delete();
        }

    }

    public abstract void update(double delta);
    public abstract void physicsUpdate(double delta);
}
