package com.kadme.test.service.impl;

import com.kadme.test.model.Line;
import com.kadme.test.model.Point;
import com.kadme.test.model.Polygon;
import com.kadme.test.service.OutlineBuilder;
import com.kadme.test.util.ByAngleComparator;
import com.kadme.test.util.DrawComponent;
import com.kadme.test.util.FindIntersectingPoint;
import org.apache.log4j.Logger;

import java.util.*;

import static com.kadme.test.util.OutlineBuilderConstants.INVALID_POINT;


public class OutlineBuilderImpl implements OutlineBuilder {
    private final static Logger logger = Logger.getLogger(OutlineBuilderImpl.class);
    private final FindIntersectingPoint findIntersectingPoint = new FindIntersectingPoint();
    private List<Point> polygonPoints = new ArrayList<>();
    private List<List<Point>> headArray = new ArrayList<>();
    private List<List<Point>> tailArray = new ArrayList<>();


    @Override
    public Polygon buildOutline(Set<Line> inputLines) {

        logger.info("Set<Line>  size is " + inputLines.size() + "\n" + inputLines);

        Map<Line, HashSet<Line>> nonIntersectingLinesMap = new HashMap<>();
        Map<Line, HashSet<Line>> intersectingLinesMap = new HashMap<>();

        //Create two maps; line as key and set of non intersecting & intersecting lines as respective values
        inputLines.forEach(baseLine -> categorizeIntersectingNonIntersectingLines
                (baseLine, inputLines, nonIntersectingLinesMap, intersectingLinesMap));

        //Sanitize groups, remove empty sets
        sanitizeGroupMap(nonIntersectingLinesMap);
        sanitizeGroupMap(intersectingLinesMap);

        //Identify groups of non intersecting and intersecting lines
        Set<HashSet<Line>> nonIntersectingGroup = groupCategorizedLines(nonIntersectingLinesMap.entrySet());
        Set<HashSet<Line>> intersectingGroup = groupCategorizedLines(intersectingLinesMap.entrySet());

        logger.info("\n NonIntersectingGroup size is " + nonIntersectingGroup.size() + "\n" + nonIntersectingGroup);
        logger.info("\n IntersectingGroup size is " + intersectingGroup.size() + "\n" + intersectingGroup);

        //Line of thoughts
        /*From the nonIntersectingGroup ->
        step 1 : take head of every line -> find center -> sort clockwise
                 take tail of every line -> find center -> sort anticlockwise
        step 2 : Fill polygon -> connect sorted heads of nonIntersectingGroup until last head
                 Find intersection (I) of last line of first group with first line of second group
                  connect last head of first group with intersection point (I) -> connect (I) with head of second group
         */

        // step 1 populate head tail array from first group
        nonIntersectingGroup.forEach(this::makeSortedHeadTailList);
        //step 2
        if (nonIntersectingGroup.size() == 1) {
            makeSimplePolygon(headArray, tailArray);
        } else {
            makeComplexPolygon(headArray, tailArray);
        }

        logger.info("\n Head Points size is  " + headArray.size() + "\n" + headArray);
        logger.info("\n Tail Points size is  " + tailArray.size() + "\n" + tailArray);
        logger.info("\n Final Polygon Points size is  " + polygonPoints.size() + "\n" + polygonPoints);
        //Draw Component
        new DrawComponent(inputLines, polygonPoints).draw();
        return new Polygon(polygonPoints);
    }

