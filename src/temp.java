import game.Coordinate;
import game.Maze;
import solver.Zombie;
import solver.ZombieRecursiveImpl;

import java.util.List;

/**
 * Created by amd on 3/6/15.
 */
public class temp {
    public static void main(String []args){
        Maze m = new Maze(8,8);
        m.setCoordinateValue(0,0,'S');
        m.setCoordinateValue(7,7,'F');
        m.setCoordinateValue(7,0,'Z');
        Zombie solver = new ZombieRecursiveImpl();
        List<Coordinate> path = solver.solve(m);
        for (Coordinate p:path)
            System.out.println(p);
    }
}
/**
 *  public MazeGUIPane(Zombie solver) {
 GridPane root = new GridPane();
 for (int row = 0; row < size; row++) {
 for (int col = 0; col < size; col++) {
 final StackPane square = new StackPane();
 String color;
 if ((row + col) % 2 == 0) {
 color = "white";
 } else {
 color = "black";
 }
 square.setUserData(new Chess(row, col));
 square.setStyle("-fx-background-color: " + color + ";");
 square.setOnMouseClicked(new EventHandler<MouseEvent>() {
 public void handle(MouseEvent event) {
 System.out.println(square.getUserData());
 String newcolor = "red";
 square.setStyle("-fx-background-color: " + newcolor + ";");
 }
 });
 root.add(square, col, row);

 }

 }
 }
 **/