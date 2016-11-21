package hu.elte.databasesystems.model.rtree;

import hu.elte.databasesystems.model.rtree.geometry.Geometry;

/**
 * Created by Andras Makoviczki on 2016. 11. 19.
 */
public class Context<T,S extends Geometry> {
    private Integer maxChildren;
    private Integer minChildren;
    private Splitter splitter;
    private Selector selector;

    public Context(Integer maxChildren, Integer minChildren, Splitter splitter, Selector selector) {
        this.maxChildren = maxChildren;
        this.minChildren = minChildren;
        this.splitter = splitter;
        this.selector = selector;
    }

    public Context() {
        this.minChildren = 2;
        this.maxChildren = 4;
        this.splitter = new QuadraticSplitter();
        this.selector = new MinimalAreaIncreaseSelector();
    }

    public Integer getMaxChildren() {
        return maxChildren;
    }

    public void setMaxChildren(Integer maxChildren) {
        this.maxChildren = maxChildren;
    }

    public Integer getMinChildren() {
        return minChildren;
    }

    public void setMinChildren(Integer minChildren) {
        this.minChildren = minChildren;
    }

    public Splitter getSplitter() {
        return splitter;
    }

    public void setSplitter(Splitter splitter) {
        this.splitter = splitter;
    }

    public Selector getSelector() {
        return selector;
    }

    public void setSelector(Selector selector) {
        this.selector = selector;
    }
}
