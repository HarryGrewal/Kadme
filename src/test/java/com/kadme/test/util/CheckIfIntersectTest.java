package com.kadme.test.util;

import com.kadme.test.model.Point;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class CheckIfIntersectTest {

    private final CheckIfIntersect checkIfIntersect = new CheckIfIntersect();

    @Test
    void doIntersect() {
        Point p1 = new Point(1, 1);
        Point q1 = new Point(10, 1);
        Point p2 = new Point(1, 2);
        Point q2 = new Point(10, 2);

        Assertions.assertFalse(checkIfIntersect.doIntersect(p1, q1, p2, q2));

        p1 = new Point(10, 1);
        q1 = new Point(0, 10);
        p2 = new Point(0, 0);
        q2 = new Point(10, 10);

        Assertions.assertTrue(checkIfIntersect.doIntersect(p1, q1, p2, q2));

        p1 = new Point(195.0, 50.0);
        q1 = new Point(195.0, 450.0);
        p2 = new Point(190.0, 50.0);
        q2 = new Point(195.0, 450);

        Assertions.assertTrue(checkIfIntersect.doIntersect(p1, q1, p2, q2));

        p1 = new Point(-5, -5);
        q1 = new Point(0, 0);
        p2 = new Point(1, 1);
        q2 = new Point(10, 10);

        Assertions.assertFalse(checkIfIntersect.doIntersect(p1, q1, p2, q2));

    }
}
