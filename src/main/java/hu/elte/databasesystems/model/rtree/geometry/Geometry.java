package hu.elte.databasesystems.model.rtree.geometry;

/**
 * Created by Andras Makoviczki on 2016. 11. 19..
 */
public interface Geometry {
    public Rectangle mbr();
    public Double perimeter();
    public Double area();
    public Double distance(Geometry g);
}
