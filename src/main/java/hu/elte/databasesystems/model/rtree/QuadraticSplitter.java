package hu.elte.databasesystems.model.rtree;

import hu.elte.databasesystems.model.rtree.geometry.Geometry;
import hu.elte.databasesystems.model.rtree.geometry.HasGeometry;
import hu.elte.databasesystems.model.rtree.geometry.ListPair;
import hu.elte.databasesystems.model.rtree.geometry.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andras Makoviczki on 2016. 11. 19..
 */
public class QuadraticSplitter implements Splitter {

    public <T extends HasGeometry> ListPair<T> split(List<T> items, Integer minSize) {
        Pair<T> worstCombination = worstCombination(items);

        List<T> group1 = new ArrayList<T>();
        List<T> group2 = new ArrayList<T>();

        group1.add(worstCombination.getValue1());
        group2.add(worstCombination.getValue2());

        List<T> remaining = new ArrayList<T>(items);
        remaining.remove(worstCombination.getValue1());
        remaining.remove(worstCombination.getValue2());

        Integer minGroupSize = items.size() / 2;

        while (remaining.size() > 0){
            assignRemaining(group1,group2,remaining,minGroupSize);
        }

        return new ListPair<T>(group1,group2);
    }

    private <T extends HasGeometry> void assignRemaining(List<T> group1, List<T> group2, List<T> remaining, Integer minGroupSize) {
        Rectangle mbr1 = Util.mbr(group1);
        Rectangle mbr2 = Util.mbr(group2);
        T item1 = getBest(remaining,group1,mbr1);
        T item2 = getBest(remaining,group2,mbr2);

        //area1 lesser than area2
        boolean l = item1.geometry().mbr().add(mbr1).area() <= item2.geometry().mbr().add(mbr2).area();

        if(l && (group2.size() + remaining.size() -1 >= minGroupSize) || !l && (group1.size() + remaining.size() == minGroupSize)){
            group1.add(item1);
            remaining.remove(item1);
        } else {
            group2.add(item2);
            remaining.remove(item2);
        }
    }

    private <T extends HasGeometry> T getBest(List<T> list, List<T> group, Rectangle mbr) {
        T minEntry = null;
        Double minArea = null;

        for (T entry : list){
            Double area = mbr.add(entry.geometry().mbr()).area();
            if(minArea == null || area < minArea){
                minArea = area;
                minEntry = entry;
            }
        }

        return minEntry;
    }


    private <T extends HasGeometry> Pair<T> worstCombination(List<T> items) {
        T e1 = null;
        T e2 = null;
        {
            Double maxArea = null;
            for (int i = 0; i < items.size(); i++) {
                for (int j = i+1; j < items.size(); j++) {
                    T entry1 = items.get(i);
                    T entry2 = items.get(j);
                    //Rectangle entry3 = entry1.geometry().mbr().add(entry2.geometry().mbr());
                    Double area = entry1.geometry().mbr().add(entry2.geometry().mbr()).area();

                    if(maxArea == null || area > maxArea){
                        e1 = entry1;
                        e2 = entry2;
                        maxArea = area;
                    }
                }
            }
        }

        if(e1 != null){
            return new Pair<T>(e1,e2);
        } else {
            return new Pair<T>(items.get(0),items.get(0));
        }
    }
}
