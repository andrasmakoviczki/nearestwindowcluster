package hu.elte.databasesystems.model.rtree;

import hu.elte.databasesystems.model.rtree.geometry.HasGeometry;
import hu.elte.databasesystems.model.rtree.geometry.Rectangle;

import java.util.Comparator;

/**
 * Created by Andras Makoviczki on 2016. 11. 19..
 */
class Comparators {
    //Area Increase Area Comparator
    public static <T extends HasGeometry> Comparator<HasGeometry> areaIncreaseAC(final Rectangle r) {
        return new Comparator<HasGeometry>() {
            public int compare(HasGeometry o1, HasGeometry o2) {
                Integer value = Double.compare(areaIncrease(r, o1), areaIncrease(r, o2));
                if (value == 0) {
                    value = Double.compare(area(r, o1), area(r, o2));
                }
                return value;
            }
        };
    }

    private static Double area(Rectangle r, HasGeometry g) {
        return g.geometry().mbr().add(r).area();
    }

    private static Double areaIncrease(Rectangle r, HasGeometry g) {
        Rectangle sum = g.geometry().mbr().add(r);
        return sum.area() - g.geometry().mbr().area();
    }


}
