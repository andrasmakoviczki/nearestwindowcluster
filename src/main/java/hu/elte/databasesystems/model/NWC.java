package hu.elte.databasesystems.model;

import hu.elte.databasesystems.model.rtree.geometry.Rectangle;

import java.util.ArrayList;
import java.util.PriorityQueue;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * Created by Andras Makoviczki on 2016. 11. 14.
 */
public class NWC {
    public Double euclidianDistance(DataObject p, DataObject q){
        return sqrt(pow(p.getX()-q.getX(),2) + pow(p.getY()-q.getY(),2));
    }

    public Double distMin(DataObject q, QualifiedWindow qwin) {
        Double distance = Double.MAX_VALUE;

        for (DataObject p: qwin.getSqwin()) {
            //euclidian distance
            Double actDistance = euclidianDistance(p,q);

            if(actDistance < distance){
                distance = actDistance;
            }
        }

        return distance;
    }

    public Double distMax(DataObject q, QualifiedWindow qwin) {
        Double distance = Double.MIN_VALUE;

        for (DataObject p: qwin.getSqwin()) {
            //euclidian distance
            Double actDistance = euclidianDistance(p,q);

            if(actDistance > distance){
                distance = actDistance;
            }
        }

        return distance;
    }

    public Double distAvg(DataObject q, QualifiedWindow qwin) {
        Double distance = 0.0;

        for (DataObject p: qwin.getSqwin()) {
            //euclidian distance
            distance = distance + euclidianDistance(p,q);
        }

        return distance/qwin.getQwin().getNumDataObjects();
    }

    //TODO
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
    }

    //Properties: az ablakban legalább egy objektum a függőleges és egy objektum a vízszintes élen helyezkedik el
    //NWC
    // eljut minden data object-hez távolság szerint növekvő sorrendben q-ból
    // a data object-ek R-tree-val indexeltek TODO: R-tree

    public ArrayList<DataObject> NWC(NWCQuery nwcq, PriorityQueue pq){
        Double distBest = Double.MAX_VALUE;
        ArrayList<DataObject> objs = new ArrayList<DataObject>();

        return objs;
    }

    public Boolean isPrunedByDEP(Rectangle rect, Integer n){
        Integer ub = 0;
        ///TODO


        if (ub < n){
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<DataObject> IWP(Rectangle rect){
        ArrayList<DataObject> result = new ArrayList<DataObject>();
        Integer nodes = 0;



        return result;
    }
}
