package com.kadme.test.util;

import com.kadme.test.model.Line;
import com.kadme.test.model.Point;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FindIntersectingPointTest {

    private static final Point A = new Point(1, 1);
    private static final Point B = new Point(4, 4);
    private static final Point C = new Point(1, 8);
    private static final Point D = new Point(2, 4);

    private static final Point E = new Point(0, 1);
    private static final Point F = new Point(0, 4);
    private static final Point G = new Point(1, 8);
    private static final Point H = new Point(1, 4);



    @Test
    void findIntersectionPoint() {

        FindIntersectingPoint findIntersection = new FindIntersectingPoint();

        Point p1 = findIntersection.findIntersectionPoint(new Line(A, B), new Line(C, D));
        Point p2 = findIntersection.findIntersectionPoint(new Line(E, F), new Line(G, H));

        Assertions.assertEquals(new Point(2.4, 2.4), p1);
        Assertions.assertEquals(new Point(Double.MAX_VALUE, Double.MAX_VALUE), p2);

    }
}
