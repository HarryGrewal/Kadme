package com.kadme.test;

import com.kadme.test.exception.EmptyLineException;
import com.kadme.test.model.Polygon;
import com.kadme.test.service.OutlineBuilder;
import com.kadme.test.service.impl.OutlineBuilderImpl;
import org.apache.log4j.PropertyConfigurator;

import static com.kadme.test.util.OutlineBuilderConstants.EXAMPLE_2_SET;

public class OutlineBuilderApplication {

    public static void main(String[] args) {

        PropertyConfigurator.configure("log4j.properties");
        OutlineBuilder outlineBuilder = new OutlineBuilderImpl();

        try {
            Polygon polygon = outlineBuilder.buildOutline(EXAMPLE_2_SET);
        } catch (EmptyLineException e) {
            e.printStackTrace();
        }
    }

}
