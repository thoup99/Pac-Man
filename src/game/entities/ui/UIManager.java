package game.entities.ui;

import game.Constants;
import game.entities.ui.text.ScoreEntity;
import j2d.attributes.position.Position2D;

public class UIManager {
    ScoreEntity scoreDisplay;
    ScoreEntity highScoreDisplay;
    ScoreEntity lifeUpDisplay;
    ScoreEntity playCountDisplay;
    LifeDisplay lifeDisplay;
    FruitDisplay fruitDisplay;

    public UIManager() {
        scoreDisplay = new ScoreEntity(new Position2D(16, 16), 16, 0, 6);
        highScoreDisplay = new ScoreEntity(new Position2D(176, 16), 16, 0, 6);
        lifeUpDisplay = new ScoreEntity(new Position2D(320, 16), 16, 0, 6);
        playCountDisplay = new ScoreEntity(new Position2D(144, 560), 16, 0, 2);
        lifeDisplay = new LifeDisplay();
        fruitDisplay = new FruitDisplay();

        lifeUpDisplay.setScore(Constants.LIFE_UP_INTERVAL);
        incrementPlayCounter();
    }

    public void loadHighScore() {
        highScoreDisplay.setScore(0); //Used in the Client Server Version
    }

    public void addScore(int toAdd) {
        scoreDisplay.addScore(toAdd);

        if (scoreDisplay.getScore() > highScoreDisplay.getScore()) {
            highScoreDisplay.setScore(scoreDisplay.getScore());
        }
    }

    public void setLives(int lives) {
        lifeDisplay.setLives(lives);
    }

    public void addFruit(int level) {
        fruitDisplay.addFruit(level);
    }

    public int getCurrentScore() {
        return scoreDisplay.getScore();
    }

    public void incrementPlayCounter() {
        playCountDisplay.addScore(1);
    }

    public void resetUI() {
        scoreDisplay.setScore(0);
        lifeDisplay.setLives(Constants.START_LIVES);
        fruitDisplay.reset();
    }

}
