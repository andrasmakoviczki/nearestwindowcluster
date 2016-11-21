package hu.elte.databasesystems.model.rtree;

import hu.elte.databasesystems.model.rtree.geometry.Geometry;
import hu.elte.databasesystems.model.rtree.geometry.HasGeometry;
import hu.elte.databasesystems.model.rtree.geometry.Rectangle;

/**
 * Created by Andras Makoviczki on 2016. 11. 18.
 */
public class Entry<T,S extends Geometry> implements HasGeometry {
    private T value;
    private S geomerty;

    public Entry(T value, S geomerty) {
        this.value = value;
        this.geomerty = geomerty;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public S getGeomerty() {
        return geomerty;
    }

    public void setGeomerty(S geomerty) {
        this.geomerty = geomerty;
    }

    public Geometry geometry() {
        return this.geomerty;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "value=" + value +
                ", geomerty=" + geomerty +
                '}';
    }
}
