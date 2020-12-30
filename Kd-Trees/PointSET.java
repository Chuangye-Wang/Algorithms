/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;

public class PointSET {
    private SET<Point2D> set = new SET<Point2D>();

    // construct an empty set of points
    public PointSET() {
        //
    }

    // is the set empty?
    public boolean isEmpty() {
        return set.isEmpty();
    }

    // number of points in the set
    public int size() {
        return set.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("point cannot be null");
        if (!set.contains(p)) set.add(p);
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("point cannot be null");
        return set.contains(p);
    }

    // draw all points to standard draw
    public void draw() {
        if (!set.isEmpty()) {
            for (Point2D point : set) {
                StdDraw.filledCircle(point.x(), point.y(), 0.005);
            }
        }
    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("point cannot be null");
        ArrayList<Point2D> pointsInside = new ArrayList<Point2D>();

        for (Point2D point : set) {
            if (rect.contains(point)) {
                pointsInside.add(point);
            }
        }

        return pointsInside;
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("point cannot be null");
        if (set.isEmpty()) return null;
        double minDis = Double.POSITIVE_INFINITY;
        double dis;
        Point2D nearestPoint = new Point2D(0, 0);

        for (Point2D point : set) {
            dis = point.distanceSquaredTo(p);
            if (dis < minDis) {
                minDis = dis;
                nearestPoint = point;
            }
        }

        return nearestPoint;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        PointSET ps = new PointSET();
        System.out.println(ps.size());
        ps.insert(new Point2D(0.7, 0.2));
        ps.insert(new Point2D(0.5, 0.4));
        ps.insert(new Point2D(0.2, 0.3));
        ps.insert(new Point2D(0.4, 0.7));
        ps.insert(new Point2D(0.9, 0.6));
        System.out.println(ps.size());
        System.out.println(ps.contains(new Point2D(0.5, 0.2)));
        System.out.println(ps.nearest(new Point2D(0.7, 0.2)));

        // StdDraw.rectangle(1,1,0.5, 0.5);
        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        ps.draw();

    }
}