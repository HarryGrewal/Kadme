package com.kadme.test.util;

import com.kadme.test.model.Point;

import static com.kadme.test.util.GenerateRandomLines.GenerateRandomPoints.GenerateRandomCoordinate.round;
import static com.kadme.test.util.OutlineBuilderConstants.DECIMAL_RANGE;

public class FindDistanceBetweenLines {
    double distanceBetweenPoints(Point p1, Point p2) {
        /*
        * The horizontal distance a is (xA − xB)
          The vertical distance b is (yA − yB)
          Start with:	c2 = a2 + b2
          Put in the calculations for a and b:	c2 = (xA − xB)2 + (yA − yB)2
          Square root of both sides: c = square root of [(xA-xB)^2+(yA-yB)^2]
        * */
        final double xA = p1.getX();
        final double yA = p1.getY();
        final double xB = p2.getX();
        final double yB = p2.getY();
        return round(Math.sqrt(Math.pow((xA - xB), 2) + Math.pow((yA - yB), 2)), DECIMAL_RANGE);
    }
}
