package hu.elte.databasesystems.model.rtree.geometry;


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
