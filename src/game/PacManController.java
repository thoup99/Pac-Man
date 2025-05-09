package game;


import game.board.Board;
import game.entities.board.FruitEntity;
import game.entities.board.PacMan;
import game.entities.board.ghost.GhostManager;
import game.entities.ui.UIManager;
import j2d.attributes.position.Position2D;
import j2d.components.Timer;
import j2d.components.sprite.Sprite;
import j2d.engine.gameobject.GameObject;

public class PacManController extends GameObject {
    UIManager uiManager = new UIManager();
    Sprite background = new Sprite(this, new Position2D(0, 0), "/images/background.png");
    Sprite map = new Sprite(this, new Position2D(0, 48), "/images/maze_outline.png");

    Board board;
    PacMan pacMan;
    GhostManager ghostManager;
    FruitEntity fruit;

    Timer ghostEatenTimer;
    Timer levelCompletedTimer;
    Timer pacManDeathTimer;

    int nextScoreThreshold = Constants.LIFE_UP_INTERVAL;
    int lives = Constants.START_LIVES;

    int level = 1;

    public PacManController() {
        board = new Board(this);
        pacMan = new PacMan(this, board.getNodeManager().getStartNode());
        ghostManager = new GhostManager(this, board, pacMan);
        fruit = new FruitEntity(this, board.getFruitPosition());

        ghostEatenTimer = new Timer(this, 1000, this::unpauseAll);
        levelCompletedTimer = new Timer(this, 500, this::onLevelFlashCompleted);
        pacManDeathTimer = new Timer(this, 500, this::onDeathAnimationCompleted);

        ghostEatenTimer.setOneShot(true);
        levelCompletedTimer.setOneShot(true);
        pacManDeathTimer.setOneShot(true);
        uiManager.resetUI();
    }

    public void powerPelletEaten() {
        ghostManager.forceFrightMode();
    }

    public void addScore(int score) {
        uiManager.addScore(score);

        if (uiManager.getCurrentScore() > nextScoreThreshold) {
            nextScoreThreshold += Constants.LIFE_UP_INTERVAL;

            if (lives < Constants.MAX_LIVES) {
                lives++;
                uiManager.setLives(lives);
            }
        }
    }

    public void onFruitEaten() {
        uiManager.addFruit(level);
    }

    public void pauseAll() {
        ghostManager.pauseAllGhost();
        pacMan.pause();
        fruit.pauseTimer();
    }

    public void unpauseAll() {
        ghostManager.unpauseAllGhost();
        pacMan.unpause();
        fruit.resumeTimer();
    }

    public void onGhostEaten() {
        pauseAll();
        ghostEatenTimer.start();
    }

    public void spawnFruit() {
        fruit.activate();
    }

    public void onPacManDeath() {
        pauseAll();
        ghostManager.hideGhosts();
        pacMan.allowAnimations();
        fruit.deactivate();
    }

    public void onDeathAnimationCompleted() {
        //Reset Ghost and PacMan positions
        lives--;
        if (lives <= 0) {
            uiManager.resetUI();
            uiManager.incrementPlayCounter();
            lives = Constants.START_LIVES;
            nextScoreThreshold = Constants.LIFE_UP_INTERVAL;
            level = 1;

            reloadLevel();
        } else {
            uiManager.setLives(lives);
            ghostManager.resetAllGhost();
            ghostManager.showGhosts();
            pacMan.resetPosition();
            unpauseAll();
        }
    }

    public void checkBoardClear() {
        if (board.getPelletManager().arePelletsEaten()) {
            onLevelCompleted();
        }
    }

    public void onLevelCompleted() {
        //Unload PacMan & Ghost
        ghostManager.unloadGhost();
        pacMan.queueDelete();

        //Play animation  - Timer is stand in for animation
        levelCompletedTimer.start();
    }

    private void onLevelFlashCompleted() {
        level++;
        reloadLevel();
    }

    private void reloadLevel() {
        ghostManager.unloadGhost();
        if (pacMan != null) {
            pacMan.queueDelete();
            pacMan = null;
        }

        if (fruit != null) {
            fruit.queueDelete();
            fruit = null;
        }

        //Unload current map
        board.unloadMap();

        //Load next map
        board.loadMap("/maps/map1.txt");

        //Create new pacman, ghost, and fruit;
        pacMan = new PacMan(this, board.getNodeManager().getStartNode());
        ghostManager.loadGhost(board.getNodeManager(), pacMan);
        ghostManager.startRound();
        fruit = new FruitEntity(this, board.getFruitPosition());
        fruit.setLevel(level);
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void physicsUpdate(double delta) {

    }
}
