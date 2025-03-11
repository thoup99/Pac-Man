package game.board;

import game.Constants;
import j2d.attributes.position.Position2D;

import java.util.ArrayList;
import java.util.List;

public class Board {
    List<Node> nodes;

    public Board() {
        nodes = new ArrayList<Node>();

        loadSampleNodes();
    }

    private void loadSampleNodes() {
        Node nodeA = new Node(new Position2D(80, 80));
        Node nodeB = new Node(new Position2D(160, 80));
        Node nodeC = new Node(new Position2D(80, 160));
        Node nodeD = new Node(new Position2D(160, 160));
        Node nodeE = new Node(new Position2D(208, 160));
        Node nodeF = new Node(new Position2D(80, 320));
        Node nodeG = new Node(new Position2D(208, 320));

        nodes.add(nodeA);
        nodes.add(nodeB);
        nodes.add(nodeC);
        nodes.add(nodeD);
        nodes.add(nodeE);
        nodes.add(nodeF);
        nodes.add(nodeG);

        nodeA.getNeighbors().put(Constants.Direction.RIGHT, nodeB);
        nodeA.getNeighbors().put(Constants.Direction.DOWN, nodeC);

        nodeB.getNeighbors().put(Constants.Direction.LEFT, nodeA);
        nodeB.getNeighbors().put(Constants.Direction.DOWN, nodeD);

        nodeC.getNeighbors().put(Constants.Direction.UP, nodeA);
        nodeC.getNeighbors().put(Constants.Direction.RIGHT, nodeD);
        nodeC.getNeighbors().put(Constants.Direction.DOWN, nodeF);

        nodeD.getNeighbors().put(Constants.Direction.LEFT, nodeC);
        nodeD.getNeighbors().put(Constants.Direction.UP, nodeB);
        nodeD.getNeighbors().put(Constants.Direction.RIGHT, nodeE);

        nodeE.getNeighbors().put(Constants.Direction.LEFT, nodeD);
        nodeE.getNeighbors().put(Constants.Direction.DOWN, nodeG);

        nodeF.getNeighbors().put(Constants.Direction.UP, nodeC);
        nodeF.getNeighbors().put(Constants.Direction.RIGHT, nodeG);

        nodeG.getNeighbors().put(Constants.Direction.UP, nodeE);
        nodeG.getNeighbors().put(Constants.Direction.LEFT, nodeF);

        for (Node node : nodes) {
            node.loadDrawnComponents();
        }
    }

    public List<Node> getNodes() {
        return nodes;
    }
}
