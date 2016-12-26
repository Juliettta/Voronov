package org.eltech.algorithm;

import java.util.Comparator;

public class ArcComparator implements Comparator<Arc> {

    private double sweepLineY;

    public ArcComparator(double sweepLineY) {
        this.sweepLineY = sweepLineY;
    }

    public void setSweepLineY(double sweepLineY) {
        this.sweepLineY = sweepLineY;
    }


    @Override
    public int compare(Arc o1, Arc o2) {
        if ((o1.getType() == Arc.Type.QUERY) || (o2.getType() == Arc.Type.QUERY)) {
            Arc arc;
            Arc query;

            if (o1.getType() == Arc.Type.QUERY) {
                query = o1;
                arc = o2;
            } else {
                query = o2;
                arc = o1;
            }

            if (arc.getLeftBeachLinePoint(sweepLineY).getX() > query.getSite().getX()) {
                return -1;
            } else if (arc.getRightBeachLinePoint(sweepLineY).getX() < query.getSite().getX()) {
                return 1;
            } else {
                return 0;
            }
        } else {
            Point2D myLeft = o1.getLeftBeachLinePoint(sweepLineY);
            Point2D myRight = o1.getRightBeachLinePoint(sweepLineY);
            Point2D yourLeft = o2.getLeftBeachLinePoint(sweepLineY);
            Point2D yourRight = o2.getRightBeachLinePoint(sweepLineY);

            if (myLeft.getX() == yourLeft.getX() && myRight.getX() == yourRight.getX()) {
                return 0;
            }
            if (myLeft.getX() >= yourRight.getX()) {
                return 1;
            }
            if (myRight.getX() <= yourLeft.getX()) {
                return -1;
            }
            return GeometryUtils.midpoint(myLeft, myRight).compareTo(GeometryUtils.midpoint(yourLeft, yourRight));
        }
    }
}
