package com.kadme.test.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class GenerateRandomCoordinateTest {

    @Test
    void randomCoordinate() {
        double coordinate = GenerateRandomCoordinate.randomCoordinate(0, 10);
        Assertions.assertTrue(0 <= coordinate && coordinate <= 10, "coordinate not in set range");
    }
}
