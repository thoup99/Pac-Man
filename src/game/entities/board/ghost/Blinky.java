package game.entities.board.ghost;

import game.board.nodes.Node;
import game.entities.board.PacMan;
import j2d.attributes.position.Position2D;

import java.awt.*;

import static game.Constants.*;

public class Blinky extends Ghost {

    public Blinky(Node startNode, PacMan pacman) {
        super(startNode, pacman);
        scatterPosition = new Position2D(BOARD_START_POSITION.getX() + (TILE_SIZE * BOARD_TOTAL_COLUMNS), BOARD_START_POSITION.getY());
        setGoalPosition(pacmanPosition);

        ghostCircle.setColor(Color.RED);

        ready();
    }


    @Override
    protected void calculateNewGoalPosition() {
        setGoalPosition(pacmanPosition);
    }
}
