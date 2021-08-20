import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.ArrayList;
import java.util.Scanner; // Import the Scanner class to read text files



public class KDTree {

    Node root = null;

    public KDTree buildKDTree(String fileName){
        ArrayList<Point2D> points = new ArrayList<>();
        ArrayList<Point2D> pointsX = new ArrayList<>();
        ArrayList<Point2D> pointsY = new ArrayList<>();
        try {
            File myObj = new File(fileName);
            Scanner myReader = new Scanner(myObj);

            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                String arr[] = data.split("\t");
                Point2D point = new Point2D(Double.parseDouble(arr[0]), Double.parseDouble(arr[1]));
                points.add(point);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        for(int i=0; i<points.size()/2; i++){
            pointsX.add(points.get(i));
        }
        for(int i=points.size()/2; i<points.size(); i++){
            pointsY.add(points.get(i));
        }

        root = auxBuildKDTree(pointsX, pointsY, 0);
        return null;
    }

    private Node auxBuildKDTree(ArrayList<Point2D> Px, ArrayList<Point2D> Py, int depth){
        if(Px.size() == 0 || Py.size() == 0){
            //Point2D point = new Point2D(-999, -999);
            //return new Node(point, null, null);
            return null;

        }
        if(depth % 2 == 0 && Px.size() == 1){
            return new Node(Px.get(0), null, null);
        }
        else if(depth % 2 == 1 && Py.size() == 1){
            return new Node(Py.get(0), null, null);
        }
        else{
            Point2D l;
            ArrayList<Point2D> P1x = new ArrayList<>();
            ArrayList<Point2D> P2x = new ArrayList<>();
            ArrayList<Point2D> P1y = new ArrayList<>();
            ArrayList<Point2D> P2y = new ArrayList<>();
            if (depth % 2 == 0){ // Depth is even
                l = Px.get(Px.size()/2);
                for (int i = 0; i < (Px.size()/2); i++) {
                    P1x.add(Px.get(i));
                }
                for (int i = (Px.size()/2)+1; i< Px.size(); i++){
                    P2x.add(Px.get(i));
                }
                for(int i = 0; i < P1x.size(); i++){
                    Point2D point = new Point2D(P1x.get(i).xCoordinate, P1x.get(i).yCoordinate);
                    P1y.add(point);
                }
                for(int i = 0; i < P2x.size(); i++){
                    Point2D point = new Point2D(P2x.get(i).xCoordinate, P2x.get(i).yCoordinate);
                    P2y.add(point);
                }
                P1y.sort(new yCoordinateComparator());
                P2y.sort(new yCoordinateComparator());
            }
            else{
                l = Py.get(Py.size()/2);
                for (int i = 0; i < Py.size()/2; i++) {
                    P1y.add(Py.get(i));
                }
                for (int i = (Py.size()/2)+1; i< Py.size(); i++){
                    P2y.add(Py.get(i));
                }
                for(int i = 0; i < P1y.size(); i++){
                    Point2D point = new Point2D(P1y.get(i).xCoordinate, P1y.get(i).yCoordinate);
                    P1x.add(point);
                }
                for(int i = 0; i < P2y.size(); i++){
                    Point2D point = new Point2D(P2y.get(i).xCoordinate, P2y.get(i).yCoordinate);
                    P2x.add(point);
                }
                P1x.sort(new xCoordinateComparator());
                P2x.sort(new xCoordinateComparator());
            }

            Node vLeft = auxBuildKDTree(P1x, P1y, depth+1);
            Node vRight = auxBuildKDTree(P2x, P2y, depth+1);
            Node v = new Node(l, vLeft, vRight);
            return v;
        }

    }

    public void insert(Point2D point){
        auxInsert(null, root, point, 0);
    }

    private void auxInsert(Node parent, Node node, Point2D point, int depth){
        if(node == null){
            if(depth % 2 == 0){
                parent.left = new Node(point, null, null);
            }
            else{
                parent.right = new Node(point, null, null);
            }
            System.out.println("Inserted ("+ point.xCoordinate + "," + point.yCoordinate + ")");
        }
        else{
            if(depth % 2 == 0){
                if(point.xCoordinate < node.point.xCoordinate  ){
                    auxInsert(node, node.left, point, depth+1);
                }
                else{
                    auxInsert(node, node.right, point, depth+1);
                }
            }
            else{
                if(point.yCoordinate < node.point.yCoordinate){
                    auxInsert(node, node.left, point, depth+1);
                }
                else{
                    auxInsert(node, node.right, point, depth+1);
                }
            }
        }
    }

    public Point2D search(Point2D point){
        return auxSearch(root, point, 0);
    }

    private Point2D auxSearch(Node node, Point2D point, int depth){
        if(node == null){
            System.out.println("Not Found (" + point.xCoordinate + "," + point.yCoordinate + ")");
            return null;
        }
        if(node.point.xCoordinate ==  point.xCoordinate && node.point.yCoordinate == point.yCoordinate){
            System.out.println("Found (" + point.xCoordinate + "," + point.yCoordinate + ")");
            return node.point;
        }
        else{
            if(depth % 2 == 0){
                if(point.xCoordinate < node.point.xCoordinate){
                    return auxSearch(node.left, point, depth+1);
                }
                else{
                    return auxSearch(node.right, point, depth+1);
                }
            }
            else{
                if(point.yCoordinate < node.point.yCoordinate){
                    return auxSearch(node.left, point, depth+1);
                }
                else{
                    return auxSearch(node.right, point, depth+1);
                }
            }
        }
    }


    public void displayTree(){
        auxDisplayTree(root, 0);
    }

    private void auxDisplayTree(Node node, int depth){
        if(node == null)
            return;

        for(int i=0; i<depth; i++){
            System.out.print(".");
        }

        if(node.left == null && node.right == null){ // Leaf Node
            System.out.println("(P: (" + node.point.xCoordinate + "," + node.point.yCoordinate + "))");
        }
        else{ // Internal node
            if(depth % 2 == 0){ // Vertical Cut
                System.out.println("(|: (x=" + node.point.xCoordinate + "))");
            }
            else{ // Horizontal Cut
                System.out.println("(-: (y=" + node.point.xCoordinate + "))");
            }
        }
        auxDisplayTree(node.left, depth+1);
        auxDisplayTree(node.right, depth+1);
    }

    public void displayPoints(){
        auxDisplayPoints(root);
    }

    private void auxDisplayPoints(Node node){
        if(node == null)
            return;
        else{
            auxDisplayPoints(node.left);
            System.out.println("(" + node.point.xCoordinate + "," + node.point.yCoordinate + ")");
            auxDisplayPoints(node.right);
        }
    }

    public Point2D findMin(int d){
        if(d == 0){ // x-dimension
            Point2D minX =  auxFindMinX(root, 0);
            System.out.println("minimum-x is ("+ minX.xCoordinate + "," + minX.yCoordinate +")");
            return minX;
        }
        else{ // y-dimension
            Point2D minY =   auxFindMinY(root, 0);
            System.out.println("minimum-y is ("+ minY.xCoordinate + "," + minY.yCoordinate +")");
            return minY;
        }
    }
    private Point2D auxFindMinX(Node node, int depth){
        if(node == null){
            return new Point2D(Double.MAX_VALUE, Double.MAX_VALUE);
        }
        else{
            if(depth % 2 == 0){
                Point2D leftMinimum = auxFindMinX(node.left, depth+1);
                if(leftMinimum.xCoordinate < node.point.xCoordinate)
                    return leftMinimum;
                else
                    return node.point;
            }
            else{
                Point2D leftMinimum = auxFindMinX(node.left, depth+1);
                Point2D rightMinimum = auxFindMinX(node.right, depth+1);
                if(leftMinimum.xCoordinate < node.point.xCoordinate){
                    if(rightMinimum.xCoordinate < leftMinimum.xCoordinate)
                        return rightMinimum;
                    else
                        return leftMinimum;
                }
                else{
                    if(rightMinimum.xCoordinate < node.point.xCoordinate)
                        return rightMinimum;
                    else
                        return node.point;
                }
            }
        }
    }

    private Point2D auxFindMinY(Node node, int depth){
        if(node == null){
            return new Point2D(Double.MAX_VALUE, Double.MAX_VALUE);
        }
        else{
            if(depth % 2 == 1){
                Point2D leftMinimum = auxFindMinY(node.left, depth+1);
                if(leftMinimum.yCoordinate < node.point.yCoordinate)
                    return leftMinimum;
                else
                    return node.point;
            }
            else{
                Point2D leftMinimum = auxFindMinY(node.left, depth+1);
                Point2D rightMinimum = auxFindMinY(node.right, depth+1);
                if(leftMinimum.yCoordinate < node.point.yCoordinate){
                    if(leftMinimum.yCoordinate < rightMinimum.yCoordinate)
                        return leftMinimum;
                    else
                        return rightMinimum;
                }
                else{
                    if(node.point.yCoordinate < rightMinimum.yCoordinate)
                        return node.point;
                    else
                        return rightMinimum;
                }
            }
        }
    }


    public Point2D findMax(int d){
        if(d == 0){
            Point2D maxX =  auxFindMaxX(root, 0);
            System.out.println("maximum-x is ("+ maxX.xCoordinate + "," + maxX.yCoordinate +")");
            return maxX;
        }
        else{
            Point2D maxY = auxFindMaxY(root, 0);
            System.out.println("maximum-y is ("+ maxY.xCoordinate + "," + maxY.yCoordinate +")");
            return maxY;
        }
    }
    private Point2D auxFindMaxX(Node node, int depth){
        if(node == null){
            return new Point2D(Double.MIN_VALUE, Double.MIN_VALUE);
        }
        else{
            if(depth % 2 == 0){
                Point2D rightMaximum = auxFindMaxX(node.right, depth+1);
                if(rightMaximum.xCoordinate > node.point.xCoordinate)
                    return rightMaximum;
                else
                    return node.point;
            }
            else{
                Point2D leftMaximum = auxFindMaxX(node.left, depth+1);
                Point2D rightMaximum = auxFindMaxX(node.right, depth+1);
                if(leftMaximum.xCoordinate > node.point.xCoordinate){
                    if(rightMaximum.xCoordinate > leftMaximum.xCoordinate)
                        return rightMaximum;
                    else
                        return leftMaximum;
                }
                else{
                    if(rightMaximum.xCoordinate > node.point.xCoordinate)
                        return rightMaximum;
                    else
                        return node.point;
                }
            }
        }
    }
    private Point2D auxFindMaxY(Node node, int depth){
        if(node == null){
            return new Point2D(Double.MIN_VALUE, Double.MIN_VALUE);
        }
        else{
            if(depth % 2 == 1){
                Point2D rightMaximum = auxFindMaxY(node.right, depth+1);
                if(rightMaximum.yCoordinate > node.point.yCoordinate)
                    return rightMaximum;
                else
                    return node.point;
            }
            else{
                Point2D leftMaximum = auxFindMaxY(node.left, depth+1);
                Point2D rightMaximum = auxFindMaxY(node.right, depth+1);
                if(leftMaximum.yCoordinate > node.point.yCoordinate){
                    if(leftMaximum.yCoordinate > rightMaximum.yCoordinate)
                        return leftMaximum;
                    else
                        return rightMaximum;
                }
                else{
                    if(node.point.yCoordinate > rightMaximum.yCoordinate)
                        return node.point;
                    else
                        return rightMaximum;
                }
            }
        }
    }
}
