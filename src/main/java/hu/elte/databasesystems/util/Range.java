package hu.elte.databasesystems.util;

import hu.elte.databasesystems.model.DataObject;

import java.util.ArrayList;

import static java.lang.Math.abs;

/**
 * Created by Andras Makoviczki on 2016. 11. 17.
 */
public class Range {
    private Integer minX;
    private Integer maxX;
    private Integer minY;
    private Integer maxY;
    private ArrayList<Integer> absMax;

    public Range() {
        minX = Integer.MAX_VALUE;
        maxX = Integer.MIN_VALUE;
        minY = Integer.MAX_VALUE;
        maxY = Integer.MIN_VALUE;
        absMax = new ArrayList<Integer>();
    }

    public void minMaxSearch(Integer x, Integer y) {
        if (x < minX) {
            minX = x;
        }
        if (x > maxX) {
            maxX = x;
        }
        if (y < minY) {
            minY = y;
        }
        if (y > maxY) {
            maxY = y;
        }
    }

    public Integer calculateRange(){
        absMax.add(abs(minX));
        absMax.add(abs(maxX));
        absMax.add(abs(minY));
        absMax.add(abs(maxY));

        Integer range = 0;

        for (Integer i : absMax) {
            if (i > range) {
                range = i;
            }
        }
        return range;
    }
}
