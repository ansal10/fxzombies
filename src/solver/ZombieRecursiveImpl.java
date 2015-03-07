package solver;

import game.Coordinate;
import game.Maze;

import java.util.ArrayList;
import java.util.List;

public class ZombieRecursiveImpl implements Zombie {

	@Override
	public List<Coordinate> solve(Maze m) {
		Maze mazeCopy = m.clone();
		Coordinate start = findStart(mazeCopy);
		List<Coordinate> path = new ArrayList<Coordinate>();
		System.out.println("path:");
		path = findPath(mazeCopy, start.getRow(), start.getCol(), path);
		
		return path;
	}

	private List<Coordinate> findPath(Maze m, int row, int col,
			List<Coordinate> path) {

		if (!(isClear(m, row, col)))
			return path;
		if (m.getCoordinateValue(row, col) == 'D')
			return path;
		path.add(new Coordinate(row, col, ' '));
		
		if(m.getCoordinateValue(row,  col)=='F'){
			path.get(path.size() -1).setVal('F');
			return path;
		}

		m.setCoordinateValue(row, col, 'D');
		
		path = findPath(m, row + 1, col, path);
		if (path.get(path.size() - 1).getVal() == 'F')
			return path;

		path = findPath(m, row, col + 1, path);
		if (path.get(path.size() - 1).getVal() == 'F')
			return path;

		path = findPath(m, row - 1, col, path);
		if (path.get(path.size() - 1).getVal() == 'F')
			return path;

		path = findPath(m, row, col - 1, path);
		if (path.get(path.size() - 1).getVal() == 'F')
			return path;
		
		path.remove(path.size()-1);
		return path;
	}

	private boolean isClear(Maze m, int row, int col) {
		char currVal;

		if (isValid(m, row, col)) {
			currVal = m.getCoordinateValue(row, col);
			if (currVal == ' ' || currVal == 'F' || currVal == 'S')
				return true;
		}
		return false;
	}

	private boolean isValid(Maze m, int row, int col) {
		if (row < 0 || row >= m.getNumRows()) {
			return false;
		}
		if (col < 0 || col >= m.getNumCols()) {
			return false;
		}
		return true;
	}

	private List<Coordinate> getBogusPath(Maze m) {
		List<Coordinate> path = new ArrayList<Coordinate>();
		for (int r = 1; r < (m.getNumRows() - 1); r++)
			path.add(new Coordinate(r, 1, ' '));
		for (int c = 1; c < (m.getNumCols() - 1); c++)
			path.add(new Coordinate(m.getNumRows() - 2, c, ' '));
		path.add(new Coordinate(m.getNumRows() - 1, m.getNumCols() - 2, 'F'));
		return path;
	}

	private Coordinate findStart(Maze m) {
		Coordinate start = null;
		Coordinate curr = null;

		int row = 0;
		int col = 0;
		do {
			do {
				curr = m.getCoordinate(row, col);
				if (m.getCoordinateValue(row, col) == 'S')
					start = curr;
				col++;
			} while (start == null);
			row++;
		} while (start == null);
		System.out.println("Start " + start.getRow() + " " + start.getCol());
		return start;
	}

}
