package hu.elte.databasesystems.model.rtree.geometry;

import hu.elte.databasesystems.model.rtree.Util;

import java.util.List;

/**
 * Created by Andras Makoviczki on 2016. 11. 19..
 */
public class Group<T extends HasGeometry> implements HasGeometry{
    private List<T> list;
    private Rectangle mbr;

    public Group(List<T> list) {
        this.list = list;
        this.mbr = Util.mbr(list);

    }

    public List<T> getList() {
        return list;
    }

    public Geometry geometry() {
        return mbr;
    }
}
