import java.awt.*;
import java.util.Comparator;

class xCoordinateComparator implements Comparator<Point2D> {
    @Override
    public int compare(Point2D o1, Point2D o2) {
        if(o1.xCoordinate == o2.xCoordinate)
            return 0;
        else if(o1.xCoordinate > o2.xCoordinate)
            return 1;
        else
            return -1;
    }
}

class yCoordinateComparator implements Comparator<Point2D> {
    @Override
    public int compare(Point2D o1, Point2D o2) {
        if(o1.yCoordinate == o2.yCoordinate)
            return 0;
        else if(o1.yCoordinate > o2.yCoordinate)
            return 1;
        else
            return -1;
    }
}


public class Point2D {
    public double xCoordinate;
    public double yCoordinate;

    public Point2D(double xCoordinate, double yCoordinate){
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }
}
