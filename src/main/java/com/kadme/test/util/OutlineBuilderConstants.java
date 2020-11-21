package com.kadme.test.util;

public final class OutlineBuilderConstants {

    public static final double MIN_VALUE_FOR_POINT_GENERATION = 50.0;
    public static final double MAX_VALUE_FOR_POINT_GENERATION = 500.0;
    public static final int LINE_RANGE = 50;
    public static final int POINT_RANGE = 100;
    public static final String POLYGON = "POLYGON";
    public static final String LINE = "LINE";

    private OutlineBuilderConstants() {
        throw new AssertionError();
    }
}