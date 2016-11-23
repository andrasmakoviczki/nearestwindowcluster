package hu.elte.databasesystems.model;

import hu.elte.databasesystems.util.Edge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Andras Makoviczki on 2016. 11. 17.
 */
public class SearchRegion {
    
    Rectangle r;
    RTree<Integer,DataObject> ssrp;
    DataObject p;
    Integer width;
    Integer length;

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public SearchRegion(DataObject dataObject, NWCQuery query) {
        this.r = new Rectangle(setRectangle(dataObject,query));
        this.p = dataObject;
        this.width = query.getWidth() * 2;
        this.length = query.getLength();
    }

    public RTree<Integer,DataObject> skipLowerThanP(RTree<Integer,DataObject> tree,DataObject p){
        RTree<Integer,DataObject> reducedTree = new RTree<Integer,DataObject>();
        Integer intGenerator = 0;
        for(Integer i = 0; i < tree.getSize();++i){
            if(tree.getGeometry(i).getY() >= p.getY()){
                reducedTree.add(intGenerator,tree.getGeometry(i));
            }
        }
        return reducedTree;
    }

    public void selectPoints(RTree<Integer,DataObject> tree){
        this.ssrp = new RTree<Integer, DataObject>();
        Integer intGenerator = 0;
        for(Integer i = 0; i < tree.getSize();++i){
            DataObject p = tree.getGeometry(i);

            Boolean l1=p.getX() >= r.getLeftBottom().getX() && p.getY() >= r.getLeftBottom().getY();
            Boolean l2=p.getX() <= r.getRightBottom().getX() && p.getY() >= r.getRightBottom().getY();
            Boolean l3=p.getX() <= r.getRightTop().getX() && p.getY() <= r.getRightTop().getY();
            Boolean l4=p.getX() >= r.getLeftTop().getX() && p.getY() <= r.getLeftTop().getY();

            if(l1 && l2 && l3 && l4){
                ssrp.add(intGenerator,tree.getGeometry(i));
                intGenerator = intGenerator + 1;
            }
        }

        ssrp.setParents();
    }

    public List<DataObject> setRectangle(DataObject dataObject,NWCQuery query){
        DataObject p1;
        DataObject p2;
        DataObject p3;
        DataObject p4;
        
        if(dataObject.getEdge() == Edge.RIGHT){
            p1 = new DataObject(dataObject.getX() - query.getLength(), dataObject.getY() - query.getWidth());
            p2 = new DataObject(dataObject.getX(), dataObject.getY() - query.getWidth());
            p3 = new DataObject(dataObject.getX(), dataObject.getY() + query.getWidth());
            p4 = new DataObject(dataObject.getX() - query.getLength(), dataObject.getY() + query.getWidth());
        } else if (dataObject.getEdge() == Edge.LEFT){
            p1 = new DataObject(dataObject.getX() + query.getLength(), dataObject.getY() + query.getWidth());
            p2 = new DataObject(dataObject.getX(), dataObject.getY() + query.getWidth());
            p3 = new DataObject(dataObject.getX(), dataObject.getY() - query.getWidth());
            p4 = new DataObject(dataObject.getX() + query.getLength(), dataObject.getY() - query.getWidth());
        } else{
            throw new UnsupportedOperationException();
        }
        
        List<DataObject> list = new ArrayList<DataObject>();
        list.add(p1);
        list.add(p2);
        list.add(p3);
        list.add(p4);
        
        return list;
    }

    public Rectangle getR() {
        return r;
    }

    public void setR(Rectangle r) {
        this.r = r;
    }

    public RTree<Integer, DataObject> getSsrp() {
        return ssrp;
    }

    public void setSsrp(RTree<Integer, DataObject> ssrp) {
        this.ssrp = ssrp;
    }

    public DataObject getP() {
        return p;
    }

    public void setP(DataObject p) {
        this.p = p;
    }

    //Módosítás: y helyett (p p') távolságot tárolunk
    public Map<Integer, DataObject> getYList() {
        Map list = new HashMap();
        for (Integer i = 0; i < ssrp.getSize(); i++) {
            DataObject p2 = ssrp.getGeometry(i);
            switch (p.getQuadrant()){
                case FIRST:
                    if(p.getY() <= p2.getY()){
                        list.put(Math.abs(p.getDistance()-p2.getDistance()),p2);
                    }
                    break;
                case SECOND:
                    if(p.getY() <= p2.getY()){
                        list.put(Math.abs(p.getDistance()-p2.getDistance()),p2);
                    }
                    break;
                case THIRD:
                    if(p.getY() >= p2.getY()){
                        list.put(Math.abs(p.getDistance()-p2.getDistance()),p2);
                    }
                    break;
                case FOURTH:
                    if(p.getY() >= p2.getY()){
                        list.put(Math.abs(p.getDistance()-p2.getDistance()),p2);
                    }
                    break;
                case ORIGO:
                    break;
            }
        }
        return list;
    }
}
