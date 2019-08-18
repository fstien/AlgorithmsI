import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {

    private final LineSegment[] lineSegments;
    private int segCount = 0;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException();

        for (Point p : points) {
            if (p == null) throw new IllegalArgumentException();
        }

        for (Point p1 : points)
            for (Point p2 : points)
                if (p1 != p2) {
                    if (p1.compareTo(p2) == +0.0) throw new IllegalArgumentException();
                }

        lineSegments = new LineSegment[points.length];

        double[] segmentSlopes = new double[points.length];

        double espsilon = 0.0000000001;
        for (int i = 0; i < segmentSlopes.length; i++) {
            segmentSlopes[i] = espsilon;
        }

        boolean segmentFoundAlready;
        double currentSlope;

        for (Point p1 : points) {
            for (Point p2 : points) {
                for (Point p3 : points) {
                    for (Point p4 : points) {
                        if (p1 != p2 && p1 != p3 && p1 != p4
                         && p2 != p3 && p2 != p4 && p3 != p4) {

                            if (doubleEqual(p1.slopeTo(p2), p1.slopeTo(p3))
                             && doubleEqual(p1.slopeTo(p2), p1.slopeTo(p4))) {

                                currentSlope = p1.slopeTo(p4);
                                segmentFoundAlready = false;

                                for (double slope : segmentSlopes) {
                                    if (doubleEqual(slope, currentSlope)
                                     || doubleEqual(-slope, currentSlope)) {
                                        segmentFoundAlready = true;
                                    }
                                }

                                if (!segmentFoundAlready) {
                                    // determine the longest segment
                                    LineSegment lineSegment = this.findLongest(p1, p2, p3, p4);
                                    lineSegments[segCount] = lineSegment;
                                    segmentSlopes[segCount] = currentSlope;
                                    segCount++;
                                }

                            }

                        }
                    }
                }
            }
        }

    }

    private boolean doubleEqual(double double1, double double2) {
        return Math.abs(double1 - double2) < 0.0000000001;
    }

    private LineSegment findLongest(Point... points) {
        double biggestDist = 0;
        LineSegment retSeg = null;

        for (Point pointA : points) {
            for (Point pointB : points) {
                if (this.distance(pointA, pointB) > biggestDist) {
                    retSeg = new LineSegment(pointA, pointB);
                    biggestDist = this.distance(pointA, pointB);
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

        return Math.sqrt(Math.pow(xA-xB, 2) + Math.pow(yA-yB, 2));
    }

    // the number of line segments
    public int numberOfSegments() {
        return segCount;
    }

    // the line segments
    public LineSegment[] segments() {

        LineSegment[] returnSegs = new LineSegment[segCount];

        for (int i = 0; i < segCount; i++) {
            returnSegs[i] = lineSegments[i];
        }

        return returnSegs;
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
        StdDraw.show();
    }
}