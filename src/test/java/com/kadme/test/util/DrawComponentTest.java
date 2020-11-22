package com.kadme.test.util;

import com.kadme.test.model.Line;
import com.kadme.test.model.Point;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static com.kadme.test.util.OutlineBuilderConstants.*;

class DrawComponentTest {

    @Test
    void draw() throws InterruptedException {

        final Set<Line> randomLines = new GenerateRandomLines().generateRandomLines(MIN_VALUE_FOR_POINT_GENERATION,
                MAX_VALUE_FOR_POINT_GENERATION, LINE_RANGE);
        final List<Point> randomPoints = new GenerateRandomPoints().generateRandomPoints(OutlineBuilderConstants.MIN_VALUE_FOR_POINT_GENERATION,
                OutlineBuilderConstants.MAX_VALUE_FOR_POINT_GENERATION, OutlineBuilderConstants.POINT_RANGE);
        DrawComponent drawComponent = new DrawComponent(randomLines, randomPoints);
        drawComponent.draw();

        Thread.sleep(1000);
    }

}

