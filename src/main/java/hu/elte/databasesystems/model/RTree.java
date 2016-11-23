package hu.elte.databasesystems.model;

import hu.elte.databasesystems.model.rtree.*;
import hu.elte.databasesystems.model.rtree.geometry.Geometry;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Andras Makoviczki on 2016. 11. 14.
 */

/**
 * R-fa megvalósítás az alábbi oldalak alapján
 * https://github.com/davidmoten/rtree/tree/master/src/main/java/com/github/davidmoten/rtree
 * http://dbs.mathematik.uni-marburg.de/publications/myPapers/1990/BKSS90.pdf
 * http://www-db.deis.unibo.it/courses/SI-LS/papers/Gut84.pdf
 */
public class RTree<T, S extends Geometry> implements Iterable<S> {
    private final Context context;
    private Node<T, S> root;
    private Integer size;

    public RTree() {
        this.size = 0;
        this.context = new Context();
    }

    public S getGeometry(Integer index) {
        return getGeometry(index, getRoot());
    }

    /**
     * Eddigi bejárás visszaállítása
     *
     * @param node
     */
    private void resetTraversed(Node<T, S> node) {
        if (node instanceof NonLeafNode) {
            NonLeafNode<T, S> n = (NonLeafNode<T, S>) node;
            for (int i = 0; i < n.size(); i++) {
                Node<T, S> child = n.getChild(i);
                child.resetTraversed();
                resetTraversed(child);
            }
        } else {
            LeafNode<T, S> leaf = (LeafNode<T, S>) node;
            leaf.resetTraversed();
        }
    }

    private void resetTraversed() {
        resetTraversed(getRoot());
    }

    /**
     * Megadott indexű elem visszaadása
     *
     * @param index
     * @param node
     * @return
     */
    private S getGeometry(Integer index, Node<T, S> node) {
        if (node instanceof NonLeafNode) {
            for (Integer i = 0; i < node.size(); i++) {
                if (!((NonLeafNode) node).getChild(i).isTraversed()) {
                    //noinspection unchecked,unchecked
                    return (S) getGeometry(index, ((NonLeafNode) node).getChild(i));
                }
            }
            node.setTraversed();
            return getGeometry(index, node.getParent());

        } else if (node instanceof LeafNode) {
            if (index < node.size()) {
                resetTraversed();
                //noinspection unchecked
                return (S) ((LeafNode) node).getEntry(index).getGeometry();
            } else {
                node.setTraversed();
                return getGeometry(index - node.size(), node.getParent());
            }
        }
        return null;
    }

    private Node<T, S> getRoot() {
        return root;
    }

    public Integer getSize() {
        return size;
    }

    public void add(T value, S geometry) {
        //noinspection unchecked
        add(new Entry(value, geometry));
    }

    /**
     * Új elem hozzáadása
     *
     * @param entry
     */
    public void add(Entry entry) {
        if (root != null) {
            //TODO
            @SuppressWarnings("unchecked") List<Node<T, S>> nodes = root.add(entry);

            if (nodes.size() == 1) {
                root = nodes.get(0);
            } else {
                //noinspection unchecked
                root = new NonLeafNode<T, S>(nodes, context);
            }
        } else {
            List<Entry<T, S>> nodes = new ArrayList<Entry<T, S>>();
            //noinspection unchecked
            nodes.add(entry);
            //noinspection unchecked
            root = new LeafNode<T, S>(nodes, context);
        }

        size = size + 1;
    }

    public void setParents() {
        setParents(getRoot(), null);
    }

    /**
     * Szülő pointerek beállítása
     *
     * @param node
     * @param parent
     */
    private void setParents(Node<T, S> node, Node<T, S> parent) {
        if (node instanceof NonLeafNode) {
            NonLeafNode<T, S> n = (NonLeafNode<T, S>) node;
            for (int i = 0; i < n.size(); i++) {
                Node<T, S> child = n.getChild(i);
                //noinspection unchecked
                child.setParent(n);
                //noinspection unchecked
                setParents(child, n);
            }
        } else {
            LeafNode<T, S> leaf = (LeafNode<T, S>) node;
            leaf.setParent(parent);
        }
    }

    @Override
    public String toString() {
        if (root == null)
            return "";
        else
            return write(root, "");
    }

    private String write(Node<T, S> node, String margin) {
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
                s.append(write(child, margin + marginIncrement));
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


    /**
     * R-fa iterálása
     *
     * @return
     */
    public Iterator<S> iterator() {
        return new RTreeIterator<S>();
    }

    class RTreeIterator<E extends Geometry> implements Iterator<S> {
        private Integer index = 0;

        public boolean hasNext() {
            return index < getSize();
        }

        public S next() {
            return getGeometry(index++);
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }
}

