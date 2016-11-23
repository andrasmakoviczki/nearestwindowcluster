package hu.elte.databasesystems.model.rtree.geometry;


/**
 * Created by Andras Makoviczki on 2016. 11. 19..
 */
public class Point implements Geometry {

    private final Integer x;
    private final Integer y;

    public Point(Integer x, Integer y) {
        this.x = x;
        this.y = y;
    }

    public Rectangle mbr() {
        return new Rectangle(new Double(x), new Double(y), new Double(x), new Double(y));
    }

    public Double perimeter() {
        return 0.0;
    }

    public Integer getX() {
        return x;
    }

    public Integer getY() {
        return y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
