package game.board.nodes;

import game.Constants;
import j2d.attributes.position.Position2D;

import java.util.*;

import static game.Constants.*;
import static game.Constants.TILE_SIZE;

public class NodeManager {
    List<Node> nodes;
    Node startNode;
    Node blinkyNode;
    Node inkyNode;
    Node pinkyNode;
    Node clydeNode;

    Map<String, Node> nodeMap;
    Map<Integer, List<Node>> portalMap;

    public NodeManager() {
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
        buildGhostSpawn();
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

                    if (value == 'G') {
                        nodePosition.addX(HALF_TILE_SIZE);
                        blinkyNode = newNode;
                    }
                    if (value == 'S') {
                        emptyStartNode();
                        nodePosition.addX(HALF_TILE_SIZE);
                        startNode = newNode;
                    }

                    nodes.add(newNode);
                    nodeMap.put(nodePosition.toString(), newNode);

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

                    if (value == 'G' || value == 'S') {
                        nodePosition.addX(HALF_TILE_SIZE);
                    }

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
                    Position2D nodePosition = getNodePosition(row, col);

                    if (value == 'G') {
                        nodePosition.addX(HALF_TILE_SIZE);
                    }

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

    private void buildGhostSpawn() {
        buildPinkySpawn();
        buildInkySpawn();
        buildClydeSpawn();
    }

    private void buildPinkySpawn() {
        Position2D pinkySpawnPosition = new Position2D(blinkyNode.getPosition());
        pinkySpawnPosition.addY(TILE_SIZE * 3);
        pinkyNode = new Node(pinkySpawnPosition);
        nodes.add(pinkyNode);

        blinkyNode.neighbors.put(Direction.DOWN, pinkyNode);
        pinkyNode.neighbors.put(Direction.UP, blinkyNode);
    }

    private void buildInkySpawn() {
        Position2D inkySpawnPosition = new Position2D(pinkyNode.getPosition());
        inkySpawnPosition.addX(-TILE_SIZE * 2);
        inkyNode = new Node(inkySpawnPosition);
        nodes.add(inkyNode);

        pinkyNode.neighbors.put(Direction.LEFT, inkyNode);
        inkyNode.neighbors.put(Direction.RIGHT, pinkyNode);

        buildAdjacentNodes(inkyNode);
    }

    private void buildClydeSpawn() {
        Position2D clydeSpawnPosition = new Position2D(pinkyNode.getPosition());
        clydeSpawnPosition.addX(TILE_SIZE * 2);
        clydeNode = new Node(clydeSpawnPosition);
        nodes.add(clydeNode);

        pinkyNode.neighbors.put(Direction.RIGHT, clydeNode);
        clydeNode.neighbors.put(Direction.LEFT, pinkyNode);

        buildAdjacentNodes(clydeNode);
    }

    private void buildAdjacentNodes(Node startNode) {
        Node topNode = new Node(new Position2D(startNode.getPosition().getX(),
                startNode.getPosition().getIntY() - TILE_SIZE));
        Node bottomNode = new Node(new Position2D(startNode.getPosition().getX(),
                startNode.getPosition().getIntY() + TILE_SIZE));

        nodes.add(topNode);
        nodes.add(bottomNode);

        topNode.neighbors.put(Direction.DOWN, startNode);
        startNode.neighbors.put(Direction.UP, topNode);

        startNode.neighbors.put(Direction.DOWN, bottomNode);
        bottomNode.neighbors.put(Direction.UP, startNode);
    }

    private Position2D getNodePosition(int row, int col) {
        return new Position2D(BOARD_START_POSITION.getIntX() + col * TILE_SIZE, BOARD_START_POSITION.getIntY() + row * TILE_SIZE);
    }

    private boolean isNodeSymbol(char value) {
        return value == '+' || value == 'n' || value == 'P' || value == 'S' || Character.isDigit(value) || value == 'G';
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

    public Node getBlinkyStartNode() {
        return blinkyNode;
    }

    public Node getInkyStartNode() {
        return inkyNode;
    }

    public Node getPinkyStartNode() {
        return pinkyNode;
    }

    public Node getClydeStartNode() {
        return clydeNode;
    }

    public Node getRandomNode() {
        Random random = new Random(nodes.size());
        return nodes.get(random.nextInt(nodes.size()));
    }
}
