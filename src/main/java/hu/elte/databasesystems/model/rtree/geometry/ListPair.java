package hu.elte.databasesystems.model.rtree.geometry;

import java.util.List;

/**
 * Created by Andras Makoviczki on 2016. 11. 19..
 */
public class ListPair<T extends HasGeometry> {
    private final Group<T> group1;
    private final Group<T> group2;

    public ListPair(List<T> list1, List<T> list2) {
        this.group1 = new Group<T>(list1);
        this.group2 = new Group<T>(list2);
    }

    public Group<T> getGroup1() {
        return group1;
    }

    public Group<T> getGroup2() {
        return group2;
    }

}
