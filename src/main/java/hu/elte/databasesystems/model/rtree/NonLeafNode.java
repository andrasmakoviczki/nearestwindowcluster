package hu.elte.databasesystems.model.rtree;

import hu.elte.databasesystems.model.rtree.geometry.Geometry;
import hu.elte.databasesystems.model.rtree.geometry.ListPair;
import hu.elte.databasesystems.model.rtree.geometry.Rectangle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andras Makoviczki on 2016. 11. 14.
 */
public class NonLeafNode<T, S extends Geometry> implements Node {

    private final List<Node<T, S>> children;
    private final Rectangle mbr;
    private final Context context;
    private Node parent;
    private Boolean traversed;

    public NonLeafNode(List<Node<T, S>> children, Context context) {
        this.children = children;
        this.context = context;
        this.mbr = Util.mbr(children);
        this.traversed = false;
    }

    public List<Node<T, S>> add(Entry entry) {
        Node<T, S> child = context.getSelector().select(entry.geometry().mbr(), children);

        @SuppressWarnings("unchecked") List<Node<T, S>> list = child.add(entry);

        List<Node<T, S>> children2 = replace(children, child, list);

        if (children2.size() <= context.getMaxChildren()) {
            List<Node<T, S>> childList = new ArrayList<Node<T, S>>();
            NonLeafNode<T, S> node = new NonLeafNode<T, S>(children2, context);
            node.setParent(getParent());
            //noinspection unchecked
            childList.add(node);
            return childList;
        } else {
            ListPair<Node<T, S>> pair = context.getSplitter().split(children2);
            List<Node<T, S>> childList = new ArrayList<Node<T, S>>();
            NonLeafNode<T, S> node1 = new NonLeafNode<T, S>(pair.getGroup1().getList(), context);
            NonLeafNode<T, S> node2 = new NonLeafNode<T, S>(pair.getGroup2().getList(), context);
            node1.setParent(getParent());
            node2.setParent(getParent());
            //noinspection unchecked
            childList.add(node1);
            //noinspection unchecked
            childList.add(node2);
            return childList;
        }
    }

    private List<Node<T, S>> replace(List<Node<T, S>> list, Node<T, S> element, List<Node<T, S>> replacement) {
        List<Node<T, S>> list2 = new ArrayList<Node<T, S>>();
        for (Node<T, S> node : list) {
            if (node != element) {
                list2.add(node);
            }
        }
        list2.addAll(replacement);
        return list2;
    }


    public Integer size() {
        return children.size();
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


    public Node<T, S> getChild(Integer i) {
        return children.get(i);
    }

    public Geometry geometry() {
        return mbr;
    }
}
