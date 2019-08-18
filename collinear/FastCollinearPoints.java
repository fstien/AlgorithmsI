import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {

    private final LineSegment[] lineSegments;
    private int segCount = 0;

    private Point[] usedPoints;
    private int x = 0;

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

        lineSegments = new LineSegment[points.length];
        usedPoints = new Point[points.length];

        double[] segmentSlopes = new double[points.length];
        double espsilon = 0.0000000001;
        for (int i = 0; i < segmentSlopes.length; i++) {
            segmentSlopes[i] = espsilon;
        }
        boolean slopeFoundAlready;
        double currentSlope;

        int pointCount = points.length;

        Point[] otherPoints = new Point[pointCount - 1];
        double[] slopes = new double[pointCount - 1];

        Point p;

        for (int ip = 0; ip < pointCount; ip++) {
            p = points[ip];

            int j = 0;
            for (int i = 0; i < pointCount; i++) {
                if (points[i] != p) {
                    otherPoints[j] = points[i];
                    j++;
                }
            }

            Arrays.sort(otherPoints, p.slopeOrder());

            for (int i = 0; i < slopes.length; i++) {
                slopes[i] = p.slopeTo(otherPoints[i]);
            }

            double previous = slopes[0];
            double popular = slopes[0];
            int count = 1;
            int maxCount = 1;

            for (int i = 1; i < slopes.length; i++) {
                if (doubleEqual(slopes[i], previous))
                    count++;
                else {
                    if (count > maxCount) {
                        popular = slopes[i-1];
                        maxCount = count;
                    }
                    previous = slopes[i];
                    count = 1;
                }
            }

            double popSlope = count > maxCount ? slopes[slopes.length-1] : popular;
            int popCount = count > maxCount ? count : maxCount;

            if (popCount >= 3) {
                Point[] colPoints = new Point[popCount + 1];

                colPoints[0] = p;
                int z = 0;
                for (int i = 0; i < slopes.length; i++) {
                    if (doubleEqual(slopes[i], popSlope)) {
                        colPoints[z+1] = otherPoints[i];
                        z++;
                    }
                }
                Point[] segPoints = findLongest(colPoints);

                currentSlope = colPoints[0].slopeTo(colPoints[1]);
                slopeFoundAlready = false;

                for (double slope : segmentSlopes) {
                    if (doubleEqual(slope, currentSlope)) {
                        slopeFoundAlready = true;
                    }
                }

                if (!slopeFoundAlready || this.allPointAlreadyFound(colPoints)) {
                    lineSegments[segCount] = new LineSegment(segPoints[0], segPoints[1]);
                    this.found(segPoints);
                    segCount++;
                }
            }
        }
    }

    private boolean allPointAlreadyFound(Point... points) {
        int pointCount = 0;

        for (int i = 0; i < points.length; i++) {
            for (Point point : usedPoints) {
                if (points[i].equals(point)) {
                    pointCount++;
                }
            }
        }
        return pointCount == points.length;
    }

    private void found(Point... points) {
        for (Point point : points) {
            if(!Arrays.asList(usedPoints).contains(point)) {
                usedPoints[x] = point;
                x++;
            }
        }
    }

    private boolean doubleEqual(double double1, double double2) {
        return Math.abs(double1 - double2) < 0.0000000001;
    }

    private Point[] findLongest(Point... points) {
        double biggestDist = 0;
        Point retA = null;
        Point retB = null;

        for (Point pointA : points) {
            for (Point pointB : points) {
                if (this.distance(pointA, pointB) > biggestDist) {
                    retA = pointA;
                    retB = pointB;
                    biggestDist = this.distance(pointA, pointB);
                }
            }
        }

        return new Point[] {retA, retB};
    }

    private double distance(Point a, Point b) {
        String[] stringA = a.toString().split(", ");
        String[] stringB = b.toString().split(", ");

        int xA = Integer.parseInt(stringA[0].substring(1));
        int yA = Integer.parseInt(stringA[1].substring(0, stringA[1].length()-1));

        int xB = Integer.parseInt(stringB[0].substring(1));
        int yB = Integer.parseInt(stringB[1].substring(0, stringB[1].length()-1));

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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}