    private void categorizeIntersectingNonIntersectingLines(Line baseLine, Set<Line> lines,
                                                            Map<Line, HashSet<Line>> nonIntersectingLinesMap,
                                                            Map<Line, HashSet<Line>> intersectingLinesMap) {

        /*
        Example 1, Lines set trace for LinesMap
        Set of lines -> l1, l2, l3, l4, l5, l6 ,l7 ,l8, l9 ;
        non-intersecting groups -> {l1, l2} {l3, l4, l5} {l6, l7, l8, l9}
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
                if (findIntersectingPoint.ifIntersect(baseLine, line)) {
                    intersectingLineSet.add(line);

                    logger.debug("\nLine added to IntersectingLineSet " + "\n" + line + " \nbase line is : " + baseLine);
                } else {
                    nonIntersectingLineSet.add(line);

                    logger.debug("\nLine added to NonIntersectingLineSet " + "\n" + line + " \nbase line is : " + baseLine);
                }
            }
        });

        nonIntersectingLinesMap.put(baseLine, nonIntersectingLineSet);

        logger.debug("\nNonIntersectingLinesMap size is " + nonIntersectingLinesMap.size());
        logger.debug("\nNonIntersectingLinesMap" + "\n" + nonIntersectingLinesMap);

        intersectingLinesMap.put(baseLine, intersectingLineSet);

        logger.debug("\nIntersectingLinesMap size is " + intersectingLinesMap.size());
        logger.debug("\nIntersectingLinesMap" + "\n" + intersectingLinesMap);
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
        logger.debug("\ngroupCategorizedLines(Set<Map.Entry<Line, HashSet<Line>>> entries) input size " + entries.size() + "\n" + entries);
        Set<HashSet<Line>> group = new HashSet<>();
        entries.forEach(entry -> {
            HashSet<Line> lineSet = new HashSet<>();
            lineSet.add(entry.getKey());
            lineSet.addAll(entry.getValue());
            group.add(lineSet);
        });
        logger.debug("\nGroup after categorizing size " + group.size() + "\n" + group);

        return group;
    }

    private void sanitizeGroupMap(Map<Line, HashSet<Line>> groupMap) {
        logger.debug("\nGroupMap before sanitizing size" + groupMap.size() + "\n" + groupMap);
        groupMap.entrySet().removeIf(entry ->
                (entry.getValue().isEmpty()));
        logger.debug("\nGroupMap after sanitizing size" + groupMap.size() + "\n" + groupMap);
    }

    private Point findCenter(List<Point> relativePointsWithinGroup) {
        double centroidX = 0, centroidY = 0;

        for (Point point : relativePointsWithinGroup) {
            centroidX += point.getX();
            centroidY += point.getY();
        }
        return new Point(centroidX / relativePointsWithinGroup.size(), centroidY / relativePointsWithinGroup.size());
    }

    private List<Point> getHeadOrTailArrayList(List<List<Point>> headTailWithinGroup, String relativeTo) {
        List<Point> relativePointsWithinGroup = new ArrayList<>();
        if (relativeTo.equals("HEAD")) {
            headTailWithinGroup.forEach(headTailList -> relativePointsWithinGroup.add(headTailList.get(0)));
        }
        if (relativeTo.equals("TAIL")) {
            headTailWithinGroup.forEach(headTailList -> relativePointsWithinGroup.add(headTailList.get(1)));
        }
        return relativePointsWithinGroup;
    }

    private void makeSortedHeadTailList(HashSet<Line> setOfLinesInOneGroup) {
        //create a list of list -> head point and tail point of a line relative to progressive X
        List<List<Point>> headTailWithinGroup = new ArrayList<>();
        setOfLinesInOneGroup.forEach(line ->
        {
            final double x1 = line.getP1().getX();
            final double y1 = line.getP1().getY();
            final double x2 = line.getP2().getX();
            final double y2 = line.getP2().getY();
            Point head = null, tail = null;
            //corner case: avoid a point
            if (!(x1 == x2 && y1 == y2)) {
                if (x1 == x2) {
                    double minY = Double.min(y1, y2);
                    double maxY = Double.max(y1, y2);
                    head = new Point(x1, minY);
                    tail = new Point(x1, maxY);
                } else if (Double.min(x1, x2) == x1) {
                    head = new Point(x1, y1);
                    tail = new Point(x2, y2);
                } else {
                    head = new Point(x2, y2);
                    tail = new Point(x1, y1);
                }
            }
            headTailWithinGroup.add(Arrays.asList(head, tail));
        });
        List<Point> headArrayList = new ArrayList<>(getHeadOrTailArrayList(headTailWithinGroup, "HEAD"));
        List<Point> tailArrayList = new ArrayList<>(getHeadOrTailArrayList(headTailWithinGroup, "TAIL"));

        logger.debug("\nHeadArrayList before sorting size is " + "\n" + headArrayList);
        logger.debug("\nTailArrayList before sorting size is " + "\n" + tailArrayList);

        Point headCenter = findCenter(headArrayList);
        Point tailCenter = findCenter(tailArrayList);
        logger.debug("\nHead Center is " + headCenter);
        logger.debug("\nTail Center is " + tailCenter);

        headArrayList.sort(new ByAngleComparator().compareByAngle(headCenter));
        tailArrayList.sort(new ByAngleComparator().compareByAngle(tailCenter));

        //add to main head tail
        headArray.add(headArrayList);
        tailArray.add(tailArrayList);

        logger.debug("\nHeadArrayList after sorting is " + "\n" + headArrayList);
        logger.debug("\nTailArrayList after sorting is " + "\n" + tailArrayList);
        logger.debug("\nFirst Head Point is " + headArrayList.get(0));
        logger.debug("\nLast  Head Point is " + headArrayList.get(headArrayList.size() - 1));
        logger.debug("\nFirst Tail Point is " + tailArrayList.get(0));
        logger.debug("\nLast  Tail Point is " + tailArrayList.get(tailArrayList.size() - 1));
        logger.debug("\nMain Head Array size is " + headArray.size() + "\n" + headArray);
        logger.debug("\nMain Tail Array size is " + tailArray.size() + "\n" + tailArray);
    }

    private void makeSimplePolygon(List<List<Point>> headArray, List<List<Point>> tailArray) {
        //simple logic to traverse boundary
                /*polygonPoints.add(headArrayList.get(headArrayList.size() - 1));
                polygonPoints.add(headArrayList.get(0));
                polygonPoints.add(headArrayList.get(0));
                polygonPoints.add(tailArrayList.get(0));
                polygonPoints.add(tailArrayList.get(0));
                polygonPoints.add(tailArrayList.get(tailArrayList.size() - 1));
                polygonPoints.add(tailArrayList.get(tailArrayList.size() - 1));
                polygonPoints.add(headArrayList.get(headArrayList.size() - 1));*/
        final List<Point> points = headArray.get(0);
        for (int i = points.size(); i-- > 0; ) {
            polygonPoints.add(points.get(i));
        }
        for (Point point : tailArray.get(0)) {
            polygonPoints.add(point);
        }
    }

