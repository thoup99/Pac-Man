package game.board;

import j2d.attributes.position.Position2D;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static game.Constants.*;

public class Board {
    List<Node> nodes;
    Node startNode;
    Position2D startPosition;

    private final char[][] loadedData;
    Map<String, Node> nodeMap;

    public Board() {
        nodes = new ArrayList<Node>();
        startPosition = new Position2D(BOARD_START_COLUMN * TILE_SIZE + HALF_TILE_SIZE,
                BOARD_START_ROW * TILE_SIZE + HALF_TILE_SIZE);

        loadedData = new char[BOARD_TOTAL_ROWS][BOARD_TOTAL_COLUMNS];
        nodeMap = new HashMap<String, Node>();

        //loadSampleNodes();
        loadMap("/maps/map1.txt");

        //Avoids Errors on Startup
        if (startNode == null) {
            startNode = new Node(new Position2D(64, 64));
        }
    }

    public void loadMap(String mapPath) {
        try (BufferedReader bReader = new BufferedReader(new InputStreamReader(Board.class.getResourceAsStream(mapPath))) ) {
            for (int row = 0; row < BOARD_TOTAL_ROWS; row++) {
                String line = bReader.readLine();
                String[] chars = line.split(" ");
                for (int col = 0; col < BOARD_TOTAL_COLUMNS; col++) {
                    loadedData[row][col] = chars[col].charAt(0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error reading map. Reason: " + e.getMessage());
        }

        loadNodes();
    }

    private void loadNodes() {
        nodes.clear();
        createNodes();
        connectNodesHorizontally();
        connectNodesVertically();
        reloadVisualConnections();
    }

    private void printMap() {
        for (int row = 0; row < BOARD_TOTAL_ROWS; row++) {
            for (int col = 0; col < BOARD_TOTAL_COLUMNS; col++) {
                System.out.print(loadedData[row][col]);
            }
            System.out.println();
        }
    }

    private void createNodes() {
        for (int row = 0; row < BOARD_TOTAL_ROWS; row++) {
            for (int col = 0; col < BOARD_TOTAL_COLUMNS; col++) {
                char value = loadedData[row][col];
                if (value == '+' || value == 'n' || value == 'P' || value == 'S') {
                    Position2D nodePosition = getNodePosition(row, col);
                    Node newNode = new Node(nodePosition);
                    nodes.add(newNode);
                    nodeMap.put(nodePosition.toString(), newNode);

                    if (value == 'S') {
                        startNode = newNode;
                    }
                }
            }
        }
    }

    private void connectNodesHorizontally() {
        for (int row = 0; row < BOARD_TOTAL_ROWS; row++) {
            Node lastNode = null;
            for (int col = 0; col < BOARD_TOTAL_COLUMNS; col++) {
                char value = loadedData[row][col];
                if (value == 'X') {
                    lastNode = null;
                } else if (isNodeSymbol(value)) {
                    Position2D nodePosition = getNodePosition(row, col);
                    if (lastNode == null) {
                        lastNode = nodeMap.get(nodePosition.toString());
                    } else {
                        Node currentNode = nodeMap.get(nodePosition.toString());
                        lastNode.neighbors.put(Direction.RIGHT, currentNode);
                        currentNode.neighbors.put(Direction.LEFT, lastNode);
                        lastNode = currentNode;
                    }
                }
            }
        }
    }

    private void connectNodesVertically() {
        for (int col = 0; col < BOARD_TOTAL_COLUMNS; col++) {
            Node lastNode = null;
            for (int row = 0; row < BOARD_TOTAL_ROWS; row++) {
                char value = loadedData[row][col];
                if (value == 'X') {
                    lastNode = null;
                } else if (isNodeSymbol(value)) {
                    Position2D nodePosition = new Position2D(startPosition.getIntX() + col * TILE_SIZE, startPosition.getIntY() + row * TILE_SIZE);
                    if (lastNode == null) {
                        lastNode = nodeMap.get(nodePosition.toString());
                    } else {
                        Node currentNode = nodeMap.get(nodePosition.toString());
                        lastNode.neighbors.put(Direction.DOWN, currentNode);
                        currentNode.neighbors.put(Direction.UP, lastNode);
                        lastNode = currentNode;
                    }
                }
            }
        }
    }

    private Position2D getNodePosition(int row, int col) {
        return new Position2D(startPosition.getIntX() + col * TILE_SIZE, startPosition.getIntY() + row * TILE_SIZE);
    }

    private boolean isNodeSymbol(char value) {
        return value == '+' || value == 'n' || value == 'P' || value == 'S';
    }

    private void reloadVisualConnections() {
        for (Node node : nodes) {
            node.loadDrawnComponents();
        }
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public Node getStartNode() {
        return startNode;
    }
}
