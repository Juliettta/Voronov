package org.eltech.algorithm;

import java.util.*;

public class VoronoiDiagram {
    private double MIN_DIM;
    private double MAX_DIM;

    private double sweepLineY = Double.POSITIVE_INFINITY;
    private ArcComparator arcComparator = new ArcComparator(sweepLineY);
    private Queue<Event> events = new PriorityQueue<>();
    private TreeMap<Arc, Event> arcs = new TreeMap<>(arcComparator);
    private Set<BreakPoint> breakPoints = new HashSet<>();
    private List<Edge> edgeList = new ArrayList<>();

    public static List<Edge> createDiagram(List<Point2D> points) {
        return new VoronoiDiagram().build(points);
    }

    public void initSweepLineValue(List<Point2D> points) {
        double bottom = Float.POSITIVE_INFINITY;
        double top = Float.NEGATIVE_INFINITY;

        for (Point2D sight : points) {
            double y = sight.getY();
            if (y < bottom) bottom = y;
            if (y > top) top = y;
        }

        MAX_DIM = top + 1;
        MIN_DIM = bottom - 1;
        sweepLineY = MAX_DIM;
        arcComparator.setSweepLineY(sweepLineY);
    }

    private List<Edge> build(List<Point2D> sites) {
        initSweepLineValue(sites);
        for (Point2D site : sites) {
            events.add(new Event(site));
        }

        while (!events.isEmpty()) {
            Event cur = events.poll();
            sweepLineY = cur.getSight().getY();
            arcComparator.setSweepLineY(sweepLineY);
            if (cur.getType() == Event.Type.SITE_EVENT) {
                handleSiteEvent(cur);
            } else {
                handleCircleEvent(cur);
            }
        }

        this.sweepLineY = MIN_DIM;
        for (BreakPoint bp : breakPoints) {
            bp.finishEdge(sweepLineY);
        }
        return edgeList;
    }

    private void handleSiteEvent(Event cur) {
        if (arcs.size() == 0) {
            arcs.put(new Arc(cur.getSight()), null);
            return;
        }

        Map.Entry<Arc, Event> arcEntryAbove = arcs.floorEntry(new Arc(cur.getSight(), Arc.Type.QUERY));

        Event falseEvent = arcEntryAbove.getValue();
        if (falseEvent != null) {
            events.remove(falseEvent);
        }

        Arc arcAbove = arcEntryAbove.getKey();
        BreakPoint breakPointLeft = arcAbove.getLeftBreakPoint();
        BreakPoint breakPointRight = arcAbove.getRightBreakPoint();

        Edge newEdge = new Edge(arcAbove.getSite(), cur.getSight());
        this.edgeList.add(newEdge);

        BreakPoint newBreakPointLeft = new BreakPoint(arcAbove.getSite(),
                cur.getSight(),
                newEdge);
        BreakPoint newBreakPointRight = new BreakPoint(
                cur.getSight(),
                arcAbove.getSite(),
                newEdge);
        breakPoints.add(newBreakPointLeft);
        breakPoints.add(newBreakPointRight);

        Arc leftArc = new Arc(breakPointLeft, newBreakPointLeft);
        Arc centerArc = new Arc(newBreakPointLeft, newBreakPointRight);
        Arc rightArc = new Arc(newBreakPointRight, breakPointRight);

        arcs.remove(arcAbove);
        arcs.put(leftArc, null);
        arcs.put(centerArc, null);
        arcs.put(rightArc, null);

        checkForCircleEvent(leftArc);
        checkForCircleEvent(rightArc);
    }

    private void handleCircleEvent(Event event) {
        Map.Entry<Arc, Event> rightEntry = arcs.higherEntry(event.getArc());
        Map.Entry<Arc, Event> leftEntry = arcs.lowerEntry(event.getArc());


        if (rightEntry != null) {
            Event falseEvent = rightEntry.getValue();
            if (falseEvent != null) events.remove(falseEvent);
            arcs.put(rightEntry.getKey(), null);
        }

        if (leftEntry != null) {
            Event falseEvent = leftEntry.getValue();
            if (falseEvent != null) events.remove(falseEvent);
            arcs.put(leftEntry.getKey(), null);
        }

        arcs.remove(event.getArc());

        event.getArc().getLeftBreakPoint().finishEdge(event.getCenter());
        event.getArc().getRightBreakPoint().finishEdge(event.getCenter());

        breakPoints.remove(event.getArc().getLeftBreakPoint());
        breakPoints.remove(event.getArc().getRightBreakPoint());

        Edge e = new Edge(
                event.getArc().getLeftBreakPoint().getSite1(),
                event.getArc().getRightBreakPoint().getSite2());

        edgeList.add(e);

        if ((leftEntry == null) || (rightEntry == null)) {
            return;
        }

        Arc leftArc = leftEntry.getKey();
        Arc rightArc = rightEntry.getKey();

        e.setP1(event.getCenter());

        BreakPoint newBP = new BreakPoint(
                event.getArc().getLeftBreakPoint().getSite1(),
                event.getArc().getRightBreakPoint().getSite2(),
                e);
        breakPoints.add(newBP);

        rightArc.setLeftBreakPoint(newBP);
        leftArc.setRightBreakPoint(newBP);

        checkForCircleEvent(leftArc);
        checkForCircleEvent(rightArc);
    }

    private void checkForCircleEvent(Arc arc) {
        BreakPoint leftBreakPoint = arc.getLeftBreakPoint();
        BreakPoint rightBreakPoint = arc.getRightBreakPoint();

        if ((leftBreakPoint == null) || (rightBreakPoint == null)) {
            return;
        }

        if (GeometryUtils.ccw(leftBreakPoint.getSite1(), arc.getSite(), rightBreakPoint.getSite2()) != -1) {
            return;
        }

        Point2D circleCenter = leftBreakPoint.getEdge().intersection(rightBreakPoint.getEdge());

        if (circleCenter != null) {
            double radius = GeometryUtils.distanceTo(arc.getSite(), circleCenter);
            Point2D circleEventPoint = new Point2D(circleCenter.getX(), circleCenter.getY() - radius);
            Event ce = new Event(arc, circleEventPoint, circleCenter);
            arcs.put(arc, ce);
            events.add(ce);
        }
    }
}

