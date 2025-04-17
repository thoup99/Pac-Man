package j2d.components;

import j2d.engine.gameobject.GameObject;
import j2d.engine.updates.MasterTimer;

public class Timer extends Component{
    boolean isTimeout = true;
    boolean isTicking = false;
    boolean oneShot = false;
    long durationNanoseconds;
    double timeRemainingNanoseconds;
    Runnable callback;

    public Timer(GameObject parentGameObject, long durationMilliseconds, Runnable callback) {
        super(parentGameObject);
        this.durationNanoseconds = milliToNano(durationMilliseconds);
        timeRemainingNanoseconds = this.durationNanoseconds;
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
                isTimeout = true;
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
        isTimeout = false;
        if (timeRemainingNanoseconds <= 0) {
            timeRemainingNanoseconds = durationNanoseconds;
        }
    }

    public void stop() {
        isTicking = false;
        timeRemainingNanoseconds = durationNanoseconds;
    }

    public void resume() {
        isTicking = true;
    }

    public void restart() {
        timeRemainingNanoseconds = durationNanoseconds;
    }

    public void pause() {
        isTicking = false;
    }

    public void addTime(int milliseconds) {
        long nanoTimeToAdd = milliToNano(milliseconds);
        timeRemainingNanoseconds += nanoTimeToAdd;
    }

    public double timeRemaining() {
        return nanoToMilli(timeRemainingNanoseconds);
    }

    private long milliToNano(long milliseconds) {
        return milliseconds * 1_000_000;
    }

    private double nanoToMilli(double nanoSeconds) {
        return nanoSeconds / 1_000_000;
    }

    public void setDurationMilliseconds(long durationMilliseconds) {
        this.durationNanoseconds = milliToNano(durationMilliseconds);
    }

    public boolean isTimedOut() {
        return isTimeout;
    }

    @Override
    public void delete() {
        MasterTimer.unregisterTimer(this);
    }
}
