package game;

public class Coordinate {
	private int row;
	private int col;
	private char val;
	
	public Coordinate(int row, int col, char val) {
		this.row = row;
		this.col = col;
		this.val = val;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}
	
	public char getVal() {
		return val;
	}
	
	public void setVal(char val) {
		this.val = val;
	}
	
	public Coordinate clone(){
		return new Coordinate(row, col, val);		
	}
	
	public String toString(){
		return "Row: " + row + " Col: " + col + " Val: ";		
	}
}
