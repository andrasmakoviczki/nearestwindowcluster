package hu.elte.databasesystems.model.util;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * Created by Andras Makoviczki on 2016. 11. 17.
 */
class euclideanDistance implements Distance {

    private Double distance;

    public euclideanDistance() {
    }

    public Double getDistance() {
        return distance;
    }


    public void setDistance(Integer x1, Integer y1) {
        distance = sqrt(pow(x1, 2) + pow(y1, 2));
    }
}
