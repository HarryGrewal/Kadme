package com.kadme.test.util;

public class GenerateRandomCoordinate {

    public static double randomCoordinate(double minPointRange, double maxPointRange) {

        // This will Create A Random Number in between  Min And Max.
        double x = (Math.random() * ((maxPointRange - minPointRange) + 1)) + minPointRange;
        // Creates Answer To The Nearest 100 th, You Can Modify This To Change How It Rounds. ex. 5.36
        return Math.round(x * 100.0) / 100.0;
    }

}
