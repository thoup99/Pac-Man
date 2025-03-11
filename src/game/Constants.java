package game;

public class Constants {
    public static int TILE_SIZE = 32;
    public static int SCREEN_ROWS = 18;
    public static int SCREEN_COLUMNS = 14;
    public static int SCREEN_WIDTH = SCREEN_COLUMNS * TILE_SIZE;
    public static int SCREEN_HEIGHT = SCREEN_ROWS * TILE_SIZE;

    public enum Direction {
        STOP(0), UP(1), DOWN(-1), LEFT(-2), RIGHT(2);


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
}
