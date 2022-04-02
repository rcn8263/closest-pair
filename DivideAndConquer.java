//author: Ryan Nowak

import java.util.Arrays;

public class DivideAndConquer extends ClosestPairAlg {

    public Triple closestPair(Point[] P) {
        //Construct Px and Py
        Pointx[] px = new Pointx[P.length];
        Pointy[] py = new Pointy[P.length];
        for (int i = 0; i < P.length; i++) {
            px[i] = new Pointx(P[i].x, P[i].y);
            py[i] = new Pointy(P[i].x, P[i].y);
        }
        Arrays.sort(px);
        Arrays.sort(py);

        Triple closest = closestPairRec(px, py);

        return closest;
    }

    private Triple closestPairRec(Point[] px, Point[] py) {
        if (px.length <= 3) {
            //find closest pair by measuring all pairwise distances
            Point p1 = new Point(px[0].x, px[0].y);
            Point p2 = new Point(px[1].x, px[1].y);
            double dist = Double.MAX_VALUE;

            for (Point point1: px) {
                for (Point point2: px) {
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

        //Construct Qx, Qy, Rx, Ry (O(n) time)
        int n1 = px.length / 2; //first half of points
        int n2 = (int) Math.ceil( (double) px.length / 2);  //second half of points
        Pointx[] qx = new Pointx[n1];   //first half of px
        Pointy[] qy = new Pointy[n1];   //first half of px sorted by y
        Pointx[] rx = new Pointx[n2];   //second half of px
        Pointy[] ry = new Pointy[n2];   //second half of px sorted by y
        for (int i = 0; i < n1; i++) {
            qx[i] = new Pointx(px[i].x, px[i].y);
            qy[i] = new Pointy(px[i].x, px[i].y);
        }
        for (int i = 0; i < n2; i++) {
            rx[i] = new Pointx(px[i+n1].x, px[i+n1].y);
            ry[i] = new Pointy(px[i+n1].x, px[i+n1].y);
        }
        Arrays.sort(qy);
        Arrays.sort(ry);

        Triple left = closestPairRec(qx, qy);
        Triple right = closestPairRec(rx, ry);
        Triple closest;
        if (left.dist < right.dist) {
            closest = new Triple(left.p1, left.p2, left.dist);
        }
        else {
            closest = new Triple(right.p1, right.p2, right.dist);
        }
        Double closestDist = closest.dist;


        //maximum x-coordinate of a point in set Q
        int x = qx[qx.length-1].x;

        //L = {(x,y) : x = x∗}
        int L = x;

        //S = points in P within distance closestDist of L.
        int numS = 0;   //number of points that will be in S
        for (Point point: py) {
            if (Math.abs(point.x - L) <= closestDist) { //within the distance of L
                numS++;
            }
        }
        Pointy[] S = new Pointy[numS];
        int idx = 0;
        for (int i = 0; i < py.length; i++) {
            if (Math.abs(py[i].x - L) <= closestDist) { //within the distance of L
                S[idx] = new Pointy(py[i].x, py[i].y);
                idx++;
            }
        }

        //Construct Sy (O(n) time)
        //Arrays.sort(S);

        for (int i = 0; i < S.length; i++) {
            for (int j = 0; j < S.length; j++) {
                if (i == j) {
                    continue;
                }
                else if (S[i].distance(S[j]) < closestDist) {
                    closest.p1 = S[i];
                    closest.p2 = S[j];
                    closest.dist = S[i].distance(S[j]);
                    closestDist = closest.dist;
                }
            }
        }

        return closest;

    }

}
