package game.board.nodes;

import game.Constants;
import j2d.attributes.position.Position2D;

import java.util.*;

import static game.Constants.*;
import static game.Constants.TILE_SIZE;

public class NodeController {
    List<Node> nodes;
    Node startNode;

    Map<String, Node> nodeMap;
    Map<Integer, List<Node>> portalMap;

    public NodeController() {
        nodes = new ArrayList<Node>();
        nodeMap = new HashMap<String, Node>();
        portalMap = new HashMap<>();
    }

    public void loadNodes(char[][] mapData) {
        emptyNodes();
        createNodes(mapData);
        connectNodesHorizontally(mapData);
        connectNodesVertically(mapData);
        pairPortals();
        reloadVisualConnections();
    }

    private void emptyNodes() {
        for (Node node : nodes) {
            node.queueDelete();
        }
        nodes.clear();
    }

    private void emptyStartNode() {
        if (startNode != null) {
            startNode.queueDelete();
        }
    }

    private void createNodes(char[][] mapData) {
        for (int row = 0; row < BOARD_TOTAL_ROWS; row++) {
            for (int col = 0; col < BOARD_TOTAL_COLUMNS; col++) {
                char value = mapData[row][col];
                if (isNodeSymbol(value)) {
                    Position2D nodePosition = getNodePosition(row, col);
                    Node newNode = new Node(nodePosition);
                    nodes.add(newNode);
                    nodeMap.put(nodePosition.toString(), newNode);

                    if (value == 'S') {
                        emptyStartNode();
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

    private void connectNodesHorizontally(char[][] mapData) {
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
                        lastNode.neighbors.put(Constants.Direction.RIGHT, currentNode);
                        currentNode.neighbors.put(Constants.Direction.LEFT, lastNode);
                        lastNode = currentNode;
                    }
                }
            }
        }
    }

    private void connectNodesVertically(char[][] mapData) {
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

    private void printMap(char[][] mapData) {
        for (int row = 0; row < BOARD_TOTAL_ROWS; row++) {
            for (int col = 0; col < BOARD_TOTAL_COLUMNS; col++) {
                System.out.print(mapData[row][col]);
            }
            System.out.println();
        }
    }

    private void reloadVisualConnections() {
        for (Node node : nodes) {
            node.loadDrawnComponents();
        }
    }

    public Node getStartNode() {
        return startNode;
    }

    public Node getRandomNode() {
        Random random = new Random(nodes.size());
        return nodes.get(random.nextInt(nodes.size()));
    }
}
