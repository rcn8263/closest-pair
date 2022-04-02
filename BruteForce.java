//author: Ryan Nowak

public class BruteForce extends ClosestPairAlg {

    public Triple closestPair(Point[] P) {
        Point p1 = new Point(P[0].x, P[0].y);
        Point p2 = new Point(P[1].x, P[1].y);
        double dist = P[0].distance(P[1]);

        for (Point point1: P) {
            for (Point point2: P) {
                if (point1 == point2) {
                    continue;
                }
                else if (point1.distance(point2) < dist) {
                    p1.x = point1.x;
                    p1.y = point1.y;
                    p2.x = point2.x;
                    p2.y = point2.y;
                    dist = point1.distance(point2);
                }
            }
        }
        return new Triple(p1, p2, dist);
    }

}
