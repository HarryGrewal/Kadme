package com.kadme.test.service.impl;

import com.kadme.test.model.Line;
import com.kadme.test.util.LinesGenerator;
import com.kadme.test.util.OutlineBuilderConstants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

class FindOrderImplTest {

    private LinesGenerator linesGenerator = new LinesGenerator();

    @BeforeEach
    void setUp() {
        final Set<Line> lines = linesGenerator.generateRandomLines(OutlineBuilderConstants.MIN_VALUE_FOR_POINT_GENERATION,
                OutlineBuilderConstants.MAX_VALUE_FOR_POINT_GENERATION, OutlineBuilderConstants.LINE_RANGE);
    }

    @Test
    void getLineOrderOfGroupInGroups() {
    }

    @Test
    void getLineOrderOfSingleGroup() {
    }
}
