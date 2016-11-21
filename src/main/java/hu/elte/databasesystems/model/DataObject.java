package hu.elte.databasesystems.model;

import hu.elte.databasesystems.model.rtree.geometry.Point;

/**
 * Created by Andras Makoviczki on 2016. 11. 07.
 */
public class DataObject extends Point{

    private String name;
    private Quadrant quadrant;
    private Double distance;

    public DataObject(Integer x, Integer y, String name) {
        super(x,y);
        this.name = name;
    }

    @Override
    public String toString() {
        return "DataObject{" +
                "x='" + getX() + '\'' +
                "y='" + getY() + '\'' +
                "name='" + name + '\'' +
                ", quadrant=" + quadrant +
                ", distance=" + distance +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Quadrant getQuadrant() {
        return quadrant;
    }

    public void setQuadrant(Quadrant quadrant) {
        this.quadrant = quadrant;
    }

    public void setQuadrant(Integer x, Integer y){
        if(x > 0 && y > 0)
        {
            quadrant = Quadrant.FIRST;
        } else if(x > 0 && y < 0){
            quadrant = Quadrant.FOURTH;
        } else if(x < 0 && y > 0){
            quadrant = Quadrant.SECOND;
        } else if (x < 0 && y < 0){
            quadrant = Quadrant.THIRD;
        } else {
            quadrant= Quadrant.ORIGO;
        }

    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

}
