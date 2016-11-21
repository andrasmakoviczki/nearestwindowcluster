package hu.elte.databasesystems.model;

import java.util.ArrayList;

/**
 * Created by Andras Makoviczki on 2016. 11. 07.
 */
public class QualifiedWindow {
    private NWCQuery qwin;
    private ArrayList<DataObject> sqwin;

    public QualifiedWindow(NWCQuery qwin, ArrayList<DataObject> sqwin) {
        this.qwin = qwin;
        this.sqwin = sqwin;
    }

    public NWCQuery getQwin() {
        return qwin;
    }

    public void setQwin(NWCQuery qwin) {
        this.qwin = qwin;
    }

    public ArrayList<DataObject> getSqwin() {
        return sqwin;
    }

    public void setSqwin(ArrayList<DataObject> sqwin) {
        this.sqwin = sqwin;
    }


    //Feltétel
    //sqwin részhalmaza p-nek
    //|sqwin| >= n

}
