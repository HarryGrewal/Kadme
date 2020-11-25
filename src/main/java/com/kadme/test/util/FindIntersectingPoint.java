package com.kadme.test.util;

import com.kadme.test.model.Line;
import com.kadme.test.model.Point;

import static com.kadme.test.util.OutlineBuilderConstants.INVALID_POINT;

/*
* Given two points (x1, y1) and (x2, y2).
The equation of line formed by these points.
Let the given lines be :
a1x + b1y = c1
a2x + b2y = c2
We have to now solve these 2 equations to find the point of intersection.
To solve, we multiply 1. by b2 and 2 by b1
This gives us,
a1b2x + b1b2y = c1b2
a2b1x + b2b1y = c2b1
Subtracting these we get,
(a1b2 – a2b1) x = c1b2 – c2b1
This gives us the value of x. Similarly, we can find the value of y. (x, y) gives us the point of intersection.
* */

public class FindIntersectingPoint {

    public Point findIntersectionPoint(Line l1, Line l2) {

        // Line l1 represented as a1x + b1y = c1
        double a1 = l1.getP2().getY() - l1.getP1().getY();
        double b1 = l1.getP1().getX() - l1.getP2().getX();
        double c1 = a1 * (l1.getP1().getX()) + b1 * (l1.getP1().getY());

        // Line l2 represented as a2x + b2y = c2
        double a2 = l2.getP2().getY() - l2.getP1().getY();
        double b2 = l2.getP1().getX() - l2.getP2().getX();
        double c2 = a2 * (l2.getP1().getX()) + b2 * (l2.getP1().getY());

        double determinant = a1 * b2 - a2 * b1;

        if (determinant == 0) {
            // The lines are parallel. This is simplified
            // by returning a pair of FLT_MAX
            return INVALID_POINT;
        } else {
            double x = (b2 * c1 - b1 * c2) / determinant;
            double y = (a1 * c2 - a2 * c1) / determinant;

            double minX = Double.min(l1.getP1().getX(), l1.getP2().getX());
            double maxX = Double.max(l1.getP1().getX(), l1.getP2().getX());
            double minY = Double.min(l1.getP1().getY(), l1.getP2().getY());
            double maxY = Double.max(l1.getP1().getY(), l1.getP2().getY());

            // To check if (x, y) is on the segment
            return ((minX <= x && maxX >= x) && (minY <= y && maxY >= y)) ?
                    new Point(x, y) : new Point(Double.MAX_VALUE, Double.MAX_VALUE);

        }
    }
}
