package org.eltech.algorithm;

public class Edge {

    private Point2D site1;
    private Point2D site2;
    private double k;
    private double b;
    private boolean isVertical;
    private Point2D p1;
    private Point2D p2;

    public Edge(Point2D site1, Point2D site2) {
        this.site1 = site1;
        this.site2 = site2;
        isVertical = (site1.getY() == site2.getY());
        if (isVertical) {
            k = b = 0;
        } else {
            k = -(site1.getX() - site2.getX()) / (site1.getY() - site2.getY());
            Point2D midpoint = GeometryUtils.midpoint(site1, site2);
            b = midpoint.getY() - k * midpoint.getX();
        }
    }

    public Point2D intersection(Edge that) {
        if ((this.k == that.k) && (this.b != that.b)) {
            return null;
        }

        double x, y;
        if (this.isVertical) {
            x = (this.site1.getX() + this.site2.getX()) / 2;
            y = that.k * x + that.b;
        } else if (that.isVertical) {
            x = (that.site1.getX() + that.site2.getX()) / 2;
            y = this.k * x + this.b;
        } else {
            x = (that.b - this.b) / (this.k - that.k);
            y = k * x + b;
        }
        return new Point2D(x, y);
    }

    public double getK() {
        return k;
    }

    public double getB() {
        return b;
    }

    public Point2D getP1() {
        return p1;
    }

    public void setP1(Point2D p1) {
        this.p1 = p1;
    }

    public Point2D getP2() {
        return p2;
    }

    public void setP2(Point2D p2) {
        this.p2 = p2;
    }

    @Override
    public String toString() {
        return "{" + site1 + ", " + site2 + "]";
    }
}
