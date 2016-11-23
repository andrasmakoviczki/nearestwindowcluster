package hu.elte.databasesystems.model.rtree;

import hu.elte.databasesystems.model.rtree.geometry.HasGeometry;
import hu.elte.databasesystems.model.rtree.geometry.Rectangle;

import java.util.Collection;

/**
 * Created by Andras Makoviczki on 2016. 11. 19..
 */
public class Util {

    public static Rectangle mbr(Collection<? extends HasGeometry> items) {
        Double minX1 = Double.MAX_VALUE;
        Double minY1 = Double.MAX_VALUE;
        Double maxX2 = -Double.MAX_VALUE;
        Double maxY2 = -Double.MAX_VALUE;

        for (HasGeometry item : items) {
            Rectangle rectangle = item.geometry().mbr();
            if (rectangle.getX1() < minX1)
                minX1 = rectangle.getX1();
            if (rectangle.getY1() < minY1)
                minY1 = rectangle.getY1();
            if (rectangle.getX2() > maxX2)
                maxX2 = rectangle.getX2();
            if (rectangle.getY2() > maxY2)
                maxY2 = rectangle.getY2();
        }
        return new Rectangle(minX1, minY1, maxX2, maxY2);
    }
}
