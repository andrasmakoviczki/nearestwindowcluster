package hu.elte.databasesystems.model;

/**
 * Created by Andras Makoviczki on 2016. 11. 07.
 */

/**
 * Ablak lekérdezése
 */
class NWCQuery {

    private final Integer length;
    private final Integer width;
    private final Integer numDataObjects;

    public NWCQuery(Integer length, Integer width, Integer numDataObjects) {
        this.length = length;
        this.width = width;
        this.numDataObjects = numDataObjects;
    }

    public Integer getLength() {
        return length;
    }

    public Integer getWidth() {
        return width;
    }

    public Integer getNumDataObjects() {
        return numDataObjects;
    }
}
