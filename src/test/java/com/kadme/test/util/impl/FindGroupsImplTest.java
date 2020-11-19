package com.kadme.test.util.impl;

import com.kadme.test.Line;
import com.kadme.test.util.FindGroups;
import com.kadme.test.util.LinesGenerator;
import com.kadme.test.util.MyConstants;
import org.junit.jupiter.api.Test;

import java.util.Set;

class FindGroupsImplTest {

    LinesGenerator linesGenerator = new LinesGenerator();

    @Test
    void findGroups() {
        FindGroups findGroups = new FindGroupsImpl();
        final Set<Line> lines = linesGenerator.generateRandomLines(MyConstants.MIN_VALUE_FOR_POINT_GENERATION,
                MyConstants.MAX_VALUE_FOR_POINT_GENERATION, MyConstants.LINE_RANGE);

        System.out.println(lines);
    }
}
