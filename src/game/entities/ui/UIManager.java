package game.entities.ui;

public class UIManager {
    ScoreDisplay scoreDisplay;
    ScoreDisplay highScoreDisplay;
    LifeDisplay lifeDisplay;
    FruitDisplay fruitDisplay;

    public UIManager() {
        scoreDisplay = new ScoreDisplay();
        highScoreDisplay = new ScoreDisplay();
        lifeDisplay = new LifeDisplay();
        fruitDisplay = new FruitDisplay();
    }


}
