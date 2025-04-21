package game.entities.ui.text;

import j2d.attributes.position.Position2D;
import j2d.engine.gameobject.GameObject;

import java.util.ArrayList;

public class ScoreEntity extends GameObject {
    private Position2D position;
    private final int CHAR_WIDTH;
    private final int CHAR_SPACE;
    private int score = 0;
    private final int scoreLength;
    private int maxScore;
    private final ArrayList<CharEntity> charEntities = new ArrayList<>();

    /**
     * Constructor for ScoreEntity
     *
     * @param pos         Position2D to link to
     * @param scoreLength Int max length of the score
     */
    public ScoreEntity(Position2D pos, int charWidth, int spacing, int scoreLength) {
        this.position = pos;
        this.CHAR_WIDTH = charWidth;
        this.CHAR_SPACE = spacing;
        this.scoreLength = scoreLength;

        calculateMaxScore();
        createCharEntities();
    }

    /**
     * Calculates the max possible score achievable given the score length
     */
    private void calculateMaxScore() {
        maxScore = ((int) Math.pow(10, scoreLength)) - 1;
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
     * Creates a set of charEntities based off the length of the score
     */
    private void createCharEntities() {
        emptyCharEntities();
        for (int i = 0; i < scoreLength; i++) {
            CharEntity newEntity = new CharEntity(new Position2D(position.getX() + ((CHAR_WIDTH + CHAR_SPACE) * i), position.getY()));
            charEntities.add(newEntity);
        }
    }

    /**
     * Takes the current score and sets the charEntities to the
     * appropriate values. If the score is larger than the max score
     * all values are set to 9.
     */
    private void updateScoreDisplay() {
        if (score >= maxScore) {
            setAllNine();
        } else {
            String scoreString = String.format("%0" + scoreLength + "d", score);

            for (int i = 0; i < scoreLength; i++) {
                charEntities.get(i).setCharacter(scoreString.charAt(i));
            }
        }
    }

    /**
     * Sets every charEntity to 9
     */
    private void setAllNine() {
        for (CharEntity charEntity : charEntities) {
            charEntity.setCharacter('9');
        }
    }

    /**
     * Resets the score to 0 and updates the displayed score.
     */
    public void reset() {
        score = 0;
        updateScoreDisplay();
    }

    /**
     * Adds to the current Score and updates the display
     *
     * @param scoreToAdd Int Score to add to the current Score
     */
    public void addScore(int scoreToAdd) {
        score += scoreToAdd;
        updateScoreDisplay();
    }

    /**
     * Sets the score to a new value and updates the display
     *
     * @param scoreToSet Int sets the score to this value
     */
    public void setScore(int scoreToSet) {
        score = scoreToSet;
        updateScoreDisplay();
    }

    /**
     * @return Returns the current score
     */
    public int getScore() {
        return score;
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void physicsUpdate(double delta) {

    }
}
