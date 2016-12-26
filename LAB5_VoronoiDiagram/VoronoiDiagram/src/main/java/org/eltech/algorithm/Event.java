package org.eltech.algorithm;

public class Event implements Comparable<Event> {

    public enum Type {
        SITE_EVENT,
        CIRCLE_EVENT,
        NULL
    }

    private Point2D sight;
    private Arc arc;
    private Point2D center;
    private Type type;

    public Event(Point2D sight) {
        this.sight = sight;
        this.type = Type.SITE_EVENT;
    }

    public Event(Arc arc, Point2D sight, Point2D center) {
        this.sight = sight;
        this.arc = arc;
        this.center = center;
        this.type = Type.CIRCLE_EVENT;
    }

    public Point2D getSight() {
        return sight;
    }

    public Arc getArc() {
        return arc;
    }

    public Point2D getCenter() {
        return center;
    }

    public Type getType() {
        return type;
    }

    @Override
    public int compareTo(Event obj) {
        if (sight.getY() < obj.sight.getY()) return 1;
        if (sight.getY() > obj.sight.getY()) return -1;
        if (sight.getX() == obj.sight.getX()) return 0;
        return (sight.getX() < obj.sight.getX()) ? -1 : 1;
    }
}
