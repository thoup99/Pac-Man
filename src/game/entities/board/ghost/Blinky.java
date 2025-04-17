package game.entities.board.ghost;

import game.board.nodes.Node;
import game.board.nodes.NodeManager;
import game.entities.board.PacMan;
import j2d.attributes.position.Position2D;

import java.awt.*;

import static game.Constants.*;

public class Blinky extends Ghost {

    public Blinky(NodeManager nodeManager, PacMan pacman) {
        super(0, nodeManager.getBlinkyStartNode(), nodeManager, pacman);
        homePosition = nodeManager.getPinkyStartNode().getPosition();
        scatterPosition = new Position2D(BOARD_START_POSITION.getX() + (TILE_SIZE * BOARD_TOTAL_COLUMNS),
                BOARD_START_POSITION.getY());
        setGoalPosition(pacmanPosition);

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
