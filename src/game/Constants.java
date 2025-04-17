package game;

import j2d.attributes.position.Position2D;

public class Constants {
    public static int TILE_SIZE = 16;
    public static int HALF_TILE_SIZE = TILE_SIZE / 2;
    public static int SCREEN_ROWS = 36;
    public static int SCREEN_COLUMNS = 28;
    public static int SCREEN_WIDTH = SCREEN_COLUMNS * TILE_SIZE;
    public static int SCREEN_HEIGHT = SCREEN_ROWS * TILE_SIZE;

    public static int BOARD_START_ROW = 3;
    public static int BOARD_START_COLUMN = 0;

    public static int BOARD_TOTAL_ROWS = 31;
    public static int BOARD_TOTAL_COLUMNS = 28;

    public static Position2D BOARD_START_POSITION = new Position2D(BOARD_START_COLUMN * TILE_SIZE + HALF_TILE_SIZE,
            BOARD_START_ROW * TILE_SIZE + HALF_TILE_SIZE);

    public enum Direction {
        STOP(0), UP(2), DOWN(-2), LEFT(1), RIGHT(-1), PORTAL(3);


        private final int value;

        Direction(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static Direction getOpposite(Direction direction) {
            switch (direction) {
                case UP:
                    return Direction.DOWN;
                case DOWN:
                    return Direction.UP;
                case LEFT:
                    return Direction.RIGHT;
                case RIGHT:
                    return Direction.LEFT;

                default:
                    return STOP;
            }
        }
    }

    //Debug Variables
    public static final boolean visualizeNodes = false;
}
