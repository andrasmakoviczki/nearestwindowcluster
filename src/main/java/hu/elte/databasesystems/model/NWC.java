package hu.elte.databasesystems.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Andras Makoviczki on 2016. 11. 14.
 */

/**
 * Nearest Window Cluster Queries algoritmus
 */
public class NWC {

    @SuppressWarnings("unused")
    private static final Logger log = LoggerFactory.getLogger(NWC.class);
    private Double distBest;

    public Double getDistBest() {
        return distBest;
    }

    //Algoritmus futtatása
    public QualifiedWindow run(RTree<Integer, DataObject> tree, Integer width, Integer length, Integer numOfObject) {

        if (width == null) {
            width = 8;
        }
        if (length == null) {
            length = 8;
        }
        if (numOfObject == null) {
            numOfObject = 3;
        }

        //Inicializálás
        Double distBest = Double.MAX_VALUE;
        QualifiedWindow bestQwin = null;

        //Iterálás a pontokon x koordináta szerint
        for (Integer i = 0; i < tree.getSize(); i++) {
            DataObject p = tree.getGeometry(i);
            NWCQuery query = new NWCQuery(length, width, numOfObject);
            //Keresési terület meghatározása
            SearchRegion sr = new SearchRegion(p, query);
            //Területen lévő pontok kiválasztása
            sr.selectPoints(tree);

            List<QualifiedWindow> qwins = new ArrayList<QualifiedWindow>();
            //Iterálása a keresési terület pontjain y koordináta (távolság) szerint
            for (Map.Entry<Double, DataObject> item : sr.getYList().entrySet()) {
                //Window meghatározása
                qwins.add(new QualifiedWindow(query, sr, item.getValue()));
            }

            //Iterálás a window objektumokon
            for (QualifiedWindow qwin : qwins) {
                //Ha qualified window-t találtunk és a közelebb van, mint az eddigiek,
                //akkor ezt az ablakot választjuk ki
                if (qwin.isQualified() && qwin.mindist() < distBest) {
                    distBest = qwin.mindist();
                    bestQwin = qwin;
                    this.distBest = distBest;
                }
            }
        }

        if (bestQwin != null && !bestQwin.isQualified()) {
            bestQwin = null;
        }

        return bestQwin;

    }
}
