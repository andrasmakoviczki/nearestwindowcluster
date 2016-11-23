package hu.elte.databasesystems.model;

/**
 * Created by Andras Makoviczki on 2016. 11. 07.
 */
public class NWCQuery {
    //query point
    private DataObject q;
    //szélesség
    private Integer length;
    //magasság
    private Integer width;
    private Integer numDataObjects;

    public NWCQuery(Integer length, Integer width, Integer numDataObjects){
        this(new DataObject(0,0,"origin"),length,width,numDataObjects);
    }

    public NWCQuery(DataObject q, Integer length, Integer width, Integer numDataObjects) {
        this.q = q;
        this.length = length;
        this.width = width;
        this.numDataObjects = numDataObjects;
    }

    public DataObject getQ() {
        return q;
    }

    public void setQ(DataObject q) {
        this.q = q;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getNumDataObjects() {
        return numDataObjects;
    }

    public void setNumDataObjects(Integer numDataObjects) {
        this.numDataObjects = numDataObjects;
    }
}
