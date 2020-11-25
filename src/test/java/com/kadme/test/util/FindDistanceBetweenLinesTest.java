package com.kadme.test.util;

import com.kadme.test.model.Point;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FindDistanceBetweenLinesTest {

    @Test
    void distanceBetweenPoints() {
        Point p1 = new Point(3, 2);
        Point p2 = new Point(9, 7);
        FindDistanceBetweenLines findDistanceBetweenLines = new FindDistanceBetweenLines();
        Assertions.assertEquals(findDistanceBetweenLines.distanceBetweenPoints(p1, p2), 7.81);

        p1 = new Point(-3, 5);
        p2 = new Point(7, -1);
        Assertions.assertEquals(findDistanceBetweenLines.distanceBetweenPoints(p1, p2), 11.66);
    }
}
