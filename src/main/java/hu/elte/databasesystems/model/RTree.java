package hu.elte.databasesystems.model;

import hu.elte.databasesystems.model.rtree.Node;

/**
 * Created by Andras Makoviczki on 2016. 11. 14..
 */
public class RTree<E> {
    private Node root;
    private Integer size;

    public RTree(Node root, Integer size) {
        this.root = root;
        this.size = size;
    }

    public RTree() {
        this(null,0);
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public void add(){}
    public void delete(){}
    public void search(){}

}

