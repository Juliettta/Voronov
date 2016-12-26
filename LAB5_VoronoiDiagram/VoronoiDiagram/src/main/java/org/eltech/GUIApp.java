package org.eltech;

import org.eltech.algorithm.Point2D;
import org.eltech.algorithm.VoronoiDiagram;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class GUIApp {
    public static final int SEGMENTS_COUNT = 1000;

    public static void main(String[] args) {
        Random rand = new Random();
        ArrayList<Point2D> points = new ArrayList<>(SEGMENTS_COUNT);
        for (int i = 0; i < SEGMENTS_COUNT; i++) {
            points.add(i, new Point2D(
                    rand.nextDouble() * (SegmentsView.WIDTH),
                    rand.nextDouble() * (SegmentsView.HEIGHT)
            ));
        }

        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(new SegmentsView(points, VoronoiDiagram.createDiagram(points)));
            frame.pack();
            frame.setVisible(true);
        });
    }
}
