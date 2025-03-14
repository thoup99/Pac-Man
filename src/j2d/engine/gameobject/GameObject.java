package j2d.engine.gameobject;

import j2d.components.Component;
import j2d.components.physics.collider.Collider;
import j2d.engine.input.keyboard.KeyHandler;
import j2d.engine.input.keyboard.KeySubscriber;
import j2d.engine.input.mouse.button.MouseButtonHandler;
import j2d.engine.input.mouse.button.MouseButtonSubscriber;
import j2d.engine.input.mouse.motion.MouseMotionHandler;
import j2d.engine.input.mouse.motion.MouseMotionSubscriber;
import j2d.engine.input.mouse.wheel.MouseWheelHandler;
import j2d.engine.input.mouse.wheel.MouseWheelSubscriber;
import j2d.engine.updates.gametick.GameTick;
import j2d.engine.updates.physics.CollisionServer;
import j2d.engine.updates.physics.PhysicsServer;

import java.util.ArrayList;
import java.util.List;

public abstract class GameObject {
    public List<Component> components = new ArrayList<>();

    public GameObject() {

    }

    protected void ready() {
        GameTick.registerGameObject(this);
        PhysicsServer.registerGameObject(this);

        for (Component c : components) {
            if (c instanceof Collider) {
                Collider collider = (Collider) c;
                CollisionServer.registerCollider(collider);
            }
        }
    }

    public void queueDelete() {
        GameObjectDeletion.queueGameObject(this);
    }

    /**
     *  Removes all references to the GameObject making it
     *  eligible for garbage collection
     */
    void delete() {
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

        //Copy the List of components since .delete removes it from original List
        List<Component> componentsToRemove = new ArrayList<>(components);
        for (Component c : componentsToRemove) {
            c.delete();
        }

    }

    public abstract void update(double delta);
    public abstract void physicsUpdate(double delta);
    public void onCollision(GameObject other) {};
}
