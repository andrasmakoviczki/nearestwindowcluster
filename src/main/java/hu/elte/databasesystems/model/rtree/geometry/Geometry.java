package hu.elte.databasesystems.model.rtree.geometry;

/**
 * Created by Andras Makoviczki on 2016. 11. 19..
 */
public interface Geometry {
    Rectangle mbr();

    Double perimeter();

}
