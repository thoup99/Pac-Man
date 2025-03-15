package game.entities.board.ghost;

import game.Constants.Direction;
import game.board.nodes.Node;
import game.entities.board.PacMan;
import game.entities.board.ghost.GhostManager.GhostMode;
import j2d.attributes.position.Position2D;

import static game.Constants.*;

public class Blinky extends Ghost {

    public Blinky(Node startNode, PacMan pacman) {
        super(startNode, pacman);
        scatterPosition = new Position2D(BOARD_START_POSITION.getX() + (TILE_SIZE * BOARD_TOTAL_COLUMNS), BOARD_START_POSITION.getY());
        setGoalPosition(pacmanPosition);

        ready();
    }

    @Override
    public void update(double delta) {
        if (didOvershootTargetNode()) {
            currentNode = targetNode;
            checkSpawnReturn();

            handleNodeTransition();
        }

        moveInCurrentDirection(delta);
    }
}
