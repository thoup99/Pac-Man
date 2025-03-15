package game.entities.board.ghost;

import game.board.nodes.Node;
import game.entities.board.PacMan;
import j2d.attributes.Vector2D;
import j2d.attributes.position.Position2D;
import j2d.engine.window.Window;

import java.awt.*;

import static game.Constants.BOARD_START_POSITION;
import static game.Constants.TILE_SIZE;

public class Pinky extends Ghost {

    public Pinky(Node startNode, PacMan pacman) {
        super(startNode, pacman);
        scatterPosition = new Position2D(BOARD_START_POSITION);
        calculateNewGoalPosition();

        ghostCircle.setColor(Color.PINK);

        ready();
    }

    @Override
    protected void calculateNewGoalPosition() {
        Vector2D directionVector = getPacManDirectionVector().multiply(4);
        Position2D newGoalPosition = new Position2D(pacmanPosition);
        newGoalPosition.addX(TILE_SIZE * directionVector.getIntX());
        newGoalPosition.addY(TILE_SIZE * directionVector.getIntY());

        setGoalPosition(newGoalPosition);
    }
}
