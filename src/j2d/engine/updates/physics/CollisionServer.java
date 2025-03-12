package j2d.engine.updates.physics;

import j2d.components.physics.collider.BoxCollider;
import j2d.components.physics.collider.CircleCollider;
import j2d.components.physics.collider.Collider;

import java.util.ArrayList;
import java.util.List;

public class CollisionServer {
    private static final List<Collider> colliders = new ArrayList<>();

    public static void registerCollider(Collider collider) {
        if (!colliders.contains(collider)) {
            synchronized (colliders) {
                colliders.add(collider);
            }
        }
    }

    public static void unregisterCollider(Collider collider) {
        synchronized (colliders) {
            colliders.remove(collider);
        }
    }

    public static void checkCollisions() {
        synchronized (colliders) {
            //For every Collider...
            for (int i = 0; i < colliders.size(); i++) {
                //... check every collider that follows
                for (int j = i + 1; j < colliders.size(); j++) {
                    Collider collider1 = colliders.get(i);
                    Collider collider2 = colliders.get(j);

                    if (isColliding(collider1, collider2)) {
                        collider1.getParent().onCollision(collider2.getParent());
                        collider2.getParent().onCollision(collider1.getParent());
                    }
                }
            }
        }
    }

    private static boolean isColliding(Collider collider1, Collider collider2) {
        //Determine Collision Type
        if ((collider1 instanceof BoxCollider) && (collider2 instanceof BoxCollider)) {
            return boxOnBox((BoxCollider) collider1, (BoxCollider) collider2);

        } else if ((collider1 instanceof CircleCollider) && (collider2 instanceof CircleCollider)) {
            return circleOnCircle((CircleCollider) collider1, (CircleCollider) collider2);

        } else if ((collider1 instanceof BoxCollider) && (collider2 instanceof CircleCollider)) {
            return circleOnBox((CircleCollider) collider2, (BoxCollider) collider1);

        } else if ((collider1 instanceof CircleCollider) && (collider2 instanceof BoxCollider)) {
            return circleOnBox((CircleCollider) collider1, (BoxCollider) collider2);
        }

        return false;
    }

    private static boolean circleOnCircle(CircleCollider circle1, CircleCollider circle2) {
        //Use squared length values to avoid using sqrt operation
        double radiusSquared = Math.pow(circle1.getRadius() + circle2.getRadius(), 2);
        double distanceSquared = circle1.getPosition().distance(circle2.getPosition()).getMagnitudeSquared();
        return distanceSquared <= radiusSquared;
    }

    private static boolean circleOnBox(CircleCollider circleCollider, BoxCollider boxCollider) {
        //Separating Axis Theorem
        return false;
    }

    private static boolean boxOnBox(BoxCollider box1, BoxCollider box2) {
        //Separating Axis Theorem
        return false;
    }
}
