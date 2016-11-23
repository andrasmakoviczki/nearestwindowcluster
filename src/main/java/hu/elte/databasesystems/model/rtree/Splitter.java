package hu.elte.databasesystems.model.rtree;

import hu.elte.databasesystems.model.rtree.geometry.HasGeometry;
import hu.elte.databasesystems.model.rtree.geometry.ListPair;

import java.util.List;

/**
 * Created by Andras Makoviczki on 2016. 11. 18.
 */
public interface Splitter {
    <T extends HasGeometry> ListPair<T> split(List<T> items);
}
