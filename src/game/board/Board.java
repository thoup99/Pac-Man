package game.board;

import game.PacManController;
import game.board.nodes.NodeManager;
import game.board.pellets.PelletManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static game.Constants.*;

public class Board {
    private final char[][] mapData;

    NodeManager nodeManager;
    PelletManager pelletManager;

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

}
