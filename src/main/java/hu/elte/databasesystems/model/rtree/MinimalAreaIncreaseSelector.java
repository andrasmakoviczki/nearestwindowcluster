package hu.elte.databasesystems.model.rtree;

import hu.elte.databasesystems.model.rtree.geometry.Geometry;

import java.util.List;

import static java.util.Collections.min;

/**
 * Created by Andras Makoviczki on 2016. 11. 19..
 */
public class MinimalAreaIncreaseSelector implements Selector {

    public <T,S extends Geometry> Node<T,S> select(Geometry g, List<Node<T,S>> nodes) {
        return min(nodes,Comparators.areaIncreaseAC(g.mbr()));
    }
}
