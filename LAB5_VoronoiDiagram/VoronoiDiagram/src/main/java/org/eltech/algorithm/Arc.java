package org.eltech.algorithm;

public class Arc{

    public enum Type {
        ARC, QUERY
    }

    private BreakPoint leftBreakPoint;
    private BreakPoint rightBreakPoint;
    private Point2D site;
    private Type type;

    public Arc(BreakPoint leftBreakPoint, BreakPoint right) {
        this.type = Type.ARC;
        this.leftBreakPoint = leftBreakPoint;
        this.rightBreakPoint = right;
        this.site = (leftBreakPoint != null) ? leftBreakPoint.getSite2() : right.getSite1();
    }

    public Arc(Point2D site, Type type) {
        this.type = type;
        this.site = site;
        this.leftBreakPoint = null;
        this.rightBreakPoint = null;
    }

    public Arc(Point2D site) {
        this(site, Type.ARC);
    }

    public Point2D getLeftBeachLinePoint(double sweepLineY) {
        return (leftBreakPoint != null) ? leftBreakPoint.getBeachLinePoint(sweepLineY) : Point2D.LEFT_TOP;
    }

    public Point2D getRightBeachLinePoint(double sweepLineY) {
        return (rightBreakPoint != null) ? rightBreakPoint.getBeachLinePoint(sweepLineY) : Point2D.RIGHT_TOP;
    }

    public Point2D getSite() {
        return site;
    }

    public BreakPoint getLeftBreakPoint() {
        return leftBreakPoint;
    }

    public BreakPoint getRightBreakPoint() {
        return rightBreakPoint;
    }

    public void setLeftBreakPoint(BreakPoint leftBreakPoint) {
        this.leftBreakPoint = leftBreakPoint;
    }

    public void setRightBreakPoint(BreakPoint rightBreakPoint) {
        this.rightBreakPoint = rightBreakPoint;
    }

    public Type getType() {
        return type;
    }
}