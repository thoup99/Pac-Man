package game.entities.board.ghost;

import game.Constants.Direction;
import game.board.nodes.Node;
import game.entities.board.PacMan;
import j2d.attributes.Vector2D;
import j2d.attributes.position.Position2D;

import java.awt.*;

import static game.Constants.BOARD_START_POSITION;
import static game.Constants.TILE_SIZE;

public class Pinky extends Ghost {
    private PacMan pacman;

    public Pinky(Node startNode, PacMan pacman) {
        super(startNode, pacman);
        this.pacman = pacman;
        scatterPosition = new Position2D(BOARD_START_POSITION);
        calculateNewGoalPosition();

        ghostCircle.setColor(Color.PINK);

        ready();
    }

    @Override
    protected void calculateNewGoalPosition() {
        Direction pacManDirection = pacman.getFacingDirection();
        Vector2D multVector = new Vector2D();
        if (pacManDirection == Direction.UP) {
            multVector = new Vector2D(4, 4);
        } else if (pacManDirection == Direction.DOWN) {
            multVector = new Vector2D(0, -4);
        } else if (pacManDirection == Direction.LEFT) {
            multVector = new Vector2D(-4, 0);
        } else if (pacManDirection == Direction.RIGHT) {
            multVector = new Vector2D(4, 0);
        }

        Position2D newGoalPosition = new Position2D(pacmanPosition);
        newGoalPosition.addX(TILE_SIZE * multVector.getIntX());
        newGoalPosition.addY(TILE_SIZE * multVector.getIntY());

        setGoalPosition(newGoalPosition);
    }
}
