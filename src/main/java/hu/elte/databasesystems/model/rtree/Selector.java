package hu.elte.databasesystems.model.rtree;

import hu.elte.databasesystems.model.rtree.geometry.Geometry;

import java.util.List;

/**
 * Created by Andras Makoviczki on 2016. 11. 18.
 */
public interface Selector {
    <T,S extends Geometry> Node<T,S> select(Geometry g, List<Node<T,S>> nodes);
}
