package game;


import game.board.Board;
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

    Timer ghostEatenTimer;
    Timer levelCompletedTimer;
    Timer pacManDeathTimer;

    final int START_LIVES = 3;
    final int MAX_LIVES = 5;
    final int LIFE_SCORE_THRESHOLD = 10000;
    int nextScoreThreshold = LIFE_SCORE_THRESHOLD;
    int lives = START_LIVES;

    public PacManController() {
        board = new Board(this);
        pacMan = new PacMan(this, board.getNodeManager().getStartNode());
        ghostManager = new GhostManager(this, board, pacMan);

        ghostEatenTimer = new Timer(this, 1000, this::unpauseAll);
        levelCompletedTimer = new Timer(this, 500, this::onLevelFlashCompleted);
        pacManDeathTimer = new Timer(this, 500, this::onDeathAnimationCompleted);

        ghostEatenTimer.setOneShot(true);
        levelCompletedTimer.setOneShot(true);
        pacManDeathTimer.setOneShot(true);
    }

    public void powerPelletEaten() {
        ghostManager.forceFrightMode();
    }

    public void addScore(int score) {
        uiManager.addScore(score);

        if (uiManager.getCurrentScore() > nextScoreThreshold) {
            nextScoreThreshold += LIFE_SCORE_THRESHOLD;

            if (lives < MAX_LIVES) {
                lives++;
            }
        }
    }

    public void pauseAll() {
        ghostManager.pauseAllGhost();
        pacMan.pause();
    }

    public void unpauseAll() {
        ghostManager.unpauseAllGhost();
        pacMan.unpause();
    }

    public void onGhostEaten() {
        pauseAll();
        ghostEatenTimer.start();
    }

    public void onPacManDeath() {
        pauseAll();
        pacMan.allowAnimations();
    }

    public void onDeathAnimationCompleted() {
        //Reset Ghost and PacMan positions
        lives--;
        if (lives <= 0) {
            uiManager.resetUI();
            uiManager.incrementPlayCounter();
            lives = START_LIVES;
            nextScoreThreshold = LIFE_SCORE_THRESHOLD;

            reloadLevel();
        } else {

            ghostManager.resetAllGhost();
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
        reloadLevel();
    }

    private void reloadLevel() {
        ghostManager.unloadGhost();
        if (pacMan != null) {
            pacMan.queueDelete();
            pacMan = null;
        }

        //Unload current map
        board.unloadMap();

        //Load next map
        board.loadMap("/maps/map1.txt");

        //Create new pacman and ghost
        pacMan = new PacMan(this, board.getNodeManager().getStartNode());
        ghostManager.loadGhost(board.getNodeManager(), pacMan);
        ghostManager.startRound();
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void physicsUpdate(double delta) {

    }
}
