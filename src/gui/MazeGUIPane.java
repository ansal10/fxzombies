package gui;

import game.Box;
import game.Coordinate;
import game.Maze;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import solver.Zombie;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MazeGUIPane extends BorderPane {
    static final int ROWS = 8;
    static final int COLS = 8;
    static final int SIZE= 8;
    List<StackPane> squares = new ArrayList<StackPane>();
    List<StackPane> moves = new ArrayList<StackPane>();
    final Label messegeLabel = new Label();
    HBox labelBox = getHbox();
    final Maze maze = new Maze(ROWS, COLS);

    public MazeGUIPane(final Zombie solver) {
        super();

        File f = new File("src/application/application.css");
        this.getStylesheets().clear();
        this.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
        System.out.println("file:///" + f.getAbsolutePath().replace("\\", "/"));
        messegeLabel.setText("New Maze");

        labelBox.getChildren().add(messegeLabel);
        this.setTop(labelBox);

        maze.setBoard(getMazeBoard());
        for(int i = 0 ; i < ROWS*COLS ; i++)
            squares.add(new StackPane());

        initialize(maze,true);


        Button b1 = new Button("Solve maze");
        b1.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                List<Coordinate> path = solver.solve(maze);
                if(path.size()==0)
                    messegeLabel.setText("Maze is not Solvable");
                else
                    messegeLabel.setText("Maze is Solved");

                for(StackPane square:squares){
                    Coordinate data = (Coordinate)square.getUserData();
                    if(data.getVal()==' ')
                        square.getStyleClass().add("grid-clear");

                    for(Coordinate coordinate:path){
                        if(coordinate.getCol()==data.getCol() && coordinate.getRow()==data.getRow())
                            square.getStyleClass().add("grid-solved");

                    }
                }

            }
        });

        Button b2 = new Button("New Maze");
        b2.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                squares.removeAll(squares);
                for(int i = 0 ; i < ROWS*COLS ; i++)
                    squares.add(new StackPane());
                maze.setBoard(getMazeBoard());
                initialize(maze,false);
                messegeLabel.setText("New Maze");
                moves.removeAll(moves);
            }
        });
        HBox hbox = getHbox();

        hbox.getChildren().addAll(b1,b2);

        this.setBottom(hbox);

        this.setHeight(500);
        this.setWidth(800);
    }

    char[][] getMazeBoard(){
        char [][] mazeBoard = new char[ROWS][COLS];
        Random rand = new Random();
        for(int row = 0 ; row<ROWS ; row++){
            for(int col=0 ; col<COLS ; col++){
                if(rand.nextGaussian()>.75){
                    mazeBoard[row][col]='W';
                }
                else{
                    mazeBoard[row][col]=' ';
                }
            }
        }
        mazeBoard[0][0]='S';
        mazeBoard[ROWS-1][COLS-1]='F';
        return mazeBoard;
    }

    private void initialize(Maze maze, boolean isFirst){
        File f = new File("src/application/application.css");
        this.getStylesheets().clear();
        this.getStylesheets().add("file:///" + f.getAbsolutePath().replace("\\", "/"));
        System.out.println("file:///" + f.getAbsolutePath().replace("\\", "/"));
        messegeLabel.setText("New Maze");

        final GridPane gridPane = new GridPane();

        final Box lastVisit = new Box(0,-1,'S');

        for (int row = 0; row < SIZE; row++) {
            for (int col = 0; col < SIZE; col++) {
                final StackPane square = squares.get(row*COLS + col);
                getStyleClass().add("bordered-titled-border");
                if(maze.getCoordinate(row,col).getVal()=='W')
                    square.getStyleClass().add("grid-blocked");
                else
                    square.getStyleClass().add("grid-clear");

                square.setUserData(maze.getCoordinate(row, col));



                square.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    public void handle(MouseEvent event) {
                        Coordinate data = (Coordinate)square.getUserData();
                        System.out.println("last visit is "+lastVisit.toString());
                        System.out.println("current data is "+data.toString());

                        if(data.getVal()!='W' && isValidMove(lastVisit, data)) {
                            square.getStyleClass().add("grid-steps");

                            moves.add(square);
                            lastVisit.setCoordinate(data.getRow(), data.getCol(), data.getVal());
                        }
                        else {
                            Stage dialogStage = new Stage();
                            dialogStage.initModality(Modality.WINDOW_MODAL);
                            dialogStage.setScene(new Scene(VBoxBuilder.create().
                                    children(new Text("You can not move to this Block , Please visit adjacent block or Start")).
                                    alignment(Pos.CENTER).padding(new Insets(5)).build()));
                            dialogStage.show();

                        }
                        checkAndFinish();

                    }
                });

                    gridPane.add(square, col, row);

            }

        }
        squares.get(0).getChildren().add(new Label("START"));
        squares.get(squares.size()-1).getChildren().add(new Label("END"));

        for (int i = 0; i < SIZE; i++) {
            gridPane.getColumnConstraints().add(new ColumnConstraints(50));
            gridPane.getRowConstraints().add(new RowConstraints(30));
        }
        this.setCenter(gridPane);
    }

    private void checkAndFinish() {
        if(moves.size()>0){
            StackPane square = moves.get(moves.size()-1);
            Coordinate coordinate = (Coordinate)square.getUserData();
            boolean b = coordinate.getCol()==(COLS-1) && coordinate.getRow()==(ROWS-1);
            System.out.println("last move is "+coordinate.toString()+"  "+b);

            if(b){
                for(StackPane s : moves) {
                    s.getStyleClass().add("grid-done");
                }
                moves.removeAll(moves);
                messegeLabel.setText("Maze Solved");

            }
            else if(!canMove((Coordinate)moves.get(moves.size() - 1).getUserData())){
                for(StackPane s : moves) {
                    s.getStyleClass().add("grid-nopath");
                }
                moves.removeAll(moves);
                messegeLabel.setText("Maze Cannot be Solved Now");
            }

        }
    }

    private boolean canMove(Coordinate curr) {

        Coordinate []c=new Coordinate[4];
         c[0] = new Coordinate(curr.getRow()+1, curr.getCol(), ' ');
         c[1] = new Coordinate(curr.getRow(), curr.getCol()+1, ' ');
         c[2] = new Coordinate(curr.getRow()-1, curr.getCol(), ' ');
         c[3] = new Coordinate(curr.getRow(), curr.getCol()-1, ' ');
        for(int i = 0 ; i < 4 ; i++)
            if (c[i].getRow()<ROWS && c[i].getCol()<COLS &&c[i].getCol()>=0 && c[i].getRow()>=0 && maze.getCoordinateValue(c[i].getRow(),c[i].getCol())!='W' && notInMatch(c[i]))
                return true;
        return false;

    }

    private boolean notInMatch(Coordinate curr){
        for(StackPane s:moves){
            Coordinate c = (Coordinate)s.getUserData();
            if(c.getCol()==curr.getCol() && c.getRow()==curr.getRow())
                return false;
        }
        return true;
    }

    public HBox getHbox(){
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(15, 12, 15, 12));
        hbox.setSpacing(10);
        hbox.setStyle("-fx-background-color: #336699;");
        return hbox;
    }

    public boolean isValidMove(Coordinate x , Coordinate y){
        if (y.getVal()=='W')
            return false;
        else{
            if (x.getCol()==y.getCol() && (x.getRow()==(y.getRow()+1)||(x.getRow()==(y.getRow()-1))))
                return true;
            else if (x.getRow()==y.getRow() && (x.getCol()==(y.getCol()+1)||(x.getCol()==(y.getCol()-1))))
                return true;
            else
                return false;
        }
    }
}

