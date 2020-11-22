package com.kadme.test.util;

import com.kadme.test.model.Line;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

class FindGroupsTest {

    private GenerateRandomLines generateRandomLines = new GenerateRandomLines();

    @BeforeEach
    void setUp() {
        final Set<Line> lines = generateRandomLines.generateRandomLines(OutlineBuilderConstants.MIN_VALUE_FOR_POINT_GENERATION,
                OutlineBuilderConstants.MAX_VALUE_FOR_POINT_GENERATION, OutlineBuilderConstants.LINE_RANGE);
    }

    @Test
    void findGroups() {
        //TODO
        FindGroups findGroups = new FindGroups();

    }


}
