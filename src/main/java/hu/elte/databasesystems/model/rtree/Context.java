package hu.elte.databasesystems.model.rtree;

import hu.elte.databasesystems.model.rtree.geometry.Geometry;

/**
 * Created by Andras Makoviczki on 2016. 11. 19.
 */
public class Context<T, S extends Geometry> {
    private final Integer maxChildren;
    private final Splitter splitter;
    private final Selector selector;

    public Context() {
        this.maxChildren = 4;
        this.splitter = new QuadraticSplitter();
        this.selector = new MinimalAreaIncreaseSelector();
    }

    public Integer getMaxChildren() {
        return maxChildren;
    }

    public Splitter getSplitter() {
        return splitter;
    }

    public Selector getSelector() {
        return selector;
    }

}
