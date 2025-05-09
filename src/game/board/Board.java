package game.board;

import game.PacManController;
import game.board.nodes.NodeManager;
import game.board.pellets.PelletManager;
import j2d.attributes.position.Position2D;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static game.Constants.*;

public class Board {
    private final char[][] mapData;

    NodeManager nodeManager;
    PelletManager pelletManager;
    Position2D fruitPosition;

    public Board(PacManController pacManController) {
        mapData = new char[BOARD_TOTAL_ROWS][BOARD_TOTAL_COLUMNS];

        nodeManager = new NodeManager();
        pelletManager = new PelletManager(pacManController);

        //loadSampleNodes();
        loadMap("/maps/map1.txt");
    }

    public void loadMap(String mapPath) {
        try (BufferedReader bReader = new BufferedReader(new InputStreamReader(Board.class.getResourceAsStream(mapPath))) ) {
            for (int row = 0; row < BOARD_TOTAL_ROWS; row++) {
                String line = bReader.readLine();
                String[] chars = line.split(" ");
                for (int col = 0; col < BOARD_TOTAL_COLUMNS; col++) {
                    mapData[row][col] = chars[col].charAt(0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error reading map. Reason: " + e.getMessage());
        }

        if (mapData == null) {
            throw new RuntimeException("Error reading map.");
        }

        nodeManager.loadNodes(mapData);
        pelletManager.loadPellets(mapData);
        locateFruitPosition();
    }

    private void locateFruitPosition() {
        for (int row = 0; row < BOARD_TOTAL_ROWS; row++) {
            for (int col = 0; col < BOARD_TOTAL_COLUMNS; col++) {
                char value = mapData[row][col];
                if (value == 'F') {
                    fruitPosition = new Position2D(BOARD_START_POSITION.getIntX() + HALF_TILE_SIZE + col * TILE_SIZE, BOARD_START_POSITION.getIntY() + row * TILE_SIZE);
                    return;
                }
            }
        }
        //Fallback
        fruitPosition = nodeManager.getStartNode().getPosition();
    }

    public void unloadMap() {
        nodeManager.unloadAll();
        pelletManager.emptyPellets();
    }

    public NodeManager getNodeManager() {
        return nodeManager;
    }

    public PelletManager getPelletManager() {
        return pelletManager;
    }

    public Position2D getFruitPosition() {return fruitPosition;}

}
