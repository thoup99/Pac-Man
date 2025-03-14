package j2d.engine.updates;

import j2d.components.Timer;

import java.util.ArrayList;
import java.util.List;

public class MasterTimer {
    final static List<Timer> timers = new ArrayList<>();

    private MasterTimer() {}

    public static void registerTimer(Timer timer) {
        if (!timers.contains(timer)) {
            synchronized (timers) {
                timers.add(timer);
            }
        }
    }

    public static void unregisterTimer(Timer timer) {
        synchronized (timers) {
            timers.remove(timer);
        }
    }

    public static void tickAll(double deltaNanoSeconds) {
        synchronized (timers) {
            for (Timer timer : timers) {
                timer.tick(deltaNanoSeconds);
            }
        }
    }
}
