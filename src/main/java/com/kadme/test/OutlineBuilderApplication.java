package com.kadme.test;

import com.kadme.test.service.OutlineBuilder;
import com.kadme.test.service.impl.OutlineBuilderImpl;
import com.kadme.test.util.DrawPolygon;

import java.util.HashSet;

public class OutlineBuilderApplication {

    public static void main(String[] args) {

        OutlineBuilder outlineBuilder = new OutlineBuilderImpl();
        // Input  Set<Lines>

        new DrawPolygon(outlineBuilder.buildOutline(new HashSet<>()));

    }

}
