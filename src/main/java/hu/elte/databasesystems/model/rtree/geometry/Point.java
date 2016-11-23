package hu.elte.databasesystems.model.rtree.geometry;


import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/**
 * Created by Andras Makoviczki on 2016. 11. 19..
 */
public class Point implements Geometry {

    private Integer x;
    private Integer y;

    public Rectangle mbr() {
        return new Rectangle(new Double(x),new Double(y),new Double(x),new Double(y));
    }

    public Double perimeter() {
        return 0.0;
    }

    public Double area() {
        return 0.0;
    }

    public Double distance(){
        return distance(0,0);
    }

    public Double distance(Integer x, Integer y){
        return distance(new Point(x,y));
    }

    public Double distance(Point point){
        return distance(point);
    }

    public Double distance(Geometry geometry) {
        Point p = (Point) geometry;
        return sqrt(pow(p.getX()-x,2) + pow(p.getY()-y,2));
    }


    public Point(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
