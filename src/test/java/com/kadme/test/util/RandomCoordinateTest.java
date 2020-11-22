package com.kadme.test.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class RandomCoordinateTest {

    @Test
    void randomCoordinate() {
        double coordinate = RandomCoordinate.randomCoordinate(0, 10);
        Assertions.assertTrue(0 <= coordinate && coordinate <= 10, "coordinate not in set range");
    }
}
