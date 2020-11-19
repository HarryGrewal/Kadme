package com.kadme.test.util.impl;

/*
Input : A = (1, 1), B = (4, 4)
        C = (1, 8), D = (2, 4)
Output : The intersection of the given lines
         AB and CD is: (2.4, 2.4)

Input : A = (0, 1), B = (0, 4)
        C = (1, 8), D = (1, 4)
Output : Lines are parallel

First of all, let us assume that we have two points (x1, y1) and (x2, y2).
Now, we find the equation of line formed by these points.
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

*Note: This gives the point of intersection of two lines,
 but if we are given line segments instead of lines,
 we have to also recheck that the point so computed actually lies on both the line segments.
 If the line segment is specified by points (x1, y1) and (x2, y2),
 then to check if (x, y) is on the segment we have to just check that
 min (x1, x2) <= x <= max (x1, x2)
 min (y1, y2) <= y <= max (y1, y2)
---------------------------------------------------------------------------------------------------
determinant = a1 b2 - a2 b1
if (determinant == 0)
{
    // Lines are parallel
}
else
{
    x = (c1b2 - c2b1)/determinant
    y = (a1c2 - a2c1)/determinant
}
---------------------------------------------------------------------------------------------------
 */

import com.kadme.test.Line;
import com.kadme.test.Point;
import com.kadme.test.util.Intersection;

public class IntersectionImpl implements Intersection {

    @Override
    public Point findIntersectionPoint(Line L1, Line L2) {

        // Line L1 represented as a1x + b1y = c1
        double a1 = L1.getP2().getY() - L1.getP1().getY();
        double b1 = L1.getP1().getX() - L1.getP2().getX();
        double c1 = a1 * (L1.getP1().getX()) + b1 * (L1.getP1().getY());

        // Line L2 represented as a2x + b2y = c2
        double a2 = L2.getP2().getY() - L2.getP1().getY();
        double b2 = L2.getP1().getX() - L2.getP2().getX();
        double c2 = a2 * (L2.getP1().getX()) + b2 * (L2.getP1().getY());

        double determinant = a1 * b2 - a2 * b1;

        if (determinant == 0) {
            // The lines are parallel. This is simplified
            // by returning a pair of FLT_MAX
            return new Point(Double.MAX_VALUE, Double.MAX_VALUE);
        } else {
            double x = (b2 * c1 - b1 * c2) / determinant;
            double y = (a1 * c2 - a2 * c1) / determinant;
            return new Point(x, y);
        }
    }
}
