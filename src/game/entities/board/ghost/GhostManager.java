package game.entities.board.ghost;

import game.board.Board;
import game.entities.board.PacMan;
import j2d.components.Timer;
import j2d.engine.gameobject.GameObject;

import java.util.ArrayList;
import java.util.List;

public class GhostManager extends GameObject {
    enum GhostMode {CHASE, SCATTER, FRIGHT, SPAWN}

    final List<Ghost> ghosts = new ArrayList<Ghost>();
    Blinky blinky;

    Timer scatterTimer;
    Timer chaseTimer;
    Timer frightTimer;

    public GhostManager(Board board, PacMan pacMan) {
        blinky = new Blinky(board.getNodeManager().getBlinkyStartNode(), pacMan);

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

    public void forceFrightMode() {
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
