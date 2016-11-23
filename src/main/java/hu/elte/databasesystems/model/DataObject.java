package hu.elte.databasesystems.model;

import hu.elte.databasesystems.model.rtree.geometry.Point;
import hu.elte.databasesystems.util.Edge;
import hu.elte.databasesystems.util.Quadrant;

/**
 * Created by Andras Makoviczki on 2016. 11. 07.
 */
public class DataObject extends Point{

    private String name;
    private Quadrant quadrant;
    private Edge edge;
    private Double distance;

    public DataObject(Integer x, Integer y, String name) {
        super(x,y);
        this.name = name;
    }

    public DataObject(Integer x, Integer y) {
        super(x,y);
        this.name = "";
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

    public void setQuadrant(){
        if(getX() > 0 && getY() > 0){
            quadrant = Quadrant.FIRST;
        } else if(getX() > 0 && getY() < 0){
            quadrant = Quadrant.FOURTH;
        } else if(getX() < 0 && getY() > 0){
            quadrant = Quadrant.SECOND;
        } else if (getX() < 0 && getY() < 0){
            quadrant = Quadrant.THIRD;
        } else {
            quadrant= Quadrant.ORIGO;
        }
    }

    public Edge getEdge() {
        return edge;
    }

    public void setEdge() {
        switch (quadrant){
            case FIRST:
                edge = Edge.RIGHT;
                break;
            case SECOND:
                edge = Edge.LEFT;
                break;
            case THIRD:
                edge = Edge.LEFT;
                break;
            case ORIGO:
                break;
            case FOURTH:
                edge = Edge.RIGHT;
                break;
        }
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

}
