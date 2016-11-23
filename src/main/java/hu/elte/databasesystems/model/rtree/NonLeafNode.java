package hu.elte.databasesystems.model.rtree;

import hu.elte.databasesystems.model.rtree.geometry.Geometry;
import hu.elte.databasesystems.model.rtree.geometry.ListPair;
import hu.elte.databasesystems.model.rtree.geometry.Rectangle;

import javax.naming.InsufficientResourcesException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andras Makoviczki on 2016. 11. 14.
 */
public class NonLeafNode<T,S extends Geometry> implements Node{

    private List<Node<T,S>> children;
    private Rectangle mbr;
    private Context context;
    private Node parent;
    private Boolean traversed;

    public NonLeafNode(List<Node<T,S>> children, Context context) {
        this.children = children;
        this.context = context;
        this.mbr = Util.mbr(children);
        this.traversed = false;
    }

    public List<Node<T,S>> copyNodeList(List<Node<T,S>> list){
        List<Node<T,S>> newList = new ArrayList<Node<T, S>>();
        for (Node<T,S> item : list)
            if (item instanceof LeafNode) {
                List<Entry<T,S>> eList = new ArrayList<Entry<T, S>>();
                for (Object entryItem: ((LeafNode) item).getEntries()) {
                    Entry e = new Entry(((Entry) entryItem).getValue(),((Entry) entryItem).getGeomerty());
                    eList.add(e);
                }
                newList.add(new LeafNode<T, S>(eList, context));
            } else {
                List<Node<T,S>> nList = new ArrayList<Node<T, S>>();
                for (Object nodeItem: ((NonLeafNode) item).getChildren()) {
                    if (item instanceof NonLeafNode){
                        NonLeafNode n = new NonLeafNode<T,S>(((NonLeafNode)nodeItem).getChildren(),
                                ((NonLeafNode)nodeItem).getContext());
                        nList.add(n);
                    }
                }
                newList.add(new NonLeafNode<T, S>(nList, context));
            }
        return newList;
    }

    public List<Node<T,S>> add(Entry entry) {
        Node<T,S> child = context.getSelector().select(entry.geometry().mbr(),children);

        //Másolás
        //List<Node<T,S>> oldList = copyNodeList(children);
        List<Node<T,S>> list = child.add(entry);
        //Mire jó?
        List<Node<T,S>> children2 = replace(children,child,list);

        if (children2.size() <= context.getMaxChildren()) {
            List<Node<T,S>> childList = new ArrayList<Node<T, S>>();
            NonLeafNode<T,S> node = new NonLeafNode<T, S>(children2,context);
            node.setParent(getParent());
            childList.add(node);
            return childList;
        } else {
            ListPair<Node<T,S>> pair = context.getSplitter().split(children2,context.getMinChildren());
            List<Node<T,S>> childList = new ArrayList<Node<T,S>>();
            NonLeafNode<T,S> node1 = new NonLeafNode<T,S>(pair.getGroup1().getList(),context);
            NonLeafNode<T,S> node2 = new NonLeafNode<T,S>(pair.getGroup2().getList(),context);
            node1.setParent(getParent());
            node2.setParent(getParent());
            childList.add(node1);
            childList.add(node2);
            return childList;
        }
    }

    private List<Node<T,S>> replace(List<Node<T,S>> list, Node<T,S> element, List<Node<T,S>> replacement) {
        List<Node<T,S>> list2 = new ArrayList<Node<T,S>>();
        for (Node<T,S> node : list){
            if (node != element){
                list2.add(node);
            }
        }
        list2.addAll(replacement);
        return list2;
    }


    public void delete(Entry entry) {
        throw new UnsupportedOperationException();
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


    public Node<T,S> getChild(Integer i){
        return children.get(i);
    }

    public List<Node<T,S>> getChildren() {
        return children;
    }

    public void setChildren(List<Node<T,S>> children) {
        this.children = children;
    }

    public Rectangle getMbr() {
        return mbr;
    }

    public void setMbr(Rectangle mbr) {
        this.mbr = mbr;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public Geometry geometry() {
        return mbr;
    }
}
