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
import java.util.Collections;

public class BruteCollinearPoints {
    private final LineSegment[] allSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Input points array is null");
        }
        for (Point point:points) {
            if (point == null) {
                throw new IllegalArgumentException("Input points array contains null");
            }
        }
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i+1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("Input points array is null");
                }
            }
        }
        ArrayList<LineSegment> segmentsList = new ArrayList<LineSegment>();
        Point[] pointsC = Arrays.copyOf(points, points.length);
        Arrays.sort(pointsC);
        // System.out.println(Arrays.toString(points));
        // System.out.println(points);
        // System.out.println(Arrays.toString(pointsC));
        for (int i = 0; i < (pointsC.length - 3); i++)
            for (int j = i + 1; j < (points.length - 2); j++)
                for (int k = j + 1; k < (pointsC.length - 1); k++)
                    for (int m = k + 1; m < (pointsC.length); m++) {
                        if (pointsC[i].slopeTo(pointsC[j]) == pointsC[i].slopeTo(pointsC[m]) &&
                                pointsC[i].slopeTo(pointsC[j]) == pointsC[i].slopeTo(pointsC[k])) {

                            LineSegment lineSegment1 = new LineSegment(pointsC[i], pointsC[m]);
                            if (!segmentsList.contains(lineSegment1)) {
                                // System.out.println(segmentsList.contains(lineSegment1));
                                segmentsList.add(lineSegment1);
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        ArrayList<LineSegment> segmentsList1 = new ArrayList<LineSegment>();
        Collections.addAll(segmentsList1, collinear.segments());
        boolean b = segmentsList1.contains(collinear.segments()[0]);
        // boolean a = collinear.segments()[1].equals(collinear.segments()[2]);
        System.out.println(segmentsList1 + " " + b);
        // System.out.println(collinear.segments()[1] + " " + collinear.segments()[2] + "true or false = " + b + a);
        StdDraw.show();
    }
}
