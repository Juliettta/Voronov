package org.eltech.algorithm;

public class BreakPoint {

    private Point2D site1;
    private Point2D site2;
    private Edge edge;

    private double cacheSweepLineY;
    private Point2D cachePoint;

    public BreakPoint(Point2D left, Point2D right, Edge edge) {
        this.site1 = left;
        this.site2 = right;
        this.edge = edge;
    }

    public void finishEdge(Point2D center) {
        if (edge.getP1() == null) {
            edge.setP1(center);
        } else {
            edge.setP2(center);
        }
    }

    public void finishEdge(double sweepLineY) {
        Point2D p = this.getBeachLinePoint(sweepLineY);
        if (edge.getP1() == null) {
            edge.setP1(p);
        } else {
            edge.setP2(p);
        }
    }

    public Point2D getBeachLinePoint(double sweepLineY) {
        if (sweepLineY == cacheSweepLineY) {
            return cachePoint;
        }
        cacheSweepLineY = sweepLineY;

        double x, y;
        if (site1.getY() == site2.getY()) {
            x = (site1.getX() + site2.getX()) / 2.0;
            y = ((x - site1.getX()) * (x - site1.getX()) + site1.getY() * site1.getY() - sweepLineY * sweepLineY) / (2 * (site1.getY() - sweepLineY));
        } else {
            double px = (site1.getY() > site2.getY()) ? site1.getX() : site2.getX();
            double py = (site1.getY() > site2.getY()) ? site1.getY() : site2.getY();
            double m = edge.getK();
            double b = edge.getB();

            double d = 2 * (py - sweepLineY);

            double A = 1;
            double B = -2 * px - d * m;
            double C = px * px + py * py - sweepLineY * sweepLineY - d * b;
            int sign = (site1.getY() > site2.getY()) ? -1 : 1;
            double det = B * B - 4 * A * C;

            if (det <= 0) {
                x = -B / (2 * A);
            } else {
                x = (-B + sign * Math.sqrt(det)) / (2 * A);
            }
            y = m * x + b;
        }

        cachePoint = new Point2D(x, y);
        return cachePoint;
    }

    public Edge getEdge() {
        return this.edge;
    }

    public Point2D getSite1() {
        return site1;
    }

    public Point2D getSite2() {
        return site2;
    }
}