    private void makeComplexPolygon(List<List<Point>> headArray, List<List<Point>> tailArray) {
        Point intersection, lastX, lastY, nextFirstX, nextFirstY;
        Line l1, l2;
        for (int i = 0; i < headArray.size() && i < tailArray.size(); i++) {
            final List<Point> firstGroup = headArray.get(i);
            for (int j = firstGroup.size(); j-- > 0; ) {
                polygonPoints.add(firstGroup.get(j));
            }
            lastX = firstGroup.get(0);
            lastY = tailArray.get(i).get(0);
            l1 = new Line(lastX, lastY);
            if (i + 1 < tailArray.size() && i + 1 < headArray.size()) {
                nextFirstX = headArray.get(i + 1).get(headArray.get(i + 1).size() - 1);
                nextFirstY = tailArray.get(i + 1).get(tailArray.get(i + 1).size() - 1);
                l2 = new Line(nextFirstX, nextFirstY);

                intersection = findIntersectingPoint.findIntersectionPoint(l1, l2);
                logger.info("\nIntersection in make complex polygon from Head returns " + intersection);
                logger.info("\nIntersection done between lines  " + l1 + "\t" + l2);
                if (!intersection.equals(INVALID_POINT)) {
                    polygonPoints.add(intersection);
                }
            }
        }

        for (int i = 0; i < headArray.size() && i < tailArray.size(); i++) {
            final List<Point> firstGroup = tailArray.get(i);
            for (Point point : firstGroup) {
                polygonPoints.add(point);
            }
            lastX = firstGroup.get(firstGroup.size() - 1);
            lastY = headArray.get(i).get(headArray.get(i).size() - 1);
            l1 = new Line(lastX, lastY);
            if (i + 1 < tailArray.size() && i + 1 < headArray.size()) {
                nextFirstX = tailArray.get(i + 1).get(0);
                nextFirstY = headArray.get(i + 1).get(0);
                l2 = new Line(nextFirstX, nextFirstY);

                intersection = findIntersectingPoint.findIntersectionPoint(l1, l2);
                logger.info("\nIntersection in make complex polygon from Tail returns " + intersection);
                logger.info("\nIntersection done between lines  " + l1 + "\t" + l2);
                if (!intersection.equals(INVALID_POINT)) {
                    polygonPoints.add(intersection);
                }
            }
        }
    }
}
