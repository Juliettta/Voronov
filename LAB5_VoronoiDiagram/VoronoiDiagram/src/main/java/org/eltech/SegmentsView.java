package org.eltech;

import org.eltech.algorithm.Edge;
import org.eltech.algorithm.Point2D;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SegmentsView extends JPanel {
    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    private List<Point2D> points;
    private List<Edge> edges;

    public SegmentsView(List<Point2D> points, List<Edge> edges) {
        this.points = points;
        this.edges = edges;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(WIDTH, HEIGHT);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        points.forEach((point) -> {
            g.drawOval((int) point.getX() - 2, (int) point.getY() - 2, 4, 4);
        });

        g.setColor(Color.red);
        edges.forEach(edge -> {
            Point2D a = edge.getP1();
            Point2D b = edge.getP2();
            if (Math.abs(a.getY() - b.getY()) > 300)
                return;
            g.drawLine((int) a.getX(), (int) a.getY(), (int) b.getX(), (int) b.getY());
        });
    }
}
