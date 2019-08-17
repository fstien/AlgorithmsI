import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {

    private LineSegment[] lineSegments;
    private int segCount = 0;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();

        for (Point p : points) {
            if (p == null) throw new IllegalArgumentException();
        }

        for (Point p1 : points)
            for (Point p2 : points)
                if (p1 != p2) {
                    if (p1.compareTo(p2) == +0.0) throw new IllegalArgumentException();
                }

        int pointCount = points.length;
        Point[] otherPoints = new Point[pointCount -1];

        for (Point p : points) {

            int j = 0;
            for (int i = 0; i < pointCount; i++) {
                if (points[i] != p) {
                    otherPoints[j] = points[i];
                    j++;
                }
            }

            Arrays.sort(otherPoints, p.slopeOrder());



        }

    }

    private LineSegment findLongest(Point... points) {
        double biggestDist = 0;
        LineSegment retSeg = null;

        for (Point pointA : points) {
            for (Point pointB : points) {
                if (this.distance(pointA, pointB) > biggestDist) {
                    retSeg = new LineSegment(pointA, pointB);
                }
            }
        }

        return retSeg;
    }

    private double distance(Point a, Point b) {
        String[] A = a.toString().split(", ");
        String[] B = b.toString().split(", ");

        int xA = Integer.parseInt(A[0].substring(1));
        int yA = Integer.parseInt(A[1].substring(0, A[1].length()-1));

        int xB = Integer.parseInt(B[0].substring(1));
        int yB = Integer.parseInt(B[1].substring(0, B[1].length()-1));

        return Math.sqrt( Math.pow( xA-xB, 2) + Math.pow( yA-yB, 2)  );
    }

    // the number of line segments
    public int numberOfSegments() {
        return segCount;
    }

    // the line segments
    public LineSegment[] segments() {
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
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}