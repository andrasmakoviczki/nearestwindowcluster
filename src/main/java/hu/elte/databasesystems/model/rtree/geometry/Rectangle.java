package hu.elte.databasesystems.model.rtree.geometry;

import java.util.Collection;

/**
 * Created by Andras Makoviczki on 2016. 11. 14.
 */
public class Rectangle implements Geometry {
    private Double x1;
    private Double y1;
    private Double x2;
    private Double y2;

    public Rectangle(Double x1, Double y1, Double x2, Double y2) {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public Boolean contains(Double x, Double y){
        return x >= x1 && x <= x2 && y >= y1 && y >= y2;
    }

    public Boolean isIntersect(Double x1, Double y1, Double x2, Double y2, Double a1, Double b1, Double a2, Double b2){
        return x1 <= a2 && a1 <= x2 && y1 <= b2 && b1 <= y2;
    }

    public Boolean isIntersect(Rectangle rectangle){
        return isIntersect(x1,y1,x2,y2,rectangle.getX1(),rectangle.getY1(),rectangle.getX2(),rectangle.getY2());
    }

    public Rectangle mbr(){
        return this;
    }

    public Double perimeter() {
        return 2*(x2-x1) + 2*(y2-y1);
    }

    public Double area() {
        return (x2-x1)*(y2-y1);
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

    //Téglalap növelése
    public Rectangle add(Rectangle r){
       return new Rectangle(Math.min(x1,r.getX1()),Math.min(y1,r.getY1()),Math.max(x2,r.getX2()),Math.max(y2,r.getY2()));
    }

    @Override
    public String toString() {
        return "Rectangle{" +
                "x1=" + x1 +
                ", y1=" + y1 +
                ", x2=" + x2 +
                ", y2=" + y2 +
                '}';
    }
}
