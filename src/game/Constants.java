package game;

public class Constants {
    public static int TILE_SIZE = 32;
    public static int SCREEN_ROWS = 18;
    public static int SCREEN_COLUMNS = 14;
    public static int SCREEN_WIDTH = SCREEN_COLUMNS * TILE_SIZE;
    public static int SCREEN_HEIGHT = SCREEN_ROWS * TILE_SIZE;

    enum Direction {STOP, UP, DOWN, LEFT, RIGHT}
}
