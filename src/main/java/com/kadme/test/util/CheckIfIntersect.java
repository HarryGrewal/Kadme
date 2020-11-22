package com.kadme.test.util;

import com.kadme.test.model.Point;

/*
Two segments (p1,q1) and (p2,q2) intersect if and only if one of the following two conditions is verified

1. General Case:
– (p1, q1, p2) and (p1, q1, q2) have different orientations and
– (p2, q2, p1) and (p2, q2, q1) have different orientations.

2. Special Case
– (p1, q1, p2), (p1, q1, q2), (p2, q2, p1), and (p2, q2, q1) are all collinear and
– the x-projections of (p1, q1) and (p2, q2) intersect
– the y-projections of (p1, q1) and (p2, q2) intersect
*/
public class CheckIfIntersect {

    public boolean doIntersect(Point p1, Point q1, Point p2, Point q2) {
        // Find the four orientations needed for general and 
        // special cases 
        int o1 = checkOrientation(p1, q1, p2);
        int o2 = checkOrientation(p1, q1, q2);
        int o3 = checkOrientation(p2, q2, p1);
        int o4 = checkOrientation(p2, q2, q1);

        // General case 
        if (o1 != o2 && o3 != o4)
            return true;

        // Special Cases 
        // p1, q1 and p2 are collinear and p2 lies on segment p1q1 
        if (o1 == 0 && onSegment(p1, p2, q1)) return true;

        // p1, q1 and q2 are collinear and q2 lies on segment p1q1 
        if (o2 == 0 && onSegment(p1, q2, q1)) return true;

        // p2, q2 and p1 are collinear and p1 lies on segment p2q2 
        if (o3 == 0 && onSegment(p2, p1, q2)) return true;

        // p2, q2 and q1 are collinear and q1 lies on segment p2q2 
        if (o4 == 0 && onSegment(p2, q1, q2)) return true;

        return false; // Doesn't fall in any of the above cases 
    }

    // To find orientation of ordered triplet (p, q, r).
    private int checkOrientation(Point p, Point q, Point r) {

        {
            // referenced https://www.geeksforgeeks.org/orientation-3-ordered-points/ for below formula.
            double val = (q.getY() - p.getY()) * (r.getX() - q.getX()) - (q.getX() - p.getX()) * (r.getY() - q.getY());

            if (val == 0) return 0; // collinear

            return (val > 0) ? 1 : 2; // clock or counter clock wise
        }
    }

    // Given three collinear points p, q, r,
    // the function checks if point q lies on line segment 'pr'
    private boolean onSegment(Point p, Point q, Point r) {
        if (q.getX() <= Math.max(p.getX(), r.getX()) && q.getX() >= Math.min(p.getX(), r.getX()) &&
                q.getY() <= Math.max(p.getY(), r.getY()) && q.getY() >= Math.min(p.getY(), r.getY()))
            return true;

        return false;
    }

}
