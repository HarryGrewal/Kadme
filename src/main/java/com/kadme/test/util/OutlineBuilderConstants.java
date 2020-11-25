package com.kadme.test.util;

import com.kadme.test.model.Line;
import com.kadme.test.model.Point;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public final class OutlineBuilderConstants {

    public static final double MIN_VALUE_FOR_POINT_GENERATION = 50.0;
    public static final double MAX_VALUE_FOR_POINT_GENERATION = 600.0;
    public static final int LINE_RANGE = 10;
    public static final int POINT_RANGE = 20;
    public static final String POLYGON = "POLYGON";
    public static final String WEAR_MASK = "WEAR_MASK";
    public static final String LINE = "LINE";
    public static final String EXAMPLE_1 = "EXAMPLE_1";
    public static final String EXAMPLE_2 = "EXAMPLE_2";
    public static final String EXAMPLE_3 = "EXAMPLE_3";
    public static final Point INVALID_POINT = new Point(Double.MAX_VALUE, Double.MAX_VALUE);

    public static final Set<Line> EXAMPLE_1_SET = Stream.of(
            new Line(new Point(100, 50), new Point(350, 450)),
            new Line(new Point(150, 50), new Point(400, 450)),
            new Line(new Point(200, 50), new Point(450, 450)),
            new Line(new Point(50, 250), new Point(550, 275)),
            new Line(new Point(50, 300), new Point(600, 325)),
            new Line(new Point(500, 50), new Point(50, 500)),
            new Line(new Point(550, 50), new Point(100, 550)),
            new Line(new Point(600, 50), new Point(150, 600)))
            .collect(Collectors.toCollection(HashSet::new));


    public static final Set<Line> EXAMPLE_3_SET = new HashSet<Line>() {{
        add(new Line(new Point(100, 50), new Point(450, 500)));
        add(new Line(new Point(150, 50), new Point(500, 500)));
        add(new Line(new Point(200, 50), new Point(550, 500)));
        add(new Line(new Point(500, 50), new Point(50, 500)));
        add(new Line(new Point(550, 50), new Point(50, 550)));
        add(new Line(new Point(600, 50), new Point(50, 600)));
    }};

    public static final Set<Line> EXAMPLE_2_SET = new HashSet<Line>() {{
        add(new Line(new Point(175, 50), new Point(180, 450)));
        add(new Line(new Point(180, 50), new Point(185, 450)));
        add(new Line(new Point(185, 50), new Point(190, 450)));
        add(new Line(new Point(190, 50), new Point(195, 450)));
        add(new Line(new Point(195, 50), new Point(200, 450)));
        add(new Line(new Point(200, 50), new Point(250, 450)));
    }};

    private OutlineBuilderConstants() {
        throw new AssertionError();
    }
}
