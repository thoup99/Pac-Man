package game;


import game.board.Board;
import game.entities.board.PacMan;
import game.entities.board.ghost.Ghost;
import j2d.attributes.position.Position2D;
import j2d.components.sprite.Sprite;
import j2d.engine.GameObject;

public class PacManController extends GameObject {
    Sprite background = new Sprite(this, new Position2D(0, 0), "/images/background.png");
    Sprite map = new Sprite(this, new Position2D(0, 48), "/images/Arcade - Pac-Man - Maze Parts.png");
    Board board;
    PacMan pacMan;

    public PacManController() {
        board = new Board();
        pacMan = new PacMan(board.getNodeController().getStartNode());
        Ghost ghost = new Ghost(board.getNodeController().getStartNode());
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void physicsUpdate(double delta) {

    }
}
