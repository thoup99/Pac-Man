package j2d.engine.audio;

public abstract class AudioClip {

    public abstract void loadResource(String rscPath);
    public abstract void loadAbsolute(String absPath);

    public abstract void play();
    public abstract void play(float start);
    public abstract float pause();
    public abstract void stop();
}
