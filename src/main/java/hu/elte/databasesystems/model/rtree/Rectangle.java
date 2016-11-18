package hu.elte.databasesystems.model.rtree;

/**
 * Created by Andras Makoviczki on 2016. 11. 14..
 */
public class Rectangle {
    private Double x1;
    private Double x2;
    private Double y1;
    private Double y2;

    public Rectangle(Double x1, Double x2, Double y1, Double y2) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    //Tartalmazza-e a megadott pontot
    public Boolean contains(Double x, Double y){
        return x >= x1 && x <= x2 && y >= y1 && y >= y2;
    }

    public Rectangle mbr(){
        return this;
    }

    public Double getX1() {
        return x1;
    }

    public void setX1(Double x1) {
        this.x1 = x1;
    }

    public Double getX2() {
        return x2;
    }

    public void setX2(Double x2) {
        this.x2 = x2;
    }

    public Double getY1() {
        return y1;
    }

    public void setY1(Double y1) {
        this.y1 = y1;
    }

    public Double getY2() {
        return y2;
    }

    public void setY2(Double y2) {
        this.y2 = y2;
    }
}
