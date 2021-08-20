public class Node{
    Point2D point;
    Node left;
    Node right;

    public Node(Point2D point, Node left, Node right){
        this.point = point;
        this.left = left;
        this.right = right;
    }
}