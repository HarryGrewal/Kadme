package com.kadme.test.util;

import com.kadme.test.model.Line;
import com.kadme.test.model.Point;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.kadme.test.util.OutlineBuilderConstants.INVALID_POINT;

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
        Point p1, p2, q1, q2;
        FindIntersectingPoint findIntersection = new FindIntersectingPoint();

        p1 = findIntersection.findIntersectionPoint(new Line(A, B), new Line(C, D));
        p2 = findIntersection.findIntersectionPoint(new Line(E, F), new Line(G, H));

        Assertions.assertEquals(new Point(2.4, 2.4), p1);
        Assertions.assertEquals(INVALID_POINT, p2);

        p1 = new Point(195.0, 50.0);
        q1 = new Point(195.0, 450.0);
        p2 = new Point(190.0, 50.0);
        q2 = new Point(195.0, 450);

        Assertions.assertTrue(findIntersection.ifIntersect(new Line(p1, q1), new Line(p2, q2)));


    }
}
