package hu.elte.databasesystems.model;

import hu.elte.databasesystems.model.rtree.geometry.Geometry;
import hu.elte.databasesystems.model.rtree.geometry.Point;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.abs;

/**
 * Created by Andras Makoviczki on 2016. 11. 22..
 */
public class Rectangle<T extends Point> implements Geometry {
    private T leftTop;
    private T leftBottom;
    private T rightBottom;
    private T rightTop;


    public Rectangle(final T p1, final T p2, final T p3, final T p4) {
        this(new ArrayList<T>(){{add(p1);add(p2);add(p3);add(p4);}});
    }

    public Rectangle(List<T> points) {

        T minPoint = null;
        T maxPoint = null;
        Integer minX = Integer.MAX_VALUE;
        Integer minY = Integer.MAX_VALUE;
        Integer maxX = Integer.MIN_VALUE;
        Integer maxY = Integer.MIN_VALUE;

        for (T p: points) {
            if (p.getX() <= minX) {
                minX = p.getX();
                if (p.getY() <= minY) {
                    minY = p.getY();
                    minPoint = p;
                }
            }

            if (p.getX() >= maxX) {
                maxX = p.getX();
                if (p.getY() >= maxY) {
                    maxY = p.getY();
                    maxPoint = p;
                }
            }
        }

        this.leftBottom = minPoint;
        this.rightTop = maxPoint;

        points.remove(minPoint);
        points.remove(maxPoint);

        if(points.size() != 2){
            throw new UnsupportedOperationException();
        }

        Boolean l1 = points.get(0).getX() < points.get(1).getX();
        Boolean l2 = points.get(0).getY() < points.get(1).getY();

        if(l1){
            this.leftTop = points.get(0);
            this.rightBottom = points.get(1);
        } else if(!l1 && l2){
            this.leftTop = points.get(1);
            this.rightBottom = points.get(0);
        } else if(!l1 && !l2){
            throw new UnsupportedOperationException();
        }
    }


    //TODO
    public hu.elte.databasesystems.model.rtree.geometry.Rectangle mbr() {
        return null;
    }

    public Double perimeter() {
        return Double.valueOf(Integer.toString(
                abs(2* (leftBottom.getX() - rightBottom.getX())) + abs(2* (leftBottom.getY()-leftTop.getY()))));
    }

    public Double area() {
        return Double.valueOf(Integer.toString(
                abs(leftBottom.getX() - rightBottom.getX()) * abs(leftBottom.getY()-leftTop.getY())));
    }

    public Boolean contain(Rectangle searchRegion){
        Boolean l1=leftBottom.getX() >= searchRegion.getLeftBottom().getX() && leftBottom.getY() >= searchRegion.getLeftBottom().getY();
        Boolean l2=rightBottom.getX() <= searchRegion.getRightBottom().getX() && getRightBottom().getY() >= searchRegion.getRightBottom().getY();
        Boolean l3=rightTop.getX() <= searchRegion.getRightTop().getX() && getRightTop().getY() <= searchRegion.getRightTop().getY();
        Boolean l4=leftTop.getX() >= searchRegion.getLeftTop().getX() && getLeftTop().getY() <= searchRegion.getLeftTop().getY();

        if(l1 && l2 && l3 && l4){
            return true;
        } else {
            return false;
        }
    }

    public Double distance(Geometry g) {
        return null;
    }

    public T getLeftTop() {
        return leftTop;
    }

    public void setLeftTop(T leftTop) {
        this.leftTop = leftTop;
    }

    public T getLeftBottom() {
        return leftBottom;
    }

    public void setLeftBottom(T leftBottom) {
        this.leftBottom = leftBottom;
    }

    public T getRightBottom() {
        return rightBottom;
    }

    public void setRightBottom(T rightBottom) {
        this.rightBottom = rightBottom;
    }

    public T getRightTop() {
        return rightTop;
    }

    public void setRightTop(T rightTop) {
        this.rightTop = rightTop;
    }
}
