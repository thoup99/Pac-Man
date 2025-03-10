package j2d.engine.render;

import java.awt.*;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Map;

/**
 * Renderer.java
 * A class to allow Rendering of all objects to happen in one place.
 * This is to reduce redundancy and creation of long blocks of code
 * calling .j2d.engine.render on objects
 *
 * @author Tyler Houp
 */
public class Renderer {
    public static final int DEBUG_LAYER = -777;
    private static  Map<Integer, ArrayList<Renderable>> layers = new HashMap<>();
    private static int layerCount = 0;

    public static void main (String[] args) {
        layers.put(0, new ArrayList<>());
    }

    /**
     * Adds Object to Array of items to be rendered every frame
     * @param r Object that implements Renderable
     */
    public static void add(Renderable r) {
        add(r, 0);
    }

    public static void add(Renderable r, int layer) {
        if (!layers.containsKey(layer)) {
            boolean createdSuccessfully = createLayer(layer);
            if (!createdSuccessfully) {
                return;
            }
        }

        if (!layers.get(layer).contains(r)) {
            layers.get(layer).add(r);
        }
    }

    private static boolean doesLayerExist(int number) {
        return layers.containsKey(number);
    }

    public static boolean createLayer(int number) {
        if (!doesLayerExist(number) && number == layerCount) {
            layers.put(number, new ArrayList<>());
            layerCount++;
            System.out.println("Layer " + number + " created");
            return true;
        }
        return false;
    }

    public static void clearLayer(int number) {
        layers.get(number).clear();
    }

    public static void deleteLayer(int number) {
        if (number == layerCount - 1 && layerCount != 0) {
            layers.remove(number);
        }
        else {
            System.out.println("Layer " + number + " not deleted. Only the topmost layer can be deleted {" + (layerCount - 1) + "}");
        }
    }

    /**
     * Removes Object from being rendered every frame
     * @param r Object that implements Renderable
     */
    public static boolean remove(Renderable r, int layer) {
        return layers.get(layer).remove(r);
    }

    public static boolean remove(Renderable r) {
        for (ArrayList<Renderable> layer : layers.values()) {
            for (Renderable lR : layer) {
                if (r.equals(lR)) {
                    return layer.remove(lR);
                }
            }
        }
        return false;
    }

    public static void createDebugLayer() {
        if (!layers.containsKey(DEBUG_LAYER)) {
            layers.put(DEBUG_LAYER, new ArrayList<>());
        }
    }

    public static int getTopLayer() {
        if (layerCount == 0) {
            return 0;
        }
        return layerCount - 1;
    }

    public static int getTotalObjects() {
        int count = getTotalNonDebugObjects();
        count += layers.get(DEBUG_LAYER).size();
        return count;
    }

    public static int getTotalNonDebugObjects() {
        int count = 0;
        for (int i = 0; i < layerCount; i++) {
            count += layers.get(i).size();
        }
        return count;
    }

    /**
     * Calls "j2d.engine.render" on all Objects added to Renderer
     * @param g2 Graphics2D Object passed to all added Objects
     */
    public static void renderAll(Graphics2D g2) {
        if (g2 == null) {
            return;
        }

        for (int i = 0; i < layerCount; i++) {
            final ArrayList<Renderable> layer = (ArrayList<Renderable>) layers.get(i).clone();
            for (Renderable r : layer) {
                r.render(g2);
            }
        }

        for (Renderable r : layers.get(DEBUG_LAYER)) {
            r.render(g2);
        }
    }
}
