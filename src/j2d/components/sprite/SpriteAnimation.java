package j2d.components.sprite;

import java.util.List;

public class SpriteAnimation {
    private final List<AnimationFrame> frames;
    private final boolean looping;
    private final Runnable onFinish;

    public SpriteAnimation(List<AnimationFrame> frames) {
        this(false, null, frames);
    }

    public SpriteAnimation(boolean looping, List<AnimationFrame> frames) {
        this(looping, null, frames);
    }

    public SpriteAnimation(Runnable onFinish, List<AnimationFrame> frames) {
        this(false, onFinish, frames);
    }

    public SpriteAnimation(boolean looping, Runnable onFinish, List<AnimationFrame> frames) {
        this.frames = frames;
        this.looping = looping;
        this.onFinish = onFinish;
    }

    public List<AnimationFrame> getFrames() { return frames; }
    public int size() { return frames.size(); }
    public boolean isLooping() { return looping; }
    public Runnable getOnFinish() { return onFinish; }
}
