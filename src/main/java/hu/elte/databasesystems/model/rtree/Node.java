package hu.elte.databasesystems.model.rtree;

import hu.elte.databasesystems.model.rtree.geometry.Geometry;
import hu.elte.databasesystems.model.rtree.geometry.HasGeometry;

import java.util.List;

/**
 * Created by Andras Makoviczki on 2016. 11. 14.
 */
public interface Node<T,S extends Geometry> extends HasGeometry {
    List<Node<T,S>> add(Entry<T,S> entry);
    void delete(Entry<T,S> entry);
    Integer size();
    //void traversed();
    Node<T,S> getParent();
    void setParent(Node<T,S> parent);
}
