/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;


public class KdTree {
    // private SET<Point2D> set = new SET<Point2D>();
    private Node root;
    private int size = 0;
    private static final boolean X = true;
    // private final boolean Y = false;

    // construct an empty set of points
    public KdTree() {
        //
    }

    private class Node {
        private Node left = null, right = null;
        private final double key;
        private final Point2D point2D;
        // private boolean coordinate;
        public Node(double key, Point2D p) {
            this.key = key;
            this.point2D = p;
            // this.coordinate = b;
        }
    }

    // is the set empty?
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set
    public int size() {
        return size;
    }

    // add the point to the tree (if it is not already in the tree)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException("point cannot be null");
        root = put(root, p.x(), p, X);
    }

    private Node put(Node node, double key, Point2D p, boolean b) {
        if (node == null) {
            size++;
            return new Node(key, p);
        }
        int comp = Double.compare(key, node.key);
        if (b == X) key = p.y();
        else key = p.x();
        if (comp < 0) node.left = put(node.left, key, p, !b);
        if (comp > 0) node.right = put(node.right, key, p, !b);
        if (comp == 0) {
            if (!p.equals(node.point2D)) node.right = put(node.right, key, p, !b);
        }

        return node;
    }

    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("point cannot be null");
        return checkContain(root, p.x(), p, X);
    }

    private boolean checkContain(Node node, double key, Point2D p, boolean b) {
        if (node == null) {
            return false;
        }
        boolean contain = false;

        int comp = Double.compare(key, node.key);
        if (b == X) key = p.y();
        else key = p.x();
        if (comp < 0) contain = checkContain(node.left, key, p, !b);
        if (comp > 0) contain = checkContain(node.right, key, p, !b);
        if (comp == 0) {
            if (p.equals(node.point2D)) return true;
            else contain = checkContain(node.right, key, p, !b);
        }

        return contain;

    }

    // draw all points to standard draw
    public void draw() {
        drawPoint2D(root);
    }

    private void drawPoint2D(Node node) {
        if (node == null) return;
        StdDraw.filledCircle(node.point2D.x(), node.point2D.y(), 0.005);
        drawPoint2D(node.left);
        drawPoint2D(node.right);

    }

    // all points that are inside the rectangle (or on the boundary)
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("rect cannot be null");
        ArrayList<Point2D> pointsInside = new ArrayList<Point2D>();
        rangeSearch(root, rect, X, pointsInside);

        return pointsInside;
    }

    private void rangeSearch(Node node, RectHV rect, boolean b, ArrayList<Point2D> arrayList) {
        if (node == null) return ;
        double keyMin, keyMax;
        if (b == X) {
            keyMin = rect.xmin();
            keyMax = rect.xmax();
        }
        else {
            keyMin = rect.ymin();
            keyMax = rect.ymax();
        }
        if (rect.contains(node.point2D)) {
            arrayList.add(node.point2D);
            rangeSearch(node.left, rect, !b, arrayList);
            rangeSearch(node.right, rect, !b, arrayList);
        }
        else {
            if (node.key > keyMax) {
                rangeSearch(node.left, rect, !b, arrayList);
            }
            else if (node.key < keyMin) {
                rangeSearch(node.right, rect, !b, arrayList);
            }
            else {
                rangeSearch(node.left, rect, !b, arrayList);
                rangeSearch(node.right, rect, !b, arrayList);
            }
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("point cannot be null");
        if (root == null) return null;
        double minDis = Double.POSITIVE_INFINITY;
        Point2D point = new Point2D(0, 0);
        Point2D nearestPoint;
        nearestPoint = nearestSearch(root, p, X, minDis, point);

        return nearestPoint;
    }

    private Point2D nearestSearch(Node node, Point2D p, boolean b, double minDis, Point2D target) {
        if (node == null) return target;
        // System.out.println("node.point2D = " + node.point2D);
        double key;
        if (b == X) {
            key = p.x();
        }
        else {
            key = p.y();
        }
        Point2D nextTarget = target;
        Point2D nextTarget1 = target;
        Point2D nextTarget2 = target;
        double nextDis = minDis;
        double dis = p.distanceSquaredTo(node.point2D);

        if (dis < nextDis) {
            nextTarget = node.point2D;
            nextTarget1 = node.point2D;
            nextTarget2 = node.point2D;
            nextDis = dis;
        }
        if (key < node.key) {
            nextTarget1 = nearestSearch(node.left, p, !b, nextDis, nextTarget1);
            if (nextDis > (node.key - key) * (node.key - key)) {
                nextTarget2 = nearestSearch(node.right, p, !b, nextDis, nextTarget2);
            }
            if (p.distanceSquaredTo(nextTarget1) > p.distanceSquaredTo(nextTarget2)) nextTarget = nextTarget2;
            else nextTarget = nextTarget1;
        }
        else {
            nextTarget1 = nearestSearch(node.right, p, !b, nextDis, nextTarget1);
            if (nextDis > (node.key - key) * (node.key - key)) {
                nextTarget2 = nearestSearch(node.left, p, !b, nextDis, nextTarget2);
            }
            if (p.distanceSquaredTo(nextTarget1) > p.distanceSquaredTo(nextTarget2)) nextTarget = nextTarget2;
            else nextTarget = nextTarget1;
        }

        return nextTarget;
    }

    // unit testing of the methods (optional)
    public static void main(String[] args) {
        KdTree ps = new KdTree();
        System.out.println(ps.size());
        /*
        ps.insert(new Point2D(0.7, 0.2));
        ps.insert(new Point2D(0.5, 0.4));
        ps.insert(new Point2D(0.2, 0.3));
        ps.insert(new Point2D(0.4, 0.7));
        ps.insert(new Point2D(0.9, 0.6));
        System.out.println(ps.size());
        */
        ps.insert(new Point2D(1, 0.5));
        ps.insert(new Point2D(0.0625, 0.5625));
        ps.insert(new Point2D(0.625, 0.9375));
        ps.insert(new Point2D(0.3125, 0.3125));
        ps.insert(new Point2D(0.4375, 0.375));
        ps.insert(new Point2D(0.9375, 0.625));
        ps.insert(new Point2D(0.5625, 0.0));
        ps.insert(new Point2D(0.5, 0.875));
        ps.insert(new Point2D(0.25, 0.125));
        ps.insert(new Point2D(0.0, 0.4375));

        System.out.println(ps.contains(new Point2D(0.5, 0.4)));
        System.out.println("nearest point is " + ps.nearest(new Point2D(0.125, 0.8125)));

        StdDraw.setXscale(0, 1);
        StdDraw.setYscale(0, 1);
        ps.draw();
    }
}
