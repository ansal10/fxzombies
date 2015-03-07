package game;

/**
 * Created by amd on 3/7/15.
 */
public class Box extends Coordinate {

    private int row;
    private int col;
    private char val;

    @Override
    public int getRow() {
        return row;
    }


    @Override
    public int getCol() {
        return col;
    }

    @Override
    public char getVal() {
        return val;
    }



    public Box(int row, int col, char val) {
        super(row, col, val);
        this.row = row;
        this.col = col;
        this.val = val;


    }


    public void setCoordinate(int row, int col, char val) {
        this.row = row;
        this.col = col;
        this.val = val;
    }

    public String toString(){
        return "Row: " + row + " Col: " + col + " Val: ";
    }

}
