package org.eltech.algorithm;

public class GeometryUtils {

    public static Point2D midpoint(Point2D p1, Point2D p2) {
        return new Point2D(
                (p1.getX() + p2.getX()) / 2.0,
                (p1.getY() + p2.getY()) / 2.0);
    }

    public static int ccw(Point2D a, Point2D b, Point2D c) {
        double area = (b.getX() - a.getX()) * (c.getY() - a.getY()) -
                (b.getY() - a.getY()) * (c.getX() - a.getX());

        return (int) Math.signum(area);
    }

    public static double distanceTo(Point2D point, Point2D that) {
        return Math.sqrt((point.getX() - that.getX()) * (point.getX() - that.getX())
                + (point.getY() - that.getY()) * (point.getY() - that.getY()));
    }
}
