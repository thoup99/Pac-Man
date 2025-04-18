package game.board.pellets;

import game.PacManController;
import game.entities.board.PacMan;
import game.entities.board.ghost.GhostManager;
import j2d.attributes.position.OffsetPosition2D;
import j2d.attributes.position.Position2D;
import j2d.components.sprite.AnimatedSprite;
import j2d.components.sprite.AnimationFrame;
import j2d.components.sprite.SpriteAnimation;
import j2d.engine.gameobject.GameObject;

import java.awt.*;
import java.util.Arrays;

public class PowerPellet extends Pellet {
    private final PacManController pacManController;
    private enum pelletAnimations {FLASH}
    private AnimatedSprite<pelletAnimations> sprite;

    public PowerPellet(PelletManager pelletManager, Position2D position, PacManController pacManController) {
        super(pelletManager, position, 8);
        this.pacManController = pacManController;

        loadAnimations();
        playAnimation();

        ready();
    }

    @Override
    protected void loadVisuals() {
        OffsetPosition2D spritePosition = new OffsetPosition2D(position, -8, -8);
        sprite = new AnimatedSprite<pelletAnimations>(this, spritePosition, pelletManager.getPowerPelletImage(), 1, 2);
        sprite.setPadding(1, 1);
        sprite.setSpacing(1, 1);
    }

    private void loadAnimations() {
        int flashTime = 300;
        SpriteAnimation flash = new SpriteAnimation(true, Arrays.asList(
                new AnimationFrame(0, flashTime),
                new AnimationFrame(1, flashTime)
            )
        );

        sprite.addAnimation(pelletAnimations.FLASH, flash);
    }

    private void playAnimation() {
        sprite.playAnimation(pelletAnimations.FLASH);
    }

    @Override
    public void onCollision(GameObject other) {
        if (other instanceof PacMan) {
            pacManController.powerPelletEaten();
            queueDelete();
        }
    }
}
