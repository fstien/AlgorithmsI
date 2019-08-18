import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;


public class BruteCollinearPoints {

    private final LineSegment[] lineSegments;
    private int segCount = 0;

    private Point[] usedPoints;
    private int x = 0;

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

        usedPoints = new Point[points.length];

        for (Point p1 : points) {
            for (Point p2 : points) {
                for (Point p3 : points) {
                    for (Point p4 : points) {
                        if (p1 != p2 && p1 != p3 && p1 != p4
                         && p2 != p3 && p2 != p4 && p3 != p4) {

                            if (doubleEqual(p1.slopeTo(p2), p1.slopeTo(p3))
                             && doubleEqual(p1.slopeTo(p2), p1.slopeTo(p4))) {

                                if (!this.includesPointAlreadyFound(p1, p2, p3, p4)) {
                                    // determine the longest segment
                                    Point[] segPoints = this.findLongest(p1, p2, p3, p4);
                                    lineSegments[segCount] = new LineSegment(segPoints[0], segPoints[1]);
                                    this.found(segPoints[0], segPoints[1]);
                                    segCount++;
                                }

                            }

                        }
                    }
                }
            }
        }

    }

    private boolean includesPointAlreadyFound(Point... points) {
        for (int i = 0; i < points.length; i++) {
            for (Point point : usedPoints) {
                if (points[i].equals(point)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void found(Point... points) {
        for (Point point : points) {
            usedPoints[x] = point;
            x++;
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}