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
    //Sprite map = new Sprite(this, new Position2D(0, 48), "/images/Arcade - Pac-Man - Maze Parts.png");
    Board board;
    PacMan pacMan;
    GhostManager ghostManager;

    Timer ghostEatenTimer;

    public PacManController() {
        board = new Board(this);
        pacMan = new PacMan(board.getNodeManager().getStartNode(), this);
        ghostManager = new GhostManager(board, pacMan);
        ghostEatenTimer = new Timer(this, 1000, this::unpauseAll);
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

    @Override
    public void update(double delta) {

    }

    @Override
    public void physicsUpdate(double delta) {

    }
}
