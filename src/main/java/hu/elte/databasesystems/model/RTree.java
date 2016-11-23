package hu.elte.databasesystems.model;

import hu.elte.databasesystems.model.rtree.*;
import hu.elte.databasesystems.model.rtree.geometry.Geometry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

/**
 * Created by Andras Makoviczki on 2016. 11. 14.
 * Csak pont objektumokra, más poligonokra nem
 */

public class RTree<T,S extends Geometry> implements Iterable<S> {
    private Node<T,S> root;
    private Integer size;
    private Context context;

    //Builder -> Készít egy fát a megfelelő tulajdonságokkal: minChild, maxChild, splitter, selector

    public RTree() {
        this.size = 0;
        this.context = new Context();
    }

    public RTree(Node<T,S> root, Integer size) {
        this.root = root;
        this.size = size;
        this.context = new Context();
    }

    public S getGeometry(Integer index){
        return getGeometry(index,getRoot());
    }

    public void resetTraversed(Node<T,S> node,Node<T,S> parent){
        if (node instanceof NonLeafNode) {
            NonLeafNode<T, S> n = (NonLeafNode<T, S>) node;
            for (int i = 0; i < n.size(); i++) {
                Node<T, S> child = n.getChild(i);
                child.resetTraversed();
                resetTraversed(child,n);
            }
        } else {
            LeafNode<T, S> leaf = (LeafNode<T, S>) node;
            leaf.resetTraversed();
        }
    }

    public void resetTraversed(){
        resetTraversed(getRoot(),null);
    }

    public S getGeometry(Integer index,Node<T,S> node){
         if(node instanceof NonLeafNode){
            for (Integer i = 0; i < node.size(); i++) {
                if (!((NonLeafNode) node).getChild(i).isTraversed()){
                    return (S) getGeometry(index,((NonLeafNode) node).getChild(i));
                }
            }
            node.setTraversed();
            return (S) getGeometry(index,node.getParent());

        } else if (node instanceof LeafNode){
            if(index < node.size()){
                resetTraversed();
                return (S) ((LeafNode) node).getEntry(index).getGeomerty();
            } else {
                node.setTraversed();
                return getGeometry(index - node.size(),node.getParent());
            }
        }
        return null;
    }

    public Node<T,S> getRoot() {
        return root;
    }

    public void setRoot(Node<T,S> root) {
        this.root = root;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public void add(T value, S geometry) {
        add(new Entry(value,geometry));
    }

    public void add(Entry entry){
        if(root != null){
            //TODO
            List<Node<T,S>> nodes = root.add(entry);
            Node<T,S> node;

            if(nodes.size() == 1){
                root = nodes.get(0);
            } else {
                root = new NonLeafNode<T,S>(nodes,context);
            }
        } else {
            List<Entry<T,S>> nodes = new ArrayList<Entry<T,S>>();
            nodes.add(entry);
            root = new LeafNode<T,S>(nodes,context);
        }

        size = size + 1;
    }

    public void setParents(){
        setParents(getRoot(),null);
    }

    public void setParents(Node<T,S> node,Node<T,S> parent) {
        if (node instanceof NonLeafNode) {
            NonLeafNode<T, S> n = (NonLeafNode<T, S>) node;
            for (int i = 0; i < n.size(); i++) {
                Node<T, S> child = n.getChild(i);
                child.setParent(n);
                setParents(child,n);
            }
        } else {
            LeafNode<T, S> leaf = (LeafNode<T, S>) node;
            leaf.setParent(parent);
        }
    }

    public void add(Iterable<Entry<T, S>> entries) {
        RTree<T, S> tree = this;
        for (Entry<T, S> entry : entries)
            tree.add(entry);
    }

    public void delete(){
        throw new UnsupportedOperationException();
    }

    public void search(){}

    @Override
    public String toString() {
        if (root == null)
            return "";
        else
            return asString(root, "");
    }

    //TODO
    private String asString(Node<T, S> node, String margin) {
        String marginIncrement = "  ";
        StringBuilder s = new StringBuilder();
        s.append(margin);
        s.append("mbr=");
        s.append(node.geometry());
        s.append('\n');
        if (node instanceof NonLeafNode) {
            NonLeafNode<T, S> n = (NonLeafNode<T, S>) node;
            for (int i = 0; i < n.size(); i++) {
                Node<T, S> child = n.getChild(i);
                s.append(asString(child, margin + marginIncrement));
            }
        } else {
            LeafNode<T, S> leaf = (LeafNode<T, S>) node;

            for (Entry<T, S> entry : leaf.getEntries()) {
                s.append(margin);
                s.append(marginIncrement);
                s.append("entry=");
                s.append(entry);
                s.append('\n');
            }
        }
        return s.toString();
    }



    public Iterator<S> iterator() {
        return new RTreeIteator<S>();
    }

    class RTreeIteator<S extends Geometry> implements Iterator<S>{
        private Integer index = 0;

        public boolean hasNext() {
            if (index < getSize()){
                return true;
            } else {
                return false;
            }
        }

        public S next() {
            return (S) getGeometry(index++);
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}

