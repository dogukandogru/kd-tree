import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Driver {
    public static KDTree kdTree = new KDTree();
    public static void main(String[] args) {
        try {
            File myObj = new File(args[0]);
            Scanner myReader = new Scanner(myObj);
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                data = data.replaceAll("\t"," ");
                runCommand(data);
            }
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    private static void runCommand(String command){
        String args[] = command.split(" ");
        if(command.startsWith("build-kdtree")){
            kdTree.buildKDTree(args[1]);
            System.out.println();
        }
        else if(command.startsWith("insert")){
            Point2D point = new Point2D(Double.parseDouble(args[1]), Double.parseDouble(args[2]));
            kdTree.insert(point);
        }
        else if(command.startsWith("remove")){
            System.out.println("My homework does not support REMOVE function :(");
        }
        else if(command.startsWith("search")){
            Point2D point = new Point2D(Double.parseDouble(args[1]), Double.parseDouble(args[2]));
            kdTree.search(point);
        }
        else if(command.startsWith("display-tree")){
            kdTree.displayTree();
        }
        else if(command.startsWith("display-points")){
            kdTree.displayPoints();
        }
        else if(command.startsWith("find-min-x")){
            kdTree.findMin(0);
        }
        else if(command.startsWith("find-min-y")){
            kdTree.findMin(1);
        }
        else if(command.startsWith("find-max-x")){
            kdTree.findMax(0);
        }
        else if(command.startsWith("find-max-y")){
            kdTree.findMax(1);
        }
        else if(command.startsWith("print-range")){
            System.out.println("My homework does not support PRINT-RANGE function :(");
        }
        else if(command.startsWith("quit")){
            return;
        }
    }
}
