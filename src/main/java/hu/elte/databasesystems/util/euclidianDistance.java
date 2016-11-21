package hu.elte.databasesystems.util;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * Created by Andras Makoviczki on 2016. 11. 17.
 */
public class euclidianDistance implements Distance {

    private Double distance;

    public euclidianDistance() {
    }

    public Double getDistance() {
        return distance;
    }


    public void setDistance(Integer x1, Integer y1, Integer x2, Integer y2) {
        distance = sqrt(pow(x1-x2,2) + pow(y1-y2,2));
    }
}
