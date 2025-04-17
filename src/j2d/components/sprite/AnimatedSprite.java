package j2d.components.sprite;

import j2d.attributes.position.Position2D;
import j2d.components.Timer;
import j2d.engine.gameobject.GameObject;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnimatedSprite<T> extends SpriteSheet {
    private Map<T, SpriteAnimation> animationMap = new HashMap<>();
    SpriteAnimation currentAnimation;
    AnimationFrame currentAnimationFrame = null;
    int currentFrameIndex = -1;
    int currentAnimationSize = -1;

    Timer animationTimer;

    boolean looping = false;
    Runnable onAnimationFinished;

    /**
     * Constructor for SpriteSheet Class
     *
     * @param parent
     * @param pos         Position2D SpriteSheet will be linked to
     * @param loadedSheet BufferedImage that is the sprite sheet image
     * @param numRows     Number of rows in the sprite sheet
     * @param numCols     Number of columns in the sprite sheet
     */
    public AnimatedSprite(GameObject parent, Position2D pos, BufferedImage loadedSheet, int numRows, int numCols) {
        super(parent, pos, loadedSheet, numRows, numCols);
        animationTimer = new Timer(parent, 0, this::advanceAnimation);
    }

    public void addAnimation(T identifier, SpriteAnimation animation) {
        animationMap.put(identifier, animation);
    }

    public void playAnimation(T key) {
        //Load Data
        currentAnimation = animationMap.get(key);
        currentAnimationSize = currentAnimation.size();
        looping = currentAnimation.isLooping();
        setOnAnimationFinished(currentAnimation.getOnFinish());

        //Reset Internal Variables
        restartAnimation();
    }

    public void pauseAnimation() {
        animationTimer.pause();
    }

    public void resumeAnimation() {
        animationTimer.resume();
    }

    public void stopAnimation() { animationTimer.stop(); }

    public void restartAnimation() {
        currentFrameIndex = 0;
        currentAnimationFrame = currentAnimation.getFrames().get(currentFrameIndex);
        setCurrentFrame();
        animationTimer.start();
    }

    public void advanceAnimation() {
        currentFrameIndex++;
        //If animation is not over
        if (currentFrameIndex < currentAnimationSize) {
            currentAnimationFrame = currentAnimation.getFrames().get(currentFrameIndex);
            setCurrentFrame();
            animationTimer.start();

        } else { //Animation is over
            if (onAnimationFinished != null) {
                onAnimationFinished.run();
            }

            if (looping) {
                restartAnimation();
            } else {
                animationTimer.stop();
            }
        }
    }

    private void setCurrentFrame() {
        animationTimer.setDurationMilliseconds(currentAnimationFrame.durationMilliseconds);
        double overshoot = animationTimer.timeRemaining();
        animationTimer.restart();
        animationTimer.addTime((int) overshoot);
        setSprite(currentAnimationFrame.spriteNumber);
    }

    public void setLooping(boolean looping) {
        this.looping = looping;
    }

    public double getRemainingTime() {
        return animationTimer.timeRemaining();
    }

    public void setOnAnimationFinished(Runnable onAnimationFinished) {
        this.onAnimationFinished = onAnimationFinished;
    }
}
