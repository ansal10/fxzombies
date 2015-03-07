package game;


public class Maze {
	private Coordinate[][] board;

	public Maze(int rows, int cols) {
		this.board = new Coordinate[rows][cols];
		for (int row = 0; row < rows; row++)
			for (int col = 0; col < cols; col++)
				board[row][col] = new Coordinate(row, col, ' ');
	}



    public void setBoard(char[][] boardChars){
		for (int row = 0; row < boardChars.length; row++)
			for (int col = 0; col < boardChars[0].length; col++)
				board[row][col] = new Coordinate(row, col, boardChars[row][col]);		
	}

	public Maze clone(){
		Maze copy = new Maze(getNumRows(), getNumCols());
		for(int row = 0; row < board.length; row++)
			for(int col = 0; col < board[0].length; col++)
				copy.setCoordinateValue(row, col, board[row][col].getVal());
		return copy;
	}
	
	public Coordinate getCoordinate(int row, int col) {
		return board[row][col];
	}

	public char getCoordinateValue(int row, int col) {
		return board[row][col].getVal();
	}

	public void setCoordinateValue(int row, int col, char val) {
		board[row][col].setVal(val);
	}

	public int getNumRows() {
		return board.length;
	}

	public int getNumCols() {
		return board[0].length;
	}

}
