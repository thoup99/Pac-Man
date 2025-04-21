package game.entities.board.ghost;

import game.PacManController;
import game.board.Board;
import game.board.nodes.NodeManager;
import game.entities.board.PacMan;
import game.entities.board.ghost.Ghost.GhostMode;
import j2d.components.Timer;
import j2d.engine.gameobject.GameObject;

import java.util.ArrayList;
import java.util.List;

public class GhostManager extends GameObject {
    PacManController pacManController;
    private GhostMode preFrightMode;
    private GhostMode globalMode = GhostMode.SCATTER;
    final List<Ghost> ghosts = new ArrayList<Ghost>();
    Blinky blinky;
    Pinky pinky;
    Inky inky;
    Clyde clyde;

    Timer scatterTimer;
    Timer chaseTimer;
    Timer frightTimer;

    private final int baseEatBonus = 200;
    int ghostEaten = 0;

    public GhostManager(PacManController pacManController, Board board, PacMan pacMan) {
        this.pacManController = pacManController;
        Ghost.ghostManager = this;
        loadGhost(board.getNodeManager(), pacMan);

        scatterTimer = new Timer(this, 7000, this::startChaseMode);
        scatterTimer.setOneShot(true);

        chaseTimer = new Timer(this, 20000, this::startScatterMode);
        chaseTimer.setOneShot(true);

        frightTimer = new Timer(this, 7000, this::startChaseMode);
        frightTimer.setOneShot(true);

        startRound();

        ready();
    }

    public void loadGhost(NodeManager nodeManager, PacMan pacMan) {
        blinky = new Blinky(nodeManager, pacMan);
        pinky = new Pinky(nodeManager, pacMan);
        inky = new Inky(nodeManager, pacMan, blinky);
        clyde = new Clyde(nodeManager, pacMan);

        ghosts.add(blinky);
        ghosts.add(pinky);
        ghosts.add(inky);
        ghosts.add(clyde);
    }

    public void unloadGhost() {
        for (Ghost ghost : ghosts) {
            ghost.queueDelete();
        }
        ghosts.clear();
    }

    public void startRound() {
        for (Ghost ghost : ghosts) {
            ghost.currentMode = GhostMode.AWAITING_START;
            ghost.startRound();
        }
        scatterTimer.start();
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
            if (ghost.currentMode == GhostMode.SCATTER || (ghost.currentMode == GhostMode.FRIGHT && frightTimer.isTimedOut())) {
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
        ghostEaten = 0;

        preFrightMode = globalMode;
        globalMode = GhostMode.FRIGHT;

        chaseTimer.pause();
        scatterTimer.pause();
        frightTimer.restart();
        frightTimer.start();
    }

    public void onGhostEaten() {
        ghostEaten++;
        pacManController.addScore(baseEatBonus * ghostEaten);
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

    public void resetAllGhost() {
        for (Ghost ghost: ghosts) {
            ghost.resetPosition();
        }
        startRound();
    }

    public void resetAllTimers() {
        chaseTimer.stop();
        scatterTimer.stop();
        frightTimer.stop();

        chaseTimer.restart();
        scatterTimer.restart();
        frightTimer.restart();
    }

    @Override
    public void update(double delta) {

    }

    private void printDebugGhostMode() {
        System.out.println("-----------");
        System.out.println("Ghost mode: " + globalMode);
        for (Ghost ghost : ghosts) {
            System.out.println(ghost.getClass().getName() + ": " + ghost.currentMode);
        }
    }

    private void printDebugTimers() {
        System.out.println("--------------");
        System.out.println("Scatter mode: " + scatterTimer.timeRemaining());
        System.out.println("Chase mode: " + chaseTimer.timeRemaining());
        System.out.println("Fright mode: " + frightTimer.timeRemaining());
    }

    @Override
    public void physicsUpdate(double delta) {

    }
}
