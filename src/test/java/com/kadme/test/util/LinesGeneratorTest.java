package com.kadme.test.util;

import com.kadme.test.model.Line;
import org.junit.jupiter.api.Test;

import java.util.Set;

class LinesGeneratorTest {

    private LinesGenerator linesGenerator = new LinesGenerator();

    @Test
    void generateRandomLines() {
        final Set<Line> lines = linesGenerator.generateRandomLines(OutlineBuilderConstants.MIN_VALUE_FOR_POINT_GENERATION,
                OutlineBuilderConstants.MAX_VALUE_FOR_POINT_GENERATION, OutlineBuilderConstants.LINE_RANGE);
        System.out.println(lines);
    }
}
