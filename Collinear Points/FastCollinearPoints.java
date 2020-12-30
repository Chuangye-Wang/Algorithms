/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private final LineSegment[] allSegments;

    // finds all line segments containing 4 points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Input points array is null");
        }
        for (Point point:points) {
            if (point == null) {
                throw new IllegalArgumentException("Input points array contains null");
            }
        }
        for (int i = 0; i < points.length - 1; i++){
            for (int j = i+1; j < points.length; j++){
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("Input points array is null");
                }
            }
        }
        ArrayList<LineSegment> segmentsList = new ArrayList<LineSegment>();
        Point[] pointsC = Arrays.copyOf(points, points.length);
        Point[] pointsC0 = Arrays.copyOf(points, points.length);
        // ArrayList<Point> pointsListC;
        Point origin;
        for (int i = 0; i < pointsC0.length; i++) {
            origin = pointsC0[i];
            Arrays.sort(pointsC);
            Arrays.sort(pointsC, origin.slopeOrder());
            int pointsCount = 1;
            Point startPoint = null;
            for (int j = 0; j < pointsC0.length - 1; j++) {
                if (pointsC[j].slopeTo(origin) == pointsC[j+1].slopeTo(origin)) {
                    pointsCount++;
                    if (pointsCount == 2) {
                        startPoint = pointsC[j];
                        pointsCount++;
                    }
                    if (pointsCount >= 4) {
                        if (j + 1 == pointsC0.length - 1) {
                            if (startPoint.compareTo(origin) > 0) {
                                LineSegment lineSegment1 = new LineSegment(origin, pointsC[j + 1]);
                                segmentsList.add(lineSegment1);
                                System.out.println("2222222");
                            }
                            pointsCount = 1;
                        }
                    }
                }
                // p->r->s->q->t, take only p->t segment. Exclude the repeatable segments and record the longest segment.
                else {
                    if (pointsCount >= 4) {
                        if (startPoint.compareTo(origin) > 0) {
                            LineSegment lineSegment1 = new LineSegment(origin, pointsC[j]);
                            segmentsList.add(lineSegment1);
                        }
                    }
                    pointsCount = 1;
                }
            }
        }
        allSegments = segmentsList.toArray(new LineSegment[segmentsList.size()]);
    }

    // the number of line segments
    public int numberOfSegments() {
        return allSegments.length;
    }

    // the line segments
    public LineSegment[] segments() {
        return Arrays.copyOf(allSegments, allSegments.length);
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        System.out.println(Arrays.toString(collinear.segments()));
        System.out.println(collinear.numberOfSegments());
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

