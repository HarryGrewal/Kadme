package com.kadme.test.util.impl;

import com.kadme.test.Line;
import com.kadme.test.Point;
import com.kadme.test.util.Intersection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class IntersectionImplTest {

    @Test
    void findIntersectionPoint() {
        Intersection intersection = new IntersectionImpl();
        Point A = new Point(1, 1);
        Point B = new Point(4, 4);
        Point C = new Point(1, 8);
        Point D = new Point(2, 4);

        Point E = new Point(0, 1);
        Point F = new Point(0, 4);
        Point G = new Point(1, 8);
        Point H = new Point(1, 4);

        Point p1 = intersection.findIntersectionPoint(new Line(A, B), new Line(C, D));
        Point p2 = intersection.findIntersectionPoint(new Line(E, F), new Line(G, H));

        Assertions.assertEquals(new Point(2.4, 2.4), p1);
        Assertions.assertEquals(new Point(Double.MAX_VALUE, Double.MAX_VALUE), p2);


    }
}
