package hu.elte.databasesystems.model;

import hu.elte.databasesystems.AppController;
import hu.elte.databasesystems.model.rtree.Entry;
import hu.elte.databasesystems.model.rtree.LeafNode;
import hu.elte.databasesystems.model.rtree.Node;
import hu.elte.databasesystems.model.rtree.NonLeafNode;
import hu.elte.databasesystems.model.rtree.geometry.Rectangle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * Created by Andras Makoviczki on 2016. 11. 14.
 */
public class NWC {

    private Double distBest;

    public Double getDistBest() {
        return distBest;
    }

    public void setDistBest(Double distBest) {
        this.distBest = distBest;
    }

    private static final Logger log = LoggerFactory.getLogger(NWC.class);

    public QualifiedWindow run(RTree<Integer,DataObject> tree, Integer width, Integer length, Integer numOfObject) {

        if(width == null){
            width = 8;
        }
        if(length == null){
            length = 8;
        }
        if(numOfObject == null){
            numOfObject = 3;
        }

        Double distBest = Double.MAX_VALUE;
        QualifiedWindow bestQwin = null;

        for (Integer i = 0; i < tree.getSize(); i++) {
            DataObject p = tree.getGeometry(i);
            NWCQuery query = new NWCQuery(length, width, numOfObject);
            SearchRegion sr = new SearchRegion(p, query);
            sr.selectPoints(tree);

            List<QualifiedWindow> qwins = new ArrayList<QualifiedWindow>();
            for (Map.Entry<Integer, DataObject> item : sr.getYList().entrySet()) {
                qwins.add(new QualifiedWindow(query, sr, item.getValue()));
            }

            for (QualifiedWindow qwin : qwins) {
                if (qwin.isQualified() && qwin.mindist() < distBest) {
                    distBest = qwin.mindist();
                    bestQwin = qwin;
                    this.distBest = distBest;
                }
            }
        }

        if(bestQwin != null && !bestQwin.isQualified()){
            bestQwin = null;
        }

        return bestQwin;

    }

    /*public ArrayList<DataObject> NWC(NWCQuery query, PriorityQueue<Node<Integer,DataObject>> pq){
        Double distBest = Double.MAX_VALUE;
        ArrayList<DataObject> objs = new ArrayList<DataObject>();
        //TODO
        Object pr = null;
        Rectangle mbr = null;

        //TODO
        //pq.add()

        while (pq.size() > 0){
            Node<Integer,DataObject> p = pq.remove();
            if(p instanceof NonLeafNode){
                mbr = ((NonLeafNode) p).getMbr();
                //TODO DEP
                if(mbr != null && !isPrunedByDEP(mbr,0)){
                    for (Integer i = 0; i < p.size();++i) {
                        pq.add(((NonLeafNode) p).getChild(i));
                    }
                }
            } else if (p instanceof LeafNode) {
                //TODO
                DataObject obj = null; //((LeafNode) p).getEntry().getGeomerty();
                obj.setQuadrant();
                obj.setEdge();
                SearchRegion sr = null;//new SearchRegion();
                SearchRegion sr2 = null;
                //TODO
                if(sr2 != null && !isPrunedByDEP(new Rectangle(0.0,0.0,0.0,0.0),0)){
                    List<DataObject> ssrp = (List<DataObject>) IWP(new Rectangle(0.0,0.0,0.0,0.0),0);
                    List<DataObject> orderedSsrp = new ArrayList<DataObject>();
                    //TODO: rendezés y szerint
                    //Arrays.sort(ssrp);
                    //remove
                    for(Integer i = 0; i < ssrp.size(); ++i){
                        QualifiedWindow qwin = new QualifiedWindow(query,null,null);
                        //TODO check mindist
                        if(qwin.getNumberOfObjects() > query.getNumDataObjects() && minDist(qwin) < distBest){
                            if(minDist(qwin) < distBest){
                                distBest = minDist(qwin);
                                for (Integer j = 0; i < qwin.getSqwin().getSize();++i){
                                    objs.add(qwin.getSqwin().getGeometry(i));
                                }
                                //TODO update PR
                            }
                        }
                    }
                }
            }
            else {throw new UnsupportedOperationException();}
        }
        return objs;
    }*/

    /*public Double minDist(QualifiedWindow qwin){
        Double minDistance = Double.MAX_VALUE;
        for (DataObject item : qwin.getSqwin()){
            if(item.getDistance() < minDistance){
                minDistance = item.getDistance();
            }
        }
        return minDistance;
    }*/

    /*public Boolean isPrunedByDEP(Rectangle rect, Integer n){
        Integer ub = 0;
        ///TODO


        if (ub < n){
            return true;
        } else {
            return false;
        }
    }*/

    /*public RTree<Integer, DataObject> IWP(Rectangle rect, int o){
        RTree<Integer,DataObject> result = new RTree<Integer, DataObject>();
        List<Node> nodes = null;
        //TODO
        Integer r = null;
        for(Integer i = 1; i < r; ++i){
            if(rect.contains(mbrh(i),null)){
                Node N = bp(i);
                nodes.add(N);
                break;
            }
        }

        List<Object> Ni = new ArrayList<Object>();
        for(Object item : Ni){
            Object mbr0j;
            if(rect.intersect(null) != null){
                nodes.add((Node) item);
            }
        }

        for (Node n: nodes) {
            //TODO???
            result.add((Entry) n);
        }

        return result;
    }*/

    /*private Node bp(Integer i) {
        throw new UnsupportedOperationException();
    }

    private Double mbrh(Integer i) {
        throw new UnsupportedOperationException();
    }

    //Legközelebbi pont a qualified window-ban q-hoz, ami lefedi a {p1,..pn}-t
    public Double distNearest(DataObject q, QualifiedWindow qwin){
        //minVqwin<-qwins(MINDIST(q,qwin))
        //qwins össszes {p1,pn}-t tartalmazó qualified window
        return null;
    }

    //Vázlat
    public ArrayList<DataObject> problemTransformation(){
        //1. Init
        Double distBest = Double.MAX_VALUE;
        ArrayList<DataObject> objs = new ArrayList<DataObject>();
        String nearestQwin = "";
        do {
            //2. legközelebbi qwin megkeresése
            //3.
            QualifiedWindow qwin = null;
            Double minDist = null;
            if (qwin != null && minDist < distBest) {
                //4.  ???
                //5.
                if (minDist < distBest) {
                    objs = null;
                    distBest = minDist;
                }
            }
        }
        //6.
        while(nearestQwin != null);
        //7
        return objs;
    }*/
}
