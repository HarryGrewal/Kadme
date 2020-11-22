package com.kadme.test;

import com.kadme.test.model.Polygon;
import com.kadme.test.service.OutlineBuilder;
import com.kadme.test.service.impl.OutlineBuilderImpl;
import com.kadme.test.util.GenerateRandomLines;

import static com.kadme.test.util.OutlineBuilderConstants.*;

public class OutlineBuilderApplication {

    public static void main(String[] args) {

        OutlineBuilder outlineBuilder = new OutlineBuilderImpl();

        Polygon polygon = outlineBuilder.buildOutline(new GenerateRandomLines().
                generateRandomLines(MIN_VALUE_FOR_POINT_GENERATION,
                        MAX_VALUE_FOR_POINT_GENERATION, LINE_RANGE));
    }

}
