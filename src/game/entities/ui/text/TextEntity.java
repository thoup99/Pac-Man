package game.entities.ui.text;

import j2d.attributes.position.Position2D;
import j2d.engine.gameobject.GameObject;

import java.util.ArrayList;

public class TextEntity extends GameObject {
    private String text;
    Position2D position;
    private final ArrayList<CharEntity> charEntities =  new ArrayList<>();

    /**
     * Constructor for TextEntity
     * @param pos Position2D to be linked to
     * @param text String of text to display
     */
    public TextEntity(Position2D pos, String text) {
        this.text = text;
        this.position = pos;
        createCharEntities();
    }

    /**
     * Clears out array of charEntities and destroys all entities inside
     */
    private void emptyCharEntities() {
        for (CharEntity charEntity : charEntities) {
            charEntity.queueDelete();
        }
        charEntities.clear();
    }

    /**
     * Creates a set of charEntities based off the current text
     */
    private void createCharEntities() {
        emptyCharEntities();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            if (c == ' ') {
                continue;
            }

            CharEntity newEntity = new CharEntity(new Position2D(position.getX() + ((28 + 4) * i), position.getY()), c);
            charEntities.add(newEntity);
        }
    }

    /**
     * Shows Text
     */
    public void show() {
        for (CharEntity charEntity : charEntities) {
            charEntity.show();
        }
    }

    /**
     * Hides Text
     */
    public void hide() {
        for (CharEntity charEntity : charEntities) {
            charEntity.hide();
        }
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void physicsUpdate(double delta) {

    }
}
