package game;


import game.board.Board;
import game.entities.board.PacMan;
import game.entities.board.ghost.GhostManager;
import j2d.attributes.position.Position2D;
import j2d.components.Timer;
import j2d.components.sprite.Sprite;
import j2d.engine.gameobject.GameObject;

public class PacManController extends GameObject {
    //Sprite background = new Sprite(this, new Position2D(0, 0), "/images/background.png");
    Sprite map = new Sprite(this, new Position2D(0, 48), "/images/Arcade - Pac-Man - Maze Parts.png");
    Board board;
    PacMan pacMan;
    GhostManager ghostManager;

    Timer ghostEatenTimer;
    Timer levelCompletedTimer;
    Timer pacManDeathTimer;

    public PacManController() {
        board = new Board(this);
        pacMan = new PacMan(board.getNodeManager().getStartNode(), this);
        ghostManager = new GhostManager(board, pacMan);

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
        //Pause Ghost and PacMan
        pauseAll();

        //Play animation - Timer is stand in for animation
        pacManDeathTimer.start();
    }

    private void onDeathAnimationCompleted() {
        //Reset Ghost and PacMan positions
        ghostManager.resetAllGhost();
        pacMan.resetPosition();
        unpauseAll();
    }

    public void onLevelCompleted() {
        //Pause Ghost and PacMan
        pauseAll();

        //Play animation  - Timer is stand in for animation
        levelCompletedTimer.start();
    }

    private void onLevelFlashCompleted() {
        //Load next level
        //Reset Ghost and PacMan positions
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void physicsUpdate(double delta) {

    }
}
