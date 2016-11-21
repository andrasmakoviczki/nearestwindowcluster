package hu.elte.databasesystems.model.rtree.geometry;

import java.util.List;

/**
 * Created by Andras Makoviczki on 2016. 11. 19..
 */
public class ListPair<T extends HasGeometry> {
    private Group<T> group1;
    private Group<T> group2;

    private Double marginSum;
    private Double areaSum;

    public ListPair(List<T> list1, List<T> list2) {
        this.group1 = new Group<T>(list1);
        this.group2 = new Group<T>(list2);
        this.marginSum = group1.geometry().mbr().perimeter() + group2.geometry().perimeter();
        this.areaSum = group1.geometry().mbr().area() + group2.geometry().perimeter();
    }

    public Group<T> getGroup1() {
        return group1;
    }

    public Group<T> getGroup2() {
        return group2;
    }

    public Double getMarginSum() {
        return marginSum;
    }

    public Double getAreaSum() {
        return areaSum;
    }
}
