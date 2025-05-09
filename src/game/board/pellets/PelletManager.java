package game.board.pellets;

import game.PacManController;
import j2d.attributes.position.Position2D;
import j2d.components.sprite.Sprite;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static game.Constants.*;
import static game.Constants.TILE_SIZE;

public class PelletManager {
    private final List<Pellet> pellets;
    private final List<PowerPellet> powerPellets;
    private final PacManController pacManController;

    private static final int PELLET_SCORE = 10;
    private static final int POWER_PELLET_SCORE = 50;

    private static final int FRUIT_SPAWN_ONE = 70;
    private static final int FRUIT_SPAWN_TWO = 170;
    private int pelletsEaten = 0;

    private final BufferedImage pelletImage;
    private final BufferedImage powerPelletImage;

    public PelletManager(PacManController pacManController) {
        pellets = new ArrayList<>();
        powerPellets = new ArrayList<>();
        this.pacManController = pacManController;

        pelletImage = Sprite.loadImage("./images/pellet.png");
        powerPelletImage = Sprite.loadImage("./images/power_pellet_sheet.png");
    }

    public void loadPellets(char[][] mapData) {
        pelletsEaten = 0;
        for (int row = 0; row < BOARD_TOTAL_ROWS; row++) {
            for (int col = 0; col < BOARD_TOTAL_COLUMNS; col++) {
                char value = mapData[row][col];
                if (isPelletSymbol(value)) {
                    Pellet newPellet = new Pellet(this, getPelletPosition(row, col));
                    pellets.add(newPellet);
                } else if (isPowerPelletSymbol(value)) {
                    PowerPellet newPowerPellet = new PowerPellet(this, getPelletPosition(row, col), pacManController);
                    powerPellets.add(newPowerPellet);
                }
            }
        }
    }

    private Position2D getPelletPosition(int row, int col) {
        return new Position2D(BOARD_START_POSITION.getIntX() + col * TILE_SIZE, BOARD_START_POSITION.getIntY() + row * TILE_SIZE);
    }

    private boolean isPelletSymbol(char value) {
        return value == '.' || value == '+';
    }

    private boolean isPowerPelletSymbol(char value) {
        return value == 'P' || value == 'p';
    }

    public BufferedImage getPelletImage() {
        return pelletImage;
    }

    public BufferedImage getPowerPelletImage() {
        return powerPelletImage;
    }

    public void emptyPellets() {
        for (Pellet pellet : pellets) {
            pellet.queueDelete();
        }
        for (PowerPellet powerPellet : powerPellets) {
            powerPellet.queueDelete();
        }

        pellets.clear();
        powerPellets.clear();
    }

    public boolean arePelletsEaten() {
        return pellets.isEmpty() && powerPellets.isEmpty();
    }

    protected void removePellet(Pellet pellet) {
        if (pellet instanceof PowerPellet) {
            pacManController.addScore(POWER_PELLET_SCORE);
            powerPellets.remove(pellet);
        } else {
            pacManController.addScore(PELLET_SCORE);
            pellets.remove(pellet);
        }

        pelletsEaten++;
        if (pelletsEaten == FRUIT_SPAWN_ONE || pelletsEaten == FRUIT_SPAWN_TWO) {
            pacManController.spawnFruit();
        }
    }
}
