package com.kadme.test.service.impl;

import com.kadme.test.model.Line;
import com.kadme.test.model.Polygon;
import com.kadme.test.service.OutlineBuilder;
import com.kadme.test.util.CheckIfIntersect;
import com.kadme.test.util.DrawComponent;
import com.kadme.test.util.FindIntersectingPoint;

import java.util.*;

public class OutlineBuilderImpl implements OutlineBuilder {

    private CheckIfIntersect checkIfIntersect;
    private FindIntersectingPoint findIntersectingPoint;

    @Override
    public Polygon buildOutline(Set<Line> lines) {

        Map<Line, HashSet<Line>> nonIntersectingLinesMap = new HashMap<>();
        Map<Line, HashSet<Line>> intersectingLinesMap = new HashMap<>();

        //Create two maps; line as key and set of non intersecting & intersecting lines as respective values
        lines.forEach(baseLine -> categorizeIntersectingNonIntersectingLines
                (baseLine, lines, nonIntersectingLinesMap, intersectingLinesMap));

        //Identify groups of non intersecting and intersecting lines
        List<HashSet<Line>> nonIntersectingGroup = identifyLineGroupsFromCategory(nonIntersectingLinesMap.entrySet());
        List<HashSet<Line>> intersectingGroup = identifyLineGroupsFromCategory(intersectingLinesMap.entrySet());


        //Figure out from this group of non intersecting lines, closest lines between two group and
        // calculate their intersection point and figure out the order of the lines within the group
        //once we get the intersection point simply travel via first group to intersection point and
        // then to other points in the group and then to intersection point and to third group and so on
        //By travel I meant include points in Polygon class
        //Verify all the points are included in polygon


        //Draw Component
        new DrawComponent(lines).draw();
        return new Polygon(null);
    }

    private void categorizeIntersectingNonIntersectingLines(Line baseLine, Set<Line> lines,
                                                            Map<Line, HashSet<Line>> nonIntersectingLinesMap,
                                                            Map<Line, HashSet<Line>> intersectingLinesMap) {
        checkIfIntersect = new CheckIfIntersect();
        HashSet<Line> nonIntersectingLineSet = new HashSet<>();
        HashSet<Line> intersectingLineSet = new HashSet<>();
        lines.forEach(line -> {
            if (baseLine != line) {
                if (checkIfIntersect.doIntersect(baseLine.getP1(), baseLine.getP2(),
                        line.getP1(), line.getP2())) {
                    intersectingLineSet.add(line);
                } else {
                    nonIntersectingLineSet.add(line);
                }
            }
        });
        nonIntersectingLinesMap.put(baseLine, nonIntersectingLineSet);
        intersectingLinesMap.put(baseLine, intersectingLineSet);
    }

    private List<HashSet<Line>> identifyLineGroupsFromCategory(Set<Map.Entry<Line, HashSet<Line>>> entries) {
        List<HashSet<Line>> group = new ArrayList<>();
        entries.stream().forEach(setEntry -> {
            HashSet<Line> lineSet = new HashSet<>();
            lineSet.add(setEntry.getKey());
            lineSet.addAll(setEntry.getValue());
            group.add(lineSet);
        });
        return group;
    }
}
