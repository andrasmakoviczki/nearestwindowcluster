package hu.elte.databasesystems.util;

/**
 * Created by Andras Makoviczki on 2016. 11. 17.
 */
public interface Distance {
    void setDistance(Integer x1, Integer y1, Integer x2, Integer y2);
    Double getDistance();
}
