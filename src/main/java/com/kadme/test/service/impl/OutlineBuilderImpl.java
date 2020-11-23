package com.kadme.test.service.impl;

import com.kadme.test.model.Line;
import com.kadme.test.model.Polygon;
import com.kadme.test.service.OutlineBuilder;
import com.kadme.test.util.CheckIfIntersect;
import com.kadme.test.util.DrawComponent;
import com.kadme.test.util.FindIntersectingPoint;

import java.util.*;

public class OutlineBuilderImpl implements OutlineBuilder {

    private CheckIfIntersect checkIfIntersect = new CheckIfIntersect();
    private FindIntersectingPoint findIntersectingPoint = new FindIntersectingPoint();

    @Override
    public Polygon buildOutline(Set<Line> lines) {

        Map<Line, HashSet<Line>> nonIntersectingLinesMap = new HashMap<>();
        Map<Line, HashSet<Line>> intersectingLinesMap = new HashMap<>();

        //Create two maps; line as key and set of non intersecting & intersecting lines as respective values
        lines.forEach(baseLine -> categorizeIntersectingNonIntersectingLines
                (baseLine, lines, nonIntersectingLinesMap, intersectingLinesMap));

        //Sanitize groups, remove empty sets
        sanitizeGroupMap(nonIntersectingLinesMap);
        sanitizeGroupMap(intersectingLinesMap);

        //Identify groups of non intersecting and intersecting lines
        Set<HashSet<Line>> nonIntersectingGroup = groupCategorizedLines(nonIntersectingLinesMap.entrySet());
        Set<HashSet<Line>> intersectingGroup = groupCategorizedLines(intersectingLinesMap.entrySet());


        System.out.println("\n line size \n" + lines.size());
        System.out.println("\n nonIntersectingGroup size \n" + nonIntersectingGroup.size());
        System.out.println("\n intersectingGroup size \n" + intersectingGroup.size());

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

        /*
        Example 1, Lines set trace for LinesMap
        Set of lines -> l1, l2, l3, l4, l5, l6 ,l7 ,l8, l9 ; non-intersecting groups -> {l1, l2} {l3, l4, l5} {l6, l7, l8, l9}
        for baseLine l1
        non-intersecting : l1 {l2}
        intersectingLineSet : l1 {l3, l4, l5, l6, l7, l8, l9}
        for baseLine l2
        non-intersecting : l2 {l1}
        intersectingLineSet : l2 {l3, l4, l5, l6, l7, l8, l9}
        for baseLine l3
        non-intersecting : l3 {l4, l5}
        intersectingLineSet : l3 {l6, l7, l8, l9, l1, l2}
        for baseLine l4
        non-intersecting : l4 {l3, l5}
        intersectingLineSet : l4 {l6, l7, l8, l9, l1, l2}
        ...
        * */
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


    private Set<HashSet<Line>> groupCategorizedLines(Set<Map.Entry<Line, HashSet<Line>>> entries) {

        /*
         * Example 1, Trace group from non-intersecting LinesMap entrySet
         * at list location 0 for l1 -> {l1, l2}
         * at list location 1 for l2 -> {l1, l2}
         * at list location 2 for l3 -> {l3, l4, l5}
         * at list location 3 for l4 -> {l3, l4, l5}
         * ...
         * from intersecting LinesMap entrySet
         * at list location 0 for l1 -> {l1, l3, l4, l5, l6, l7, l8, l9}
         * at list location 1 for l2 -> {l2, l3, l4, l5, l6, l7, l8, l9}
         * at list location 2 for l3 -> {l1, l2, l3, l6, l7, l8, l9}
         * at list location 3 for l4 -> {l1, l2, l4, l6, l7, l8, l9}
         * ...
         * */
        Set<HashSet<Line>> group = new HashSet<>();
        entries.stream().forEach(entry -> {
            HashSet<Line> lineSet = new HashSet<>();
            lineSet.add(entry.getKey());
            lineSet.addAll(entry.getValue());
            group.add(lineSet);
        });
        return group;
    }

    private void sanitizeGroupMap(Map<Line, HashSet<Line>> groupMap) {
        Set<Map.Entry> toSanitize = new HashSet<>();
        groupMap.entrySet().removeIf(entry ->
                (entry.getValue().size() == 0 ||
                        entry.getValue().equals(entry.getKey())));
    }

    private Set<Line> validLineGroup(List<HashSet<Line>> group) {
        //remove all lines from nonIntersectingGroup having empty set
        //remove lines from non-intersecting set which are not found in intersecting set

        return null;
    }

}
