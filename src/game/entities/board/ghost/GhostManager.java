package game.entities.board.ghost;

import game.board.Board;
import game.board.nodes.NodeManager;
import game.entities.board.PacMan;
import j2d.components.Timer;
import j2d.engine.gameobject.GameObject;

import java.util.ArrayList;
import java.util.List;

public class GhostManager extends GameObject {
    public enum GhostMode {CHASE, SCATTER, FRIGHT, RETURN_SPAWN, AWAITING_START, LEAVING_START}
    private GhostMode globalMode = GhostMode.SCATTER;

    final List<Ghost> ghosts = new ArrayList<Ghost>();
    Blinky blinky;
    Pinky pinky;
    Inky inky;
    Clyde clyde;

    Timer scatterTimer;
    Timer chaseTimer;
    Timer frightTimer;

    public GhostManager(Board board, PacMan pacMan) {
        NodeManager nodeManager = board.getNodeManager();
        blinky = new Blinky(nodeManager.getBlinkyStartNode(), nodeManager, pacMan);
        pinky = new Pinky(nodeManager.getPinkyStartNode(), nodeManager, pacMan);
        inky = new Inky(nodeManager.getInkyStartNode(), nodeManager, pacMan, blinky);
        clyde = new Clyde(nodeManager.getClydeStartNode(), nodeManager, pacMan);

        ghosts.add(blinky);
        ghosts.add(pinky);
        ghosts.add(inky);
        ghosts.add(clyde);

        scatterTimer = new Timer(this, 7000, this::startChaseMode);
        scatterTimer.setOneShot(true);

        chaseTimer = new Timer(this, 20000, this::startScatterMode);
        chaseTimer.setOneShot(true);

        frightTimer = new Timer(this, 7000, this::startChaseMode);
        frightTimer.setOneShot(true);

        startRound();

        ready();
    }

    private void startRound() {
        for (Ghost ghost : ghosts) {
            ghost.currentMode = GhostMode.AWAITING_START;
            ghost.startRound();
        }
    }

    private void startScatterMode() {
        for (Ghost ghost : ghosts) {
            if (ghost.currentMode == GhostMode.CHASE) {
                ghost.startScatter();
            }
        }
        globalMode = GhostMode.SCATTER;
        scatterTimer.start();
    }

    private void startChaseMode() {
        for (Ghost ghost : ghosts) {
            if (ghost.currentMode == GhostMode.SCATTER || ghost.currentMode == GhostMode.FRIGHT) {
                ghost.startChase();
            }
        }
        globalMode = GhostMode.CHASE;
        chaseTimer.start();
    }

    public void forceFrightMode() {
        for (Ghost ghost : ghosts) {
            if (ghost.currentMode == GhostMode.SCATTER || ghost.currentMode == GhostMode.CHASE) {
                ghost.startFright();
            }
        }
        globalMode = GhostMode.FRIGHT;
        frightTimer.start();
    }

    public void pauseAllGhost() {
        scatterTimer.pause();
        chaseTimer.pause();
        frightTimer.pause();

        for (Ghost ghost : ghosts) {
            ghost.pause();
        }
    }

    public void unpauseAllGhost() {
        if (globalMode == GhostMode.SCATTER) {
            scatterTimer.resume();
        } else if (globalMode == GhostMode.CHASE) {
            chaseTimer.resume();
        } else if (globalMode == GhostMode.FRIGHT) {
            frightTimer.resume();
        }

        for (Ghost ghost : ghosts) {
            ghost.resume();
        }
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void physicsUpdate(double delta) {

    }
}
