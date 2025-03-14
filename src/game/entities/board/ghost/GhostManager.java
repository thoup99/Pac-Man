package game.entities.board.ghost;

import game.board.Board;
import game.entities.board.PacMan;
import j2d.components.Timer;
import j2d.engine.gameobject.GameObject;

import java.util.ArrayList;
import java.util.List;

public class GhostManager extends GameObject {
    private static GhostManager ghostManager;
    enum GhostMode {CHASE, SCATTER, FRIGHT, SPAWN}

    final List<Ghost> ghosts = new ArrayList<Ghost>();
    Blinky blinky;

    Timer scatterTimer;
    Timer chaseTimer;
    Timer frightTimer;

    public GhostManager(Board board, PacMan pacMan) {
        ghostManager = this;
        blinky = new Blinky(board.getNodeManager().getRandomNode(), pacMan);

        ghosts.add(blinky);

        scatterTimer = new Timer(this, 7000, this::startChaseMode);
        scatterTimer.setOneShot(true);

        chaseTimer = new Timer(this, 20000, this::startScatterMode);
        chaseTimer.setOneShot(true);

        frightTimer = new Timer(this, 7000, this::startChaseMode);
        frightTimer.setOneShot(true);

        startScatterMode();

        ready();
    }

    private void startScatterMode() {
        for (Ghost ghost : ghosts) {
            if (ghost.currentMode == GhostMode.CHASE) {
                ghost.startScatter();
            }
        }
        scatterTimer.start();
    }

    private void startChaseMode() {
        for (Ghost ghost : ghosts) {
            if (ghost.currentMode == GhostMode.SCATTER || ghost.currentMode == GhostMode.FRIGHT) {
                ghost.startChase();
            }
        }
        chaseTimer.start();
    }

    public static void forceFrightMode() {
        ghostManager.startFrightMode();
    }

    private void startFrightMode() {
        for (Ghost ghost : ghosts) {
            if (ghost.currentMode == GhostMode.SCATTER || ghost.currentMode == GhostMode.CHASE) {
                ghost.startFright();
            }
        }
        frightTimer.start();
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void physicsUpdate(double delta) {

    }
}
