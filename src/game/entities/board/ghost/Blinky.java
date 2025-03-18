package game.entities.board.ghost;

import game.board.nodes.Node;
import game.board.nodes.NodeManager;
import game.entities.board.PacMan;
import j2d.attributes.position.Position2D;

import java.awt.*;

import static game.Constants.*;

public class Blinky extends Ghost {

    public Blinky(Node startNode, NodeManager nodeManager, PacMan pacman) {
        super(startNode, nodeManager, pacman);
        scatterPosition = new Position2D(BOARD_START_POSITION.getX() + (TILE_SIZE * BOARD_TOTAL_COLUMNS),
                BOARD_START_POSITION.getY());
        setGoalPosition(pacmanPosition);

        ghostCircle.setColor(Color.RED);

        ready();
    }

    @Override
    protected void startRound() {
        startScatter();
    }


    @Override
    protected void calculateNewGoalPosition() {
        setGoalPosition(pacmanPosition);
    }
}
