package game.board;

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
    List<Node> nodes;
    Node startNode;

    private final char[][] mapData;
    Map<String, Node> nodeMap;
    Map<Integer, List<Node>> portalMap;

    PelletController pelletController;

    public Board() {
        nodes = new ArrayList<Node>();

        mapData = new char[BOARD_TOTAL_ROWS][BOARD_TOTAL_COLUMNS];
        nodeMap = new HashMap<String, Node>();
        portalMap = new HashMap<>();

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

        loadNodes();
        pelletController.loadPellets(mapData);
    }

    private void loadNodes() {
        nodes.clear();
        createNodes();
        connectNodesHorizontally();
        connectNodesVertically();
        pairPortals();
        reloadVisualConnections();
    }

    private void printMap() {
        for (int row = 0; row < BOARD_TOTAL_ROWS; row++) {
            for (int col = 0; col < BOARD_TOTAL_COLUMNS; col++) {
                System.out.print(mapData[row][col]);
            }
            System.out.println();
        }
    }

    private void createNodes() {
        for (int row = 0; row < BOARD_TOTAL_ROWS; row++) {
            for (int col = 0; col < BOARD_TOTAL_COLUMNS; col++) {
                char value = mapData[row][col];
                if (isNodeSymbol(value)) {
                    Position2D nodePosition = getNodePosition(row, col);
                    Node newNode = new Node(nodePosition);
                    nodes.add(newNode);
                    nodeMap.put(nodePosition.toString(), newNode);

                    if (value == 'S') {
                        startNode = newNode;
                    }
                    if (Character.isDigit(value)) {
                        addToPortalMap(newNode, Character.getNumericValue(value));
                    }
                }
            }
        }
    }

    private void addToPortalMap(Node portalNode, int portalID) {
        if (!portalMap.containsKey(portalID)) {
            portalMap.put(portalID, new ArrayList<>());
        }
        List<Node> portalPairs = portalMap.get(portalID);
        if (portalPairs.size() > 2) {
            throw new RuntimeException("Too many pairs in portal map. Check Map File.");
        } else {
            portalPairs.add(portalNode);
        }
    }

    private void connectNodesHorizontally() {
        for (int row = 0; row < BOARD_TOTAL_ROWS; row++) {
            Node lastNode = null;
            for (int col = 0; col < BOARD_TOTAL_COLUMNS; col++) {
                char value = mapData[row][col];
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
                char value = mapData[row][col];
                if (value == 'X') {
                    lastNode = null;
                } else if (isNodeSymbol(value)) {
                    Position2D nodePosition = new Position2D(BOARD_START_POSITION.getIntX() + col * TILE_SIZE, BOARD_START_POSITION.getIntY() + row * TILE_SIZE);
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

    private void pairPortals() {
        for (List<Node> portalPairs : portalMap.values()) {
            Node firstNode = portalPairs.get(0);
            Node secondNode = portalPairs.get(1);

            firstNode.neighbors.put(Direction.PORTAL, secondNode);
            secondNode.neighbors.put(Direction.PORTAL, firstNode);
        }
    }

    private Position2D getNodePosition(int row, int col) {
        return new Position2D(BOARD_START_POSITION.getIntX() + col * TILE_SIZE, BOARD_START_POSITION.getIntY() + row * TILE_SIZE);
    }

    private boolean isNodeSymbol(char value) {
        return value == '+' || value == 'n' || value == 'P' || value == 'S' || Character.isDigit(value);
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
