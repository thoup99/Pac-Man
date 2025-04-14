package j2d.components.sprite;

public class AnimationFrame {
    public int spriteNumber;
    public long durationMilliseconds;

    public AnimationFrame(int spriteNumber, long durationMilliseconds) {
        this.spriteNumber = spriteNumber;
        this.durationMilliseconds = durationMilliseconds;
    }
}
