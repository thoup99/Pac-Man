package game.board;

import j2d.attributes.position.Position2D;
import j2d.components.graphics.shapes.FillCircle;
import j2d.components.graphics.shapes.Line;
import j2d.engine.GameObject;

import game.Constants.Direction;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Node extends GameObject {
    Position2D position;
    Map<Direction, Node> neighbors;

    FillCircle pointCircle;
    List<Line> lines;

    public Node(Position2D position) {
        this.position = position;
        neighbors = new HashMap<Direction, Node>();
        initNeighbors();

        lines = new ArrayList<Line>();
        loadDrawnComponents();

        ready();
    }

    private void initNeighbors() {
        neighbors.put(Direction.UP, null);
        neighbors.put(Direction.DOWN, null);
        neighbors.put(Direction.LEFT, null);
        neighbors.put(Direction.RIGHT, null);
    }

    public void loadDrawnComponents() {
        pointCircle = new FillCircle(this, 0, position, 10);
        pointCircle.setColor(Color.RED);

        lines.clear();
        for (Node neighborNode : neighbors.values()) {
            if (neighborNode != null) {
                Line newLine = new Line(this, 0, position, neighborNode.getPosition());
                newLine.setColor(Color.WHITE);
                newLine.setStrokeWidth(5);
                lines.add(newLine);
            }
        }
    }

    public Position2D getPosition() {
        return position;
    }

    public Map<Direction, Node> getNeighbors() {
        return neighbors;
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void physicsUpdate(double delta) {

    }
}
