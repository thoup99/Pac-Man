package game.board;

import game.board.nodes.Node;
import game.board.nodes.NodeController;
import game.board.pellets.PelletController;
import j2d.attributes.position.Position2D;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static game.Constants.*;

public class Board {
    private final char[][] mapData;

    NodeController nodeController;
    PelletController pelletController;

    public Board() {
        mapData = new char[BOARD_TOTAL_ROWS][BOARD_TOTAL_COLUMNS];

        nodeController = new NodeController();
        pelletController = new PelletController();

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

        nodeController.loadNodes(mapData);
        pelletController.loadPellets(mapData);
    }



    public NodeController getNodeController() {
        return nodeController;
    }

    public PelletController getPelletController() {
        return pelletController;
    }

}
