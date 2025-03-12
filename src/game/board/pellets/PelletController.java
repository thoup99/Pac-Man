package game.board.pellets;

import j2d.attributes.position.Position2D;

import java.util.ArrayList;
import java.util.List;

import static game.Constants.*;
import static game.Constants.TILE_SIZE;

public class PelletController {
    private List<Pellet> pellets;
    private List<PowerPellet> powerPellets;

    public PelletController() {
        pellets = new ArrayList<>();
        powerPellets = new ArrayList<>();
    }

    public void loadPellets(char[][] mapData) {
        for (int row = 0; row < BOARD_TOTAL_ROWS; row++) {
            for (int col = 0; col < BOARD_TOTAL_COLUMNS; col++) {
                char value = mapData[row][col];
                if (isPelletSymbol(value)) {
                    Pellet newPellet = new Pellet(getPelletPosition(row, col));
                    pellets.add(newPellet);
                } else if (isPowerPelletSymbol(value)) {
                    PowerPellet newPowerPellet = new PowerPellet(getPelletPosition(row, col));
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

    public void emptyPellets() {
        for (Pellet pellet : pellets) {
            pellet.delete();
        }
        for (PowerPellet powerPellet : powerPellets) {
            powerPellet.delete();
        }

        pellets.clear();
        powerPellets.clear();
    }

    public boolean arePelletsEaten() {
        return pellets.isEmpty();
    }
}
