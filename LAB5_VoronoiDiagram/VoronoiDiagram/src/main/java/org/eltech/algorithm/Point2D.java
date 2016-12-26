package org.eltech.algorithm;

public class Point2D implements Comparable<Point2D> {

    public static final Point2D RIGHT_TOP = new Point2D(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
    public static final Point2D LEFT_TOP = new Point2D(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY);

    private final double x;
    private final double y;

    public Point2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public int compareTo(Point2D o) {
        int xCompare = Double.compare(this.x, o.x);
        return xCompare != 0 ? xCompare : Double.compare(this.y, o.y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Point2D point = (Point2D) o;

        if (Double.compare(point.x, x) != 0) return false;
        return Double.compare(point.y, y) == 0;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "[" + x + ", " + y + "]";
    }
}
