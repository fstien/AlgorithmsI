
public class BruteCollinearPoints {

    private LineSegment[] lineSegments;
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
        boolean segmentFoundAlready;
        double currentSlope;

        for (Point p1 : points) {
            for (Point p2 : points) {
                for (Point p3 : points) {
                    for (Point p4 : points) {
                        if (p1 != p2 && p1 != p3 && p1 != p4) {

                            if (p1.slopeTo(p4) == p2.slopeTo(p3)) {

                                currentSlope = p1.slopeTo(p4);
                                segmentFoundAlready = false;

                                for (double slope : segmentSlopes)
                                    if (slope == currentSlope) segmentFoundAlready = true;


                                if (!segmentFoundAlready) {
                                    lineSegments[segCount] = new LineSegment(p1, p2);
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
}