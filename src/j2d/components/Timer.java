package j2d.components;

import j2d.engine.gameobject.GameObject;
import j2d.engine.updates.MasterTimer;

public class Timer extends Component{
    boolean isTicking = false;
    boolean oneShot = false;
    int durationNanoseconds;
    double timeRemainingNanoseconds;
    Runnable callback;

    public Timer(GameObject parentGameObject, int durationMilliseconds, Runnable callback) {
        super(parentGameObject);
        this.durationNanoseconds = milliToNano(durationMilliseconds);
        timeRemainingNanoseconds = this.durationNanoseconds;
        System.out.println(this.durationNanoseconds);
        this.callback = callback;
        MasterTimer.registerTimer(this);
    }

    public void setOneShot(boolean oneShot) {
        this.oneShot = oneShot;
    }

    public void tick(double deltaNanoseconds) {
        if (isTicking) {
            timeRemainingNanoseconds -= deltaNanoseconds;
            if (timeRemainingNanoseconds <= 0) {
                callback.run();
                if (oneShot) {
                    stop();
                } else {
                    start();
                }
            }
        }
    }

    public void start() {
        isTicking = true;
        if (timeRemainingNanoseconds <= 0) {
            timeRemainingNanoseconds = durationNanoseconds;
        }
    }

    public void stop() {
        isTicking = false;
        timeRemainingNanoseconds = durationNanoseconds;
    }

    public void pause() {
        isTicking = false;
    }

    public double timeRemaining() {
        return nanoToMilli(timeRemainingNanoseconds);
    }

    private int milliToNano(int milliseconds) {
        return milliseconds * 1_000_000;
    }

    private double nanoToMilli(double nanoSeconds) {
        return nanoSeconds / 1_000_000;
    }

    @Override
    public void delete() {
        MasterTimer.unregisterTimer(this);
    }
}
