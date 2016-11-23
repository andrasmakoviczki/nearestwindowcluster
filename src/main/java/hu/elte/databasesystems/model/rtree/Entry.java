package hu.elte.databasesystems.model.rtree;

import hu.elte.databasesystems.model.rtree.geometry.Geometry;
import hu.elte.databasesystems.model.rtree.geometry.HasGeometry;

/**
 * Created by Andras Makoviczki on 2016. 11. 18.
 */
public class Entry<T, S extends Geometry> implements HasGeometry {
    private final T value;
    private final S geometry;

    public Entry(T value, S geometry) {
        this.value = value;
        this.geometry = geometry;
    }

    public S getGeometry() {
        return geometry;
    }

    public Geometry geometry() {
        return this.geometry;
    }

    @Override
    public String toString() {
        return "Entry{" +
                "value=" + value +
                ", geometry=" + geometry +
                '}';
    }
}
