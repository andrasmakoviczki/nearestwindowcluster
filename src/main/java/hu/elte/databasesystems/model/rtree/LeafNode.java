package hu.elte.databasesystems.model.rtree;

import hu.elte.databasesystems.model.rtree.geometry.Geometry;
import hu.elte.databasesystems.model.rtree.geometry.ListPair;
import hu.elte.databasesystems.model.rtree.geometry.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andras Makoviczki on 2016. 11. 14.
 */
public class LeafNode<T,S extends Geometry> implements Node{
    private List<Entry<T,S>> entries;
    private Rectangle mbr;
    private Context<T,S> context;
    private Node<T,S> parent;
    private Boolean traversed;

    public LeafNode(List<Entry<T,S>> entries, Context context) {
        this.entries = entries;
        this.mbr = Util.mbr(entries);
        this.context = context;
        this.traversed = false;
    }

    public Integer size(){
        return entries.size();
    }

    public void setTraversed() {
        this.traversed = true;
    }

    public void resetTraversed() {
        this.traversed = false;
    }

    public Boolean isTraversed() {
        return traversed;
    }


    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public List<Node<T,S>> add(Entry entry) {
        entries.add(entry);
        //< or <=
        if (entries.size() <= context.getMaxChildren()) {
            List<Node<T,S>> list = new ArrayList<Node<T, S>>();
            LeafNode<T,S> leaf = new LeafNode<T,S>(entries,context);
            leaf.setParent(getParent());
            list.add(leaf);

            return list;
        } else {
            ListPair<Entry<T,S>> pair = context.getSplitter().split(entries,context.getMinChildren());
            List<Node<T,S>> list = new ArrayList<Node<T,S>>();
            LeafNode<T,S> leaf1 = new LeafNode<T,S>(pair.getGroup1().getList(),context);
            LeafNode<T,S> leaf2 = new LeafNode<T,S>(pair.getGroup2().getList(),context);
            leaf1.setParent(getParent());
            leaf2.setParent(getParent());

            list.add(leaf1);
            list.add(leaf2);
            return list;
        }
    }

    public void delete(Entry entry) {
        throw new UnsupportedOperationException();
    }

    public Geometry geometry() {
        return mbr;
    }

    public List<Entry<T, S>> getEntries() {
        return entries;
    }

    public Entry<T,S> getEntry(Integer i){
        return entries.get(i);
    }

    public Context<T, S> getContext() {
        return context;
    }


}
