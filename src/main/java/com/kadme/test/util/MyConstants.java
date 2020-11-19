package com.kadme.test.util;

public final class MyConstants {

    public static final double MIN_VALUE_FOR_POINT_GENERATION = 0.0;
    public static final double MAX_VALUE_FOR_POINT_GENERATION = 10.0;
    public static final int LINE_RANGE = 20;


    private MyConstants() {
        //this prevents even the native class from
        //calling this constructor as well :
        throw new AssertionError();
    }
}
