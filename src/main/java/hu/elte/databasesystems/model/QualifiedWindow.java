package hu.elte.databasesystems.model;

import hu.elte.databasesystems.model.rtree.geometry.Point;

/**
 * Created by Andras Makoviczki on 2016. 11. 07.
 */

/**
 * Qualified window
 */
public class QualifiedWindow {
    private final RTree<Integer, DataObject> sqwin;
    private final Rectangle r;
    private final SearchRegion searchRegion;

    private final Integer width;
    private final Integer length;
    private final Integer numberOfObjects;

    private final DataObject p;
    private final DataObject p2;

    public QualifiedWindow(NWCQuery query, SearchRegion sr, DataObject p2) {
        this.length = query.getLength();
        this.width = query.getWidth();
        this.numberOfObjects = query.getNumDataObjects();
        this.searchRegion = sr;
        this.p = sr.getP();
        this.p2 = p2;
        this.r = setWindow();
        this.sqwin = selectPoints(sr.getSsrp());
    }

    public SearchRegion getSearchRegion() {
        return searchRegion;
    }

    public Rectangle getR() {
        return r;
    }

    public Boolean isQualified() {
        return sqwin.getSize() >= numberOfObjects;
    }

    public Double mindist() {
        Double minDist = Double.MAX_VALUE;
        for (Integer i = 0; i < sqwin.getSize(); i++) {
            if (sqwin.getGeometry(i).getDistance() < minDist) {
                minDist = sqwin.getGeometry(i).getDistance();
            }
        }

        return minDist;
    }

    /**
     * Terület által meghatározott pontok
     *
     * @param tree
     * @return
     */
    private RTree<Integer, DataObject> selectPoints(RTree<Integer, DataObject> tree) {
        RTree<Integer, DataObject> newTree = new RTree<Integer, DataObject>();
        Integer intGenerator = 0;
        for (Integer i = 0; i < tree.getSize(); ++i) {
            DataObject p = tree.getGeometry(i);

            Boolean l1 = p.getX() >= r.getLeftBottom().getX() && p.getY() >= r.getLeftBottom().getY();
            Boolean l2 = p.getX() <= r.getRightBottom().getX() && p.getY() >= r.getRightBottom().getY();
            Boolean l3 = p.getX() <= r.getRightTop().getX() && p.getY() <= r.getRightTop().getY();
            Boolean l4 = p.getX() >= r.getLeftTop().getX() && p.getY() <= r.getLeftTop().getY();

            if (l1 && l2 && l3 && l4) {
                newTree.add(intGenerator, tree.getGeometry(i));
                intGenerator = intGenerator + 1;
            }
        }
        newTree.setParents();
        return newTree;
    }

    /**
     * Ablak meghatározása síknegyedek alapján
     *
     * @return
     */
    private Rectangle setWindow() {
        Rectangle srRectangle = searchRegion.getR();
        Integer yDistance = (int) Math.ceil(Math.abs(p.getY() - p2.getY()));
        //magasság - (p,p') távolság
        Integer delta = width - yDistance;

        Point leftTop;
        Point rightTop;
        Point leftBottom;
        Point rightBottom;

        switch (p.getQuadrant()) {
            case FIRST:
                leftBottom = new Point(srRectangle.getLeftBottom().getX(),
                        p.getY() - delta);
                rightBottom = new Point(srRectangle.getRightBottom().getX(),
                        p.getY() - delta);
                rightTop = new Point(srRectangle.getRightTop().getX(),
                        p.getY() + yDistance);
                leftTop = new Point(srRectangle.getLeftTop().getX(),
                        p.getY() + yDistance);
                break;
            case SECOND:
                leftBottom = new Point(srRectangle.getLeftBottom().getX(),
                        p.getY() - delta);
                rightBottom = new Point(srRectangle.getRightBottom().getX(),
                        p.getY() - delta);
                rightTop = new Point(srRectangle.getRightTop().getX(),
                        p.getY() + yDistance);
                leftTop = new Point(srRectangle.getLeftTop().getX(),
                        p.getY() + yDistance);
                break;
            case THIRD:
                leftBottom = new Point(srRectangle.getLeftBottom().getX(),
                        p.getY() - yDistance);
                rightBottom = new Point(srRectangle.getRightBottom().getX(),
                        p.getY() - yDistance);
                rightTop = new Point(srRectangle.getRightTop().getX(),
                        p.getY() + delta);
                leftTop = new Point(srRectangle.getLeftTop().getX(),
                        p.getY() + delta);
                break;
            case FOURTH:
                leftBottom = new Point(srRectangle.getLeftBottom().getX(),
                        p.getY() - yDistance);
                rightBottom = new Point(srRectangle.getRightBottom().getX(),
                        p.getY() - yDistance);
                rightTop = new Point(srRectangle.getRightTop().getX(),
                        p.getY() + delta);
                leftTop = new Point(srRectangle.getLeftTop().getX(),
                        p.getY() + delta);
                break;
            default:
                leftBottom = null;
                rightBottom = null;
                rightTop = null;
                leftTop = null;
                break;
        }

        //noinspection unchecked
        return new Rectangle(leftBottom, rightBottom, rightTop, leftTop);
    }

    public Integer getLength() {
        return length;
    }

    public Integer getWidth() {
        return width;
    }

    public RTree<Integer, DataObject> getSqwin() {
        return sqwin;
    }


}
